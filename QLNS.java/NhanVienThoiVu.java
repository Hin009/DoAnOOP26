import java.time.LocalDate;
public final class NhanVienThoiVu extends NhanVien {
    private double soGioLamTheoNgay, donGiaGio;
    public NhanVienThoiVu(String maNV, String hoTen, LocalDate ngaySinh, LocalDate ngayVaoLam, String gioiTinh, String soDienThoai, String diaChi, String maCV, String maPB, double luongCoBan, String tinhTrang, double soGioLamTheoNgay, double donGiaGio) {
        super(maNV, hoTen, ngaySinh, ngayVaoLam, gioiTinh, soDienThoai, diaChi, maCV, maPB, luongCoBan, tinhTrang); setSoGioLamTheoNgay(soGioLamTheoNgay); setDonGiaGio(donGiaGio);
    }
    public double getSoGioLamTheoNgay() { return soGioLamTheoNgay; }
    public void setSoGioLamTheoNgay(double n) {
        KiemTra.so(n);
        if (n > 24) throw new IllegalArgumentException("Gio lam theo ngay toi da 24.");
        soGioLamTheoNgay = n;
    }
    public double getDonGiaGio() { return donGiaGio; }
    public void setDonGiaGio(double n) { donGiaGio = KiemTra.duong(n); }
    @Override public String getLoai() { return "TV"; }
    @Override public double tinhLuong() { return soGioLamTheoNgay * donGiaGio; }
    @Override public double tinhLuongTheoCong(double ngayCong, double gioCong, double ngayChuan) {
        return KiemTra.so(gioCong) * donGiaGio;
    }
    @Override public String toFileString() { return "TV;" + duLieuChung() + ";" + soGioLamTheoNgay + ";" + donGiaGio; }
}
