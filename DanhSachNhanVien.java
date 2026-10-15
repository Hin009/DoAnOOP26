public class DanhSachNhanVien {
    private NhanVien[] ds;
    private int n;

    public DanhSachNhanVien() { ds = new NhanVien[10]; n = 0; }

    public void them(NhanVien nv) {
        if (n == ds.length) ds = java.util.Arrays.copyOf(ds, n * 2);  // mở rộng khi đầy
        ds[n++] = nv;
    }

    public double tongLuong() {
        double tong = 0;
        for (int i = 0; i < n; i++)
            tong += ds[i].tinhLuong();   // ĐA HÌNH: mỗi object tự tính theo kiểu của nó
        return tong;
    }
    // còn cần: xoa, sua, timTheoMa, sapXep, hienThi...
}