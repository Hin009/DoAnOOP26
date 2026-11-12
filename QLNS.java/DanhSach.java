import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/** Mang doi tuong tu mo rong; khong dung ArrayList lam bo nho luu danh sach. */
public class DanhSach<T extends BanGhi> implements Iterable<T> {
    private T[] ds;
    private int n;
    public DanhSach(T[] mangRong) {
        if (mangRong == null || mangRong.length != 0) throw new IllegalArgumentException("Truyen mang rong dung kieu doi tuong.");
        ds = Arrays.copyOf(mangRong, 8); n = 0;
    }
    public int getSoLuong() { return n; }
    public T get(int i) {
        if (i < 0 || i >= n) throw new IndexOutOfBoundsException("Vi tri ngoai danh sach.");
        return ds[i];
    }
    private int viTri(String ma) {
        for (int i = 0; i < n; i++) if (ds[i].getMa().equalsIgnoreCase(ma)) return i;
        return -1;
    }
    public T timTheoMa(String ma) { int i = viTri(ma); return i < 0 ? null : get(i); }
    public void them(T x) {
        if (x == null) throw new IllegalArgumentException("Doi tuong khong duoc null.");
        KiemTra.ma(x.getMa());
        if (viTri(x.getMa()) >= 0) throw new IllegalArgumentException("Trung ma: " + x.getMa());
        if (n == ds.length) ds = Arrays.copyOf(ds, Math.max(8, n * 2));
        ds[n++] = x;
    }
    /** Sua giu nguyen ma de khong pha vo lien ket. */
    public void sua(String ma, T x) {
        int i = viTri(ma);
        if (i < 0) throw new IllegalArgumentException("Khong tim thay: " + ma);
        if (x == null || !x.getMa().equalsIgnoreCase(ma)) throw new IllegalArgumentException("Khong duoc doi ma.");
        ds[i] = x;
    }
    public boolean xoa(String ma) {
        int i = viTri(ma);
        if (i < 0) return false;
        System.arraycopy(ds, i + 1, ds, i, n - i - 1);
        ds[--n] = null;
        return true;
    }
    public void xoaHet() { Arrays.fill(ds, null); n = 0; }
    public void sapXep(java.util.Comparator<? super T> c) {
        // Insertion sort truc tiep tren mang doi tuong.
        for (int i = 1; i < n; i++) {
            T x = get(i); int j = i - 1;
            while (j >= 0 && c.compare(get(j), x) > 0) { ds[j + 1] = ds[j]; j--; }
            ds[j + 1] = x;
        }
    }
    /** Ban sao chi doc de hien thi/tuong thich API cu. */
    public List<T> danhSach() {
        List<T> copy = new ArrayList<>();
        for (int i = 0; i < n; i++) copy.add(get(i));
        return java.util.Collections.unmodifiableList(copy);
    }
    @Override public Iterator<T> iterator() { return danhSach().iterator(); }
    public void xuatDanhSach() {
        for (T x : this) System.out.println(x);
        System.out.println("So luong: " + n);
    }
}
