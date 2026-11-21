import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DanhSachChamCong extends DanhSach<ChamCong> {
    public DanhSachChamCong() { super(new ChamCong[0]); }
    public void themChamCong(ChamCong x) { them(x); }
    public ChamCong[] getDsChamCong() { return danhSach(); }

    /** Chuyen ma cu sang CC_MaNV_so bang cac mang tam, tranh trung ma. */
    public int chuanHoaMaCu() {
        Pattern cu = Pattern.compile("CC_[A-Z]_?([0-9]+)");
        int n = getSoLuong();
        String[] daDung = new String[n], maMoi = new String[n];
        ChamCong[] canDoi = new ChamCong[n], doiSo = new ChamCong[n];
        int soDaDung = 0, soCanDoi = 0, soDoiSo = 0;
        for (ChamCong cc : this)
            if (!cu.matcher(cc.getMa()).matches()) daDung[soDaDung++] = cc.getMa();
        // Uu tien CC_B_20 truoc CC_B20 de giu so thu tu da co.
        for (int luot = 0; luot < 2; luot++) {
            for (ChamCong cc : this) {
                Matcher m = cu.matcher(cc.getMa());
                if (!m.matches()) continue;
                if (cc.getMa().matches("CC_[A-Z]_[0-9]+") != (luot == 0)) continue;
                String ma = "CC_" + cc.getMaNV() + "_" + m.group(1);
                if (!daDung(daDung, soDaDung, ma)) {
                    daDung[soDaDung++] = ma;
                    canDoi[soCanDoi] = cc; maMoi[soCanDoi++] = ma;
                } else doiSo[soDoiSo++] = cc;
            }
        }
        for (int i = 0; i < soDoiSo; i++) {
            ChamCong cc = doiSo[i];
            String prefix = "CC_" + cc.getMaNV() + "_";
            int so = 1;
            while (daDung(daDung, soDaDung, prefix + so)) so++;
            String ma = prefix + so;
            daDung[soDaDung++] = ma;
            canDoi[soCanDoi] = cc; maMoi[soCanDoi++] = ma;
        }
        for (int i = 0; i < soCanDoi; i++) canDoi[i].setMaChamCong(maMoi[i]);
        return soCanDoi;
    }
    private static boolean daDung(String[] ma, int n, String key) {
        for (int i = 0; i < n; i++) if (ma[i].equalsIgnoreCase(key)) return true;
        return false;
    }
}
