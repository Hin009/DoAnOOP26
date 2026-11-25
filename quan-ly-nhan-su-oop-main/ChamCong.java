import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Mot ban ghi cham cong cua mot nhan vien trong mot ngay/ca lam viec.
 * maNhanVien la khoa lien ket voi lop NhanVien cua nhom.
 */
public class ChamCong implements Comparable<ChamCong> {
    public static final double SO_GIO_MOT_CONG = 8.0;

    private String maChamCong;
    private String maNhanVien;
    private LocalDate ngay;
    private LocalTime gioVao;
    private LocalTime gioRa; // null neu chua cham cong ra
    private String trangThai;

    /** Constructor mac dinh, dung khi can tao doi tuong rong truoc khi gan du lieu. */
    public ChamCong() {
        maChamCong = "";
        maNhanVien = "";
        ngay = LocalDate.now();
        gioVao = null;
        gioRa = null;
        trangThai = "Chua cham cong";
    }

    public ChamCong(String maChamCong, String maNhanVien,
                    LocalDate ngay, LocalTime gioVao,
                    LocalTime gioRa, String trangThai) {
        setMaChamCong(maChamCong);
        setMaNhanVien(maNhanVien);
        setNgay(ngay);
        setGioVao(gioVao);
        setGioRa(gioRa);
        setTrangThai(trangThai);
    }

    public String getMaChamCong() {
        return maChamCong;
    }

    public void setMaChamCong(String maChamCong) {
        if (maChamCong == null || maChamCong.trim().isEmpty()
                || maChamCong.contains(";") || maChamCong.contains("\n")) {
            throw new IllegalArgumentException("Ma cham cong khong duoc de trong va khong duoc chua dau ';'.");
        }
        this.maChamCong = maChamCong.trim();
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.trim().isEmpty()
                || maNhanVien.contains(";") || maNhanVien.contains("\n")) {
            throw new IllegalArgumentException("Ma nhan vien khong duoc de trong va khong duoc chua dau ';'.");
        }
        this.maNhanVien = maNhanVien.trim();
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        if (ngay == null) {
            throw new IllegalArgumentException("Ngay cham cong khong duoc null.");
        }
        this.ngay = ngay;
    }

    public LocalTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalTime gioVao) {
        this.gioVao = gioVao;
    }

    public LocalTime getGioRa() {
        return gioRa;
    }

    public void setGioRa(LocalTime gioRa) {
        this.gioRa = gioRa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        if (trangThai == null || trangThai.trim().isEmpty()
                || trangThai.contains(";") || trangThai.contains("\n") || trangThai.contains("\r")) {
            throw new IllegalArgumentException("Trang thai khong hop le.");
        }
        this.trangThai = trangThai.trim();
    }

    /**
     * Tinh so gio lam. Neu gio ra som hon gio vao thi xem la ca qua dem.
     * Neu chua co du gio vao/ra thi tra ve 0.
     */
    public double tinhSoGioLam() {
        if (gioVao == null || gioRa == null) {
            return 0.0;
        }

        LocalDateTime thoiDiemVao = ngay.atTime(gioVao);
        LocalDateTime thoiDiemRa = ngay.atTime(gioRa);
        if (thoiDiemRa.isBefore(thoiDiemVao)) {
            thoiDiemRa = thoiDiemRa.plusDays(1);
        }

        long soPhut = Duration.between(thoiDiemVao, thoiDiemRa).toMinutes();
        return soPhut / 60.0;
    }

    /** Quy doi gio lam thanh cong, voi 8 gio = 1 cong. */
    public double tinhSoCong() {
        return tinhSoGioLam() / SO_GIO_MOT_CONG;
    }

    /** Dinh dang mot ban ghi de luu vao tep text, cac truong cach nhau bang ';'. */
    public String toFileString() {
        String vao = (gioVao == null) ? "-" : gioVao.toString();
        String ra = (gioRa == null) ? "-" : gioRa.toString();
        return maChamCong + ";" + maNhanVien + ";" + ngay + ";"
                + vao + ";" + ra + ";" + trangThai;
    }

    /** Tao mot doi tuong ChamCong tu mot dong trong tep text. */
    public static ChamCong fromFileString(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length != 6) {
            throw new IllegalArgumentException("Dong du lieu phai co dung 6 truong.");
        }

        LocalDate ngay = LocalDate.parse(parts[2]);
        LocalTime gioVao = parts[3].equals("-") ? null : LocalTime.parse(parts[3]);
        LocalTime gioRa = parts[4].equals("-") ? null : LocalTime.parse(parts[4]);

        return new ChamCong(parts[0], parts[1], ngay, gioVao, gioRa, parts[5]);
    }

    /** Sap xep theo ngay; neu cung ngay thi sap xep theo ma nhan vien. */
    @Override
    public int compareTo(ChamCong other) {
        int ketQuaNgay = this.ngay.compareTo(other.ngay);
        if (ketQuaNgay != 0) {
            return ketQuaNgay;
        }
        return this.maNhanVien.compareToIgnoreCase(other.maNhanVien);
    }

    @Override
    public String toString() {
        String vao = (gioVao == null) ? "--:--" : gioVao.toString();
        String ra = (gioRa == null) ? "--:--" : gioRa.toString();
        return String.format("%-10s %-10s %-12s %-8s %-8s %-18s %6.2f %6.2f",
                maChamCong, maNhanVien, ngay, vao, ra, trangThai,
                tinhSoGioLam(), tinhSoCong());
    }
}
