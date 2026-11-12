import java.time.LocalDate;

public final class PhongBan implements BanGhi {
    private final String maPB;
    private String tenPB;
    private String maTruongPhong;

    public PhongBan(String maPB, String tenPB, String maTruongPhong) {
        this.maPB = KiemTra.ma(maPB);
        setTenPB(tenPB);
        setMaTruongPhong(maTruongPhong);
    }
    public String getMaPB() { return maPB; }
    public String getTenPB() { return tenPB; }
    public void setTenPB(String tenPB) { this.tenPB = KiemTra.chuoi(tenPB); }
    public String getMaTruongPhong() { return maTruongPhong; }
    public void setMaTruongPhong(String maTruongPhong) { this.maTruongPhong = (maTruongPhong == null || maTruongPhong.isBlank() || maTruongPhong.equals("-")) ? "-" : KiemTra.ma(maTruongPhong); }
    @Override public String getMa() { return maPB; }
    @Override public String toFileString() { return KiemTra.dong(maPB, tenPB, maTruongPhong); }
    @Override public String toString() { return toFileString(); }
}
