public class DanhSachQuyDinhThamNien extends DanhSach<QuyDinhNamPhuCapThamNien> {
    public DanhSachQuyDinhThamNien() { super(new QuyDinhNamPhuCapThamNien[0]); }
    public void themQuyDinhThamNien(QuyDinhNamPhuCapThamNien x) { them(x); }
    public java.util.List<QuyDinhNamPhuCapThamNien> getDsQuyDinhThamNien() { return danhSach(); }
}
