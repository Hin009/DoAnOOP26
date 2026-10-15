public class NhanVienHopDong extends NhanVien {
    private int soGioLam;

    public NhanVienHopDong(String ten, double luongCoBan, int soGioLam) {
        super(ten, luongCoBan);
        this.soGioLam = soGioLam;
    }

    @Override
    public double tinhLuong() { return luongCoBan * soGioLam; }

    @Override
    public String toFileLine() {
        return "HOPDONG;" + ma + ";" + ten + ";" + luongCoBan + ";" + soGioLam;
    }
}