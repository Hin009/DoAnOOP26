# Tài liệu chuẩn bị bảo vệ đồ án

## 1. Giới thiệu trong khoảng một phút

“Đồ án của em quản lý nhân sự và tính lương bằng Java, chạy trên terminal. Có hai loại nhân viên là chính thức và thời vụ, cùng kế thừa lớp trừu tượng NhanVien. Các danh sách lưu bằng mảng đối tượng và tự tăng sức chứa. Chương trình quản lý phòng ban, chức vụ, chấm công, phụ cấp, thưởng và khấu trừ. Khi lập bảng lương tháng, chương trình gọi phương thức tính lương qua biến kiểu NhanVien; mỗi lớp con thực hiện công thức riêng. Dữ liệu được lưu bằng file text UTF-8, có kiểm tra mã trùng và liên kết trước khi chấp nhận. Ngoài chức năng cơ bản, em có phiếu lương, thống kê, nghỉ phép hưởng lương và xử lý ca qua đêm.”

Đây là lời giới thiệu tham khảo. Hãy tự chạy và giải thích được từng phần trước buổi bảo vệ.

## 2. Câu hỏi lý thuyết và trả lời

**Class và object khác nhau thế nào?** Class mô tả thuộc tính/hành vi; object là một thể hiện cụ thể của class, được tạo bằng `new`. `PhongBan` là class; `new PhongBan("PB01", "Nhân sự", "-")` tạo object.

**Bốn tính chất OOP?** Đóng gói, kế thừa, đa hình, trừu tượng. Trong đồ án: trường `private`; hai loại nhân viên `extends NhanVien`; ghi đè hàm tính lương và gọi qua biến cha; `NhanVien` là lớp abstract, cung cấp hợp đồng chung.

**Đóng gói có phải chỉ là getter/setter?** Không. Cần bảo vệ trạng thái hợp lệ. Ví dụ `setLuongCoBan` từ chối số âm, NaN và vô cực; mã thực thể phần lớn là `final`; danh sách trả bản sao chỉ đọc. Kiểm tra liên kết nhiều object thuộc về `QuanLyNhanSu`.

**Constructor là gì? Có kiểu trả về không?** Constructor khởi tạo object, trùng tên class, không khai báo kiểu trả về. Constructor có tham số giúp object có đủ dữ liệu khi tạo. Nếu đã tự khai báo constructor thì Java không tự thêm constructor không tham số.

**`this` và `super`?** `this` chỉ object hiện tại, giúp phân biệt biến tham số với thuộc tính. `super(...)` gọi constructor của lớp cha. Hai constructor nhân viên gọi `super(...)` để khởi tạo phần thông tin chung.

**Kế thừa khi nào hợp lý?** Khi có quan hệ “là một”. Nhân viên chính thức là một nhân viên. Phòng ban có nhiều nhân viên là quan hệ kết hợp, không nên cho PhongBan kế thừa NhanVien.

**Abstract class có tạo object trực tiếp được không?** Không thể `new NhanVien(...)`. Lớp abstract có thể có thuộc tính, constructor và hàm đã cài đặt; lớp con cụ thể phải cài đặt các hàm abstract còn thiếu.

**Interface khác abstract class?** Interface mô tả một hợp đồng mà nhiều lớp có thể cùng thực hiện. Abstract class thích hợp chia sẻ trạng thái và phần cài đặt chung. Java chỉ cho một lớp cha trực tiếp nhưng có thể implements nhiều interface. Interface cũng có thể có phương thức default/static; không nên phát biểu rằng interface tuyệt đối không có phần cài đặt.

**Override khác overload?** Override: lớp con cung cấp cài đặt cho phương thức có cùng chữ ký từ lớp cha/interface. Overload: cùng tên phương thức nhưng danh sách tham số khác nhau. Constructor `QuanLyNhanSu()` và `QuanLyNhanSu(String)` là overload; `tinhLuongTheoCong(...)` ở hai lớp con là override.

**Đa hình động hoạt động thế nào?** Kiểu biến có thể là `NhanVien`, object thật là `NhanVienChinhThuc` hoặc `NhanVienThoiVu`. Khi gọi phương thức instance bị override, Java chọn cài đặt theo kiểu object lúc chạy. Chỗ chứng minh trong đồ án là `QuanLyNhanSu.lapBangLuong`.

