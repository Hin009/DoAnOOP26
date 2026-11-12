import java.time.LocalDate;

public final class ChiTietThuong implements BanGhi {
    private final String maCTThuong;
    private String maNV;
    private String maThuong;
    private LocalDate ngayThuong;
    private double soTien;
    private String lyDo;

    public ChiTietThuong(String maCTThuong, String maNV, String maThuong, LocalDate ngayThuong, double soTien, String lyDo) {
        this.maCTThuong = KiemTra.ma(maCTThuong);
        setMaNV(maNV);
        setMaThuong(maThuong);
        setNgayThuong(ngayThuong);
        setSoTien(soTien);
        setLyDo(lyDo);
    }
    public String getMaCTThuong() { return maCTThuong; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = KiemTra.ma(maNV); }
    public String getMaThuong() { return maThuong; }
    public void setMaThuong(String maThuong) { this.maThuong = KiemTra.ma(maThuong); }
    public LocalDate getNgayThuong() { return ngayThuong; }
    public void setNgayThuong(LocalDate ngayThuong) { this.ngayThuong = KiemTra.ngay(ngayThuong); }
    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = KiemTra.so(soTien); }
    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = KiemTra.chuoi(lyDo); }
    @Override public String getMa() { return maCTThuong; }
    @Override public String toFileString() { return KiemTra.dong(maCTThuong, maNV, maThuong, ngayThuong, soTien, lyDo); }
    @Override public String toString() { return toFileString(); }
}
