import java.util.ArrayList;

public class DanhSachChucVu {
    private ArrayList<ChucVu> dsChucVu;

    public DanhSachChucVu() {
        this.dsChucVu = new ArrayList<>();
    }

    public void themChucVu(ChucVu cv) {
        for (ChucVu x : dsChucVu) {
            if (x.getMaCV().equalsIgnoreCase(cv.getMaCV())) {
                System.out.println("Ma chuc vu da ton tai!");
                return;
            }
        }
        dsChucVu.add(cv);
    }

    public void xuatDanhSach() {
        System.out.println("--- DANH SACH CHUC VU ---");
        for (ChucVu cv : dsChucVu) {
            System.out.println(cv.getMaCV() + " - " + cv.getTenCV() + " - Phu cap: " + cv.getTienPhuCap());
        }
    }

    public ArrayList<ChucVu> getDsChucVu() {
        return dsChucVu;
    }
}