**`instanceof` trong đồ án có thay thế đa hình không?** Không. Công thức lương được gọi đa hình. `instanceof` chỉ dùng khi cần lấy thông số riêng để lưu bản chụp, chẳng hạn hệ số hoặc đơn giá, và khi thống kê loại nhân viên.

**Static là gì?** Thành viên static thuộc class, dùng chung giữa các object. Ví dụ `ChamCong.SO_GIO_MOT_CONG = 8.0` và `KiemTra.so(...)`. Hàm static không có `this`; không thể truy cập trực tiếp trường instance khi chưa có object.

**Static method có override không?** Không có override động như instance method; cùng chữ ký ở lớp con là che khuất phương thức (hiding). Constructor cũng không được kế thừa/override.

**`final`, `finally` khác nhau thế nào?** `final` giới hạn gán lại biến, override hàm hoặc kế thừa class. `finally` là khối sau try/catch, thường dùng dọn tài nguyên. Biến final tham chiếu object không làm object đó bất biến hoàn toàn.

**Mảng khác ArrayList?** Mảng có độ dài cố định sau khi tạo, truy cập theo chỉ số. ArrayList là cấu trúc thư viện có thể tăng sức chứa. Đồ án dùng mảng thật, chủ động tạo mảng lớn hơn khi đầy và chép dữ liệu sang. Sức chứa `ds.length` khác số phần tử hợp lệ `n`.

**Độ phức tạp?** Tìm theo mã O(n); xóa và dịch phần tử O(n); thêm thường O(1), một lần mở rộng O(n), chi phí thêm trung bình phân bổ O(1) khi tăng gấp đôi. Insertion sort O(n²) trường hợp xấu. Việc kiểm tra toàn vẹn sau thao tác có thêm chi phí riêng, ví dụ kiểm tra chồng khoảng thâm niên O(r²).

**`==` và `equals` khi so sánh String?** `==` so sánh tham chiếu. `equals` so sánh nội dung; `equalsIgnoreCase` bỏ qua hoa thường. Mã nhập vào được chuẩn hóa chữ hoa.

**Generic là gì?** Tham số kiểu giúp tái sử dụng mã với kiểm tra kiểu lúc biên dịch. `DanhSach<T extends BanGhi>` chỉ nhận kiểu có hợp đồng BanGhi. `DanhSachNhanVien` dùng `NhanVien[]`, `DanhSachPhongBan` dùng `PhongBan[]`.

**Vì sao không `new T[8]`?** Java không cho tạo trực tiếp mảng của tham số kiểu chưa xác định do type erasure. Lớp con truyền một mảng rỗng có kiểu cụ thể; lớp cha dùng `Arrays.copyOf` để tăng sức chứa, giữ kiểu mảng.

**Exception và try-with-resources?** Ngoại lệ báo trường hợp không thể thực hiện bình thường. Chương trình bắt lỗi số/ngày, quy tắc nghiệp vụ và I/O để thông báo. Try-with-resources tự đóng Scanner/stream khi kết thúc khối, kể cả khi có ngoại lệ.

## 3. Câu hỏi trực tiếp về đồ án

**Vì sao sơ đồ dùng ArrayList còn code dùng array?** Yêu cầu chấm ghi rõ lớp mảng các đối tượng. Em giữ các thực thể và quan hệ UML, đổi kho lưu danh sách thành mảng để thể hiện khả năng tự quản lý sức chứa, thêm, xóa, tìm và sắp xếp.

**Có bao nhiêu class?** 33 class ứng dụng và 2 class kiểm thử; thêm 3 interface. Riêng UML có 13 class thực thể và 11 danh sách, cùng Main, QuanLyNhanSu, FileStore. Không tính interface như class.

**Các class có phải chỉ tạo để đủ số lượng?** Các thực thể có dữ liệu và hợp đồng lưu text. Các danh sách có kiểu cụ thể và kế thừa chức năng quản lý mảng; DanhSachNhanVien còn có tìm kiếm. Phần nghiệp vụ nằm trong QuanLyNhanSu. Không cần chép lại cùng một thuật toán thêm/xóa ở 11 file.

**Điểm sử dụng interface cụ thể?** `NhanVien implements TinhLuong, BanGhi`. `QuanLyNhanSu implements IFileText`. `DanhSach` xử lý chung các phần tử có `getMa()` nhờ ràng buộc `T extends BanGhi`.

**Quan hệ các bảng được lưu thế nào?** Bằng mã: nhân viên chứa mã phòng/chức vụ; công và chi tiết thưởng/trừ chứa mã nhân viên; chi tiết chứa mã danh mục. `kiemTraToanVen()` xác nhận mã đích tồn tại. Trưởng phòng phải là nhân viên thuộc đúng phòng.

