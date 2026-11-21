public class DanhSachQuyDinhTruLuong extends DanhSach<QuyDinhTruLuong> {
    public DanhSachQuyDinhTruLuong() { super(new QuyDinhTruLuong[0]); }
    public void themQuyDinhTruLuong(QuyDinhTruLuong x) { them(x); }
    public QuyDinhTruLuong[] getDsQuyDinhTruLuong() { return danhSach(); }
}
