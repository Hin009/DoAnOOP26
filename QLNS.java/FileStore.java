import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.util.*;

/** Moi lan luu tao file tam, sau do thay file dich; khong ghi de tung phan. */
public class FileStore {
    private final Path duongDan;
    private final String dinhDang;
    public static final String PHIEN_BAN = "QLNS_TEXT_V1";
    public FileStore(String duongDan) { this.duongDan = Path.of(duongDan); this.dinhDang = "UTF-8; semicolon"; }
    public Path getDuongDan() { return duongDan; }
    public String getDinhDang() { return dinhDang; }
    public void ghi(QuanLyNhanSu ql) throws IOException {
        ql.kiemTraToanVen();
        ql.getDsChamCong().chuanHoaMaCu();
        java.text.NumberFormat tien = java.text.NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"));
        tien.setMaximumFractionDigits(340);
        List<String> lines = new ArrayList<>(); lines.add(PHIEN_BAN);
        for (DanhSach<? extends BanGhi> ds : ql.cacDanhSach()) {
            Map<String, List<String[]>> nhom = new LinkedHashMap<>();
            for (BanGhi x : ds) {
                String loai = x.getClass().getSimpleName();
                String[] row = (loai + ";" + x.toFileString()).split(";", -1);
                for (int c : cotTien(loai)) row[c] = tien.format(Double.parseDouble(row[c]));
                nhom.computeIfAbsent(loai, k -> new ArrayList<>())
                    .add(row);
            }
            for (var entry : nhom.entrySet()) {
                lines.add("");
                lines.add("# === " + entry.getKey() + " ===");
                String[] cot = tenCot(entry.getKey()).split(";", -1);
                List<String[]> rows = entry.getValue();
                int[] rong = new int[cot.length];
                for (int i = 0; i < cot.length; i++) rong[i] = cot[i].length();
                for (String[] row : rows)
                    for (int i = 0; i < row.length; i++) rong[i] = Math.max(rong[i], row[i].length());
                lines.add("# " + canCot(cot, rong));
                for (String[] row : rows) lines.add("  " + canCot(row, rong));
            }
        }
        ghiNguyenTu(duongDan, lines);
    }
    private static String canCot(String[] row, int[] rong) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (i > 0) out.append(" ; ");
            out.append(row[i]);
            if (i < row.length - 1) out.append(" ".repeat(rong[i] - row[i].length()));
        }
        return out.toString();
    }
    private static String tenCot(String loai) {
        switch (loai) {
            case "PhongBan": return "Loai;Ma PB;Ten phong;Truong phong";
            case "ChucVu": return "Loai;Ma CV;Ten chuc vu;Phu cap";
            case "NhanVienChinhThuc":
            case "NhanVienThoiVu": return "Loai;CT/TV;Ma NV;Ho ten;Ngay sinh;Ngay vao lam;Gioi tinh;Dien thoai;Dia chi;Ma CV;Ma PB;Luong co ban;Tinh trang;" + (loai.equals("NhanVienChinhThuc") ? "He so luong;Du phong" : "Gio/ngay;Don gia/gio");
            case "TrangThai": return "Loai;Ma TT;Ten trang thai;He so;Gio mac dinh";
            case "ChamCong": return "Loai;Ma CC;Ma NV;Ngay;Gio vao;Gio ra;Ma TT";
            case "QuyDinhNamPhuCapThamNien": return "Loai;Ma QD;Tu nam;Den nam;Phu cap";
            case "Thuong": return "Loai;Ma thuong;Ten thuong;So tien;Dieu kien";
            case "ChiTietThuong": return "Loai;Ma CT;Ma NV;Ma thuong;Ngay;So tien;Ghi chu";
            case "QuyDinhTruLuong": return "Loai;Ma QD;Ten quy dinh;Dieu kien;So tien;Don vi";
            case "ChiTietTruLuong": return "Loai;Ma CT;Ma NV;Ma QD;Ngay;So tien;Ghi chu";
            case "BangLuong": return "Loai;Ma BL;Ma NV;Thang;Nam;Luong co ban;He so;Don gia/gio;Ngay chuan;Ngay cong;Gio huong luong;Luong theo cong;PC chuc vu;PC tham nien;Thuong;Khau tru;Thuc nhan;Ngay lap;Cong thuc";
            default: throw new IllegalArgumentException("Loai ban ghi khong ho tro: " + loai);
        }
    }
    /** Chi dinh dang cot tien, giu nguyen ma, dien thoai, he so va so gio. */
    private static int[] cotTien(String loai) {
        switch (loai) {
            case "NhanVienChinhThuc": return new int[] {11};
            case "NhanVienThoiVu": return new int[] {11, 14};
            case "ChucVu": case "Thuong": return new int[] {3};
            case "QuyDinhNamPhuCapThamNien": case "QuyDinhTruLuong": return new int[] {4};
            case "ChiTietThuong": case "ChiTietTruLuong": return new int[] {5};
            case "BangLuong": return new int[] {5, 7, 11, 12, 13, 14, 15, 16};
            default: return new int[0];
        }
    }
    /** Doc ca tien phan nhom kieu Viet va so Java cua file cu (10000000.0, 1.0E7). */
    private static double docTienFile(String s) {
        s = s.strip();
        if (s.matches("-?[0-9]{1,3}(?:\\.[0-9]{3})+(?:,[0-9]+)?") || s.matches("-?[0-9]+,[0-9]+"))
            s = s.replace(".", "").replace(',', '.');
        return Double.parseDouble(s);
    }
    public QuanLyNhanSu doc() throws IOException {
        List<String> lines = Files.readAllLines(duongDan, StandardCharsets.UTF_8);
        if (lines.isEmpty() || !lines.get(0).equals(PHIEN_BAN)) throw new IOException("Sai dinh dang/phien ban file.");
        QuanLyNhanSu tam = new QuanLyNhanSu(duongDan.toString());
        for (int i=1; i<lines.size(); i++) {
            if (lines.get(i).isBlank() || lines.get(i).stripLeading().startsWith("#")) continue;
            try { themDong(tam, lines.get(i)); }
            catch (RuntimeException e) { throw new IOException("Dong " +(i+1)+": "+ e.getMessage(), e); }
        }
        try { tam.kiemTraToanVen(); }
        catch (RuntimeException e) { throw new IOException("Lien ket du lieu sai: "+e.getMessage(), e); }
        return tam;
    }
    public static void ghiNguyenTu(Path path, List<String> lines) throws IOException {
        Path absolute = path.toAbsolutePath();
        Files.createDirectories(absolute.getParent());
        Path tmp = Files.createTempFile(absolute.getParent(), ".qlns-", ".tmp");
        try {
            Files.write(tmp, lines, StandardCharsets.UTF_8);
            try { Files.move(tmp, absolute, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE); }
            catch (AtomicMoveNotSupportedException e) { Files.move(tmp, absolute, StandardCopyOption.REPLACE_EXISTING); }
        } finally { Files.deleteIfExists(tmp); }
    }
    private static void soTruong(String[] p, int n) {
        if (p.length != n) throw new IllegalArgumentException("Sai so truong: can "+n+", co "+p.length);
    }
    /** Dinh dang nhan vien cu: CT/TV;ma;...;thamSo1;thamSo2. */
    public static NhanVien docNhanVien(String line) {
        String[] p = Arrays.stream(line.split(";", -1)).map(String::strip).toArray(String[]::new);
        soTruong(p,14);
        if (p[0].equals("CT")) return new NhanVienChinhThuc(p[1],p[2],LocalDate.parse(p[3]),LocalDate.parse(p[4]),p[5],p[6],p[7],p[8],p[9],docTienFile(p[10]),p[11],Double.parseDouble(p[12]));
        if (p[0].equals("TV")) return new NhanVienThoiVu(p[1],p[2],LocalDate.parse(p[3]),LocalDate.parse(p[4]),p[5],p[6],p[7],p[8],p[9],docTienFile(p[10]),p[11],Double.parseDouble(p[12]),docTienFile(p[13]));
        throw new IllegalArgumentException("Loai nhan vien phai la CT hoac TV.");
    }
    private void themDong(QuanLyNhanSu q, String line) {
        String[] p = Arrays.stream(line.split(";", -1)).map(String::strip).toArray(String[]::new);
        for (int c : cotTien(p[0])) if (c < p.length) p[c] = Double.toString(docTienFile(p[c]));
        line = String.join(";", p);
        switch (p[0]) {
            case "PhongBan": soTruong(p, 4); q.getDsPhongBan().them(new PhongBan(p[1], p[2], p[3])); break;
            case "ChucVu": soTruong(p, 4); q.getDsChucVu().them(new ChucVu(p[1], p[2], Double.parseDouble(p[3]))); break;
            case "TrangThai": soTruong(p, 5); q.getDsTrangThai().them(new TrangThai(p[1], p[2], Double.parseDouble(p[3]), Double.parseDouble(p[4]))); break;
            case "QuyDinhNamPhuCapThamNien": soTruong(p, 5); q.getDsQuyDinhThamNien().them(new QuyDinhNamPhuCapThamNien(p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]), Double.parseDouble(p[4]))); break;
            case "Thuong": soTruong(p, 5); q.getDsThuong().them(new Thuong(p[1], p[2], Double.parseDouble(p[3]), p[4])); break;
            case "ChiTietThuong": soTruong(p, 7); q.getDsChiTietThuong().them(new ChiTietThuong(p[1], p[2], p[3], LocalDate.parse(p[4]), Double.parseDouble(p[5]), p[6])); break;
            case "QuyDinhTruLuong": soTruong(p, 6); q.getDsQuyDinhTruLuong().them(new QuyDinhTruLuong(p[1], p[2], p[3], Double.parseDouble(p[4]), p[5])); break;
            case "ChiTietTruLuong": soTruong(p, 7); q.getDsChiTietTruLuong().them(new ChiTietTruLuong(p[1], p[2], p[3], LocalDate.parse(p[4]), Double.parseDouble(p[5]), p[6])); break;
            case "BangLuong": soTruong(p, 19); q.getDsBangLuong().them(new BangLuong(p[1], p[2], Integer.parseInt(p[3]), Integer.parseInt(p[4]), Double.parseDouble(p[5]), Double.parseDouble(p[6]), Double.parseDouble(p[7]), Double.parseDouble(p[8]), Double.parseDouble(p[9]), Double.parseDouble(p[10]), Double.parseDouble(p[11]), Double.parseDouble(p[12]), Double.parseDouble(p[13]), Double.parseDouble(p[14]), Double.parseDouble(p[15]), Double.parseDouble(p[16]), LocalDate.parse(p[17]), p[18])); break;
            case "NhanVienChinhThuc": case "NhanVienThoiVu":
                soTruong(p, 15);
                if (!p[1].equals(p[0].equals("NhanVienChinhThuc") ? "CT" : "TV")) throw new IllegalArgumentException("Loai nhan vien khong khop.");
                NhanVien nv = docNhanVien(line.substring(line.indexOf(';')+1));
                q.getDsNhanVien().them(nv); break;
            case "ChamCong": soTruong(p, 7);
                q.getDsChamCong().them(ChamCong.fromFileString(line.substring(line.indexOf(';')+1))); break;
            default: throw new IllegalArgumentException("Loai ban ghi khong ho tro: "+p[0]);
        }
    }
}