**Xóa nhân viên có xóa luôn lương không?** Không. Nếu có dữ liệu tham chiếu, thao tác bị từ chối và khôi phục. Chuyển tình trạng NGHI_VIEC để giữ lịch sử. Muốn xóa dữ liệu thử phải bỏ các liên kết trước.

**Tại sao không lưu sẵn giờ làm?** Giờ làm suy ra từ giờ vào/ra. Giờ hưởng lương còn phụ thuộc trạng thái. Tính từ nguồn giúp tránh một bản ghi có giờ vào/ra khác với tổng giờ đã lưu.

**Phân biệt lương cơ sở và thực nhận?** `tinhLuong()` không tham số minh họa lương cơ sở theo loại: CT theo tháng, TV theo ngày dự kiến. Hàm tính lương tháng đúng nghiệp vụ là `tinhLuongTheoCong(...)`, được gọi trong `lapBangLuong`, sau đó mới cộng/trừ các khoản. Không cộng trực tiếp CT theo tháng với TV theo ngày để lập quỹ lương thực tế.

**Thời vụ có lấy số giờ dự kiến để trả lương tháng không?** Không. `soGioLamTheoNgay` là thông tin hợp đồng/dự kiến. Lương tháng lấy tổng giờ hưởng lương từ chấm công và đơn giá giờ.

**Vì sao mã đi muộn không tự phạt?** Giờ chấm công dùng tính thời gian hưởng lương. Tiền phạt là khoản riêng phải được duyệt. Chức năng ghi khấu trừ theo quy định xác nhận ngày có trạng thái phù hợp rồi tạo chi tiết. Không tự trừ hai lần qua cả thao tác công và một khoản tự sinh không kiểm soát.

**Vì sao sửa lương cơ bản không đổi ngay các bảng lương đã lập?** Bảng lương là bản chụp thông số khi lập. Tính lại tháng nào thì chỉ thay bản chụp tháng đó; giữ các tháng khác. Bản hiện tại chưa có khóa kỳ, nên vẫn cho người quản lý tính lại kỳ cũ.

**Đọc file lỗi giữa chừng có mất dữ liệu không?** Không. Chương trình đọc vào hệ thống tạm, kiểm tra từng dòng, mã trùng và toàn bộ liên kết. Chỉ sau khi thành công mới thay dữ liệu đang dùng. Khi khởi động mà file lỗi, chương trình dừng và không tạo mẫu ghi đè lên file đó.

**Lưu file có đảm bảo tuyệt đối mọi sự cố không?** Chương trình ghi vào file tạm cùng thư mục rồi thay file đích. Nếu hệ thống file hỗ trợ atomic move, bước thay có tính nguyên tử; nếu không, có phương án thay thông thường. Chưa có cơ chế sao lưu nhiều phiên bản hay bảo đảm khi thiết bị lưu trữ hỏng.

**Giới hạn hiện tại?** Một bản ghi công/ngày, không tự trừ nghỉ trưa, không tính thuế/bảo hiểm hay hệ số tăng ca, không có đăng nhập/phân quyền hoặc nhiều người cùng ghi file. Phụ cấp theo quy tắc đơn giản mô tả trong README. Báo cáo lấy tên nhân viên hiện tại; bảng lương chụp các thông số lương, chưa chụp lịch sử đổi tên/phòng.

**Phát triển tiếp thế nào?** Tách ca làm việc, bổ sung khóa kỳ lương, nhật ký thay đổi, tính thuế/bảo hiểm theo yêu cầu riêng, chuyển tiền tệ sang BigDecimal và lưu database khi có nhiều người dùng. Đây là hướng phát triển, không phải chức năng đã cài đặt.

## 4. Bài tập gõ code thường gặp

Các đoạn dưới là bài tập tham khảo đặt vào class được nêu. Tự gõ, biên dịch và giải thích trước khi học thuộc.

### Tìm nhân viên theo mã — đặt trong DanhSachNhanVien

```java
public NhanVien timNhanVien(String ma) {
    for (int i = 0; i < getSoLuong(); i++) {
        NhanVien nv = get(i);
        if (nv.getMaNV().equalsIgnoreCase(ma)) return nv;
    }
    return null;
}
```

Giải thích trường hợp không thấy, phân biệt null với object rỗng, độ phức tạp O(n).

