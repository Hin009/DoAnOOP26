import java.time.LocalDate;
public final class NhanVienChinhThuc extends NhanVien {
    private double heSoLuong;
    public NhanVienChinhThuc(String maNV, String hoTen, LocalDate ngaySinh, LocalDate ngayVaoLam, String gioiTinh, String soDienThoai, String diaChi, String maCV, String maPB, double luongCoBan, String tinhTrang, double heSoLuong) {
        super(maNV, hoTen, ngaySinh, ngayVaoLam, gioiTinh, soDienThoai, diaChi, maCV, maPB, luongCoBan, tinhTrang); setHeSoLuong(heSoLuong);
    }
    public double getHeSoLuong() { return heSoLuong; }
    public void setHeSoLuong(double n) { heSoLuong = KiemTra.duong(n); }
    @Override public String getLoai() { return "CT"; }
    @Override public double tinhLuong() { return getLuongCoBan() * heSoLuong; }
    @Override public double tinhLuongTheoCong(double ngayCong, double gioCong, double ngayChuan) {
        return tinhLuong() / KiemTra.duong(ngayChuan) * KiemTra.so(ngayCong);
    }
    @Override public String toFileString() { return "CT;" + duLieuChung() + ";" + heSoLuong + ";0"; }
}
