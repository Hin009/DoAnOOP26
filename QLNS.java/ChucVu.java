import java.time.LocalDate;

public final class ChucVu implements BanGhi {
    private final String maCV;
    private String tenCV;
    private double tienPhuCap;

    public ChucVu(String maCV, String tenCV, double tienPhuCap) {
        this.maCV = KiemTra.ma(maCV);
        setTenCV(tenCV);
        setTienPhuCap(tienPhuCap);
    }
    public String getMaCV() { return maCV; }
    public String getTenCV() { return tenCV; }
    public void setTenCV(String tenCV) { this.tenCV = KiemTra.chuoi(tenCV); }
    public double getTienPhuCap() { return tienPhuCap; }
    public void setTienPhuCap(double tienPhuCap) { this.tienPhuCap = KiemTra.so(tienPhuCap); }
    @Override public String getMa() { return maCV; }
    @Override public String toFileString() { return KiemTra.dong(maCV, tenCV, tienPhuCap); }
    @Override public String toString() { return toFileString(); }
}
