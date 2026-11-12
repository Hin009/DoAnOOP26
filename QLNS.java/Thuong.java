import java.time.LocalDate;

public final class Thuong implements BanGhi {
    private final String maThuong;
    private String tenThuong;
    private double soTien;
    private String dieuKien;

    public Thuong(String maThuong, String tenThuong, double soTien, String dieuKien) {
        this.maThuong = KiemTra.ma(maThuong);
        setTenThuong(tenThuong);
        setSoTien(soTien);
        setDieuKien(dieuKien);
    }
    public String getMaThuong() { return maThuong; }
    public String getTenThuong() { return tenThuong; }
    public void setTenThuong(String tenThuong) { this.tenThuong = KiemTra.chuoi(tenThuong); }
    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = KiemTra.so(soTien); }
    public String getDieuKien() { return dieuKien; }
    public void setDieuKien(String dieuKien) { this.dieuKien = KiemTra.chuoi(dieuKien); }
    @Override public String getMa() { return maThuong; }
    @Override public String toFileString() { return KiemTra.dong(maThuong, tenThuong, soTien, dieuKien); }
    @Override public String toString() { return toFileString(); }
}
