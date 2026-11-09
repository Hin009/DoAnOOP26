import java.time.LocalDate;

public class NhanVienChinhThuc extends NhanVien {
    private double heSoLuong;

    public NhanVienChinhThuc(String maNV, String hoTen, LocalDate ngaySinh, LocalDate ngayVaoLam,
            String gioiTinh, String soDienThoai, String diaChi,
            String maCV, String maPB, double luongCoBan, String tinhTrang,
            double heSoLuong) {
        super(maNV, hoTen, ngaySinh, ngayVaoLam, gioiTinh, soDienThoai, diaChi, maCV, maPB, luongCoBan, tinhTrang);
        this.heSoLuong = heSoLuong;
    }

    public double getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(double heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    @Override
    public double tinhLuong() {
        return getLuongCoBan() * heSoLuong;
    }
}