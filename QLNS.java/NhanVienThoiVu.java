import java.time.LocalDate;

public class NhanVienThoiVu extends NhanVien {
    private double soGioLamTheoNgay;
    private double donGiaGio;

    public NhanVienThoiVu(String maNV, String hoTen, LocalDate ngaySinh, LocalDate ngayVaoLam,
            String gioiTinh, String soDienThoai, String diaChi,
            String maCV, String maPB, double luongCoBan, String tinhTrang,
            double soGioLamTheoNgay, double donGiaGio) {
        super(maNV, hoTen, ngaySinh, ngayVaoLam, gioiTinh, soDienThoai, diaChi, maCV, maPB, luongCoBan, tinhTrang);
        this.soGioLamTheoNgay = soGioLamTheoNgay;
        this.donGiaGio = donGiaGio;
    }

    public double getSoGioLamTheoNgay() {
        return soGioLamTheoNgay;
    }

    public void setSoGioLamTheoNgay(double soGioLamTheoNgay) {
        this.soGioLamTheoNgay = soGioLamTheoNgay;
    }

    public double getDonGiaGio() {
        return donGiaGio;
    }

    public void setDonGiaGio(double donGiaGio) {
        this.donGiaGio = donGiaGio;
    }

    @Override
    public double tinhLuong() {
        return soGioLamTheoNgay * donGiaGio;
    }
}