### Tổng thực nhận của một tháng — đặt trong QuanLyNhanSu

```java
public double tongThucNhan(YearMonth ky) {
    double tong = 0;
    for (BangLuong bl : bangLuongThang(ky)) {
        tong += bl.getLuongThucNhan();
    }
    return tong;
}
```

Lấy tổng bảng lương đã lập, không dùng tổng lương cơ sở của hai loại có đơn vị khác nhau.

### Nhân viên có thực nhận cao nhất — đặt trong QuanLyNhanSu

```java
public BangLuong caoNhat(YearMonth ky) {
    BangLuong max = null;
    for (BangLuong bl : bangLuongThang(ky)) {
        if (max == null || bl.getLuongThucNhan() > max.getLuongThucNhan()) {
            max = bl;
        }
    }
    return max;
}
```

Không khởi tạo max bằng 0 vì có thể tất cả khoản thực nhận đều âm. Khi bằng nhau, ví dụ này giữ bản ghi gặp trước.

### Đếm nhân viên trong phòng — đặt trong DanhSachNhanVien

```java
public int demTheoPhong(String maPB) {
    int dem = 0;
    for (NhanVien nv : this) {
        if (nv.getMaPB().equalsIgnoreCase(maPB)) dem++;
    }
    return dem;
}
```

### Chỉ ra đa hình bằng đoạn ngắn — đặt trong một hàm demo

```java
for (NhanVien nv : ql.getDsNhanVien()) {
    // Cung so cong/gio dau vao, CT va TV chon cong thuc khac nhau.
    double tien = nv.tinhLuongTheoCong(20, 160, 26);
    System.out.println(nv.getHoTen() + ": " + tien);
}
```

Không dùng `if (loai == CT)` để thay thế lời gọi hàm đã override khi giảng viên yêu cầu trình diễn đa hình.

### Ghi rồi đọc file qua biến interface

```java
QuanLyNhanSu ql = new QuanLyNhanSu();
ql.taoDuLieuMau();
IFileText tep = ql;
tep.ghiFile("data/thu-bao-ve.txt");
QuanLyNhanSu docLai = new QuanLyNhanSu();
docLai.docFile("data/thu-bao-ve.txt");
System.out.println(docLai.getDsNhanVien().getSoLuong()); // 2
```

Đặt trong hàm khai báo `throws IOException` hoặc dùng try/catch.

### Giải thích xóa một phần tử mảng

Mở `DanhSach.xoa`. Tìm vị trí i; dịch đoạn `[i+1, n)` sang trái một ô bằng `System.arraycopy`; giảm n rồi gán ô cuối bằng null. Nếu không tìm được, trả false. Giảng viên có thể yêu cầu thay `arraycopy` bằng vòng for:

```java
for (int j = i; j < n - 1; j++) ds[j] = ds[j + 1];
ds[--n] = null;
```

Đoạn này chỉ đặt bên trong `DanhSach`, nơi có quyền truy cập các thuộc tính private tương ứng.

## 5. Kịch bản demo 5–7 phút

1. Chạy `run.bat --demo`, giới thiệu hai loại nhân viên và kết quả lương 19.075.000đ.
2. Chạy `run.bat`, menu 1 → 1 xem nhân viên; quay lại bằng 0.
3. Menu 4 → 5, tìm `CC_NV02_2`, giải thích 08:30–16:00 = 7,5 giờ và trạng thái MUON.
4. Menu 11 → 2, nhập `2026-11`; menu 11 → 3 xem phiếu `NV02`.
5. Menu 12, nhập `2026-11`, mở file báo cáo trong thư mục reports.
6. Thử thêm trùng mã hoặc xóa PB01 đang được dùng; chương trình báo lỗi và giữ dữ liệu.
7. Thực hiện một thay đổi để chương trình tự lưu. Thoát bằng 0, mở lại để chứng minh dữ liệu được đọc từ file và còn nguyên.
8. Mở `NhanVien`, hai lớp con, `QuanLyNhanSu.lapBangLuong`, `DanhSach`, `FileStore` để chỉ ra abstract, kế thừa, override, mảng, static và file.
9. Chạy `java -cp build TestHeThong` để trình diễn kiểm thử tự động.

Nếu dữ liệu mẫu đã bị thay đổi, chạy demo trong bộ nhớ hoặc dùng file mới: `run.bat --data data\bao-ve-moi.txt`. Không xóa dữ liệu đang dùng chỉ để đưa về mẫu.
