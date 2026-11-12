public class DanhSachNhanVien extends DanhSach<NhanVien> {
    public DanhSachNhanVien() { super(new NhanVien[0]); }
    public void themNhanVien(NhanVien x) { them(x); }
    public java.util.List<NhanVien> getDsNhanVien() { return danhSach(); }
    public boolean xoaNhanVien(String ma) { return xoa(ma); }

    @Override
    public void xuatDanhSach() { xuatBang(this); }

    /** Tach cac nhom thong tin de bang vua terminal, khong phai cuon ngang 14 cot. */
    public static void xuatBang(Iterable<NhanVien> nguon) {
        java.util.List<NhanVien> rows = new java.util.ArrayList<>();
        for (NhanVien nv : nguon) rows.add(nv);
        System.out.println("\n================ DANH SACH NHAN VIEN ================");
        if (rows.isEmpty()) {
            System.out.println("Chua co nhan vien.\nTong so: 0 nhan vien.");
            return;
        }
        java.util.Locale vn = java.util.Locale.forLanguageTag("vi-VN");
        java.text.NumberFormat tien = java.text.NumberFormat.getIntegerInstance(vn);
        java.time.format.DateTimeFormatter ngay = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String[][] nhanSu = new String[rows.size()][];
        String[][] lienHe = new String[rows.size()][];
        String[][] luong = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            NhanVien nv = rows.get(i);
            String heSo = "-", gioNgay = "-", donGia = "-";
            if (nv instanceof NhanVienChinhThuc) {
                heSo = String.format(vn, "%.2f", ((NhanVienChinhThuc) nv).getHeSoLuong());
            } else if (nv instanceof NhanVienThoiVu) {
                NhanVienThoiVu tv = (NhanVienThoiVu) nv;
                gioNgay = String.format(vn, "%.2f", tv.getSoGioLamTheoNgay());
                donGia = tien.format(tv.getDonGiaGio());
            }
            nhanSu[i] = new String[] { nv.getMaNV(), nv.getHoTen(),
                nv.getLoai().equals("CT") ? "Chinh thuc" : "Thoi vu", nv.getMaPB(), nv.getMaCV(),
                nv.getTinhTrang().equals("DANG_LAM") ? "Dang lam" : "Nghi viec" };
            lienHe[i] = new String[] { nv.getMaNV(), nv.getNgaySinh().format(ngay), nv.getNgayVaoLam().format(ngay),
                nv.getGioiTinh(), nv.getSoDienThoai(), nv.getDiaChi() };
            luong[i] = new String[] { nv.getMaNV(), nv instanceof NhanVienChinhThuc ? tien.format(nv.getLuongCoBan()) : "-", heSo, gioNgay, donGia };
        }
        System.out.println("\nTHONG TIN NHAN SU");
        inBang(new String[] { "Ma NV", "Ho va ten", "Loai NV", "Phong", "Chuc vu", "Trang thai" }, nhanSu, 6);
        System.out.println("\nTHONG TIN CA NHAN / LIEN HE");
        inBang(new String[] { "Ma NV", "Ngay sinh", "Ngay vao lam", "Gioi tinh", "So dien thoai", "Dia chi" }, lienHe, 6);
        System.out.println("\nTHONG TIN LUONG (VND)");
        inBang(new String[] { "Ma NV", "Luong co ban", "He so luong", "Gio/ngay du kien", "Don gia/gio" }, luong, 1);
        System.out.println("Ghi chu: '-' = khong ap dung. Luong thang duoc tinh tu cham cong.");
        System.out.println("Tong so: " + rows.size() + " nhan vien.");
    }

    private static void inBang(String[] headers, String[][] rows, int cotSoDauTien) {
        int[] widths = new int[headers.length];
        for (int c = 0; c < headers.length; c++) {
            widths[c] = headers[c].length();
            for (String[] row : rows) widths[c] = Math.max(widths[c], row[c].length());
        }
        StringBuilder vien = new StringBuilder("+");
        for (int w : widths) vien.append("-".repeat(w + 2)).append('+');
        System.out.println(vien);
        inDong(headers, widths, headers.length);
        System.out.println(vien);
        for (String[] row : rows) inDong(row, widths, cotSoDauTien);
        System.out.println(vien);
    }

    private static void inDong(String[] cells, int[] widths, int cotSoDauTien) {
        StringBuilder line = new StringBuilder("|");
        for (int c = 0; c < cells.length; c++) {
            String pad = " ".repeat(widths[c] - cells[c].length());
            line.append(' ').append(c >= cotSoDauTien ? pad + cells[c] : cells[c] + pad).append(" |");
        }
        System.out.println(line);
    }
    /** Uoc tinh luong theo cong cho mot thang du cong, chua gom cac khoan cong/tru. */
    public double tinhTongQuyLuong() {
        double tong = 0;
        for (NhanVien nv : this) {
            double ngay = QuanLyNhanSu.NGAY_CONG_TIEU_CHUAN;
            double gioNgay = nv instanceof NhanVienThoiVu ? ((NhanVienThoiVu) nv).getSoGioLamTheoNgay() : ChamCong.SO_GIO_MOT_CONG;
            tong += nv.tinhLuongTheoCong(ngay, gioNgay * ngay, ngay);
        }
        return tong;
    }
    public java.util.List<NhanVien> timKiem(String key) {
        String k = key.toLowerCase(java.util.Locale.ROOT);
        java.util.List<NhanVien> result = new java.util.ArrayList<>();
        for (NhanVien nv : this)
            if ((nv.getMaNV()+" "+nv.getHoTen()+" "+nv.getMaPB()).toLowerCase(java.util.Locale.ROOT).contains(k)) result.add(nv);
        return result;
    }
}
