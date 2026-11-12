import java.time.LocalDate;
import java.util.Objects;

public final class KiemTra {
    private KiemTra() { }
    public static String chuoi(String s) {
        if (s == null || s.isBlank() || s.contains(";") || s.contains("\n") || s.contains("\r"))
            throw new IllegalArgumentException("Chuoi khong duoc rong hoac chua ; / xuong dong.");
        return s.trim();
    }
    public static String ma(String s) {
        s = chuoi(s).toUpperCase(java.util.Locale.ROOT);
        if (!s.matches("[A-Z0-9_-]+")) throw new IllegalArgumentException("Ma chi gom A-Z, 0-9, _ va -.");
        return s;
    }
    public static double so(double n) {
        if (!Double.isFinite(n) || n < 0 || n > 1e12)
            throw new IllegalArgumentException("So phai huu han, tu 0 den 1.000.000.000.000.");
        return n;
    }
    public static String soDienThoai(String s) {
        s = chuoi(s);
        if (!s.matches("[0-9]{10,11}"))
            throw new IllegalArgumentException("So dien thoai phai gom dung 10 hoac 11 chu so.");
        return s;
    }
    /** Tien VND nguyen, nhan dau cham hoac phay tach nhom 3 chu so. */
    public static double docTien(String s) {
        s = chuoi(s);
        if (!s.matches("(?:[0-9]+|[0-9]{1,3}(?:\\.[0-9]{3})+|[0-9]{1,3}(?:,[0-9]{3})+)"))
            throw new IllegalArgumentException("Tien VND phai la so nguyen; dau cham/phay chi dung tach hang nghin.");
        return so(Double.parseDouble(s.replace(".", "").replace(",", "")));
    }
    public static double duong(double n) {
        so(n);
        if (n == 0) throw new IllegalArgumentException("Gia tri phai lon hon 0.");
        return n;
    }
    public static int nguyen(int n) {
        if (n < 0) throw new IllegalArgumentException("So nguyen khong duoc am.");
        return n;
    }
    public static LocalDate ngay(LocalDate d) { return Objects.requireNonNull(d, "Thieu ngay."); }
    public static String dong(Object... fields) {
        return java.util.Arrays.stream(fields).map(String::valueOf).collect(java.util.stream.Collectors.joining(";"));
    }
    public static double tien(double n) {
        if (!Double.isFinite(n) || Math.abs(n) > 9e15) throw new IllegalArgumentException("So tien vuot gioi han.");
        return Math.round(n);
    }
}
