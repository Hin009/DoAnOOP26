public class NhanVienChinhThuc extends NhanVien {
    private double heSoLuong;

    public NhanVienChinhThuc(String ten, double luongCoBan, double heSoLuong) {
        super(ten, luongCoBan);
        this.heSoLuong = heSoLuong;
    }

    @Override
    public double tinhLuong() { return luongCoBan * heSoLuong; }

        @Override
    public String toFileLine() {
        return "CHINHTHUC;" + ma + ";" + ten + ";" + luongCoBan + ";" + heSoLuong;
    }
}