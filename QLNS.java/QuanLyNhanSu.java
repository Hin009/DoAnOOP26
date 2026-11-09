public class QuanLyNhanSu {
    // 3 thuoc tinh trung tam dung chuan UML
    private DanhSachNhanVien dsNhanVien;
    private DanhSachPhongBan dsPhongBan;
    private DanhSachChucVu dsChucVu;

    public QuanLyNhanSu() {
        this.dsNhanVien = new DanhSachNhanVien();
        this.dsPhongBan = new DanhSachPhongBan();
        this.dsChucVu = new DanhSachChucVu();
    }

    // Cac ham lay danh sach de thao tac
    public DanhSachNhanVien getDsNhanVien() {
        return dsNhanVien;
    }

    public DanhSachPhongBan getDsPhongBan() {
        return dsPhongBan;
    }

    public DanhSachChucVu getDsChucVu() {
        return dsChucVu;
    }
}