public class DanhSachChucVu extends DanhSach<ChucVu> {
    public DanhSachChucVu() { super(new ChucVu[0]); }
    public void themChucVu(ChucVu x) { them(x); }
    public ChucVu[] getDsChucVu() { return danhSach(); }
}
