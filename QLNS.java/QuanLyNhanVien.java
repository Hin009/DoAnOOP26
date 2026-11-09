import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

public class QuanLyNhanVien implements IFileText {
    private NhanVien[] ds;
    private int n;

    public QuanLyNhanVien() {
        ds = new NhanVien[0];
        n = 0;
    }

    public int getSoLuong() {
        return n;
    }

    // 1. Tim vi tri nhan vien theo ma
    private int timViTriTheoMa(String maNV) {
        for (int i = 0; i < n; i++) {
            if (ds[i].getMaNV().equalsIgnoreCase(maNV)) {
                return i;
            }
        }
        return -1;
    }

    // 2. Them nhan vien moi (tu dong tang kich thuoc mang)
    public void themNhanVien(NhanVien nv) {
        if (nv == null) {
            throw new IllegalArgumentException("Nhan vien khong duoc null.");
        }
        if (timViTriTheoMa(nv.getMaNV()) != -1) {
            throw new IllegalArgumentException("Ma nhan vien da ton tai: " + nv.getMaNV());
        }

        // Mo rong mang them 1 phan tu
        ds = Arrays.copyOf(ds, n + 1);
        ds[n] = nv;
        n++;
    }

    // 3. Xoa nhan vien theo ma
    public boolean xoaNhanVien(String maNV) {
        int viTri = timViTriTheoMa(maNV);
        if (viTri == -1) {
            return false; // Khong tim thay
        }

        // Doi cac phan tu phia sau len truoc de lap cho trong
        for (int i = viTri; i < n - 1; i++) {
            ds[i] = ds[i + 1];
        }
        n--;
        ds = Arrays.copyOf(ds, n); // Thu gon mang
        return true;
    }

    // 4. Xuat danh sach
    public void xuatDanhSach() {
        System.out.println(
                "======================================== DANH SACH NHAN VIEN ========================================");
        System.out.printf("%-10s %-20s %-12s %-10s %-15s %-15s %-15s\n",
                "Ma NV", "Ho Ten", "Ngay Sinh", "Loai NV", "Luong CB", "He so/So gio", "Tong Luong");

        for (int i = 0; i < n; i++) {
            NhanVien nv = ds[i];
            String loaiNV = (nv instanceof NhanVienChinhThuc) ? "Chinh thuc" : "Thoi vu";
            String dacThu = "";
            if (nv instanceof NhanVienChinhThuc) {
                dacThu = "HS: " + ((NhanVienChinhThuc) nv).getHeSoLuong();
            } else if (nv instanceof NhanVienThoiVu) {
                dacThu = "Gio: " + ((NhanVienThoiVu) nv).getSoGioLamTheoNgay();
            }

            System.out.printf("%-10s %-20s %-12s %-10s %-15.0f %-15s %-15.0f\n",
                    nv.getMaNV(), nv.getHoTen(), nv.getNgaySinh(), loaiNV,
                    nv.getLuongCoBan(), dacThu, nv.tinhLuong());
        }
        System.out.println("Tong so nhan vien: " + n);
    }

    // 5. Thong ke tong quy luong
    public double tinhTongQuyLuong() {
        double tong = 0;
        for (int i = 0; i < n; i++) {
            tong += ds[i].tinhLuong();
        }
        return tong;
    }

    // ================== PHAN DOC GHI FILE ==================
    @Override
    public void ghiFile(String tenFile) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(tenFile), StandardCharsets.UTF_8)) {
            // Dong tieu de
            writer.write(
                    "LoaiNV;MaNV;HoTen;NgaySinh;NgayVaoLam;GioiTinh;SDT;DiaChi;MaCV;MaPB;LuongCB;TinhTrang;ThuocTinhRieng1;ThuocTinhRieng2");
            writer.newLine();

            for (int i = 0; i < n; i++) {
                NhanVien nv = ds[i];
                // Ghi cac thuoc tinh chung
                String chung = String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s",
                        (nv instanceof NhanVienChinhThuc) ? "CT" : "TV",
                        nv.getMaNV(), nv.getHoTen(), nv.getNgaySinh(), nv.getNgayVaoLam(),
                        nv.getGioiTinh(), nv.getSoDienThoai(), nv.getDiaChi(),
                        nv.getMaCV(), nv.getMaPB(), nv.getLuongCoBan(), nv.getTinhTrang());

                // Ghi thuoc tinh rieng
                if (nv instanceof NhanVienChinhThuc) {
                    writer.write(chung + ";" + ((NhanVienChinhThuc) nv).getHeSoLuong() + ";0");
                } else if (nv instanceof NhanVienThoiVu) {
                    writer.write(chung + ";" + ((NhanVienThoiVu) nv).getSoGioLamTheoNgay() + ";"
                            + ((NhanVienThoiVu) nv).getDonGiaGio());
                }
                writer.newLine();
            }
        }
    }

    @Override
    public void docFile(String tenFile) throws IOException {
        NhanVien[] duLieuTam = new NhanVien[0];
        int soLuongTam = 0;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(tenFile), StandardCharsets.UTF_8)) {
            String line;
            int soDong = 0;
            while ((line = reader.readLine()) != null) {
                soDong++;
                if (soDong == 1 || line.trim().isEmpty())
                    continue; // Bo qua tieu de va dong trong

                String[] p = line.split(";");
                String loaiNV = p[0];
                String maNV = p[1];
                String hoTen = p[2];
                LocalDate ngaySinh = LocalDate.parse(p[3]);
                LocalDate ngayVaoLam = LocalDate.parse(p[4]);
                String gioiTinh = p[5];
                String sdt = p[6];
                String diaChi = p[7];
                String maCV = p[8];
                String maPB = p[9];
                double luongCB = Double.parseDouble(p[10]);
                String tinhTrang = p[11];

                NhanVien nv = null;
                if (loaiNV.equals("CT")) {
                    double heSo = Double.parseDouble(p[12]);
                    nv = new NhanVienChinhThuc(maNV, hoTen, ngaySinh, ngayVaoLam, gioiTinh, sdt, diaChi, maCV, maPB,
                            luongCB, tinhTrang, heSo);
                } else if (loaiNV.equals("TV")) {
                    double soGio = Double.parseDouble(p[12]);
                    double donGia = Double.parseDouble(p[13]);
                    nv = new NhanVienThoiVu(maNV, hoTen, ngaySinh, ngayVaoLam, gioiTinh, sdt, diaChi, maCV, maPB,
                            luongCB, tinhTrang, soGio, donGia);
                }

                if (nv != null) {
                    duLieuTam = Arrays.copyOf(duLieuTam, soLuongTam + 1);
                    duLieuTam[soLuongTam] = nv;
                    soLuongTam++;
                }
            }
        }
        this.ds = duLieuTam;
        this.n = soLuongTam;
    }
}