public abstract class NhanVien implements ILuong, IGhiFile {
    private static int dem = 0;           // static: đếm số nhân viên đã tạo

    protected String ma;
    protected String ten;
    protected double luongCoBan;

    public NhanVien() { ma = taoMa(); }

    public NhanVien(String ten, double luongCoBan) {
        this.ma = taoMa();
        this.ten = ten;
        this.luongCoBan = luongCoBan;
    }

    private static String taoMa() {       // static method
        dem++;
        return String.format("NV%03d", dem);
    }

    public static int getSoLuong() { return dem; }

    public abstract double tinhLuong();   // abstract: con bắt buộc tự cài
}