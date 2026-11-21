public class DanhSachTrangThai extends DanhSach<TrangThai> {
    public DanhSachTrangThai() { super(new TrangThai[0]); }
    public void themTrangThai(TrangThai x) { them(x); }
    public TrangThai[] getDsTrangThai() { return danhSach(); }
}
