public class DanhSachQuyDinhThamNien extends DanhSach<QuyDinhNamPhuCapThamNien> {
    public DanhSachQuyDinhThamNien() { super(new QuyDinhNamPhuCapThamNien[0]); }
    public void themQuyDinhThamNien(QuyDinhNamPhuCapThamNien x) { them(x); }
    public QuyDinhNamPhuCapThamNien[] getDsQuyDinhThamNien() { return danhSach(); }
}
