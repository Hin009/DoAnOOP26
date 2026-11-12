import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/** Chuong trinh chay thu module BangChamCong. */
public class TestBangChamCong {
    public TestBangChamCong() { }
    public static void main(String[] args) {
        try {
            BangChamCong bang = new BangChamCong();

            // Tao du lieu mau. Khi ghep voi du an, thay ma NV bang ma nhan vien co that.
            bang.them(new ChamCong("CC001", "NV001",
                    LocalDate.of(2026, 11, 2),
                    LocalTime.of(8, 0), LocalTime.of(17, 0), "Du cong"));

            bang.them(new ChamCong("CC002", "NV001",
                    LocalDate.of(2026, 11, 3),
                    LocalTime.of(8, 15), LocalTime.of(17, 0), "Di muon"));

            bang.them(new ChamCong("CC003", "NV002",
                    LocalDate.of(2026, 11, 3),
                    LocalTime.of(8, 0), LocalTime.of(12, 0), "Nghi nua ngay"));

            System.out.println("=== DANH SACH CHAM CONG ===");
            bang.xuatDanhSach();

            System.out.println("\nTong gio lam NV001: "
                    + bang.tinhTongGioTheoNhanVien("NV001"));
            System.out.println("Tong cong NV001: "
                    + bang.tinhTongCongTheoNhanVien("NV001"));

            System.out.println("\n=== TIM GAN DUNG 'NV00' ===");
            ChamCong[] ketQua = bang.timGanDung("NV00");
            for (ChamCong x : ketQua) {
                System.out.println(x);
            }

            // Thu sap xep va ghi file.
            bang.sapXepTheoNgay();
            IFileText thaoTacTep = bang; // Bien interface tham chieu den doi tuong BangChamCong
            thaoTacTep.ghiFile("bang_cham_cong.txt");

            // Thu doc lai file vao mot doi tuong BangChamCong khac.
            BangChamCong bangDocLai = new BangChamCong();
            IFileText dichVuDocFile = bangDocLai;
            dichVuDocFile.docFile("bang_cham_cong.txt");
            System.out.println("\n=== DANH SACH DOC TU FILE ===");
            bangDocLai.xuatDanhSach();

        } catch (IOException | IllegalArgumentException ex) {
            System.out.println("Co loi: " + ex.getMessage());
        }
    }
}
