import java.time.LocalDate;

public final class ChiTietTruLuong implements BanGhi {
    private final String maCTTru;
    private String maNV;
    private String maQDTL;
    private LocalDate ngayTru;
    private double soTien;
    private String lyDo;

    public ChiTietTruLuong(String maCTTru, String maNV, String maQDTL, LocalDate ngayTru, double soTien, String lyDo) {
        this.maCTTru = KiemTra.ma(maCTTru);
        setMaNV(maNV);
        setMaQDTL(maQDTL);
        setNgayTru(ngayTru);
        setSoTien(soTien);
        setLyDo(lyDo);
    }
    public String getMaCTTru() { return maCTTru; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = KiemTra.ma(maNV); }
    public String getMaQDTL() { return maQDTL; }
    public void setMaQDTL(String maQDTL) { this.maQDTL = KiemTra.ma(maQDTL); }
    public LocalDate getNgayTru() { return ngayTru; }
    public void setNgayTru(LocalDate ngayTru) { this.ngayTru = KiemTra.ngay(ngayTru); }
    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = KiemTra.so(soTien); }
    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = KiemTra.chuoi(lyDo); }
    @Override public String getMa() { return maCTTru; }
    @Override public String toFileString() { return KiemTra.dong(maCTTru, maNV, maQDTL, ngayTru, soTien, lyDo); }
    @Override public String toString() { return toFileString(); }
}
