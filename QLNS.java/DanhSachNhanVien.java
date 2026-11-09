import java.util.ArrayList;

public class DanhSachNhanVien {
    private ArrayList<NhanVien> dsNhanVien;

    public DanhSachNhanVien() {
        this.dsNhanVien = new ArrayList<>();
    }

    public void themNhanVien(NhanVien nv) {
        // Kiem tra trung ma
        for (NhanVien x : dsNhanVien) {
            if (x.getMaNV().equalsIgnoreCase(nv.getMaNV())) {
                System.out.println("Ma nhan vien da ton tai!");
                return;
            }
        }
        dsNhanVien.add(nv);
    }

    public boolean xoaNhanVien(String maNV) {
        return dsNhanVien.removeIf(nv -> nv.getMaNV().equalsIgnoreCase(maNV));
    }

    public void xuatDanhSach() {
        System.out.println("--- DANH SACH NHAN VIEN ---");
        for (NhanVien nv : dsNhanVien) {
            System.out.println(nv.getMaNV() + " - " + nv.getHoTen() + " - Luong: " + nv.tinhLuong());
        }
        System.out.println("Tong so nhan vien: " + dsNhanVien.size());
    }

    public double tinhTongQuyLuong() {
        double tong = 0;
        for (NhanVien nv : dsNhanVien) {
            tong += nv.tinhLuong();
        }
        return tong;
    }

    public ArrayList<NhanVien> getDsNhanVien() {
        return dsNhanVien;
    }
}