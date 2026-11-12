import java.time.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Chi dung nextLine de tranh loi troi lenh sau khi nhap so. */
public class NhapLieu {
    private final Scanner sc;
    public NhapLieu(Scanner sc) { this.sc = sc; }
    public String chuoi(String label) {
        System.out.print(label + ": ");
        if (!sc.hasNextLine()) throw new NoSuchElementException("Het dau vao.");
        String s = sc.nextLine().trim();
        if (s.equalsIgnoreCase("/huy"))
            throw new java.util.concurrent.CancellationException("Da huy thao tac.");
        return KiemTra.chuoi(s);
    }
    public String ma(String label) { return KiemTra.ma(chuoi(label)); }
    public int soNguyen(String label) { return Integer.parseInt(chuoi(label)); }
    public double so(String label) { return KiemTra.so(Double.parseDouble(chuoi(label))); }
    public String soDienThoai() {
        while (true) {
            try { return KiemTra.soDienThoai(chuoi("So dien thoai (10 hoac 11 chu so)")); }
            catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        }
    }
    public double tien(String label) {
        while (true) {
            try {
                double value = KiemTra.docTien(chuoi(label + " (VND)"));
                String hienThi = java.text.NumberFormat.getIntegerInstance(java.util.Locale.forLanguageTag("vi-VN")).format(value);
                System.out.println("Da nhap: " + hienThi + " VND");
                return value;
            } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        }
    }
    public LocalDate ngay(String label) {
        java.time.format.DateTimeFormatter dinhDang = java.time.format.DateTimeFormatter
            .ofPattern("d/M/uuuu").withResolverStyle(java.time.format.ResolverStyle.STRICT);
        while (true) {
            try {
                String s = chuoi(label + " (dd/MM/yyyy; go huy hoac /huy de quay lai)");
                if (s.equalsIgnoreCase("huy") || s.equalsIgnoreCase("hủy") || s.equalsIgnoreCase("huy de huy"))
                    throw new java.util.concurrent.CancellationException("Da huy thao tac.");
                // Van nhan dinh dang cu de tuong thich voi cach nhap truoc day.
                if (s.matches("\\d{4}-\\d{2}-\\d{2}")) return LocalDate.parse(s);
                s = s.replace('-', '/');
                if (!s.matches("\\d{1,2}/\\d{1,2}/\\d{4}"))
                    throw new IllegalArgumentException("Sai dinh dang ngay.");
                return LocalDate.parse(s, dinhDang);
            } catch (DateTimeException | IllegalArgumentException e) {
                System.out.println("Ngay khong hop le. Nhap lai dd/MM/yyyy, hoac go huy de quay ve menu chinh.");
            }
        }
    }
    public LocalDate ngayChamCong() {
        while (true) {
            LocalDate d = ngay("Ngay cham cong");
            LocalDate homNay = LocalDate.now(ZoneId.of("Asia/Bangkok"));
            if (!d.isAfter(homNay)) return d;
            String gioiHan = homNay.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("Ngay cham cong khong duoc sau " + gioiHan + ". Nhap lai hoac /huy.");
        }
    }

    public LocalTime gio(String label) {
        String s = chuoi(label + " (HH:mm, '-' neu khong co)");
        return s.equals("-") ? null : LocalTime.parse(s);
    }
    public YearMonth ky() { return YearMonth.parse(chuoi("Ky luong (yyyy-MM)")); }
}
