import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Su dung QuanLyNhanSu (Lop Tong quan theo dung UML)
        QuanLyNhanSu ql = new QuanLyNhanSu();
        Scanner sc = new Scanner(System.in);
        int chon = -1;

        // Load du lieu mau ban dau de test cho nhanh thong qua getDsNhanVien()
        ql.getDsNhanVien().themNhanVien(new NhanVienChinhThuc("NV01", "Nguyen Van A", LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1), "Nam", "090", "HCM", "CV1", "PB1", 5000000, "Dang lam", 2.5));
        ql.getDsNhanVien().themNhanVien(
                new NhanVienThoiVu("NV02", "Tran Thi B", LocalDate.of(2000, 2, 2), LocalDate.of(2023, 2, 2),
                        "Nu", "091", "HN", "CV2", "PB2", 0, "Dang lam", 120, 25000));

        do {
            System.out.println("\n===== HE THONG QUAN LY NHAN SU =====");
            System.out.println("1. Them nhan vien chinh thuc");
            System.out.println("2. Them nhan vien thoi vu");
            System.out.println("3. Xoa nhan vien theo ma");
            System.out.println("4. Hien thi danh sach nhan vien");
            System.out.println("5. Tinh tong quy luong cong ty");
            System.out.println("6. Luu danh sach xuong file  (chua lam)");
            System.out.println("7. Doc danh sach tu file  (Chua lam)");
            System.out.println("0. Thoat chuong trinh");
            System.out.print("Moi ban chon chuc nang (0-7): ");

            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so!");
                continue;
            }

            try {
                switch (chon) {
                    case 1:
                        System.out.println("--- THEM NHAN VIEN CHINH THUC ---");
                        System.out.print("Nhap ma NV: ");
                        String ma1 = sc.nextLine();
                        System.out.print("Nhap ho ten: ");
                        String ten1 = sc.nextLine();
                        System.out.print("Luong co ban: ");
                        double lcb1 = Double.parseDouble(sc.nextLine());
                        System.out.print("He so luong: ");
                        double hs = Double.parseDouble(sc.nextLine());

                        ql.getDsNhanVien().themNhanVien(
                                new NhanVienChinhThuc(ma1, ten1, LocalDate.now(), LocalDate.now(), "Nam", "012",
                                        "VN", "CV", "PB", lcb1, "Lam", hs));
                        System.out.println("Them thanh cong!");
                        break;

                    case 2:
                        System.out.println("--- THEM NHAN VIEN THOI VU ---");
                        System.out.print("Nhap ma NV: ");
                        String ma2 = sc.nextLine();
                        System.out.print("Nhap ho ten: ");
                        String ten2 = sc.nextLine();
                        System.out.print("So gio lam: ");
                        double gio = Double.parseDouble(sc.nextLine());
                        System.out.print("Don gia gio: ");
                        double gia = Double.parseDouble(sc.nextLine());

                        ql.getDsNhanVien().themNhanVien(
                                new NhanVienThoiVu(ma2, ten2, LocalDate.now(), LocalDate.now(), "Nam", "012",
                                        "VN", "CV", "PB", 0, "Lam", gio, gia));
                        System.out.println("Them thanh cong!");
                        break;

                    case 3:
                        System.out.print("Nhap ma nhan vien can xoa: ");
                        String maXoa = sc.nextLine();
                        if (ql.getDsNhanVien().xoaNhanVien(maXoa)) {
                            System.out.println("Da xoa nhan vien " + maXoa);
                        } else {
                            System.out.println("Khong tim thay ma nhan vien nay!");
                        }
                        break;

                    case 4:
                        ql.getDsNhanVien().xuatDanhSach();
                        break;

                    case 5:
                        System.out.printf("Tong quy luong cong ty phai tra: %.0f VND\n",
                                ql.getDsNhanVien().tinhTongQuyLuong());
                        break;

                    case 6:
                        // ql.getDsNhanVien().ghiFile("nhansu.txt");
                        System.out.println("Chuc nang luu file dang duoc phat trien cho ArrayList!");
                        break;

                    case 7:
                        // ql.getDsNhanVien().docFile("nhansu.txt");
                        System.out.println("Chuc nang doc file dang duoc phat trien cho ArrayList!");
                        break;

                    case 0:
                        System.out.println("Tam biet!");
                        break;
                    default:
                        System.out.println("Lua chon khong hop le!");
                }
            } catch (Exception e) {
                System.out.println("Co loi xay ra: " + e.getMessage());
            }

        } while (chon != 0);

        sc.close();
    }
}