public class PhongBan {
    private String maPB;
    private String tenPB;
    private String maTruongPhong;

    public PhongBan() {
    }

    public PhongBan(String maPB, String tenPB, String maTruongPhong) {
        this.maPB = maPB;
        this.tenPB = tenPB;
        this.maTruongPhong = maTruongPhong;
    }

    public String getMaPB() {
        return maPB;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public String getTenPB() {
        return tenPB;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }

    public String getMaTruongPhong() {
        return maTruongPhong;
    }

    public void setMaTruongPhong(String maTruongPhong) {
        this.maTruongPhong = maTruongPhong;
    }
}