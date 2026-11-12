import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Quan ly danh sach ChamCong bang mang doi tuong.
 * Lop nay thuc thi IFileText de doc/ghi du lieu len tep text.
 */
public final class BangChamCong implements IFileText {
    private ChamCong[] ds;
    private int n;

    public BangChamCong() {
        ds = new ChamCong[0];
        n = 0;
    }

    public BangChamCong(ChamCong[] danhSach) {
        this();
        if (danhSach != null) {
            for (ChamCong x : danhSach) {
                if (x != null) {
                    them(x);
                }
            }
        }
    }

    public int getSoLuong() {
        return n;
    }

    /** Nhap n ban ghi dau tien tu ban phim. Ngay: yyyy-MM-dd; gio: HH:mm; dung '-' neu chua cham cong. */
    public void nhapDanhSachBanDau(Scanner sc) {
        System.out.print("Nhap so luong ban ghi cham cong: ");
        int soLuong = Integer.parseInt(sc.nextLine().trim());
        if (soLuong < 0) {
            throw new IllegalArgumentException("So luong khong duoc am.");
        }

        ds = new ChamCong[0];
        n = 0;
        for (int i = 0; i < soLuong; i++) {
            System.out.println("\nNhap ban ghi thu " + (i + 1) + ":");
            them(nhapMotBanGhi(sc));
        }
    }

    private ChamCong nhapMotBanGhi(Scanner sc) {
        System.out.print("Ma cham cong: ");
        String maCC = sc.nextLine().trim();
        System.out.print("Ma nhan vien: ");
        String maNV = sc.nextLine().trim();
        System.out.print("Ngay (yyyy-MM-dd): ");
        LocalDate ngay = LocalDate.parse(sc.nextLine().trim());
        System.out.print("Gio vao (HH:mm, hoac '-' neu vang): ");
        String vaoText = sc.nextLine().trim();
        System.out.print("Gio ra (HH:mm, hoac '-' neu chua co): ");
        String raText = sc.nextLine().trim();
        System.out.print("Trang thai: ");
        String trangThai = sc.nextLine().trim();

        LocalTime gioVao = vaoText.equals("-") ? null : LocalTime.parse(vaoText);
        LocalTime gioRa = raText.equals("-") ? null : LocalTime.parse(raText);
        return new ChamCong(maCC, maNV, ngay, gioVao, gioRa, trangThai);
    }

    /** Them mot ban ghi; maChamCong phai duy nhat trong danh sach. */
    public void them(ChamCong x) {
        if (x == null) {
            throw new IllegalArgumentException("Ban ghi cham cong khong duoc null.");
        }
        if (timViTriTheoMa(x.getMaChamCong()) != -1) {
            throw new IllegalArgumentException("Ma cham cong da ton tai: " + x.getMaChamCong());
        }
        ds = Arrays.copyOf(ds, n + 1);
        ds[n] = x;
        n++;
    }

    /** Them nhieu ban ghi. */
    public void themNhieu(ChamCong[] danhSachMoi) {
        if (danhSachMoi == null) {
            return;
        }
        for (ChamCong x : danhSachMoi) {
            them(x);
        }
    }

    /** Tim vi tri theo ma cham cong; tra ve -1 neu khong tim thay. */
    private int timViTriTheoMa(String maChamCong) {
        for (int i = 0; i < n; i++) {
            if (ds[i].getMaChamCong().equalsIgnoreCase(maChamCong)) {
                return i;
            }
        }
        return -1;
    }

    public ChamCong timTheoMaChamCong(String maChamCong) {
        int viTri = timViTriTheoMa(maChamCong);
        return (viTri == -1) ? null : ds[viTri];
    }

    /** Sua mot ban ghi dua tren ma cham cong cu. */
    public boolean suaTheoMa(String maCu, ChamCong banGhiMoi) {
        if (banGhiMoi == null) {
            throw new IllegalArgumentException("Ban ghi moi khong duoc null.");
        }
        int viTri = timViTriTheoMa(maCu);
        if (viTri == -1) {
            return false;
        }

        int viTriMaMoi = timViTriTheoMa(banGhiMoi.getMaChamCong());
        if (viTriMaMoi != -1 && viTriMaMoi != viTri) {
            throw new IllegalArgumentException("Ma cham cong moi da duoc su dung.");
        }

        ds[viTri] = banGhiMoi;
        return true;
    }

    /** Xoa ban ghi theo ma cham cong. */
    public boolean xoaTheoMa(String maChamCong) {
        int viTri = timViTriTheoMa(maChamCong);
        if (viTri == -1) {
            return false;
        }

        for (int i = viTri; i < n - 1; i++) {
            ds[i] = ds[i + 1];
        }
        n--;
        ds = Arrays.copyOf(ds, n);
        return true;
    }

