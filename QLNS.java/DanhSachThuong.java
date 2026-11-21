public class DanhSachThuong extends DanhSach<Thuong> {
    public DanhSachThuong() { super(new Thuong[0]); }
    public void themThuong(Thuong x) { them(x); }
    public Thuong[] getDsThuong() { return danhSach(); }
}
