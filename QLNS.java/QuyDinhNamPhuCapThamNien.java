import java.time.LocalDate;

public final class QuyDinhNamPhuCapThamNien implements BanGhi {
    private final String maQDThamNien;
    private int tuNam;
    private int denNam;
    private double soTienPhuCap;

    public QuyDinhNamPhuCapThamNien(String maQDThamNien, int tuNam, int denNam, double soTienPhuCap) {
        this.maQDThamNien = KiemTra.ma(maQDThamNien);
        setTuNam(tuNam);
        setDenNam(denNam);
        setSoTienPhuCap(soTienPhuCap);
    }
    public String getMaQDThamNien() { return maQDThamNien; }
    public int getTuNam() { return tuNam; }
    public void setTuNam(int tuNam) { this.tuNam = KiemTra.nguyen(tuNam); }
    public int getDenNam() { return denNam; }
    public void setDenNam(int denNam) { this.denNam = KiemTra.nguyen(denNam); }
    public double getSoTienPhuCap() { return soTienPhuCap; }
    public void setSoTienPhuCap(double soTienPhuCap) { this.soTienPhuCap = KiemTra.so(soTienPhuCap); }
    @Override public String getMa() { return maQDThamNien; }
    @Override public String toFileString() { return KiemTra.dong(maQDThamNien, tuNam, denNam, soTienPhuCap); }
    @Override public String toString() { return toFileString(); }
}
