public class DanhSachPhongBan extends DanhSach<PhongBan> {
    public DanhSachPhongBan() { super(new PhongBan[0]); }
    public void themPhongBan(PhongBan x) { them(x); }
    public java.util.List<PhongBan> getDsPhongBan() { return danhSach(); }
}
