public class Main {
    public static void main(String[] args) {
        DanhSachNhanVien ds = new DanhSachNhanVien();
        ds.them(new NhanVienChinhThuc("An", 8000000, 2.5));
        ds.them(new NhanVienHopDong("Bình", 50000, 120));
        System.out.printf("Tong luong: %,.0f%n", ds.tongLuong());
    }
}