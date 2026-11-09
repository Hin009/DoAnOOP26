import java.time.LocalDate;

public abstract class NhanVien implements TinhLuong {
    private String maNV;
    private String hoTen;
    private LocalDate ngaySinh;
    private LocalDate ngayVaoLam;
    private String gioiTinh;
    private String soDienThoai;
    private String diaChi;
    private String maCV;
    private String maPB;
    private double luongCoBan;
    private String tinhTrang;

    public NhanVien(String maNV, String hoTen, LocalDate ngaySinh, LocalDate ngayVaoLam,
            String gioiTinh, String soDienThoai, String diaChi,
            String maCV, String maPB, double luongCoBan, String tinhTrang) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.ngayVaoLam = ngayVaoLam;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.maCV = maCV;
        this.maPB = maPB;
        this.luongCoBan = luongCoBan;
        this.tinhTrang = tinhTrang;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public LocalDate getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(LocalDate ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getMaPB() {
        return maPB;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return maNV + ";" + hoTen + ";" + ngaySinh + ";" + ngayVaoLam + ";" + gioiTinh + ";"
                + soDienThoai + ";" + diaChi + ";" + maCV + ";" + maPB + ";" + luongCoBan + ";" + tinhTrang;
    }
}