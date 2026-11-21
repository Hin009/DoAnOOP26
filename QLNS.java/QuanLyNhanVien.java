import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;

/** API module nhan vien cu, tai su dung mang cua DanhSachNhanVien. */
public class QuanLyNhanVien extends DanhSachNhanVien implements IFileText {
    public QuanLyNhanVien() { super(); }
    @Override public void ghiFile(String tenFile) throws IOException {
        String[] lines = new String[getSoLuong() + 2];
        String[] cot = "LoaiNV;MaNV;HoTen;NgaySinh;NgayVaoLam;GioiTinh;SDT;DiaChi;MaCV;MaPB;LuongCB;TinhTrang;ThuocTinhRieng1;ThuocTinhRieng2".split(";");
        String[][] rows = new String[getSoLuong()][];
        int viTri = 0;
        java.text.NumberFormat tien = java.text.NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"));
        tien.setMaximumFractionDigits(340);
        int[] rong = Arrays.stream(cot).mapToInt(String::length).toArray();
        for (NhanVien nv : this) {
            String[] row = nv.toFileString().split(";", -1);
            row[10] = tien.format(Double.parseDouble(row[10]));
            if (nv instanceof NhanVienThoiVu) row[13] = tien.format(Double.parseDouble(row[13]));
            for (int i=0; i<cot.length; i++) rong[i] = Math.max(rong[i], row[i].length());
            rows[viTri++] = row;
        }
        lines[0] = "# === DANH SACH NHAN VIEN ===";
        lines[1] = canCot(cot, rong);
        for (int i=0; i<rows.length; i++) lines[i + 2] = canCot(rows[i], rong);
        FileStore.ghiNguyenTu(Path.of(tenFile), lines);
    }
    private static String canCot(String[] row, int[] rong) {
        StringJoiner line = new StringJoiner(" ; ");
        for (int i=0; i<row.length; i++) line.add(row[i] + " ".repeat(rong[i] - row[i].length()));
        return line.toString().stripTrailing();
    }
    @Override public void docFile(String tenFile) throws IOException {
        String[] lines = FileStore.docDong(Path.of(tenFile));
        DanhSachNhanVien tam = new DanhSachNhanVien();
        boolean coTieuDe = false;
        for (int i=0;i<lines.length;i++) {
            String line = lines[i].strip();
            if (line.isBlank() || line.startsWith("#")) continue;
            if (!coTieuDe) {
                String[] cot = line.split(";", -1);
                if (cot.length != 14 || !cot[0].strip().equals("LoaiNV")) throw new IOException("Thieu tieu de file nhan vien.");
                coTieuDe = true;
                continue;
            }
            try { tam.them(FileStore.docNhanVien(line)); }
            catch (RuntimeException e) { throw new IOException("Dong "+(i+1)+": "+e.getMessage(),e); }
        }
        if (!coTieuDe) throw new IOException("Thieu tieu de file nhan vien.");
        xoaHet();
        for (NhanVien nv : tam) them(nv);
    }
}
