import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** Quan ly mang doi tuong thuong, tu mo rong khi day. */
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
    /** Tra ban sao mang dung kieu, chi gom n phan tu dang su dung. */
    public T[] danhSach() { return Arrays.copyOf(ds, n); }
    /** Iterator duyet ban sao mang de ho tro vong lap for-each. */
    @Override public Iterator<T> iterator() {
        final T[] copy = danhSach();
        return new Iterator<T>() {
            private int viTri = 0;
            @Override public boolean hasNext() { return viTri < copy.length; }
            @Override public T next() {
                if (!hasNext()) throw new NoSuchElementException("Het phan tu.");
                return copy[viTri++];
            }
        };
    }
    public void xuatDanhSach() {
        for (T x : this) System.out.println(x);
        System.out.println("So luong: " + n);
    }
}
