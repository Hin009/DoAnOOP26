import java.time.LocalDate;

public abstract class NhanVien implements TinhLuong, BanGhi {
    private final String maNV;
    private String hoTen, gioiTinh, soDienThoai, diaChi, maCV, maPB, tinhTrang;
    private LocalDate ngaySinh, ngayVaoLam;
    private double luongCoBan;
    public NhanVien(String maNV, String hoTen, LocalDate ngaySinh, LocalDate ngayVaoLam,
            String gioiTinh, String soDienThoai, String diaChi, String maCV, String maPB,
            double luongCoBan, String tinhTrang) {
        this.maNV = KiemTra.ma(maNV);
        setHoTen(hoTen); setNgaySinh(ngaySinh); setNgayVaoLam(ngayVaoLam);
        setGioiTinh(gioiTinh); setSoDienThoai(soDienThoai); setDiaChi(diaChi);
        setMaCV(maCV); setMaPB(maPB); setLuongCoBan(luongCoBan); setTinhTrang(tinhTrang);
    }
    @Override public String getMa() { return maNV; }
    public String getMaNV() { return maNV; }
    public String getHoTen() { return hoTen; }
    public final void setHoTen(String s) { hoTen = KiemTra.chuoi(s); }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public final void setNgaySinh(LocalDate d) {
        KiemTra.ngay(d);
        if (ngayVaoLam != null && !d.isBefore(ngayVaoLam)) throw new IllegalArgumentException("Ngay sinh phai truoc ngay vao lam.");
        ngaySinh = d;
    }
    public LocalDate getNgayVaoLam() { return ngayVaoLam; }
    public final void setNgayVaoLam(LocalDate d) {
        KiemTra.ngay(d);
        if (ngaySinh != null && !d.isAfter(ngaySinh)) throw new IllegalArgumentException("Ngay vao lam phai sau ngay sinh.");
        ngayVaoLam = d;
    }
    public String getGioiTinh() { return gioiTinh; }
    public final void setGioiTinh(String s) { gioiTinh = KiemTra.chuoi(s); }
    public String getSoDienThoai() { return soDienThoai; }
    public final void setSoDienThoai(String s) { soDienThoai = KiemTra.soDienThoai(s); }
    public String getDiaChi() { return diaChi; }
    public final void setDiaChi(String s) { diaChi = KiemTra.chuoi(s); }
    public String getMaCV() { return maCV; }
    public final void setMaCV(String s) { maCV = KiemTra.ma(s); }
    public String getMaPB() { return maPB; }
    public final void setMaPB(String s) { maPB = KiemTra.ma(s); }
    public double getLuongCoBan() { return luongCoBan; }
    public final void setLuongCoBan(double n) { luongCoBan = KiemTra.so(n); }
    public String getTinhTrang() { return tinhTrang; }
    public final void setTinhTrang(String s) {
        s = KiemTra.chuoi(s);
        if (!s.equals("DANG_LAM") && !s.equals("NGHI_VIEC")) throw new IllegalArgumentException("Tinh trang: DANG_LAM / NGHI_VIEC.");
        tinhTrang = s;
    }
    protected String duLieuChung() {
        return KiemTra.dong(maNV, hoTen, ngaySinh, ngayVaoLam, gioiTinh, soDienThoai, diaChi, maCV, maPB, luongCoBan, tinhTrang);
    }
    /** Luong co so; bang luong thang su dung phien ban co tham so ben duoi. */
    @Override public abstract double tinhLuong();
    public abstract double tinhLuongTheoCong(double ngayCong, double gioCong, double ngayChuan);
    public abstract String getLoai();
    @Override public String toString() { return toFileString(); }
}
