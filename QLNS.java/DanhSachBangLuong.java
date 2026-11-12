public class DanhSachBangLuong extends DanhSach<BangLuong> {
    public DanhSachBangLuong() { super(new BangLuong[0]); }
    public void themBangLuong(BangLuong x) { them(x); }
    public java.util.List<BangLuong> getDsBangLuong() { return danhSach(); }
}
