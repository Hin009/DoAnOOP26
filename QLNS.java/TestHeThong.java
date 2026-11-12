import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.util.*;

/** Kiem thu nghiep vu, khong can JUnit hay tai thu vien qua mang. */
public final class TestHeThong {
    private static int soKiemTra = 0;
    private TestHeThong() { }
    private interface ThaoTac { void chay() throws Exception; }
    private static void dung(boolean condition, String msg) {
        soKiemTra++;
        if (!condition) throw new AssertionError(msg);
    }
    private static void gan(double actual, double expected, String msg) {
        dung(Math.abs(actual - expected) < 0.00001, msg+": "+actual+" != "+expected);
    }
    private static void phaiLoi(Class<? extends Exception> type, ThaoTac f, String msg) throws Exception {
        try { f.chay(); } catch (Exception e) {
            dung(type.isInstance(e),msg+" (sai loai loi: "+e+")"); return;
        }
        throw new AssertionError("Khong chan: "+msg);
    }
    private static BangLuong luong(QuanLyNhanSu q, String nv, YearMonth ky) {
        return q.bangLuongThang(ky).stream().filter(b -> b.getMaNV().equals(nv)).findFirst().orElseThrow();
    }
    public static void main(String[] args) throws Exception {
        Path tmp=Files.createTempDirectory("qlns-test-").toAbsolutePath().normalize();
        try {
            YearMonth ky=YearMonth.of(2026,11);
            QuanLyNhanSu q=new QuanLyNhanSu(tmp.resolve("qlns.txt").toString()); q.taoDuLieuMau();
            gan(luong(q,"NV01",ky).getLuongThucNhan(),12400000,"Luong chinh thuc");
            gan(luong(q,"NV02",ky).getLuongThucNhan(),6675000,"Luong thoi vu");
            gan(luong(q,"NV02",ky).getSoGioHuongLuong(),207.5,"Tru nua gio di muon");
            q.lapBangLuong(ky,26); dung(q.getDsBangLuong().getSoLuong()==2,"Tinh lai khong trung");
            phaiLoi(IllegalArgumentException.class,()->q.lapBangLuong(ky,0),"Ngay chuan 0");
            phaiLoi(IllegalArgumentException.class,()->q.lapBangLuong(ky,32),"Ngay chuan >31");
            dung(q.getDsChamCong().getSoLuong()==52,"Mang mo rong du cho 52 ban ghi");
            phaiLoi(IllegalArgumentException.class,()->q.luuBanGhi(q.getDsPhongBan(),new PhongBan("pb01","Trung","-"),false),"Trung ma khong phan biet hoa thuong");
            phaiLoi(IllegalArgumentException.class,()->q.xoaBanGhi(q.getDsPhongBan(),"PB01"),"Xoa phong dang co nhan vien");
            dung(q.getDsPhongBan().timTheoMa("PB01")!=null,"Khoi phuc phong khi xoa loi");
            phaiLoi(IllegalArgumentException.class,()->q.xoaBanGhi(q.getDsNhanVien(),"NV01"),"Xoa NV dang co lich su");
            phaiLoi(IllegalArgumentException.class,()->q.luuBanGhi(q.getDsChamCong(),new ChamCong("DUP","NV01",LocalDate.of(2026,11,1),LocalTime.NOON,LocalTime.of(16,0),"LV"),false),"Trung nhan vien/ngay");
            dung(q.getDsChamCong().timTheoMa("DUP")==null,"Rollback them cham cong");
            phaiLoi(IllegalArgumentException.class,()->q.luuBanGhi(q.getDsQuyDinhThamNien(),new QuyDinhNamPhuCapThamNien("TN03",4,8,1),false),"Tham nien chong khoang");
            phaiLoi(IllegalArgumentException.class,()->q.luuBanGhi(q.getDsChamCong(),new ChamCong("BADNV","NONE",LocalDate.of(2026,11,1),null,null,"VANG"),false),"Ma NV khong ton tai");
            phaiLoi(IllegalArgumentException.class,()->q.ghiKhauTru("BADTR","NV01","TR01",LocalDate.of(2026,11,1),1,"Khong di muon"),"Khau tru sai trang thai");
            phaiLoi(IllegalArgumentException.class,()->q.ghiKhauTru("BADTR","NV02","TR01",LocalDate.of(2026,11,2),0.5,"Sai so lan"),"So lan phai nguyen");

            // Chi tinh cong trong ky; phep huong luong; ca qua dem; ca chua cham ra.
            q.luuBanGhi(q.getDsChamCong(),new ChamCong("PHEP1","NV01",LocalDate.of(2026,12,1),null,null,"PHEP"),false);
            q.luuBanGhi(q.getDsChamCong(),new ChamCong("DEM1","NV02",LocalDate.of(2026,12,1),LocalTime.of(22,0),LocalTime.of(6,0),"LV"),false);
            q.luuBanGhi(q.getDsChamCong(),new ChamCong("DANG1","NV02",LocalDate.of(2026,12,2),LocalTime.of(8,0),null,"LV"),false);
            q.luuBanGhi(q.getDsChamCong(),new ChamCong("VANG1","NV01",LocalDate.of(2026,12,2),null,null,"VANG"),false);
            YearMonth dec=YearMonth.of(2026,12); q.lapBangLuong(dec,26);
            gan(luong(q,"NV01",dec).getLuongTheoCong(),400000,"Phep huong mot cong");
            gan(luong(q,"NV02",dec).getLuongTheoCong(),240000,"Ca dem 8 gio, ca chua ket thuc 0 gio");
            dung(q.getDsBangLuong().getSoLuong()==4,"Giu ky cu khi lap ky moi");
            gan(luong(q,"NV01",ky).getLuongThucNhan(),12400000,"Ky cu khong bi anh huong");
            q.luuBanGhi(q.getDsChamCong(),new ChamCong("DAI1","NV01",LocalDate.of(2026,12,3),LocalTime.of(6,0),LocalTime.of(18,0),"LV"),false);
            q.lapBangLuong(dec,26);
            gan(luong(q,"NV01",dec).getSoNgayCongHuongLuong(),2,"Cong CT toi da 1/ngay");
            gan(luong(q,"NV01",dec).getSoGioHuongLuong(),20,"Gio thuc te van duoc luu");
            gan(q.phuCapThamNien(q.getDsNhanVien().timTheoMa("NV01"),LocalDate.of(2024,12,31)),200000,"Chua du 5 nam");
            gan(q.phuCapThamNien(q.getDsNhanVien().timTheoMa("NV01"),LocalDate.of(2025,1,1)),500000,"Dung moc du 5 nam");
            q.luuBanGhi(q.getDsChamCong(),new ChamCong("JAN1","NV01",LocalDate.of(2027,1,1),null,null,"VANG"),false);
            q.lapBangLuong(YearMonth.of(2027,1),26);
            gan(luong(q,"NV01",YearMonth.of(2027,1)).getLuongThucNhan(),0,"Khong co cong thi khong co phu cap");
            q.ghiKhauTru("AM1","NV01","TR02",LocalDate.of(2027,1,1),1,"Khoan no");
            q.lapBangLuong(YearMonth.of(2027,1),26);
            gan(luong(q,"NV01",YearMonth.of(2027,1)).getLuongThucNhan(),-100000,"Hien thi am khi khau tru vuot thu nhap");
            double snapshot=luong(q,"NV01",ky).getLuongThucNhan();
            q.getDsNhanVien().timTheoMa("NV01").setLuongCoBan(6000000);
            gan(luong(q,"NV01",ky).getLuongThucNhan(),snapshot,"Bang luong giu ban chup cho den khi tinh lai");
            q.lapBangLuong(ky,26);
            gan(luong(q,"NV01",ky).getLuongThucNhan(),14000000,"Tinh lai cap nhat luong co ban");
            q.getDsNhanVien().timTheoMa("NV01").setHoTen("Nguyễn Văn An");
            q.luu();
            QuanLyNhanSu doc=new QuanLyNhanSu(); doc.docFile(tmp.resolve("qlns.txt").toString());
            dung(doc.getDsNhanVien().timTheoMa("NV01").getHoTen().equals("Nguyễn Văn An"),"UTF-8 tieng Viet");
            for(int i=0;i<q.cacDanhSach().size();i++) {
                List<? extends BanGhi> a=q.cacDanhSach().get(i).danhSach(),b=doc.cacDanhSach().get(i).danhSach();
                dung(a.size()==b.size(),"So luong danh sach "+i);
                for(int j=0;j<a.size();j++) dung(a.get(j).toFileString().equals(b.get(j).toFileString()),"Round-trip du lieu "+i+"/"+j);
            }
            Path report=tmp.resolve("bao-cao.txt"); doc.xuatBaoCao(ky,report.toString());
            dung(Files.readString(report).contains("TONG THUC NHAN: 20.675.000 VND"),"Tong bao cao");
            dung(Files.readAllLines(report).equals(doc.noiDungBaoCao(ky)),"File va terminal dung chung dinh dang bang luong");
            dung(Files.readString(report).contains("| Ma NV") && Files.readString(report).contains("3. THUONG, KHAU TRU"),"Bao cao co khung va nhom cot");
            List<String> lines=Files.readAllLines(tmp.resolve("qlns.txt"));
            dung(lines.stream().anyMatch(s -> s.contains("6.000.000")),"File phan nhom luong VND");
            dung(lines.stream().anyMatch(s -> s.startsWith("# === ChamCong")),"File co tieu de nhom");
            dung(lines.stream().anyMatch(s -> s.startsWith("# ") && s.contains("Thuc nhan")),"File co ten cot luong");
            // File cu khong co chu thich/can cot van doc duoc.
            lines = lines.stream().filter(s -> !s.isBlank() && !s.stripLeading().startsWith("#"))
                .map(s -> String.join(";", Arrays.stream(s.split(";", -1)).map(String::strip).toArray(String[]::new)))
                .toList();
            List<String> raw = new ArrayList<>(); raw.add(FileStore.PHIEN_BAN);
            for (DanhSach<? extends BanGhi> ds : q.cacDanhSach())
                for (BanGhi x : ds) raw.add(x.getClass().getSimpleName()+";"+x.toFileString());
            Path legacy=tmp.resolve("cu.txt"); Files.write(legacy,raw,StandardCharsets.UTF_8);
            QuanLyNhanSu cu = new FileStore(legacy.toString()).doc();
            for (int i=0;i<doc.cacDanhSach().size();i++) {
                List<? extends BanGhi> a=doc.cacDanhSach().get(i).danhSach(), b=cu.cacDanhSach().get(i).danhSach();
                dung(a.size()==b.size(),"File cu so luong " + i);
                for (int j=0;j<a.size();j++) dung(a.get(j).toFileString().equals(b.get(j).toFileString()),"File cu du lieu " + i + "/" + j);
            }
            List<String> bad=new ArrayList<>(lines); bad.add("KhongTonTai;abc");
            Path corrupt=tmp.resolve("loi.txt"); Files.write(corrupt,bad,StandardCharsets.UTF_8);
            phaiLoi(IOException.class,()->doc.docFile(corrupt.toString()),"File loi dong cuoi");
            dung(doc.getDsNhanVien().getSoLuong()==2,"File loi khong mat du lieu cu");
            bad=new ArrayList<>(lines); bad.add(lines.get(1)); Files.write(corrupt,bad);
            phaiLoi(IOException.class,()->doc.docFile(corrupt.toString()),"File trung ma");
            bad=new ArrayList<>(lines); bad.removeIf(s -> s.startsWith("ChucVu;CV02;")); Files.write(corrupt,bad);
            phaiLoi(IOException.class,()->doc.docFile(corrupt.toString()),"File mat khoa ngoai");
            Files.writeString(corrupt,"SAI_PHIEN_BAN\n");
            phaiLoi(IOException.class,()->doc.docFile(corrupt.toString()),"Sai phien ban");
            phaiLoi(IllegalArgumentException.class,()->KiemTra.so(Double.NaN),"NaN");
            phaiLoi(IllegalArgumentException.class,()->KiemTra.so(Double.POSITIVE_INFINITY),"Vo cuc");
            phaiLoi(IllegalArgumentException.class,()->new ChucVu("BAD","Ten;loi",0),"Dau phan cach trong chuoi");
            phaiLoi(IllegalArgumentException.class,()->new ChucVu("BAD","Ten",-1),"Tien am");
            dung(KiemTra.soDienThoai("0123456789").equals("0123456789"),"Dien thoai 10 so giu so 0 dau");
            dung(KiemTra.soDienThoai("01234567890").equals("01234567890"),"Dien thoai 11 so");
            for (String s : List.of("123456789", "012345678901", "012345678a", "+84123456789", "012 3456789"))
                phaiLoi(IllegalArgumentException.class,()->KiemTra.soDienThoai(s),"Dien thoai sai: "+s);
            NhanVien an=doc.getDsNhanVien().timTheoMa("NV01");
            phaiLoi(IllegalArgumentException.class,()->an.setSoDienThoai("123456789"),"Setter chan dien thoai thieu so");
            dung(an.getSoDienThoai().equals("0901234567"),"Setter loi giu so cu");
            for (String s : List.of("50000", "50.000", "50,000")) gan(KiemTra.docTien(s),50000,"Doc tien "+s);
            gan(KiemTra.docTien("5.000.000"),5000000,"Tien trieu");
            gan(KiemTra.docTien("0"),0,"Tien bang 0");
            for (String s : List.of("50.00", "50,00", "5.000,000", "-50000", "NaN", "1e6", "1000000000001"))
                phaiLoi(IllegalArgumentException.class,()->KiemTra.docTien(s),"Tien sai: "+s);
            NhapLieu nhap=new NhapLieu(new Scanner("123456789\n0123456789\n50.00\n50.000\n31/02/2003\n9-10-2003\n"));
            dung(nhap.soDienThoai().equals("0123456789"),"Nhap lai dien thoai tai cho");
            gan(nhap.tien("Luong co ban"),50000,"Nhap lai so tien tai cho");
            dung(nhap.ngay("Ngay sinh").equals(LocalDate.of(2003,10,9)),"Ngay linh hoat va nhap lai khi sai");
            bad=new ArrayList<>(lines);
            for (int i=0;i<bad.size();i++) if (bad.get(i).startsWith("NhanVienChinhThuc;"))
                bad.set(i,bad.get(i).replace(";0901234567;",";123456789;"));
            Files.write(corrupt,bad);
            phaiLoi(IOException.class,()->doc.docFile(corrupt.toString()),"File chan dien thoai sai");
            // Lenh huy phai thoat duoc ca cac vong lap nhap lai, khong bi bat nhu loi du lieu.
            phaiLoi(java.util.concurrent.CancellationException.class,
                ()->new NhapLieu(new Scanner(" /HUY \n")).chuoi("Ho ten"),"Huy tai o chuoi");
            phaiLoi(java.util.concurrent.CancellationException.class,
                ()->new NhapLieu(new Scanner("-\n\n/huy\n")).ngay("Ngay cham cong"),"Huy sau nhieu ngay sai");
            for (String lenh : List.of("huy", "Huy", "huy de huy", "hủy"))
                phaiLoi(java.util.concurrent.CancellationException.class,
                    ()->new NhapLieu(new Scanner(lenh+"\n")).ngay("Ngay sinh"),"Huy ngay bang lenh: "+lenh);
            dung(new NhapLieu(new Scanner("Huy\n")).chuoi("Ho ten").equals("Huy"),"Khong nham ten Huy voi lenh huy");
            LocalDate homNay = LocalDate.now(ZoneId.of("Asia/Bangkok"));
            NhapLieu congTuongLai = new NhapLieu(new Scanner(homNay.plusYears(1)+"\n"+homNay.plusDays(1)+"\n"+homNay+"\n"));
            dung(congTuongLai.ngayChamCong().equals(homNay),"Chan nam sau va ngay mai, cho phep cham cong hom nay");
            dung(new NhapLieu(new Scanner(homNay.minusDays(1)+"\n")).ngayChamCong().equals(homNay.minusDays(1)),"Cho phep nhap cong qua khu");
            phaiLoi(java.util.concurrent.CancellationException.class,
                ()->new NhapLieu(new Scanner(homNay.plusYears(1)+"\nhuy\n")).ngayChamCong(),"Huy sau khi nhap cong tuong lai");
            phaiLoi(java.util.concurrent.CancellationException.class,
                ()->new NhapLieu(new Scanner("123\n/huy\n")).soDienThoai(),"Huy khi nhap lai dien thoai");
            phaiLoi(java.util.concurrent.CancellationException.class,
                ()->new NhapLieu(new Scanner("abc\n/huy\n")).tien("Luong"),"Huy khi nhap lai tien");
            phaiLoi(java.util.concurrent.CancellationException.class,
                ()->new NhapLieu(new Scanner("/huy\n")).gio("Gio vao"),"Huy tai o gio");
            phaiLoi(java.util.concurrent.CancellationException.class,
                ()->new NhapLieu(new Scanner("/huy\n")).ky(),"Huy tai o ky luong");
            QuanLyNhanSu tienQ=new QuanLyNhanSu(tmp.resolve("tien.txt").toString());
            tienQ.taoDuLieuMau();
            tienQ.getDsNhanVien().timTheoMa("NV01").setLuongCoBan(10000000);
            tienQ.getDsChucVu().timTheoMa("CV01").setTienPhuCap(1234.5);
            tienQ.luu();
            String tienText=Files.readString(tmp.resolve("tien.txt"));
            dung(tienText.contains("10.000.000") && !tienText.contains("1.0E7"),"10 trieu khong dung ky hieu khoa hoc");
            dung(tienText.contains("1.234,5"),"Dinh dang tien khong lam tron mat phan le");
            QuanLyNhanSu tienDoc=new FileStore(tmp.resolve("tien.txt").toString()).doc();
            gan(tienDoc.getDsNhanVien().timTheoMa("NV01").getLuongCoBan(),10000000,"Doc 10 trieu da phan nhom");
            gan(tienDoc.getDsChucVu().timTheoMa("CV01").getTienPhuCap(),1234.5,"Doc tien co phan le");
            String[] nvCu=tienQ.getDsNhanVien().timTheoMa("NV01").toFileString().split(";",-1);
            nvCu[10]="1.0E7";
            gan(FileStore.docNhanVien(String.join(";",nvCu)).getLuongCoBan(),10000000,"Doc ky hieu khoa hoc cu");
            QuanLyNhanVien moduleNV = new QuanLyNhanVien();
            for (NhanVien nv : tienQ.getDsNhanVien()) moduleNV.themNhanVien(nv);
            Path nhanSuFile = tmp.resolve("nhansu.txt"); moduleNV.ghiFile(nhanSuFile.toString());
            dung(Files.readString(nhanSuFile).contains("10.000.000"),"File nhansu can cot va dinh dang tien");
            QuanLyNhanVien moduleDoc = new QuanLyNhanVien(); moduleDoc.docFile(nhanSuFile.toString());
            dung(moduleDoc.getSoLuong()==moduleNV.getSoLuong(),"Doc file nhansu can cot");
            for (NhanVien nv : moduleNV)
                dung(nv.toFileString().equals(moduleDoc.timTheoMa(nv.getMa()).toFileString()),"Nhan su round-trip "+nv.getMa());
            List<String> nvRaw = new ArrayList<>();
            nvRaw.add("LoaiNV;MaNV;HoTen;NgaySinh;NgayVaoLam;GioiTinh;SDT;DiaChi;MaCV;MaPB;LuongCB;TinhTrang;ThuocTinhRieng1;ThuocTinhRieng2");
            for (NhanVien nv : moduleNV) nvRaw.add(nv.toFileString());
            Files.write(nhanSuFile,nvRaw,StandardCharsets.UTF_8); moduleDoc.docFile(nhanSuFile.toString());
            dung(moduleDoc.getSoLuong()==2,"Van doc duoc file nhansu cu khong can cot");
            DanhSachChamCong maCu = new DanhSachChamCong();
            for (String ma : List.of("CC_B20", "CC_B_20", "CC_NV02_1"))
                maCu.them(new ChamCong(ma,"NV02",LocalDate.of(2026,11,1),null,null,"LV"));
            dung(maCu.chuanHoaMaCu()==2,"Chuyen ca ma cu co va khong co gach duoi");
            dung(maCu.timTheoMa("CC_NV02_20")!=null,"Giu so thu tu cho CC_B_20");
            dung(maCu.timTheoMa("CC_NV02_2")!=null,"Cap so chua dung khi trung ma");
            dung(maCu.timTheoMa("CC_NV02_1")!=null,"Giu nguyen ma da dung dinh dang moi");
            dung(maCu.chuanHoaMaCu()==0,"Chuan hoa nhieu lan khong doi lai ma");
            // Them/sua/xoa va dich mang khong de lai o trong.
            DanhSachPhongBan ds=new DanhSachPhongBan();
            for(int i=0;i<40;i++) ds.them(new PhongBan("P"+i,"Phong "+i,"-"));
            ds.xoa("P0"); ds.xoa("P20"); ds.xoa("P39");
            dung(ds.getSoLuong()==37,"Xoa dau/giua/cuoi mang");
            dung(ds.get(0).getMa().equals("P1"),"Dich mang sau xoa");
            ds.sua("P1",new PhongBan("P1","Da sua","-"));
            dung(ds.timTheoMa("P1").getTenPB().equals("Da sua"),"Sua doi tuong");
            phaiLoi(UnsupportedOperationException.class,()->ds.danhSach().clear(),"Getter khong lo mang noi bo");
            System.out.println("PASS: "+soKiemTra+" kiem tra nghiep vu va luu/doc file.");
        } finally {
            try (var paths=Files.walk(tmp)) {
                for(Path p:paths.sorted(Comparator.reverseOrder()).toList()) {
                    if(!p.toAbsolutePath().normalize().startsWith(tmp)) throw new IOException("Sai thu muc tam.");
                    Files.delete(p);
                }
            }
        }
    }
}
