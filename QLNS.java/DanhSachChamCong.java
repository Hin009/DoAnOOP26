import java.util.*;
import java.util.regex.*;

public class DanhSachChamCong extends DanhSach<ChamCong> {
    public DanhSachChamCong() { super(new ChamCong[0]); }
    public void themChamCong(ChamCong x) { them(x); }
    public java.util.List<ChamCong> getDsChamCong() { return danhSach(); }

    /** Chuyen ma cu sang CC_MaNV_so; giu nguyen ma moi va tranh trung. */
    public int chuanHoaMaCu() {
        Pattern cu = Pattern.compile("CC_[A-Z]_?([0-9]+)");
        Set<String> daDung = new HashSet<>();
        List<ChamCong> canDoi = new ArrayList<>(), doiSo = new ArrayList<>();
        Map<ChamCong, String> maMoi = new LinkedHashMap<>();
        for (ChamCong cc : this) {
            if (cu.matcher(cc.getMa()).matches()) canDoi.add(cc);
            else daDung.add(cc.getMa());
        }
        // Uu tien ma co dau gach duoi (CC_B_20) truoc ma nhu CC_B20.
        canDoi.sort(Comparator.comparingInt(cc -> cc.getMa().matches("CC_[A-Z]_[0-9]+") ? 0 : 1));
        for (ChamCong cc : canDoi) {
            Matcher m = cu.matcher(cc.getMa()); m.matches();
            String ma = "CC_" + cc.getMaNV() + "_" + m.group(1);
            if (daDung.add(ma)) maMoi.put(cc, ma);
            else doiSo.add(cc);
        }
        for (ChamCong cc : doiSo) {
            String prefix = "CC_" + cc.getMaNV() + "_";
            int so = 1;
            while (daDung.contains(prefix + so)) so++;
            String ma = prefix + so;
            daDung.add(ma); maMoi.put(cc, ma);
        }
        for (Map.Entry<ChamCong, String> e : maMoi.entrySet()) e.getKey().setMaChamCong(e.getValue());
        return maMoi.size();
    }
}