    /** Tim gan dung theo ma cham cong, ma nhan vien hoac trang thai. */
    public ChamCong[] timGanDung(String tuKhoa) {
        if (tuKhoa == null) {
            tuKhoa = "";
        }
        String key = tuKhoa.trim().toLowerCase();
        ChamCong[] ketQua = new ChamCong[0];

        for (int i = 0; i < n; i++) {
            ChamCong x = ds[i];
            boolean khop = x.getMaChamCong().toLowerCase().contains(key)
                    || x.getMaNhanVien().toLowerCase().contains(key)
                    || x.getTrangThai().toLowerCase().contains(key);
            if (khop) {
                ketQua = Arrays.copyOf(ketQua, ketQua.length + 1);
                ketQua[ketQua.length - 1] = x;
            }
        }
        return ketQua;
    }

    /** Tim cac ban ghi theo ma nhan vien chinh xac. */
    public ChamCong[] timTheoMaNhanVien(String maNhanVien) {
        ChamCong[] ketQua = new ChamCong[0];
        for (int i = 0; i < n; i++) {
            if (ds[i].getMaNhanVien().equalsIgnoreCase(maNhanVien)) {
                ketQua = Arrays.copyOf(ketQua, ketQua.length + 1);
                ketQua[ketQua.length - 1] = ds[i];
            }
        }
        return ketQua;
    }

    /** Thong ke tong gio lam cua mot nhan vien. */
    public double tinhTongGioTheoNhanVien(String maNhanVien) {
        double tong = 0;
        for (int i = 0; i < n; i++) {
            if (ds[i].getMaNhanVien().equalsIgnoreCase(maNhanVien)) {
                tong += ds[i].tinhSoGioLam();
            }
        }
        return tong;
    }

    /** Thong ke tong cong quy doi cua mot nhan vien. */
    public double tinhTongCongTheoNhanVien(String maNhanVien) {
        return tinhTongGioTheoNhanVien(maNhanVien) / ChamCong.SO_GIO_MOT_CONG;
    }

    /** Dem so ban ghi cua mot ngay cu the. */
    public int demTheoNgay(LocalDate ngay) {
        int dem = 0;
        for (int i = 0; i < n; i++) {
            if (ds[i].getNgay().equals(ngay)) {
                dem++;
            }
        }
        return dem;
    }

    /** Sap xep theo ngay, sau do theo ma nhan vien (Comparable trong lop ChamCong). */
    public void sapXepTheoNgay() {
        Arrays.sort(ds, 0, n);
    }

    public void xuatDanhSach() {
        System.out.println("MA-CHAM-CONG MA-NHAN-VIEN NGAY         GIO-VAO  GIO-RA   TRANG-THAI          GIO-LAM  CONG");
        for (int i = 0; i < n; i++) {
            System.out.println(ds[i]);
        }
        System.out.println("Tong so ban ghi: " + n);
    }

    /** Ghi toan bo danh sach vao tep text UTF-8. */
    @Override
    public void ghiFile(String tenFile) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(tenFile), StandardCharsets.UTF_8)) {
            writer.write("maChamCong;maNhanVien;ngay;gioVao;gioRa;trangThai");
            writer.newLine();
            for (int i = 0; i < n; i++) {
                writer.write(ds[i].toFileString());
                writer.newLine();
            }
        }
    }

    /** Doc danh sach tu tep text; neu tep sai dinh dang thi bao loi kem so dong. */
    @Override
    public void docFile(String tenFile) throws IOException {
        ChamCong[] duLieuTam = new ChamCong[0];
        int soLuongTam = 0;

        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(tenFile), StandardCharsets.UTF_8)) {
            String line;
            int soDong = 0;

            while ((line = reader.readLine()) != null) {
                soDong++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (soDong == 1 && line.startsWith("maChamCong;")) {
                    continue; // bo qua dong tieu de
                }

                try {
                    ChamCong x = ChamCong.fromFileString(line);
                    for (int i = 0; i < soLuongTam; i++) {
                        if (duLieuTam[i].getMaChamCong().equalsIgnoreCase(x.getMaChamCong())) {
                            throw new IllegalArgumentException("Ma cham cong bi trung: " + x.getMaChamCong());
                        }
                    }
                    duLieuTam = Arrays.copyOf(duLieuTam, soLuongTam + 1);
                    duLieuTam[soLuongTam] = x;
                    soLuongTam++;
                } catch (RuntimeException ex) {
                    throw new IOException("Du lieu khong hop le tai dong " + soDong + ": " + ex.getMessage(), ex);
                }
            }
        }

        ds = duLieuTam;
        n = soLuongTam;
    }
}
