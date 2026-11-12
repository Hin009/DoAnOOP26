import java.time.LocalDate;

public final class TrangThai implements BanGhi {
    private final String maTrangThai;
    private String tenTrangThai;
    private double heSoHuongLuong;
    private double gioMacDinh;

    public TrangThai(String maTrangThai, String tenTrangThai, double heSoHuongLuong, double gioMacDinh) {
        this.maTrangThai = KiemTra.ma(maTrangThai);
        setTenTrangThai(tenTrangThai);
        setHeSoHuongLuong(heSoHuongLuong);
        setGioMacDinh(gioMacDinh);
    }
    public String getMaTrangThai() { return maTrangThai; }
    public String getTenTrangThai() { return tenTrangThai; }
    public void setTenTrangThai(String tenTrangThai) { this.tenTrangThai = KiemTra.chuoi(tenTrangThai); }
    public double getHeSoHuongLuong() { return heSoHuongLuong; }
    public void setHeSoHuongLuong(double heSoHuongLuong) { if (heSoHuongLuong > 1) throw new IllegalArgumentException("He so huong luong tu 0 den 1."); this.heSoHuongLuong = KiemTra.so(heSoHuongLuong); }
    public double getGioMacDinh() { return gioMacDinh; }
    public void setGioMacDinh(double gioMacDinh) { if (gioMacDinh > 24) throw new IllegalArgumentException("Gio mac dinh toi da 24."); this.gioMacDinh = KiemTra.so(gioMacDinh); }
    @Override public String getMa() { return maTrangThai; }
    @Override public String toFileString() { return KiemTra.dong(maTrangThai, tenTrangThai, heSoHuongLuong, gioMacDinh); }
    @Override public String toString() { return toFileString(); }
}
