public class ChucVu {
    private String maCV;
    private String tenCV;
    private double tienPhuCap;

    public ChucVu() {
    }

    public ChucVu(String maCV, String tenCV, double tienPhuCap) {
        this.maCV = maCV;
        this.tenCV = tenCV;
        this.tienPhuCap = tienPhuCap;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public double getTienPhuCap() {
        return tienPhuCap;
    }

    public void setTienPhuCap(double tienPhuCap) {
        this.tienPhuCap = tienPhuCap;
    }
}