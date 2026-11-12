import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.function.Function;

/** Giao dien terminal; khong chua cong thuc tinh luong. */
public class UngDungTerminal {
    private final QuanLyNhanSu q;
    private final NhapLieu in;
    public UngDungTerminal(QuanLyNhanSu q, Scanner sc) { this.q=q; in=new NhapLieu(sc); }
    public static void khoiDong(String[] args) {
        String file="data/qlns.txt";
        boolean demo=false, trong=false;
        for (int i=0;i<args.length;i++) {
            if (args[i].equals("--data") && i+1<args.length) file=args[++i];
            else if (args[i].equals("--demo")) demo=true;
            else if (args[i].equals("--trong")) trong=true;
            else { System.err.println("Cach dung: Main [--data duong-dan.txt] [--trong] [--demo]"); System.exit(1); return; }
        }
        QuanLyNhanSu q=new QuanLyNhanSu(file);
        try (Scanner sc=new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8)) {
            UngDungTerminal app=new UngDungTerminal(q,sc);
            if (demo) {
                q.taoDuLieuMau(); app.hienNhanVien(); app.hienBangLuong(YearMonth.of(2026,11));
                System.out.println("Demo trong bo nho; khong ghi de file du lieu."); return;
            }
            if (Files.exists(Path.of(file))) {
                q.docFile(file); System.out.println("Da doc du lieu: " + Path.of(file).toAbsolutePath());
                int soMa = q.getDsChamCong().chuanHoaMaCu();
                if (soMa > 0) {
                    q.luu();
                    System.out.println("Da cap nhat " + soMa + " ma cham cong sang CC_MaNV_so.");
                }
            } else if (!trong) {
                q.taoDuLieuMau(); q.luu(); System.out.println("Lan dau: da tao du lieu mau thang 11/2026 tai " + file);
            }
            app.chay();
        } catch (IOException e) {
            System.err.println("Loi file: " + e.getMessage()); System.exit(1);
        }
    }
    public void chay() throws IOException {
        while (true) {
            hienMenuChinh();
            try {
                int c=in.soNguyen("Chon");
                switch(c) {
                    case 0: q.luu(); System.out.println("Da luu. Tam biet!"); return;
                    case 1: crud(q.getDsNhanVien(),"Loai;Ma;Ten;Ngay sinh;Ngay vao;Gioi;SDT;Dia chi;CV;PB;LCB;Trang thai;He so/Gio ngay;Don gia",this::nhapNhanVien); break;
                    case 2: crud(q.getDsPhongBan(),"Ma PB;Ten PB;Ma truong phong",ma -> new PhongBan(ma,in.chuoi("Ten phong"),in.chuoi("Ma truong phong ('-' neu chua co)"))); break;
                    case 3: crud(q.getDsChucVu(),"Ma CV;Ten CV;Phu cap",ma -> new ChucVu(ma,in.chuoi("Ten chuc vu"),in.tien("Phu cap"))); break;
                    case 4: crud(q.getDsChamCong(),"Ma CC;Ma NV;Ngay;Gio vao;Gio ra;Trang thai;Gio lam;Gio huong luong",this::nhapChamCong); break;
                    case 5: crud(q.getDsTrangThai(),"Ma;Ten;He so huong luong;Gio mac dinh",ma -> new TrangThai(ma,in.chuoi("Ten trang thai"),in.so("He so huong luong 0..1"),in.so("Gio quy doi khi nghi phep (0 neu tinh theo gio vao/ra)"))); break;
                    case 6: crud(q.getDsQuyDinhThamNien(),"Ma;Tu nam;Den nam;Phu cap",ma -> new QuyDinhNamPhuCapThamNien(ma,in.soNguyen("Tu nam"),in.soNguyen("Den nam (bao gom)"),in.tien("Phu cap"))); break;
                    case 7: crud(q.getDsThuong(),"Ma;Ten;So tien;Dieu kien",ma -> new Thuong(ma,in.chuoi("Ten thuong"),in.tien("So tien"),in.chuoi("Dieu kien (quan ly duyet)"))); break;
                    case 8: chiTietThuong(); break;
                    case 9: crud(q.getDsQuyDinhTruLuong(),"Ma QD;Ten quy dinh;Dieu kien;Muc tru;Don vi",ma -> new QuyDinhTruLuong(ma,in.chuoi("Ten quy dinh"),in.ma("Ma trang thai ap dung / CHUNG"),in.tien("Muc tru"),in.ma("Don vi LAN/GIO"))); break;
                    case 10: chiTietTru(); break;
                    case 11: bangLuong(); break;
                    case 12: {
                        YearMonth ky=in.ky();
                        String path="reports/bang-luong-"+ky+".txt";
                        q.xuatBaoCao(ky,path); System.out.println("Da xuat: "+Path.of(path).toAbsolutePath()); break;
                    }
                    case 13: thongKe(); break;
                    default: System.out.println("Chon tu 0 den 13.");
                }
            } catch (java.util.concurrent.CancellationException e) {
                System.out.println("Da huy thao tac dang nhap. Tro ve menu chinh.");
            } catch (NoSuchElementException e) {
                q.luu(); System.out.println("\nHet dau vao. Da luu du lieu."); return;
            } catch (IOException | IllegalArgumentException | java.time.DateTimeException e) {
                System.out.println("Khong thuc hien duoc: "+e.getMessage());
            }
        }
    }
    private void hienMenuChinh() {
        String vien = "+" + "-".repeat(29) + "+" + "-".repeat(29) + "+";
        System.out.println();
        System.out.println("+" + "-".repeat(59) + "+");
        System.out.printf("| %-57s |%n", "QUAN LY NHAN SU VA TINH LUONG");
        System.out.println(vien);
        String[][] muc = {
            {"1. Nhan vien", "2. Phong ban"},
            {"3. Chuc vu", "4. Cham cong"},
            {"5. Trang thai", "6. Phu cap tham nien"},
            {"7. Danh muc thuong", "8. Chi tiet thuong"},
            {"9. Quy dinh tru luong", "10. Chi tiet tru luong"},
            {"11. Bang luong thang", "12. Xuat bao cao text"},
            {"13. Tim kiem / thong ke", "0. Luu va thoat"}
        };
        for (String[] dong : muc) System.out.printf("| %-27s | %-27s |%n", dong[0], dong[1]);
        System.out.println(vien);
        System.out.println("/huy: Huy thao tac dang nhap, ve menu chinh.");
    }
    private <T extends BanGhi> void crud(DanhSach<T> ds, String header, Function<String,T> nhap) throws IOException {
        while(true) {
            System.out.println("\n1. Xem  2. Them  3. Sua  4. Xoa  5. Tim theo ma  0. Quay lai");
            int c=in.soNguyen("Chon");
            if(c==0) return;
            if(c==1) {
                if (ds instanceof DanhSachNhanVien) ds.xuatDanhSach();
                else hienDanhSach(ds, header);
                continue;
            }
            if(c<2 || c>5) { System.out.println("Chon tu 0 den 5."); continue; }
            String ma = c == 2 ? nhapMaMoi(ds) : in.ma(nhanMa(ds));
            if(c==5) {
                T x=ds.timTheoMa(ma);
                if (x == null) System.out.println("Khong tim thay.");
                else hienBanGhi(x, header);
                continue;
            }
            if(c==4) q.xoaBanGhi(ds,ma);
            else {
                if(c==3) {
                    T cu=ds.timTheoMa(ma);
                    if(cu==null) throw new IllegalArgumentException("Khong tim thay ma.");
                    System.out.println("Du lieu cu:");
                    hienBanGhi(cu, header);
                    System.out.println("Nhap lai cac truong; giu nguyen ma.");
                }
                q.luuBanGhi(ds,nhap.apply(ma),c==3);
            }
            q.luu(); System.out.println("Da cap nhat va luu file. Neu co thay doi luong/cham cong, hay lap lai bang luong ky lien quan.");
        }
    }
    private String nhapMaMoi(DanhSach<?> ds) {
        while (true) {
            String ma = in.ma(nhanMa(ds));
            if (ds.timTheoMa(ma) == null) return ma;
            System.out.println(nhanMa(ds) + " da ton tai: " + ma + ". Nhap ma khac hoac /huy de huy.");
        }
    }

    private String nhanMa(DanhSach<?> ds) {
        if (ds instanceof DanhSachNhanVien) return "MaNV";
        if (ds instanceof DanhSachPhongBan) return "MaPB";
        if (ds instanceof DanhSachChucVu) return "MaCV";
        if (ds instanceof DanhSachChamCong) return "MaCC";
        if (ds instanceof DanhSachTrangThai) return "MaTrangThai";
        if (ds instanceof DanhSachQuyDinhThamNien) return "MaQDThamNien";
        if (ds instanceof DanhSachThuong) return "MaThuong";
        if (ds instanceof DanhSachChiTietThuong) return "MaCTThuong";
        if (ds instanceof DanhSachQuyDinhTruLuong) return "MaQDTL";
        if (ds instanceof DanhSachChiTietTruLuong) return "MaCTTru";
        if (ds instanceof DanhSachBangLuong) return "MaBL";
        return "Ma";
    }

    private void hienBanGhi(BanGhi x, String header) {
        if (x instanceof NhanVien) DanhSachNhanVien.xuatBang(List.of((NhanVien) x));
        else hienDanhSach(List.of(x), header);
    }

    /** Bang hien thi terminal tach khoi dinh dang luu file cua cac thuc the. */
    private void hienDanhSach(Iterable<? extends BanGhi> nguon, String header) {
        String[] cot = header.split(";", -1);
        boolean[] cotTien = new boolean[cot.length];
        int[] rong = new int[cot.length];
        for (int i = 0; i < cot.length; i++) {
            cotTien[i] = cot[i].equals("Phu cap") || cot[i].equals("So tien") || cot[i].equals("Muc tru");
            if (cotTien[i]) cot[i] += " (VND)";
            rong[i] = cot[i].length();
        }
        java.text.NumberFormat so = java.text.NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"));
        List<String[]> rows = new ArrayList<>();
        for (BanGhi x : nguon) {
            String[] row = x.toFileString().split(";", -1);
            if (x instanceof ChamCong) {
                ChamCong cc = (ChamCong) x;
                row = Arrays.copyOf(row, 8);
                row[6] = so.format(cc.tinhSoGioLam());
                row[7] = so.format(q.gioHuongLuong(cc));
            }
            for (int i = 0; i < row.length; i++) {
                if (cotTien[i]) row[i] = so.format(Double.parseDouble(row[i]));
                if (cot[i].startsWith("Ngay") && row[i].matches("\\d{4}-\\d{2}-\\d{2}"))
                    row[i] = LocalDate.parse(row[i]).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            if (x instanceof QuyDinhTruLuong) {
                TrangThai tt = q.getDsTrangThai().timTheoMa(row[2]);
                row[2] = row[2].equals("CHUNG") ? "Ap dung chung" : (tt == null ? row[2] : row[2] + " - " + tt.getTenTrangThai());
                row[4] = row[4].equals("LAN") ? "Lan" : "Gio";
            }
            for (int i = 0; i < cot.length; i++) rong[i] = Math.max(rong[i], row[i].length());
            rows.add(row);
        }
        StringBuilder vien = new StringBuilder("+");
        for (int r : rong) vien.append("-".repeat(r + 2)).append('+');
        System.out.println();
        System.out.println(vien);
        inDongBang(cot, rong, new boolean[cot.length]);
        System.out.println(vien);
        for (String[] row : rows) inDongBang(row, rong, cotTien);
        System.out.println(vien);
        if (rows.isEmpty()) System.out.println("Chua co du lieu.");
        System.out.println("So luong: " + rows.size());
    }

    private void inDongBang(String[] cells, int[] rong, boolean[] canPhai) {
        StringBuilder line = new StringBuilder("|");
        for (int i = 0; i < cells.length; i++) {
            String pad = " ".repeat(rong[i] - cells[i].length());
            line.append(' ').append(canPhai[i] ? pad + cells[i] : cells[i] + pad).append(" |");
        }
        System.out.println(line);
    }
    private NhanVien nhapNhanVien(String ma) {
        String loai=in.ma("Loai CT/TV");
        if(!loai.equals("CT") && !loai.equals("TV")) throw new IllegalArgumentException("Loai CT hoac TV.");
        String ten=in.chuoi("Ho ten"); LocalDate sinh=in.ngay("Ngay sinh"), vao=in.ngay("Ngay vao lam");
        String gt=in.chuoi("Gioi tinh"), sdt=in.soDienThoai(), dc=in.chuoi("Dia chi");
        String cv=in.ma("Ma chuc vu"), pb=in.ma("Ma phong ban");
        double lcb = loai.equals("CT") ? in.tien("Luong co ban") : 0;
        String tt=in.ma("Tinh trang DANG_LAM/NGHI_VIEC");
        if (loai.equals("CT"))
            return new NhanVienChinhThuc(ma,ten,sinh,vao,gt,sdt,dc,cv,pb,lcb,tt,in.so("He so luong"));
        System.out.println("Thoi vu: luong thang tinh theo gio cham cong x don gia gio.");
        return new NhanVienThoiVu(ma,ten,sinh,vao,gt,sdt,dc,cv,pb,0,tt,
            in.so("So gio du kien moi ngay theo hop dong (0..24)"),in.tien("Don gia gio"));
    }
    private ChamCong nhapChamCong(String ma) {
        String nv=in.ma("Ma nhan vien"); LocalDate d=in.ngayChamCong();
        java.time.LocalTime vao=in.gio("Gio vao"), ra=in.gio("Gio ra");
        return new ChamCong(ma,nv,d,vao,ra,in.ma("Ma trang thai (LV/MUON/VANG/PHEP hoac ma da tao)"));
    }
    private void chiTietThuong() throws IOException {
        System.out.println("1. Ghi thuong theo danh muc");
        System.out.println("2. Xem / Sua / Xoa / Ghi so tien rieng   0. Quay lai");
        int c=in.soNguyen("Chon");
        if(c==1) {
            q.ghiThuong(nhapMaMoi(q.getDsChiTietThuong()),in.ma("Ma NV"),in.ma("Ma thuong"),in.ngay("Ngay thuong"),in.chuoi("Ly do phe duyet")); q.luu();
            System.out.println("Da ghi thuong. Lap lai bang luong de cap nhat.");
        } else if(c==2) crud(q.getDsChiTietThuong(),"Ma CT;Ma NV;Ma thuong;Ngay;So tien;Ly do",ma -> new ChiTietThuong(ma,in.ma("Ma NV"),in.ma("Ma thuong"),in.ngay("Ngay thuong"),in.tien("So tien duoc duyet"),in.chuoi("Ly do")));
        else if(c!=0) throw new IllegalArgumentException("Chon 0..2.");
    }
    private void chiTietTru() throws IOException {
        System.out.println("1. Ghi khau tru theo quy dinh");
        System.out.println("2. Xem / Sua / Xoa / Ghi so tien rieng   0. Quay lai");
        int c=in.soNguyen("Chon");
        if(c==1) {
            q.ghiKhauTru(nhapMaMoi(q.getDsChiTietTruLuong()),in.ma("Ma NV"),in.ma("Ma quy dinh tru"),in.ngay("Ngay tru"),in.so("So luong (lan hoac gio theo quy dinh)"),in.chuoi("Ly do")); q.luu();
            System.out.println("Da ghi khau tru. Lap lai bang luong de cap nhat.");
        } else if(c==2) crud(q.getDsChiTietTruLuong(),"Ma CT;Ma NV;Ma QD;Ngay;So tien;Ly do",ma -> new ChiTietTruLuong(ma,in.ma("Ma NV"),in.ma("Ma QD tru"),in.ngay("Ngay tru"),in.tien("So tien duoc duyet"),in.chuoi("Ly do")));
        else if(c!=0) throw new IllegalArgumentException("Chon 0..2.");
    }
    private void bangLuong() throws IOException {
        System.out.println("1. Lap / Tinh lai ky luong   2. Xem ky luong");
        System.out.println("3. Xem phieu luong NV        0. Quay lai");
        int c=in.soNguyen("Chon"); if(c==0) return;
        if(c<1 || c>3) throw new IllegalArgumentException("Chon 0..3.");
        YearMonth ky=in.ky();
        if(c==1) {
            q.lapBangLuong(ky,in.so("Ngay cong tieu chuan (thuong 26)")); q.luu();
            System.out.println("Da lap lai bang luong ky "+ky+"; cac ky khac duoc giu lai.");
        }
        if(c==3) {
            String ma=in.ma("Ma NV"); boolean found=false;
            for(BangLuong b:q.bangLuongThang(ky)) if(b.getMaNV().equals(ma)) {
                System.out.println("PHIEU LUONG "+ky+" - "+ma);
                System.out.printf(Locale.ROOT,"Cong: %.3f | Gio: %.2f | Ngay chuan: %.2f%nLuong theo cong: %,.0f%nPC chuc vu: %,.0f | PC tham nien: %,.0f%nThuong: %,.0f | Khau tru: %,.0f%nTHUC NHAN: %,.0f VND%n",
                    b.getSoNgayCongHuongLuong(),b.getSoGioHuongLuong(),b.getNgayCongTieuChuan(),b.getLuongTheoCong(),b.getPhuCapChucVu(),b.getPhuCapThamNien(),b.getTongThuong(),b.getTongKhauTru(),b.getLuongThucNhan());
                System.out.println(b.getCongThucTinh()); found=true;
            }
            if(!found) System.out.println("Chua co phieu luong.");
        } else hienBangLuong(ky);
    }
    public void hienBangLuong(YearMonth ky) {
        System.out.println();
        q.noiDungBaoCao(ky).forEach(System.out::println);
    }
    public void hienNhanVien() {
        q.getDsNhanVien().xuatDanhSach();
    }
    private void thongKe() {
        System.out.println("1. Tim ten / Ma / Phong     2. Sap xep ten");
        System.out.println("3. Thong ke phong ban      4. Tong hop cham cong thang");
        int c=in.soNguyen("Chon");
        switch(c) {
            case 1: DanhSachNhanVien.xuatBang(q.getDsNhanVien().timKiem(in.chuoi("Tu khoa"))); break;
            case 2: q.getDsNhanVien().sapXep(Comparator.comparing(NhanVien::getHoTen,String.CASE_INSENSITIVE_ORDER)); hienNhanVien(); break;
            case 3:
                for(PhongBan pb:q.getDsPhongBan()) {
                    int ct=0,tv=0;
                    for(NhanVien nv:q.getDsNhanVien()) if(nv.getMaPB().equals(pb.getMa())) { if(nv instanceof NhanVienChinhThuc) ct++; else tv++; }
                    System.out.printf("%s - %s: %d chinh thuc, %d thoi vu%n",pb.getMa(),pb.getTenPB(),ct,tv);
                } break;
            case 4:
                YearMonth ky=in.ky();
                for(NhanVien nv:q.getDsNhanVien()) {
                    double gio=0,cong=0; int pending=0;
                    for(ChamCong cc:q.getDsChamCong()) if(cc.getMaNhanVien().equalsIgnoreCase(nv.getMa()) && YearMonth.from(cc.getNgay()).equals(ky)) {
                        double g=q.gioHuongLuong(cc); gio+=g; cong+=Math.min(1,g/8);
                        if(cc.getGioVao()!=null && cc.getGioRa()==null) pending++;
                    }
                    System.out.printf(Locale.ROOT,"%s - %s: %.2f gio huong luong, %.3f cong, %d ca chua cham ra%n",nv.getMa(),nv.getHoTen(),gio,cong,pending);
                } break;
            default: throw new IllegalArgumentException("Chon 1..4.");
        }
    }
}
