public class DanhSachChiTietThuong extends DanhSach<ChiTietThuong> {
    public DanhSachChiTietThuong() { super(new ChiTietThuong[0]); }
    public void themChiTietThuong(ChiTietThuong x) { them(x); }
    public java.util.List<ChiTietThuong> getDsChiTietThuong() { return danhSach(); }
}
