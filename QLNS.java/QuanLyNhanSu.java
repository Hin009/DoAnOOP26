import java.io.IOException;
import java.nio.file.Path;
import java.time.*;
import java.util.*;

/** Dieu phoi nghiep vu va kiem tra lien ket giua cac danh sach theo UML. */
public class QuanLyNhanSu implements IFileText {
    public static final int NGAY_CONG_TIEU_CHUAN = 26;
    private DanhSachNhanVien dsNhanVien;
    private DanhSachPhongBan dsPhongBan;
    private DanhSachChucVu dsChucVu;
    private DanhSachChamCong dsChamCong;
    private DanhSachTrangThai dsTrangThai;
    private DanhSachQuyDinhThamNien dsQDThamNien;
    private DanhSachThuong dsThuong;
    private DanhSachChiTietThuong dsCTThuong;
    private DanhSachQuyDinhTruLuong dsQDTL;
    private DanhSachChiTietTruLuong dsCTTruLuong;
    private DanhSachBangLuong dsBangLuong;
    private FileStore fileStore;
    public QuanLyNhanSu() { this("data/qlns.txt"); }
    public QuanLyNhanSu(String file) {
        dsNhanVien = new DanhSachNhanVien(); dsPhongBan = new DanhSachPhongBan();
        dsChucVu = new DanhSachChucVu(); dsChamCong = new DanhSachChamCong();
        dsTrangThai = new DanhSachTrangThai(); dsQDThamNien = new DanhSachQuyDinhThamNien();
        dsThuong = new DanhSachThuong(); dsCTThuong = new DanhSachChiTietThuong();
        dsQDTL = new DanhSachQuyDinhTruLuong(); dsCTTruLuong = new DanhSachChiTietTruLuong();
        dsBangLuong = new DanhSachBangLuong(); fileStore = new FileStore(file);
    }
    public DanhSachNhanVien getDsNhanVien() { return dsNhanVien; }
    public DanhSachPhongBan getDsPhongBan() { return dsPhongBan; }
    public DanhSachChucVu getDsChucVu() { return dsChucVu; }
    public DanhSachChamCong getDsChamCong() { return dsChamCong; }
    public DanhSachTrangThai getDsTrangThai() { return dsTrangThai; }
    public DanhSachQuyDinhThamNien getDsQuyDinhThamNien() { return dsQDThamNien; }
    public DanhSachThuong getDsThuong() { return dsThuong; }
    public DanhSachChiTietThuong getDsChiTietThuong() { return dsCTThuong; }
    public DanhSachQuyDinhTruLuong getDsQuyDinhTruLuong() { return dsQDTL; }
    public DanhSachChiTietTruLuong getDsChiTietTruLuong() { return dsCTTruLuong; }
    public DanhSachBangLuong getDsBangLuong() { return dsBangLuong; }
    public FileStore getFileStore() { return fileStore; }
    public List<DanhSach<? extends BanGhi>> cacDanhSach() {
        return List.of(dsPhongBan, dsChucVu, dsNhanVien, dsTrangThai, dsChamCong,
            dsQDThamNien, dsThuong, dsCTThuong, dsQDTL, dsCTTruLuong, dsBangLuong);
    }
    private static <T extends BanGhi> T canCo(DanhSach<T> ds, String ma) {
        T x = ds.timTheoMa(ma);
        if (x == null) throw new IllegalArgumentException("Khong ton tai ma lien ket: " + ma);
        return x;
    }
    /** Thao tac chi duoc giu lai neu toan bo lien ket van hop le. */
    public <T extends BanGhi> void luuBanGhi(DanhSach<T> ds, T x, boolean sua) {
        T cu = ds.timTheoMa(x.getMa());
        if (sua) ds.sua(x.getMa(), x); else ds.them(x);
        try { kiemTraToanVen(); }
        catch (RuntimeException e) {
            if (sua) ds.sua(x.getMa(), cu); else ds.xoa(x.getMa());
            throw e;
        }
    }
    public <T extends BanGhi> void xoaBanGhi(DanhSach<T> ds, String ma) {
        T cu = canCo(ds, ma); ds.xoa(ma);
        try { kiemTraToanVen(); }
        catch (RuntimeException e) { ds.them(cu); throw new IllegalArgumentException("Khong the xoa vi con du lieu lien ket. " + e.getMessage()); }
    }
    public void kiemTraToanVen() {
        for (NhanVien nv : dsNhanVien) {
            canCo(dsPhongBan, nv.getMaPB()); canCo(dsChucVu, nv.getMaCV());
            if (!nv.getNgaySinh().isBefore(nv.getNgayVaoLam())) throw new IllegalArgumentException("Ngay sinh/vao lam sai.");
        }
        for (PhongBan pb : dsPhongBan) if (!pb.getMaTruongPhong().equals("-")) {
            NhanVien truong = canCo(dsNhanVien, pb.getMaTruongPhong());
            if (!truong.getMaPB().equals(pb.getMaPB())) throw new IllegalArgumentException("Truong phong phai thuoc phong " + pb.getMaPB());
        }
        Set<String> ngayNV = new HashSet<>();
        for (ChamCong cc : dsChamCong) {
            NhanVien nv = canCo(dsNhanVien, cc.getMaNhanVien());
            TrangThai tt = canCo(dsTrangThai, cc.getTrangThai());
            if (cc.getNgay().isBefore(nv.getNgayVaoLam())) throw new IllegalArgumentException("Cham cong truoc ngay vao lam: " + cc.getMa());
            if (!ngayNV.add(nv.getMa()+"/"+cc.getNgay())) throw new IllegalArgumentException("Moi NV chi co mot ban ghi cham cong/ngay.");
            if (cc.getGioVao() == null && cc.getGioRa() != null) throw new IllegalArgumentException("Co gio ra nhung thieu gio vao.");
            if ((tt.getGioMacDinh() > 0 || tt.getHeSoHuongLuong() == 0) && (cc.getGioVao() != null || cc.getGioRa() != null))
                throw new IllegalArgumentException("Trang thai nghi/vang phai de gio vao va ra la '-'.");
        }
        for (QuyDinhNamPhuCapThamNien a : dsQDThamNien) {
            if (a.getTuNam() > a.getDenNam()) throw new IllegalArgumentException("Khoang tham nien bi nguoc.");
            for (QuyDinhNamPhuCapThamNien b : dsQDThamNien)
                if (a != b && a.getTuNam() <= b.getDenNam() && b.getTuNam() <= a.getDenNam())
                    throw new IllegalArgumentException("Khoang tham nien chong nhau: " + a.getMa() + "/" + b.getMa());
        }
        for (ChiTietThuong ct : dsCTThuong) {
            NhanVien nv = canCo(dsNhanVien, ct.getMaNV()); canCo(dsThuong, ct.getMaThuong());
            if (ct.getNgayThuong().isBefore(nv.getNgayVaoLam())) throw new IllegalArgumentException("Thuong truoc ngay vao lam.");
        }
        for (QuyDinhTruLuong qd : dsQDTL) if (!qd.getDieuKienApDung().equals("CHUNG")) canCo(dsTrangThai, qd.getDieuKienApDung());
        for (ChiTietTruLuong ct : dsCTTruLuong) {
            NhanVien nv = canCo(dsNhanVien, ct.getMaNV()); canCo(dsQDTL, ct.getMaQDTL());
            if (ct.getNgayTru().isBefore(nv.getNgayVaoLam())) throw new IllegalArgumentException("Khau tru truoc ngay vao lam.");
        }
        Set<String> kyNV = new HashSet<>();
        for (BangLuong bl : dsBangLuong) {
            canCo(dsNhanVien, bl.getMaNV()); YearMonth.of(bl.getNam(), bl.getThang());
            if (!kyNV.add(bl.getMaNV()+"/"+bl.getNam()+"/"+bl.getThang())) throw new IllegalArgumentException("Trung bang luong cung ky/NV.");
            if (bl.getNgayCongTieuChuan() <= 0 || bl.getNgayCongTieuChuan() > 31) throw new IllegalArgumentException("Ngay cong chuan: 1..31.");
            double expected = KiemTra.tien(bl.getLuongTheoCong()+bl.getPhuCapChucVu()+bl.getPhuCapThamNien()+bl.getTongThuong()-bl.getTongKhauTru());
            if (Math.abs(expected - bl.getLuongThucNhan()) > 0.01) throw new IllegalArgumentException("Sai tong luong thuc nhan: " + bl.getMa());
        }
    }
    public double gioHuongLuong(ChamCong cc) {
        TrangThai tt = canCo(dsTrangThai, cc.getTrangThai());
        return cc.tinhSoGioHuongLuong(tt);
    }
    public double phuCapThamNien(NhanVien nv, LocalDate ngay) {
        int nam = Math.max(0, Period.between(nv.getNgayVaoLam(), ngay).getYears());
        for (QuyDinhNamPhuCapThamNien q : dsQDThamNien)
            if (nam >= q.getTuNam() && nam <= q.getDenNam()) return q.getSoTienPhuCap();
        return 0;
    }
    /** So tien chi tiet la ban chup, doi quy dinh khong doi khoan da ghi. */
    public void ghiThuong(String ma, String nv, String qd, LocalDate ngay, String lyDo) {
        Thuong t = canCo(dsThuong, qd);
        luuBanGhi(dsCTThuong, new ChiTietThuong(ma,nv,qd,ngay,t.getSoTien(),lyDo), false);
    }
    public void ghiKhauTru(String ma, String nv, String qd, LocalDate ngay, double soLuong, String lyDo) {
        QuyDinhTruLuong q = canCo(dsQDTL, qd); KiemTra.duong(soLuong);
        if (q.getDonViTinh().equals("LAN") && soLuong != Math.floor(soLuong)) throw new IllegalArgumentException("So lan phai nguyen.");
        if (!q.getDieuKienApDung().equals("CHUNG")) {
            boolean khop = false;
            for (ChamCong cc : dsChamCong) if (cc.getMaNhanVien().equalsIgnoreCase(nv) && cc.getNgay().equals(ngay)
                && cc.getTrangThai().equals(q.getDieuKienApDung())) khop = true;
            if (!khop) throw new IllegalArgumentException("Khong co cham cong khop dieu kien " + q.getDieuKienApDung());
        }
        luuBanGhi(dsCTTruLuong, new ChiTietTruLuong(ma,nv,qd,ngay,KiemTra.tien(q.getMucTru()*soLuong),lyDo), false);
    }
    public void lapBangLuong(YearMonth ky, double ngayChuan) {
        KiemTra.duong(ngayChuan);
        if (ngayChuan > 31) throw new IllegalArgumentException("Ngay cong chuan khong qua 31.");
        kiemTraToanVen();
        DanhSachBangLuong moi = new DanhSachBangLuong();
        for (BangLuong bl : dsBangLuong) if (bl.getThang() != ky.getMonthValue() || bl.getNam() != ky.getYear()) moi.them(bl);
        for (NhanVien nv : dsNhanVien) {
            if (nv.getNgayVaoLam().isAfter(ky.atEndOfMonth())) continue;
            double gio=0, cong=0, thuong=0, tru=0;
            boolean coDuLieu = false;
            for (ChamCong cc : dsChamCong) if (cc.getMaNhanVien().equalsIgnoreCase(nv.getMa()) && YearMonth.from(cc.getNgay()).equals(ky)) {
                double g = gioHuongLuong(cc); gio += g; cong += Math.min(1, g / ChamCong.SO_GIO_MOT_CONG); coDuLieu = true;
            }
            for (ChiTietThuong ct : dsCTThuong) if (ct.getMaNV().equals(nv.getMa()) && YearMonth.from(ct.getNgayThuong()).equals(ky)) {
                thuong += ct.getSoTien(); coDuLieu = true;
            }
            for (ChiTietTruLuong ct : dsCTTruLuong) if (ct.getMaNV().equals(nv.getMa()) && YearMonth.from(ct.getNgayTru()).equals(ky)) {
                tru += ct.getSoTien(); coDuLieu = true;
            }
            if (nv.getTinhTrang().equals("NGHI_VIEC") && !coDuLieu) continue;
            double luong = KiemTra.tien(nv.tinhLuongTheoCong(cong, gio, ngayChuan)); // Da hinh runtime.
            double pcCV = gio > 0 ? canCo(dsChucVu,nv.getMaCV()).getTienPhuCap() : 0;
            double pcTN = gio > 0 ? phuCapThamNien(nv,ky.atEndOfMonth()) : 0;
            double hs = nv instanceof NhanVienChinhThuc ? ((NhanVienChinhThuc)nv).getHeSoLuong() : 0;
            double gia = nv instanceof NhanVienThoiVu ? ((NhanVienThoiVu)nv).getDonGiaGio() : 0;
            moi.them(new BangLuong("BL_"+ky+"_"+nv.getMa(),nv.getMa(),ky.getMonthValue(),ky.getYear(),nv.getLuongCoBan(),hs,gia,
                ngayChuan,cong,gio,luong,pcCV,pcTN,thuong,tru,KiemTra.tien(luong+pcCV+pcTN+thuong-tru),LocalDate.now(),
                nv.getLoai().equals("CT") ? "LCB * heSo / ngayChuan * ngayCong + PC + thuong - tru" : "gioHuongLuong * donGia + PC + thuong - tru"));
        }
        dsBangLuong = moi;
    }
    public List<BangLuong> bangLuongThang(YearMonth ky) {
        List<BangLuong> result = new ArrayList<>();
        for (BangLuong b : dsBangLuong) if (b.getThang() == ky.getMonthValue() && b.getNam() == ky.getYear()) result.add(b);
        result.sort(Comparator.comparingDouble(BangLuong::getLuongThucNhan).reversed());
        return result;
    }
    public void xuatBaoCao(YearMonth ky, String file) throws IOException {
        if (bangLuongThang(ky).isEmpty()) throw new IllegalArgumentException("Chua co bang luong ky nay.");
        FileStore.ghiNguyenTu(Path.of(file), noiDungBaoCao(ky));
    }
    /** Dung chung cho bao cao text va terminal de giu cung mot cach trinh bay. */
    public List<String> noiDungBaoCao(YearMonth ky) {
        List<BangLuong> list = bangLuongThang(ky);
        List<String> lines = new ArrayList<>();
        lines.add("BANG LUONG THANG " + ky + " (VND)");
        lines.add("Sap xep theo thuc nhan giam dan. Don vi tien: VND.");
        if (list.isEmpty()) {
            lines.add("Chua co bang luong trong thang nay.");
            return lines;
        }
        java.text.NumberFormat tien = java.text.NumberFormat.getIntegerInstance(Locale.forLanguageTag("vi-VN"));
        java.text.NumberFormat cong = java.text.NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"));
        cong.setMaximumFractionDigits(4);
        List<String[]> nhanVien = new ArrayList<>(), phuCap = new ArrayList<>(), thucNhan = new ArrayList<>();
        double tong = 0;
        for (BangLuong b : list) {
            nhanVien.add(new String[] { b.getMaNV(), canCo(dsNhanVien,b.getMaNV()).getHoTen(), cong.format(b.getSoNgayCongHuongLuong()), cong.format(b.getSoGioHuongLuong()) });
            phuCap.add(new String[] { b.getMaNV(), tien.format(b.getLuongTheoCong()), tien.format(b.getPhuCapChucVu()), tien.format(b.getPhuCapThamNien()) });
            thucNhan.add(new String[] { b.getMaNV(), tien.format(b.getTongThuong()), tien.format(b.getTongKhauTru()), tien.format(b.getLuongThucNhan()) });
            tong += b.getLuongThucNhan();
        }
        lines.add(""); lines.add("1. NHAN VIEN VA CHAM CONG");
        themBangBaoCao(lines, new String[] { "Ma NV", "Ho va ten", "Ngay cong", "Gio huong luong" }, nhanVien, 2);
        lines.add(""); lines.add("2. LUONG VA PHU CAP (VND)");
        themBangBaoCao(lines, new String[] { "Ma NV", "Luong theo cong", "PC chuc vu", "PC tham nien" }, phuCap, 1);
        lines.add(""); lines.add("3. THUONG, KHAU TRU VA THUC NHAN (VND)");
        themBangBaoCao(lines, new String[] { "Ma NV", "Thuong", "Khau tru", "Thuc nhan" }, thucNhan, 1);
        lines.add("So phieu: " + list.size());
        lines.add("TONG THUC NHAN: " + tien.format(tong) + " VND");
        return lines;
    }
    private static void themBangBaoCao(List<String> lines, String[] cot, List<String[]> rows, int cotSoDauTien) {
        int[] rong = Arrays.stream(cot).mapToInt(String::length).toArray();
        for (int i = 0; i < cot.length; i++)
            for (String[] row : rows) rong[i] = Math.max(rong[i], row[i].length());
        StringBuilder vien = new StringBuilder("+");
        for (int r : rong) vien.append("-".repeat(r + 2)).append('+');
        lines.add(vien.toString());
        lines.add(dongBaoCao(cot, rong, cot.length));
        lines.add(vien.toString());
        for (String[] row : rows) lines.add(dongBaoCao(row, rong, cotSoDauTien));
        lines.add(vien.toString());
    }
    private static String dongBaoCao(String[] cells, int[] rong, int cotSoDauTien) {
        StringBuilder line = new StringBuilder("|");
        for (int i = 0; i < cells.length; i++) {
            String pad = " ".repeat(rong[i] - cells[i].length());
            line.append(' ').append(i >= cotSoDauTien ? pad + cells[i] : cells[i] + pad).append(" |");
        }
        return line.toString();
    }
    @Override public void ghiFile(String file) throws IOException { new FileStore(file).ghi(this); }
    @Override public void docFile(String file) throws IOException {
        QuanLyNhanSu q = new FileStore(file).doc(); // Chi thay the sau khi doc + kiem tra thanh cong.
        dsNhanVien=q.dsNhanVien; dsPhongBan=q.dsPhongBan; dsChucVu=q.dsChucVu; dsChamCong=q.dsChamCong;
        dsTrangThai=q.dsTrangThai; dsQDThamNien=q.dsQDThamNien; dsThuong=q.dsThuong; dsCTThuong=q.dsCTThuong;
        dsQDTL=q.dsQDTL; dsCTTruLuong=q.dsCTTruLuong; dsBangLuong=q.dsBangLuong; fileStore=q.fileStore;
    }
    public void luu() throws IOException { fileStore.ghi(this); }
    public void taoDuLieuMau() {
        if (cacDanhSach().stream().anyMatch(ds -> ds.getSoLuong()>0)) throw new IllegalArgumentException("Chi tao mau khi tat ca danh sach rong.");
        dsPhongBan.them(new PhongBan("PB01","Nhan su","-")); dsPhongBan.them(new PhongBan("PB02","Ky thuat","-"));
        dsChucVu.them(new ChucVu("CV01","Nhan vien",300000)); dsChucVu.them(new ChucVu("CV02","Truong phong",1000000));
        dsNhanVien.them(new NhanVienChinhThuc("NV01","Nguyen Van An",LocalDate.of(1990,1,1),LocalDate.of(2020,1,1),"Nam","0901234567","TP HCM","CV02","PB01",5200000,"DANG_LAM",2));
        dsNhanVien.them(new NhanVienThoiVu("NV02","Tran Thi Binh",LocalDate.of(2000,2,2),LocalDate.of(2025,1,1),"Nu","0912345678","Ha Noi","CV01","PB02",0,"DANG_LAM",8,30000));
        dsPhongBan.timTheoMa("PB01").setMaTruongPhong("NV01");
        dsTrangThai.them(new TrangThai("LV","Lam viec",1,0)); dsTrangThai.them(new TrangThai("MUON","Di muon",1,0));
        dsTrangThai.them(new TrangThai("VANG","Nghi khong luong",0,0)); dsTrangThai.them(new TrangThai("PHEP","Nghi phep huong luong",1,8));
        dsQDThamNien.them(new QuyDinhNamPhuCapThamNien("TN01",1,4,200000)); dsQDThamNien.them(new QuyDinhNamPhuCapThamNien("TN02",5,99,500000));
        dsThuong.them(new Thuong("TH01","Hoan thanh cong viec",500000,"Duoc quan ly phe duyet"));
        dsQDTL.them(new QuyDinhTruLuong("TR01","Di muon","MUON",50000,"LAN")); dsQDTL.them(new QuyDinhTruLuong("TR02","Khau tru khac","CHUNG",100000,"LAN"));
        for (int day=1; day<=26; day++) {
            LocalDate d=LocalDate.of(2026,11,day);
            dsChamCong.them(new ChamCong("CC_NV01_"+day,"NV01",d,LocalTime.of(8,0),LocalTime.of(16,0),"LV"));
            dsChamCong.them(new ChamCong("CC_NV02_"+day,"NV02",d,LocalTime.of(8,day==2?30:0),LocalTime.of(16,0),day==2?"MUON":"LV"));
        }
        ghiThuong("CT01","NV01","TH01",LocalDate.of(2026,11,10),"Hoan thanh du an");
        ghiKhauTru("TRCT01","NV02","TR01",LocalDate.of(2026,11,2),1,"Di muon 30 phut");
        lapBangLuong(YearMonth.of(2026,11),NGAY_CONG_TIEU_CHUAN);
    }
}
