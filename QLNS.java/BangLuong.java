import java.time.LocalDate;

public final class BangLuong implements BanGhi {
    private final String maBL;
    private String maNV;
    private int thang;
    private int nam;
    private double luongCoBanApDung;
    private double heSoLuong;
    private double donGiaGio;
    private double ngayCongTieuChuan;
    private double soNgayCongHuongLuong;
    private double soGioHuongLuong;
    private double luongTheoCong;
    private double phuCapChucVu;
    private double phuCapThamNien;
    private double tongThuong;
    private double tongKhauTru;
    private double luongThucNhan;
    private LocalDate ngayLap;
    private String congThucTinh;

    public BangLuong(String maBL, String maNV, int thang, int nam, double luongCoBanApDung, double heSoLuong, double donGiaGio, double ngayCongTieuChuan, double soNgayCongHuongLuong, double soGioHuongLuong, double luongTheoCong, double phuCapChucVu, double phuCapThamNien, double tongThuong, double tongKhauTru, double luongThucNhan, LocalDate ngayLap, String congThucTinh) {
        this.maBL = KiemTra.ma(maBL);
        setMaNV(maNV);
        setThang(thang);
        setNam(nam);
        setLuongCoBanApDung(luongCoBanApDung);
        setHeSoLuong(heSoLuong);
        setDonGiaGio(donGiaGio);
        setNgayCongTieuChuan(ngayCongTieuChuan);
        setSoNgayCongHuongLuong(soNgayCongHuongLuong);
        setSoGioHuongLuong(soGioHuongLuong);
        setLuongTheoCong(luongTheoCong);
        setPhuCapChucVu(phuCapChucVu);
        setPhuCapThamNien(phuCapThamNien);
        setTongThuong(tongThuong);
        setTongKhauTru(tongKhauTru);
        setLuongThucNhan(luongThucNhan);
        setNgayLap(ngayLap);
        setCongThucTinh(congThucTinh);
    }
    public String getMaBL() { return maBL; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = KiemTra.ma(maNV); }
    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = KiemTra.nguyen(thang); }
    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = KiemTra.nguyen(nam); }
    public double getLuongCoBanApDung() { return luongCoBanApDung; }
    public void setLuongCoBanApDung(double luongCoBanApDung) { this.luongCoBanApDung = KiemTra.so(luongCoBanApDung); }
    public double getHeSoLuong() { return heSoLuong; }
    public void setHeSoLuong(double heSoLuong) { this.heSoLuong = KiemTra.so(heSoLuong); }
    public double getDonGiaGio() { return donGiaGio; }
    public void setDonGiaGio(double donGiaGio) { this.donGiaGio = KiemTra.so(donGiaGio); }
    public double getNgayCongTieuChuan() { return ngayCongTieuChuan; }
    public void setNgayCongTieuChuan(double ngayCongTieuChuan) { this.ngayCongTieuChuan = KiemTra.so(ngayCongTieuChuan); }
    public double getSoNgayCongHuongLuong() { return soNgayCongHuongLuong; }
    public void setSoNgayCongHuongLuong(double soNgayCongHuongLuong) { this.soNgayCongHuongLuong = KiemTra.so(soNgayCongHuongLuong); }
    public double getSoGioHuongLuong() { return soGioHuongLuong; }
    public void setSoGioHuongLuong(double soGioHuongLuong) { this.soGioHuongLuong = KiemTra.so(soGioHuongLuong); }
    public double getLuongTheoCong() { return luongTheoCong; }
    public void setLuongTheoCong(double luongTheoCong) { this.luongTheoCong = KiemTra.so(luongTheoCong); }
    public double getPhuCapChucVu() { return phuCapChucVu; }
    public void setPhuCapChucVu(double phuCapChucVu) { this.phuCapChucVu = KiemTra.so(phuCapChucVu); }
    public double getPhuCapThamNien() { return phuCapThamNien; }
    public void setPhuCapThamNien(double phuCapThamNien) { this.phuCapThamNien = KiemTra.so(phuCapThamNien); }
    public double getTongThuong() { return tongThuong; }
    public void setTongThuong(double tongThuong) { this.tongThuong = KiemTra.so(tongThuong); }
    public double getTongKhauTru() { return tongKhauTru; }
    public void setTongKhauTru(double tongKhauTru) { this.tongKhauTru = KiemTra.so(tongKhauTru); }
    public double getLuongThucNhan() { return luongThucNhan; }
    public void setLuongThucNhan(double luongThucNhan) { this.luongThucNhan = KiemTra.tien(luongThucNhan); }
    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = KiemTra.ngay(ngayLap); }
    public String getCongThucTinh() { return congThucTinh; }
    public void setCongThucTinh(String congThucTinh) { this.congThucTinh = KiemTra.chuoi(congThucTinh); }
    @Override public String getMa() { return maBL; }
    @Override public String toFileString() { return KiemTra.dong(maBL, maNV, thang, nam, luongCoBanApDung, heSoLuong, donGiaGio, ngayCongTieuChuan, soNgayCongHuongLuong, soGioHuongLuong, luongTheoCong, phuCapChucVu, phuCapThamNien, tongThuong, tongKhauTru, luongThucNhan, ngayLap, congThucTinh); }
    @Override public String toString() { return toFileString(); }
}
