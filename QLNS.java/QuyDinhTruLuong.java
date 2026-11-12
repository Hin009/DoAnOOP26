import java.time.LocalDate;

public final class QuyDinhTruLuong implements BanGhi {
    private final String maQDTL;
    private String tenQDTL;
    private String dieuKienApDung;
    private double mucTru;
    private String donViTinh;

    public QuyDinhTruLuong(String maQDTL, String tenQDTL, String dieuKienApDung, double mucTru, String donViTinh) {
        this.maQDTL = KiemTra.ma(maQDTL);
        setTenQDTL(tenQDTL);
        setDieuKienApDung(dieuKienApDung);
        setMucTru(mucTru);
        setDonViTinh(donViTinh);
    }
    public String getMaQDTL() { return maQDTL; }
    public String getTenQDTL() { return tenQDTL; }
    public void setTenQDTL(String tenQDTL) { this.tenQDTL = KiemTra.chuoi(tenQDTL); }
    public String getDieuKienApDung() { return dieuKienApDung; }
    public void setDieuKienApDung(String dieuKienApDung) { this.dieuKienApDung = KiemTra.chuoi(dieuKienApDung); }
    public double getMucTru() { return mucTru; }
    public void setMucTru(double mucTru) { this.mucTru = KiemTra.so(mucTru); }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { if (!donViTinh.equals("LAN") && !donViTinh.equals("GIO")) throw new IllegalArgumentException("Don vi: LAN hoac GIO."); this.donViTinh = KiemTra.chuoi(donViTinh); }
    @Override public String getMa() { return maQDTL; }
    @Override public String toFileString() { return KiemTra.dong(maQDTL, tenQDTL, dieuKienApDung, mucTru, donViTinh); }
    @Override public String toString() { return toFileString(); }
}
