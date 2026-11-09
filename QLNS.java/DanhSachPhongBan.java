import java.util.ArrayList;

public class DanhSachPhongBan {
    private ArrayList<PhongBan> dsPhongBan;

    public DanhSachPhongBan() {
        this.dsPhongBan = new ArrayList<>();
    }

    public void themPhongBan(PhongBan pb) {
        for (PhongBan x : dsPhongBan) {
            if (x.getMaPB().equalsIgnoreCase(pb.getMaPB())) {
                System.out.println("Ma phong ban da ton tai!");
                return;
            }
        }
        dsPhongBan.add(pb);
    }

    public void xuatDanhSach() {
        System.out.println("--- DANH SACH PHONG BAN ---");
        for (PhongBan pb : dsPhongBan) {
            System.out.println(pb.getMaPB() + " - " + pb.getTenPB());
        }
    }

    public ArrayList<PhongBan> getDsPhongBan() {
        return dsPhongBan;
    }
}