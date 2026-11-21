# HƯỚNG DẪN HIỂU CODE VÀ BẢO VỆ ĐỒ ÁN

**Đề tài:** Quản lý nhân sự và tính lương bằng Java, chạy trên terminal.

**Bản hiện tại:** dùng mảng thường trong toàn bộ mã Java; không dùng `List`, `ArrayList`, `Map`, `Set` để quản lý dữ liệu hoặc kết quả xử lý.

Tài liệu dành cho ba việc giảng viên có thể kiểm tra: **hỏi lý thuyết, hỏi về chính đồ án và yêu cầu gõ/sửa code tại chỗ**. Các câu trả lời mẫu giúp em diễn đạt; cần mở code và chạy được ví dụ để hiểu trước khi học thuộc.

## Mục lục học nhanh

1. [Giới thiệu đề tài và luồng chạy](#1-giới-thiệu-đề-tài-và-luồng-chạy).
2. [Đối chiếu thang điểm của giảng viên](#2-đối-chiếu-thang-điểm-của-giảng-viên).
3. [Hiểu vai trò các lớp](#3-hiểu-vai-trò-các-lớp).
4. [Giải thích từng tiêu chí OOP bằng code của mình](#4-giải-thích-từng-tiêu-chí-oop-bằng-code-của-mình).
5. [Hiểu chấm công và tính lương](#5-hiểu-chấm-công-và-tính-lương).
6. [Câu hỏi lý thuyết thường gặp](#6-câu-hỏi-lý-thuyết-thường-gặp).
7. [Câu hỏi giảng viên có thể hỏi về đồ án](#7-câu-hỏi-giảng-viên-có-thể-hỏi-về-đồ-án).
8. [Bài tập gõ code có lời giải](#8-bài-tập-gõ-code-có-lời-giải).
9. [Kịch bản chạy demo khi bảo vệ](#9-kịch-bản-chạy-demo-khi-bảo-vệ).
10. [Cách ôn và các câu cần tránh trả lời sai](#10-cách-ôn-và-các-câu-cần-tránh-trả-lời-sai).

## 1. Giới thiệu đề tài và luồng chạy

### 1.1. Lời giới thiệu khoảng một phút

> Đồ án của em quản lý nhân sự và tính lương bằng Java, chạy trên terminal. Chương trình có hai loại nhân viên: chính thức và thời vụ. Hai lớp này kế thừa lớp trừu tượng NhanVien và có cách tính lương khác nhau. Em dùng mảng đối tượng để quản lý dữ liệu, có thêm, sửa, xóa, tìm kiếm và sắp xếp. Chương trình quản lý phòng ban, chức vụ, chấm công, phụ cấp, thưởng, khấu trừ và lập bảng lương từng tháng. Dữ liệu được đọc, ghi bằng file text UTF-8. Khi tính lương, chương trình gọi hàm qua biến kiểu NhanVien, còn cách tính cụ thể được chọn theo đối tượng thực tế. Các phần bổ sung gồm xuất phiếu/bảng lương, xử lý ca qua đêm, nghỉ phép hưởng lương và kiểm tra dữ liệu đầu vào.

### 1.2. Chương trình chạy như thế nào?

```text
run.bat
   ↓ biên dịch các file .java
Main.main(args)
   ↓
UngDungTerminal.khoiDong(args)
   ↓ đọc dữ liệu hoặc khởi tạo mẫu nếu chưa có file
UngDungTerminal.chay()
   ↓ người dùng chọn menu và nhập thông tin
QuanLyNhanSu
   ↓ kiểm tra nghiệp vụ, làm việc với các lớp danh sách
DanhSach<T> và các đối tượng
   ↓ lưu thay đổi
FileStore → data/qlns.txt
```

Khi được hỏi một thao tác, hãy giải thích theo luồng **nhập → kiểm tra → xử lý → lưu → hiển thị**.

Ví dụ thêm nhân viên: menu 1 → chọn thêm → nhập mã chưa trùng → nhập thông tin → tạo đối tượng CT hoặc TV → gọi `luuBanGhi` → kiểm tra phòng/chức vụ và các liên kết → lưu file.

### 1.3. Những file nên mở sẵn khi bảo vệ

| File | Dùng để trình bày |
|---|---|
| [NhanVien.java](NhanVien.java) | Thuộc tính chung, đóng gói, constructor, abstract, implements |
| [NhanVienChinhThuc.java](NhanVienChinhThuc.java) | Kế thừa, `super`, hệ số lương, override |
| [NhanVienThoiVu.java](NhanVienThoiVu.java) | Công thức tính lương theo giờ |
| [DanhSach.java](DanhSach.java) | Mảng `T[]`, số lượng `n`, thêm/xóa/tìm/sắp xếp |
| [DanhSachNhanVien.java](DanhSachNhanVien.java) | Mảng có kiểu `NhanVien[]`, tìm kiếm và xuất bảng |
| [QuanLyNhanSu.java](QuanLyNhanSu.java) | Nghiệp vụ, kiểm tra liên kết, chỗ gọi đa hình thực tế |
| [FileStore.java](FileStore.java) | Đọc/ghi file text, mảng dòng dữ liệu, UTF-8 |
| [TinhLuong.java](TinhLuong.java), [IFileText.java](IFileText.java), [BanGhi.java](BanGhi.java) | Ba interface của đồ án |
| [UngDungTerminal.java](UngDungTerminal.java), [NhapLieu.java](NhapLieu.java) | Menu, nhập liệu, báo lỗi, hủy thao tác |

## 2. Đối chiếu thang điểm của giảng viên

Các mục giảng viên đưa cộng thành **11 điểm**. Bảng dưới đối chiếu từng yêu cầu; điểm thực tế và cách quy đổi do giảng viên quyết định.

| Yêu cầu | Điểm ghi trong đề | Code minh họa cụ thể | Câu nói ngắn khi trả lời |
|---|---:|---|---|
| Ít nhất 15 class | 1 | 33 class ứng dụng | “Em có các lớp thực thể, danh sách và lớp điều phối, không tính interface vào số class.” |
| Các lớp có constructor | 1 | `NhanVien(...)`, `PhongBan(...)`, `DanhSach(T[] mangRong)`... | “Constructor khởi tạo trạng thái của đối tượng; lớp con gọi constructor cha bằng super.” |
| Kế thừa hợp lý | 1 | Hai loại nhân viên `extends NhanVien` | “Nhân viên chính thức và thời vụ đều là một nhân viên.” |
| Cài đặt và sử dụng đa hình | 1 | `QuanLyNhanSu.lapBangLuong` gọi `nv.tinhLuongTheoCong(...)` | “Biến có kiểu cha, object thật quyết định công thức lúc chạy.” |
| Lớp mảng các đối tượng | 2 | `DanhSach<T>` lưu `T[] ds`, `int n`; lớp con truyền `new NhanVien[0]`... | “Em dùng mảng thường, tự quản lý sức chứa và dịch phần tử khi xóa.” |
| Đọc và ghi file text | 1 | `FileStore.doc`, `ghi`, `docDong`, `ghiNguyenTu` | “Mở chương trình sẽ đọc file; thay đổi hoặc thoát sẽ lưu file UTF-8.” |
| Thuộc tính và phương thức static | 1 | `SO_GIO_MOT_CONG`, `NGAY_CONG_TIEU_CHUAN`, `KiemTra.so` | “Static thuộc lớp, được sử dụng thực tế trong tính công và kiểm tra dữ liệu.” |
| Abstract class và abstract method | 1 | `NhanVien`; `tinhLuong`, `tinhLuongTheoCong`, `getLoai` | “Lớp cha nêu hành vi chung, lớp con bắt buộc cài đặt phần riêng.” |
| Interface | 1 | `TinhLuong`, `IFileText`, `BanGhi` | “Interface là hợp đồng hành vi; các lớp thực hiện bằng implements.” |
| Sáng tạo | 1 | Ca qua đêm, phép hưởng lương, phiếu/báo cáo lương, bảo vệ liên kết | “Em bổ sung các trường hợp sử dụng cụ thể và kiểm tra đầu vào.” |

**Ưu tiên ôn:** phần mảng có 2 điểm, nên cần tự giải thích được `ds`, `n`, thêm, xóa, tìm, mở rộng và trả bản sao. Sau đó học chỗ sử dụng đa hình và đọc/ghi file.

## 3. Hiểu vai trò các lớp

### 3.1. Bốn nhóm chính

| Nhóm | Nhiệm vụ | Ví dụ |
|---|---|---|
| Thực thể | Mô tả dữ liệu và hành vi của đối tượng | `NhanVien`, `PhongBan`, `ChamCong`, `BangLuong` |
| Danh sách | Quản lý nhiều đối tượng cùng loại bằng mảng | `DanhSachNhanVien`, `DanhSachChamCong` |
| Điều phối nghiệp vụ | Kiểm tra quan hệ giữa các danh sách, lập lương | `QuanLyNhanSu` |
| Giao diện và file | Nhập/xuất terminal, kiểm tra dữ liệu, lưu trữ | `UngDungTerminal`, `NhapLieu`, `KiemTra`, `FileStore` |

Một `NhanVien` biểu diễn **một người**. `DanhSachNhanVien` quản lý **nhiều người**. `QuanLyNhanSu` kiểm tra nhân viên đó có thuộc phòng/chức vụ hợp lệ không và kết hợp dữ liệu để tính lương.

### 3.2. Danh sách class để chứng minh số lượng

| Nhóm | Các class | Số lượng |
|---|---|---:|
| Thực thể | `NhanVien`, `NhanVienChinhThuc`, `NhanVienThoiVu`, `PhongBan`, `ChucVu`, `ChamCong`, `TrangThai`, `QuyDinhNamPhuCapThamNien`, `Thuong`, `ChiTietThuong`, `QuyDinhTruLuong`, `ChiTietTruLuong`, `BangLuong` | 13 |
| Mảng và các danh sách | `DanhSach`, `DanhSachNhanVien`, `DanhSachPhongBan`, `DanhSachChucVu`, `DanhSachChamCong`, `DanhSachTrangThai`, `DanhSachQuyDinhThamNien`, `DanhSachThuong`, `DanhSachChiTietThuong`, `DanhSachQuyDinhTruLuong`, `DanhSachChiTietTruLuong`, `DanhSachBangLuong` | 12 |
| Khởi động và điều phối | `Main`, `UngDungTerminal`, `QuanLyNhanSu` | 3 |
| Hỗ trợ | `NhapLieu`, `KiemTra`, `FileStore` | 3 |
| Module cũ còn giữ | `QuanLyNhanVien`, `BangChamCong` | 2 |
| **Tổng class ứng dụng** | Không tính interface và class kiểm thử | **33** |

Project giữ 33 class ứng dụng, không kèm class kiểm thử. Ba interface: `TinhLuong`, `IFileText`, `BanGhi`.

Ngay cả khi không tính hai module cũ, phần ứng dụng chính vẫn vượt yêu cầu 15 class.

### 3.3. Các liên kết quan trọng

- Nhân viên chứa `maPB`, `maCV` để liên kết tới phòng ban và chức vụ.
- Chấm công chứa mã nhân viên, ngày, giờ và mã trạng thái.
- Chi tiết thưởng chứa mã nhân viên, mã danh mục thưởng, ngày và số tiền được duyệt.
- Chi tiết trừ lương chứa mã nhân viên, mã quy định, ngày và số tiền trừ.
- Bảng lương chứa mã nhân viên, tháng/năm, các thông số áp dụng và kết quả lúc lập.

**Câu trả lời mẫu:** “Các lớp liên kết qua mã. Vì dùng file text nên em tự kiểm tra khóa liên kết trong kiemTraToanVen, ví dụ không nhận nhân viên có mã phòng không tồn tại.”

Đây là quan hệ **có/sử dụng**, không phải quan hệ kế thừa. Phòng ban không kế thừa nhân viên.

## 4. Giải thích từng tiêu chí OOP bằng code của mình

### 4.1. Constructor và đóng gói

**Giảng viên hỏi:** Constructor để làm gì? Chỉ ra constructor trong đồ án.

**Trả lời ngắn:** “Constructor chạy khi tạo object bằng new, giúp khởi tạo dữ liệu ban đầu. Em dùng constructor có tham số để truyền đủ thông tin, đồng thời gọi setter để kiểm tra dữ liệu.”

Mở constructor trong `NhanVien.java`, rồi mở constructor của hai lớp con. Chỉ ra:

- Tên constructor giống tên class, không có kiểu trả về, kể cả `void`.
- `this.maNV = ...` gán thuộc tính của object hiện tại.
- `super(...)` trong lớp con khởi tạo phần thông tin chung của cha.
- `QuanLyNhanSu()` và `QuanLyNhanSu(String file)` là hai constructor khác danh sách tham số: một ví dụ **overload**.

**Đóng gói:** thuộc tính được khai báo `private`; thao tác đi qua getter/setter hoặc phương thức nghiệp vụ. Ví dụ `setSoDienThoai` gọi `KiemTra.soDienThoai`, từ chối số không đủ 10 hoặc 11 chữ số. Không phải cứ có getter/setter là đã bảo vệ được mọi quy tắc: liên kết giữa nhiều đối tượng còn được kiểm tra ở `QuanLyNhanSu`.

### 4.2. Kế thừa hợp lý

```java
public abstract class NhanVien implements TinhLuong, BanGhi { /* ... */ }
public final class NhanVienChinhThuc extends NhanVien { /* ... */ }
public final class NhanVienThoiVu extends NhanVien { /* ... */ }
```

Đây là đoạn rút gọn để nhận ra quan hệ, không phải ba class đầy đủ để chép vào một file.

**Giải thích:** cả hai loại đều có mã, họ tên, ngày sinh, phòng ban... nên đặt phần chung ở cha. Chính thức bổ sung hệ số lương. Thời vụ bổ sung đơn giá giờ và giờ dự kiến mỗi ngày.

**Giảng viên hỏi:** Tại sao không viết tất cả trong một class?

**Trả lời:** “Tách lớp giúp mỗi loại cài đặt công thức riêng, dùng lại phần chung và có thể xử lý chung qua kiểu NhanVien. Nếu sau này có loại mới, em có thể thêm lớp con và cài đặt các hàm trừu tượng.”

Java cho một class kế thừa trực tiếp một class cha, nhưng có thể thực hiện nhiều interface.

### 4.3. Abstract class và abstract method

Trong `NhanVien` có:

```java
@Override public abstract double tinhLuong();
public abstract double tinhLuongTheoCong(double ngayCong, double gioCong, double ngayChuan);
public abstract String getLoai();
```

- Không thể `new NhanVien(...)` vì đây là lớp abstract.
- Có thể khai báo biến `NhanVien nv` để tham chiếu object của lớp con.
- Lớp abstract vẫn có constructor, thuộc tính và hàm đã cài đặt như getter/setter.
- Lớp con cụ thể phải cài đặt các hàm abstract còn thiếu.

**Câu trả lời mẫu:** “Mọi nhân viên cần có cách tính lương, nhưng công thức phụ thuộc loại nhân viên. Vì vậy lớp cha khai báo hàm abstract, còn hai lớp con định nghĩa công thức.”

### 4.4. Đa hình: phải chỉ ra cả cài đặt và chỗ sử dụng

**Cài đặt:** mở `tinhLuongTheoCong` trong hai lớp con.

```text
Chính thức: lương cơ bản × hệ số / ngày chuẩn × ngày hưởng lương
Thời vụ:   giờ hưởng lương × đơn giá giờ
```

**Sử dụng thực tế:** mở `QuanLyNhanSu.lapBangLuong`, tìm dòng:

```java
double luong = KiemTra.tien(nv.tinhLuongTheoCong(cong, gio, ngayChuan));
```

Ở đây `nv` có kiểu khai báo `NhanVien`. Java chọn hàm của lớp con theo object thật lúc chạy.

**Trả lời mẫu:** “Em không chỉ override rồi để đó. Trong quá trình lập bảng lương, em duyệt nhân viên bằng biến kiểu cha và gọi hàm đã override. Nếu object là CT thì chạy công thức CT; nếu TV thì chạy công thức TV.”

`instanceof` còn được dùng để lấy thông số riêng phục vụ lưu bảng lương hoặc hiển thị, nhưng phép tính lương tháng vẫn đi qua lời gọi đa hình.

**Phân biệt hai hàm:** `tinhLuong()` là giá trị cơ sở: CT theo tháng đủ công, TV theo một ngày dự kiến. `tinhLuongTheoCong(...)` nhận dữ liệu chấm công để tính phần lương trong tháng. Không cộng thẳng hai giá trị cơ sở khác đơn vị để kết luận quỹ lương thực tế.

### 4.5. Mảng thường — phần cần học kỹ nhất

#### A. Mảng được khai báo ở đâu?

Trong `DanhSach.java`:

```java
private T[] ds;
private int n;
```

Trong `DanhSachNhanVien.java`:

```java
public DanhSachNhanVien() { super(new NhanVien[0]); }
public NhanVien[] getDsNhanVien() { return danhSach(); }
```

`T` là kiểu phần tử. Với danh sách nhân viên, mảng thực tế là `NhanVien[]`. Với danh sách công, là `ChamCong[]`.

**Trả lời mẫu:** “Em dùng mảng thường có kiểu đối tượng. DanhSach chứa thuật toán chung; các lớp con truyền mảng rỗng có kiểu cụ thể. Toàn bộ getter, kết quả tìm kiếm, file và báo cáo hiện cũng dùng mảng.”

#### B. `n` khác `ds.length` thế nào?

- `ds.length`: sức chứa của mảng đã tạo.
- `n`: số phần tử đang dùng.
- Các phần tử hợp lệ ở chỉ số `0` đến `n - 1`.
- Luôn giữ `0 <= n <= ds.length`.

Ví dụ mảng có 8 ô, mới thêm 3 nhân viên thì `ds.length = 8`, `n = 3`. Chỉ duyệt ba ô đầu; các ô còn lại chưa có đối tượng.

#### C. Mảng cố định thì tại sao thêm được nhiều người?

Một mảng đã tạo không tự đổi độ dài. Khi đầy, chương trình **tạo mảng lớn hơn, sao chép và đổi biến tham chiếu sang mảng mới**:

```java
if (n == ds.length) ds = Arrays.copyOf(ds, Math.max(8, n * 2));
ds[n++] = x;
```

Mảng ban đầu được cấp sức chứa 8. Khi có 8 phần tử mà cần thêm nữa, tạo mảng 16 ô. `Arrays.copyOf` là hàm sao chép mảng, không phải `ArrayList`.

#### D. Thêm một đối tượng gồm những bước nào?

1. Kiểm tra đối tượng không null.
2. Kiểm tra mã hợp lệ và chưa trùng.
3. Nếu đầy thì tạo mảng lớn hơn.
4. Gán vào ô `ds[n]`, sau đó tăng `n`.

Ở giao diện còn kiểm tra trùng ngay sau khi nhập mã để người dùng không phải nhập hết các trường. Lớp danh sách vẫn kiểm tra lại để bảo vệ khi được gọi từ chỗ khác.

#### E. Xóa khỏi mảng làm gì?

Tìm vị trí cần xóa, dịch các phần tử phía sau sang trái, giảm `n` và đặt ô dư cuối thành null:

```java
System.arraycopy(ds, i + 1, ds, i, n - i - 1);
ds[--n] = null;
```

Ví dụ:

```text
Trước xóa: [NV01, NV02, NV03, null], n = 3
Xóa NV02
Sau xóa:   [NV01, NV03, null, null], n = 2
```

Nếu giảng viên yêu cầu gõ lại không dùng `arraycopy`, viết vòng lặp tương đương **bên trong class DanhSach**:

```java
for (int j = i; j < n - 1; j++) ds[j] = ds[j + 1];
ds[--n] = null;
```

Không chỉ gán ô cần xóa thành null rồi giữ nguyên `n`, vì như vậy để lại lỗ trong đoạn đang dùng.

#### F. Tìm, sửa, sắp xếp như thế nào?

- `viTri`/`timTheoMa`: duyệt tuần tự, so sánh mã không phân biệt hoa thường.
- `sua`: tìm đúng vị trí, thay object mới và giữ nguyên mã để không phá liên kết.
- `sapXep`: insertion sort trực tiếp trên mảng, nhận Comparator để chọn tiêu chí.
- `DanhSachNhanVien.timKiem`: tạo mảng kết quả tạm, chép những phần tử khớp, trả bản sao đúng số kết quả; không thấy thì trả mảng rỗng.

#### G. Tại sao getter không trả thẳng `ds`?

```java
public T[] danhSach() { return Arrays.copyOf(ds, n); }
```

Getter trả **mảng sao chép**, nên gán `ketQua[0] = null` không làm mất ô đầu trong kho lưu.

Đây là bản sao mảng **nông**: các ô vẫn tham chiếu đến cùng object. Nếu gọi setter trên một object lấy được thì object đó vẫn thay đổi. Không nói rằng getter đã sao chép sâu hoặc làm mọi object bất biến.

#### H. Vì sao có `Iterable` và `Iterator` mà vẫn gọi là mảng?

Chúng chỉ cho phép viết `for (NhanVien nv : dsNhanVien)`. Iterator duyệt bản sao mảng; nơi lưu dữ liệu vẫn là `T[] ds`. Không có `List` hoặc `ArrayList` phía sau.

#### I. Tại sao không viết `new T[8]`?

Java không cho tạo trực tiếp mảng của tham số kiểu chưa biết cụ thể. Lớp con truyền `new NhanVien[0]`, sau đó `Arrays.copyOf` tạo mảng mới cùng kiểu. Cách này không cần ép một mảng `Object[]` thành `NhanVien[]`.

#### J. Độ phức tạp nên trả lời thế nào?

| Thao tác của lớp danh sách | Độ phức tạp |
|---|---|
| Lấy phần tử theo chỉ số | O(1) |
| Tìm mã | O(n) |
| Thêm cả kiểm tra mã trùng | O(n) vì có bước tìm mã |
| Mở rộng khi đầy | O(n) cho lần sao chép đó |
| Xóa, kể cả tìm và dịch | O(n) |
| Insertion sort | O(n²) trường hợp xấu |
| Trả bản sao mảng | O(n) |

Riêng bước ghi vào cuối, nếu bỏ qua tìm trùng, thường là O(1); tạo mảng mới khi đầy thì O(n). Đừng trả lời toàn bộ hàm `them` là O(1) vì code có duyệt tìm trùng.

Kiểm tra nghiệp vụ còn có chi phí riêng: so sánh các cặp công, bảng lương, khoảng thâm niên; không phải tất cả thao tác của toàn hệ thống đều O(n).

### 4.6. Interface

| Interface | Các hàm chính | Lớp dùng trong đồ án |
|---|---|---|
| `TinhLuong` | `double tinhLuong()` | `NhanVien` và hai lớp con qua kế thừa |
| `IFileText` | `ghiFile`, `docFile`, có thể ném IOException | `QuanLyNhanSu`, các module hỗ trợ cũ |
| `BanGhi` | `getMa`, `toFileString` | Các thực thể; là ràng buộc kiểu của `DanhSach<T>` |

**Câu trả lời mẫu:** “BanGhi bảo đảm các phần tử của DanhSach đều có mã để tìm và một cách biểu diễn để lưu file. Nhờ đó em viết một thuật toán quản lý mảng dùng lại cho nhiều loại thực thể.”

**So với abstract class:** interface chủ yếu nêu hợp đồng hành vi; lớp abstract còn thích hợp chứa trạng thái và phần cài đặt chung. Java hiện cho interface có hàm default/static; không nên nói interface tuyệt đối không thể có phần cài đặt.

### 4.7. Thuộc tính static và phương thức static

| Thành viên thực tế | Ý nghĩa |
|---|---|
| `ChamCong.SO_GIO_MOT_CONG` | Số giờ quy đổi một công, bằng 8 |
| `QuanLyNhanSu.NGAY_CONG_TIEU_CHUAN` | Giá trị chuẩn 26 dùng trong cấu hình/mẫu |
| `FileStore.PHIEN_BAN` | Dấu nhận diện phiên bản file `QLNS_TEXT_V1` |
| `KiemTra.so`, `KiemTra.soDienThoai`, `KiemTra.docTien` | Kiểm tra dữ liệu mà không cần tạo object KiemTra |
| `FileStore.docDong`, `FileStore.ghiNguyenTu` | Hỗ trợ I/O dùng chung |

**Trả lời mẫu:** “Static thuộc class và dùng chung giữa các object. Em gọi trực tiếp qua tên lớp, ví dụ KiemTra.so. Các hằng dùng static final để không bị thay đổi sau khi khởi tạo.”

Static method không có `this`. Nó không tự truy cập được thuộc tính instance nếu không có tham chiếu object.

### 4.8. Đọc và ghi file text

**Mở:** `FileStore.doc`, `docDong`, `ghi`, `ghiNguyenTu`; sau đó mở `QuanLyNhanSu.docFile`.

File đang dùng là `data/qlns.txt`. File báo cáo nằm trong `reports/`. File báo cáo là kết quả để đọc, không phải file nguồn thay thế `qlns.txt`.

**Luồng đọc:**

1. Đọc các dòng UTF-8 vào `String[]`, tăng sức chứa khi cần.
2. Kiểm tra dòng phiên bản `QLNS_TEXT_V1`.
3. Bỏ qua dòng trống và chú thích bắt đầu bằng `#`.
4. Tách trường bằng `;`, bỏ khoảng trắng căn cột.
5. Tạo đúng loại object; nhân viên CT và TV được khôi phục đúng lớp con.
6. Đưa vào hệ thống tạm, kiểm tra số trường, mã trùng và liên kết.
7. Chỉ thay dữ liệu đang dùng khi đọc và kiểm tra thành công.

**Luồng ghi:** kiểm tra dữ liệu → định dạng các nhóm/cột bằng mảng → ghi file tạm trong cùng thư mục → thay file đích. Nếu hệ thống file không hỗ trợ atomic move, chương trình dùng cách thay file thông thường.

**Giảng viên hỏi:** Bỏ menu lưu/đọc thì còn đạt yêu cầu không?

**Trả lời:** “Có. Chương trình đọc khi khởi động, lưu sau thay đổi và khi thoát bằng 0. Em bỏ menu riêng để giao diện gọn, nhưng phương thức đọc/ghi vẫn được gọi thực tế.”

Định dạng tiền trong file như `10.000.000`; phần đọc còn nhận kiểu cũ `1.0E7`. Căn cột giúp người xem đọc dễ hơn, không làm thay đổi giá trị lương.

Lưu ý giới hạn: ghi file tạm giảm nguy cơ file dở dang, không có nghĩa bảo đảm mọi sự cố thiết bị. Khi ghi file lỗi, cần xử lý thông báo và thử lưu lại; đây chưa phải hệ thống giao dịch database có rollback đầy đủ cho mọi lỗi I/O.

### 4.9. Sáng tạo nên trình bày bằng thao tác cụ thể

Chọn khoảng 3 điểm đã chạy được thay vì đọc một danh sách dài:

- **Báo trùng mã ngay:** thêm mã đã có → báo trước khi nhập các trường còn lại.
- **Hủy nhập:** gõ `/huy` tại ô nhập để quay về menu chính; ô ngày còn nhận `huy`.
- **Ca qua đêm:** vào 22:00, ra 06:00 được tính 8 giờ.
- **Nghỉ phép hưởng lương:** trạng thái PHEP quy đổi giờ theo cấu hình.
- **Bảng/phiếu lương và báo cáo:** căn bảng, định dạng VND, dùng chung nội dung giữa file và terminal.
- **Bảo vệ quan hệ:** không xóa phòng hoặc nhân viên khi còn dữ liệu tham chiếu.

## 5. Hiểu chấm công và tính lương

### 5.1. Các đại lượng không được nhầm

| Đại lượng | Nghĩa trong đồ án |
|---|---|
| Lương cơ bản | Tham số tính lương chính thức, chưa phải thực nhận |
| Hệ số lương | Số nhân với lương cơ bản của CT |
| Giờ dự kiến/ngày | Thông tin hợp đồng của TV, không thay thế giờ chấm công tháng |
| Giờ làm | Thời lượng tính từ giờ vào/ra |
| Giờ hưởng lương | Giờ sau khi áp dụng trạng thái, gồm cả giờ quy đổi nghỉ phép |
| Ngày hưởng lương | Tổng công quy đổi từng ngày, tối đa một công/ngày trong công thức CT |
| Thực nhận | Kết quả sau khi cộng phụ cấp/thưởng và trừ khấu trừ |

TV không cần nhập lương cơ bản trên menu. Do trường chung nằm trong lớp cha theo cấu trúc hiện tại, TV vẫn kế thừa trường này nhưng giao diện truyền 0 và bảng hiển thị `-`. Công thức TV không dùng nó.

### 5.2. Công thức của bản hiện tại

```text
Giờ làm = thời gian giữa giờ vào và giờ ra
Giờ hưởng lương = (giờ mặc định của trạng thái nếu > 0, ngược lại giờ làm) × hệ số trạng thái
Ngày hưởng lương = tổng min(giờ hưởng lương từng ngày / 8, 1)

CT: lương theo công = LCB × hệ số / ngày chuẩn × ngày hưởng lương
TV: lương theo công = giờ hưởng lương trong tháng × đơn giá giờ

Thực nhận = lương theo công + PC chức vụ + PC thâm niên + thưởng − khấu trừ
```

- Ngày chuẩn nhập khi lập lương, phải lớn hơn 0 và không quá 31; thường dùng 26.
- Hệ số CT phải lớn hơn 0; nếu muốn LCB đúng bằng lương tháng đủ công thì dùng hệ số 1.
- Đơn giá giờ TV phải lớn hơn 0.
- Lương theo công và thực nhận được làm tròn đến VND.

### 5.3. Ví dụ tính tay để đối chiếu

**Ví dụ dùng bộ mẫu của `--demo`, không nhất thiết trùng file công ty mà em đã sửa.**

CT: LCB 5.200.000, hệ số 2, ngày chuẩn 26, làm đủ 26 công:

```text
Theo công = 5.200.000 × 2 / 26 × 26 = 10.400.000
Thực nhận = 10.400.000 + 1.000.000 + 500.000 + 500.000 − 0
          = 12.400.000 VND
```

TV: 207,5 giờ, đơn giá 30.000, PC chức vụ 300.000, PC thâm niên 200.000, khấu trừ 50.000:

```text
Theo công = 207,5 × 30.000 = 6.225.000
Thực nhận = 6.225.000 + 300.000 + 200.000 + 0 − 50.000
          = 6.675.000 VND
```

Tổng hai người của bộ mẫu là **19.075.000 VND**.

### 5.4. Chấm công và trạng thái

- LV/MUON trong mẫu hưởng theo giờ vào/ra và hệ số 1.
- VANG có hệ số 0.
- PHEP có giờ mặc định 8 và hệ số 1; nhập `-` cho giờ vào/ra.
- Ca chưa chấm ra hưởng 0 giờ. Hai giờ bằng nhau cho 0 giờ.
- Ca 22:00–06:00 tính sang ngày kế tiếp, nhưng được gán vào ngày bắt đầu.
- 08:00–16:00 là 8 giờ; 08:00–17:00 là 9 giờ vì chương trình chưa tự trừ nghỉ trưa.
- Khi thêm/sửa trên terminal, ngày công không được sau hôm nay. Dữ liệu mẫu hoặc bản ghi đã lưu từ trước không tự bị xóa vì quy tắc nhập mới này.

Đồ án không tự loại thứ bảy/chủ nhật; bộ mẫu minh họa 26 ngày công để đối chiếu công thức.

### 5.5. Phụ cấp, thưởng và trừ lương

- Thâm niên tính đủ năm đến cuối tháng. Các khoảng năm gồm cả hai đầu và không được chồng nhau; ngoài khoảng cấu hình thì phụ cấp bằng 0.
- Theo quy tắc đơn giản hiện tại, tháng có giờ hưởng lương > 0 thì nhận đủ phụ cấp tháng; không chia phụ cấp theo tỷ lệ ngày công. Cả CT và TV đều đi qua quy tắc này.
- Thưởng và khấu trừ chỉ cộng các chi tiết có ngày thuộc kỳ đang lập.
- Danh mục thưởng là quy tắc/số tiền tham khảo; chi tiết là khoản đã duyệt cho một nhân viên vào một ngày.
- Ghi khấu trừ theo quy định có đơn vị LAN hoặc GIO. Điều kiện là mã trạng thái hoặc CHUNG; nếu là mã trạng thái thì phải có công cùng ngày phù hợp.
- Chấm công MUON không tự sinh tiền phạt. Người quản lý ghi khoản khấu trừ riêng. Giảm giờ hưởng lương và khoản phạt được duyệt là hai tác động khác nhau.
- Thực nhận âm được hiển thị như kết quả còn thiếu, không tự ép về 0.

### 5.6. Tại sao sửa công rồi lương chưa đổi?

Bảng lương là **bản chụp thông số khi lập**. Sau khi sửa công, lương, thưởng hoặc khấu trừ, cần vào menu 11 → 1 để tính lại kỳ liên quan. Tính lại thay kỳ đó, giữ các kỳ khác và không tạo hai phiếu cho cùng nhân viên/cùng tháng.

Chưa có khóa kỳ; người quản lý vẫn có thể tính lại kỳ cũ. Tên hiển thị trong báo cáo lấy từ hồ sơ hiện tại, chưa lưu lịch sử đổi tên/phòng theo từng thời điểm.

## 6. Câu hỏi lý thuyết thường gặp

| Câu hỏi | Trả lời ngắn, đúng ý |
|---|---|
| Class và object khác nhau? | Class là mô tả chung; object là thể hiện được tạo bằng `new`. `PhongBan` là class, `new PhongBan(...)` tạo một object. |
| Bốn tính chất OOP? | Đóng gói, kế thừa, đa hình, trừu tượng. Mỗi tính chất phải chỉ ra được ví dụ ở đồ án. |
| `this` và `super`? | `this` chỉ object hiện tại; `super` dùng phần thuộc lớp cha, đặc biệt `super(...)` gọi constructor cha. |
| Constructor có được override không? | Không. Constructor không được kế thừa và không override; có thể overload. |
| Khi nào Java tự tạo constructor không tham số? | Khi class chưa tự khai báo constructor nào. |
| Override và overload? | Override thay cách thực hiện hành vi đã có từ cha/interface; overload cùng tên nhưng khác danh sách tham số. Không overload chỉ bằng đổi kiểu trả về. |
| `private` và `protected`? | Private chỉ truy cập trực tiếp trong class khai báo. Protected hỗ trợ truy cập theo gói và quan hệ kế thừa theo quy tắc Java. Lớp con lấy trường private của cha qua phương thức được cho phép. |
| Static và instance? | Static thuộc lớp; instance thuộc từng object. |
| Static method có đa hình động không? | Không như phương thức instance override. Phương thức static cùng chữ ký ở lớp con là che khuất, không chọn theo object thật. |
| `final` nghĩa là gì? | Biến không gán lại, hàm không override, class không được kế thừa. Final tham chiếu không tự làm object bất biến. |
| Abstract class có constructor không? | Có; constructor cha vẫn chạy khi tạo object lớp con. |
| Interface có thuộc tính instance không? | Các trường khai báo trong interface là hằng public static final, không phải trạng thái riêng của từng object. |
| `extends` và `implements`? | Extends dùng cho kế thừa; implements cho class thực hiện hợp đồng interface. |
| `==` và `equals` với String? | `==` so sánh tham chiếu; `equals` so sánh nội dung. Mã dùng `equalsIgnoreCase` khi cần bỏ qua hoa thường. |
| `null` là object rỗng không? | Không. Null nghĩa là không tham chiếu object nào; gọi phương thức qua null có thể gây NullPointerException. |
| Primitive và kiểu tham chiếu? | `int`, `double` là giá trị nguyên thủy; `NhanVien`, `String`, mảng là kiểu tham chiếu. |
| Java truyền tham số thế nào? | Truyền theo giá trị. Với object, giá trị được chép là tham chiếu; sửa nội dung object có thể thấy ở bên gọi, nhưng gán lại biến tham số không gán lại biến của bên gọi. |
| Mảng chứa object hay tham chiếu? | Ô mảng đối tượng giữ tham chiếu; tạo `new NhanVien[8]` chưa tạo 8 nhân viên. Các ô ban đầu là null. |
| Generic có tác dụng gì? | Viết thuật toán dùng lại, giữ kiểm tra kiểu khi biên dịch. T bị ràng buộc bởi BanGhi nên gọi được getMa. |
| Tại sao dùng nextLine để nhập số? | Đọc thống nhất một dòng rồi parse, tránh sót ký tự xuống dòng khi trộn nextInt và nextLine. |
| Exception và `throws`? | Exception biểu diễn lỗi; throws khai báo lỗi có thể truyền lên, chưa phải đã xử lý lỗi. |
| Try-with-resources? | Tự đóng tài nguyên như reader/writer khi ra khỏi khối, kể cả lúc có ngoại lệ. |
| `finally` khác `final`? | Finally là khối xử lý sau try/catch; final là từ khóa giới hạn gán lại/override/kế thừa. |
| Vì sao dùng LocalDate/YearMonth? | LocalDate biểu diễn ngày không có giờ; YearMonth biểu diễn kỳ tháng/năm để lọc công, thưởng, trừ đúng tháng. |
| Vì sao dùng double, có hạn chế gì? | Phù hợp phạm vi đồ án và công thức hiện tại nhưng có sai số nhị phân. Phần lương được làm tròn đến VND; hệ thống tiền tệ chặt chẽ hơn có thể dùng BigDecimal. |

## 7. Câu hỏi giảng viên có thể hỏi về đồ án

**1. Tại sao đã có Main còn có UngDungTerminal?**

“Main là điểm vào chương trình. UngDungTerminal xử lý menu và tương tác. QuanLyNhanSu xử lý nghiệp vụ. Tách ra để sửa giao diện không phải sửa công thức.”

**2. Chức năng nào dùng mảng thường?**

“Tất cả lớp danh sách, getter, kết quả tìm kiếm, bảng lương theo tháng, các dòng file và các hàng của bảng hiển thị. Em mở DanhSach để chỉ T[] ds, rồi mở DanhSachNhanVien để chỉ NhanVien[].”

**3. Tại sao không viết một mảng riêng và chép thuật toán 11 lần?**

“Em dùng lớp DanhSach<T> để tái sử dụng thuật toán. Mỗi lớp con vẫn có kiểu mảng cụ thể và tên theo thực thể UML. Đây là dùng generic cùng mảng thường.”

**4. Có các lớp chỉ tạo cho đủ số lượng không?**

“Các thực thể mô tả những dữ liệu khác nhau, các danh sách quản lý mảng từng loại, lớp điều phối xử lý quan hệ và giao diện phục vụ nhập/xuất. Các lớp danh sách ngắn vì kế thừa thuật toán dùng chung.”

**5. Sửa mã nhân viên được không?**

“Thao tác sửa giữ nguyên mã để bảo toàn liên kết tới công, thưởng, khấu trừ, bảng lương. Em thay các thông tin còn lại của hồ sơ.”

**6. Vì sao không xóa được phòng đang có nhân viên?**

“Nếu xóa thì nhân viên giữ mã phòng không tồn tại. Hệ thống kiểm tra toàn vẹn và khôi phục thao tác khi liên kết không hợp lệ.”

**7. Nhân viên nghỉ việc xử lý thế nào?**

“Đổi tình trạng sang NGHI_VIEC để giữ lịch sử. Tháng có công/thưởng/trừ thì vẫn có thể được lập lương. Bản hiện tại chưa có ngày nghỉ việc riêng.”

**8. Có phải dữ liệu chỉ nằm trong RAM không?**

“Khi chạy, dữ liệu là object trong mảng ở RAM. Sau thay đổi, hệ thống ghi file text; lần mở sau tạo lại object từ file.”

**9. Tại sao file dùng dấu chấm tiền còn Java tính bằng số?**

“Dấu chấm là định dạng hiển thị/phân nhóm. Phần đọc chuyển về giá trị số trước khi tính. 1.0E7 và 10.000.000 đều có giá trị mười triệu ở các định dạng được hỗ trợ.”

**10. Tại sao mã công chứa mã nhân viên, có bỏ được cột MaNV không?**

“Mã như CC_NV01_1 dễ nhận biết cho người dùng. Liên kết nghiệp vụ vẫn dùng trường MaNV riêng, không cắt chuỗi MaCC để suy ra nhân viên.”

**11. Chương trình có tự gán mã công khi thêm không?**

“Hiện menu vẫn cho nhập mã. Phần tạo mẫu dùng dạng mới, và chương trình chuyển mã cũ như CC_A_1 khi mở/lưu. Mã mới vẫn phải duy nhất.”

**12. Chỗ nào thể hiện đa hình rõ nhất?**

“Dòng nv.tinhLuongTheoCong trong lapBangLuong, không chỉ phần khai báo extends hoặc kiểm tra instanceof.”

**13. Các dữ liệu thưởng, phụ cấp, lương có phải lúc nào cũng cập nhật trực tiếp không?**

“Chi tiết thưởng/trừ và bảng lương giữ số tiền lúc ghi/lập. Khi thay đổi dữ liệu nguồn, người quản lý chủ động tính lại kỳ lương.”

**14. Lỗi file giữa chừng thì sao?**

“Đọc vào một hệ thống tạm; chỉ thay dữ liệu đang dùng sau khi kiểm tra thành công. Lỗi có thông báo dòng. Khởi động với file lỗi sẽ báo lỗi, không tạo mẫu ghi đè.”

**15. KiemTra.java có phải chương trình test không?**

“Không. Đây là lớp hỗ trợ kiểm tra dữ liệu khi ứng dụng chạy, ví dụ mã rỗng, số âm, số điện thoại và định dạng tiền. Xóa lớp này sẽ làm chương trình không biên dịch được. Bản nộp hiện tại không kèm các file test riêng.”

**16. Phần nào chưa làm?**

“Chưa có database, đăng nhập/phân quyền, khóa kỳ lương, tính thuế/bảo hiểm, hệ số tăng ca hoặc nhiều ca/ngày. Đây là giới hạn của bản hiện tại, không trình bày như chức năng đã có.”

## 8. Bài tập gõ code có lời giải

Các bài dưới là **phương thức luyện tập**, đặt trong một class hỗ trợ khi thử. Dùng được với code hiện tại, nhưng không có nghĩa mọi tên hàm dưới đây đã tồn tại trong ứng dụng.

Để tự luyện: đọc đề → che lời giải → viết các bước bằng lời → gõ code → chạy với dữ liệu rỗng, một phần tử và nhiều phần tử. Các bài dùng mảng chỉ chứa phần tử hợp lệ, không gồm ô sức chứa dư. Trong class `DanhSach`, cần nhớ thêm biến `n`.

### Bài 1. Tìm nhân viên theo mã

Ý tưởng: duyệt từ đầu, khớp thì trả object; hết mảng thì trả null.

<!-- practice -->
```java
public static NhanVien timNhanVien(NhanVien[] ds, String ma) {
    for (int i = 0; i < ds.length; i++) {
        if (ds[i].getMaNV().equalsIgnoreCase(ma)) return ds[i];
    }
    return null;
}
```

Khi giải thích phải nói được: vì sao dùng equalsIgnoreCase, trường hợp không tìm thấy và độ phức tạp O(n).

### Bài 2. Đếm nhân viên thuộc một phòng

<!-- practice -->
```java
public static int demTheoPhong(NhanVien[] ds, String maPB) {
    int dem = 0;
    for (NhanVien nv : ds) {
        if (nv.getMaPB().equalsIgnoreCase(maPB)) dem++;
    }
    return dem;
}
```

Bài này đếm toàn bộ người thuộc phòng, gồm cả người đã nghỉ việc. Nếu đề yêu cầu chỉ người đang làm, bổ sung điều kiện `nv.getTinhTrang().equals("DANG_LAM")`.

### Bài 3. Lọc nhân viên chính thức thành mảng mới

<!-- practice -->
```java
public static NhanVienChinhThuc[] locChinhThuc(NhanVien[] ds) {
    NhanVienChinhThuc[] ketQua = new NhanVienChinhThuc[ds.length];
    int n = 0;
    for (NhanVien nv : ds) {
        if (nv instanceof NhanVienChinhThuc) {
            ketQua[n++] = (NhanVienChinhThuc) nv;
        }
    }
    return java.util.Arrays.copyOf(ketQua, n);
}
```

Đừng trả toàn bộ mảng tạm khi chỉ dùng `n` ô: các ô dư có thể là null. Không thấy CT thì trả mảng rỗng.

### Bài 4. Tính tổng thực nhận của một kỳ đã lập

<!-- practice -->
```java
public static double tongThucNhan(QuanLyNhanSu q, java.time.YearMonth ky) {
    double tong = 0;
    for (BangLuong bl : q.bangLuongThang(ky)) {
        tong += bl.getLuongThucNhan();
    }
    return tong;
}
```

Đây là tổng bảng lương thực tế đã lập. Không dùng lương cơ sở theo ngày của TV để cộng với lương cơ sở theo tháng của CT.

### Bài 5. Tìm phiếu có thực nhận cao nhất

<!-- practice -->
```java
public static BangLuong luongCaoNhat(BangLuong[] ds) {
    BangLuong max = null;
    for (BangLuong bl : ds) {
        if (max == null || bl.getLuongThucNhan() > max.getLuongThucNhan()) {
            max = bl;
        }
    }
    return max;
}
```

Khởi tạo max bằng null để xử lý được cả mảng rỗng và trường hợp mọi thực nhận đều âm. Nếu bằng nhau thì giữ người gặp trước.

### Bài 6. Thêm vào một mảng đủ phần tử, không có ô dư

<!-- practice -->
```java
public static NhanVien[] themNhanVien(NhanVien[] ds, NhanVien nv) {
    if (nv == null) throw new IllegalArgumentException("Nhan vien khong duoc null.");
    if (timNhanVien(ds, nv.getMaNV()) != null) {
        throw new IllegalArgumentException("Trung ma nhan vien.");
    }
    NhanVien[] moi = java.util.Arrays.copyOf(ds, ds.length + 1);
    moi[ds.length] = nv;
    return moi;
}
```

Bên gọi cần nhận lại mảng mới, chẳng hạn `ds = themNhanVien(ds, nv)`. Trong ứng dụng, class `DanhSach` có sức chứa và `n`, nên không phải mỗi lần thêm đều tăng đúng một ô như bài luyện đơn giản này.

### Bài 7. Xóa nhân viên khỏi mảng

<!-- practice -->
```java
public static NhanVien[] xoaNhanVien(NhanVien[] ds, String ma) {
    int viTri = -1;
    for (int i = 0; i < ds.length; i++) {
        if (ds[i].getMaNV().equalsIgnoreCase(ma)) {
            viTri = i;
            break;
        }
    }
    if (viTri == -1) return ds;
    NhanVien[] moi = new NhanVien[ds.length - 1];
    for (int i = 0, j = 0; i < ds.length; i++) {
        if (i != viTri) moi[j++] = ds[i];
    }
    return moi;
}
```

Trường hợp không tìm thấy trả mảng ban đầu. Hàm này chỉ luyện thao tác mảng; muốn xóa trong hệ thống thật phải đi qua kiểm tra liên kết của `QuanLyNhanSu`.

### Bài 8. Sắp xếp họ tên bằng insertion sort

<!-- practice -->
```java
public static void sapXepTheoTen(NhanVien[] ds) {
    for (int i = 1; i < ds.length; i++) {
        NhanVien x = ds[i];
        int j = i - 1;
        while (j >= 0 && ds[j].getHoTen().compareToIgnoreCase(x.getHoTen()) > 0) {
            ds[j + 1] = ds[j];
            j--;
        }
        ds[j + 1] = x;
    }
}
```

Đây là so sánh toàn bộ chuỗi họ tên, chưa tách riêng tên gọi cuối. Hàm thay đổi thứ tự của mảng truyền vào; nếu muốn giữ thứ tự cũ, sao chép mảng trước khi sắp xếp.

### Hai yêu cầu gõ code khác dễ gặp

**Gọi qua interface:** đặt trong hàm có `throws java.io.IOException` hoặc try/catch.

```java
IFileText tep = q; // q la QuanLyNhanSu da co du lieu
tep.ghiFile("data/thu-bao-ve.txt");
QuanLyNhanSu docLai = new QuanLyNhanSu();
docLai.docFile("data/thu-bao-ve.txt");
```

**Minh họa đa hình bằng cùng một kiểu biến:** `q` là hệ thống đã có cả CT và TV.

```java
for (NhanVien nv : q.getDsNhanVien()) {
    double tien = nv.tinhLuongTheoCong(20, 160, 26);
    System.out.println(nv.getMaNV() + ": " + tien);
}
```

Lương trên là phần theo công để minh họa chọn công thức, chưa cộng phụ cấp/thưởng/trừ. Khi giảng viên yêu cầu thêm một loại nhân viên mới, em cần tạo lớp con, cài đặt các hàm abstract và xem xét cả nhập liệu lẫn đọc/ghi file cho loại mới; không chỉ thêm một class rỗng.

## 9. Kịch bản chạy demo khi bảo vệ

### 9.1. Các lệnh chạy

Tại `D:\OOP\DoAn`:

```powershell
.\run.bat
```

Chạy mẫu trong bộ nhớ rồi thoát, không ghi đè dữ liệu công ty:

```powershell
.\run.bat --demo
```

Muốn có một file dùng riêng cho buổi tập, chọn tên chưa tồn tại:

```powershell
.\run.bat --data data\bao-ve-mau.txt
```

Nếu file đó đã tồn tại, chương trình đọc nó thay vì tạo lại. Trong menu mới, CT là **chi tiết**, QD là **quy định**, PC là **phụ cấp**; CT/TV ở ô loại nhân viên vẫn là chính thức/thời vụ.

### 9.2. Demo 5–7 phút

1. Giới thiệu đề tài khoảng một phút theo mục 1.1.
2. Chọn 1 → 1 xem danh sách nhân viên, giải thích CT và TV; chọn 0 để về menu chính.
3. Chọn 4 → 5, nhập `CC_NV02_2` của bộ mẫu: 08:30–16:00 là 7,5 giờ, trạng thái MUON. Chọn 0 để quay lại.
4. Chọn 11 → 2, nhập `2026-11`; giải thích một dòng lương bằng phép tính tay ở mục 5.3.
5. Chọn 12, nhập `2026-11`, mở `reports/bang-luong-2026-11.txt` để chứng minh xuất file text.
6. Thử thêm trùng mã, quan sát báo ngay; gõ `/huy` để hủy. Hoặc thử xóa PB01 của bộ mẫu đang có nhân viên để chứng minh bảo vệ liên kết.
7. Chọn 0 để lưu và thoát; mở lại cùng file để chứng minh có đọc dữ liệu từ file.
8. Mở các file ở mục 1.3, chỉ lần lượt abstract, kế thừa, chỗ gọi đa hình, mảng và static.

Demo có thể thay đổi dữ liệu trong file thực hành riêng. Không cần sửa dữ liệu công ty chỉ để đưa về mẫu.

### 9.3. Kiểm tra trực tiếp và demo nhiều tháng

Bản nộp chỉ giữ code chương trình chính, không có file test riêng. Trình bày cách em kiểm tra qua chính menu đang chạy:

1. Chọn **11 → 2**, nhập `2026-09`; xem bảng công và thực nhận cho cả 3 nhân viên. Lặp lại với `2026-10` để chứng minh lọc đúng tháng.
2. Chọn **4 → 1** để xem các trạng thái LV, MUON, PHEP, VANG trong dữ liệu bổ sung. Mỗi nhân viên có 26 bản ghi/tháng; MUON giảm giờ hưởng lương, PHEP vẫn hưởng 8 giờ, VANG hưởng 0 giờ.
3. Chọn **8 → 1** và **10 → 1** để chỉ ra thưởng/khấu trừ có ngày thuộc kỳ lương; giải thích chấm công không tự sinh khoản phạt.
4. Chọn **12**, nhập tháng cần xuất. Mở `reports/bang-luong-2026-09.txt` hoặc `reports/bang-luong-2026-10.txt` để đối chiếu số tiền với terminal.
5. Thử nhập trùng mã, ngày không hợp lệ, số điện thoại sai hoặc xóa phòng có nhân viên; chương trình phải báo lỗi. Gõ `/huy` để quay về.
6. Chọn 0 để lưu và thoát; chạy lại rồi xem cùng tháng để chứng minh đọc/ghi file.

Dữ liệu bổ sung là dữ liệu minh họa tháng 9–10/2026 cho nhân viên hiện có, dùng lương và phụ cấp hiện tại, không mô phỏng thay đổi hợp đồng trong quá khứ. Bảng lương tháng 11 cũ được giữ nguyên; chỉ tính lại nếu người dùng yêu cầu trong menu 11.

## 10. Cách ôn và các câu cần tránh trả lời sai

### 10.1. Nếu có khoảng hai giờ để ôn

| Thời gian | Việc cần làm |
|---|---|
| 15 phút | Tự chạy menu, thêm/tìm một nhân viên và hủy một thao tác |
| 30 phút | Mở DanhSach, giải thích n/sức chứa và tự gõ tìm, thêm, xóa |
| 20 phút | Mở NhanVien và hai lớp con, chỉ đúng chỗ gọi đa hình trong lập lương |
| 15 phút | Mở FileStore, nói lại luồng đọc/ghi và file đang dùng |
| 20 phút | Tính tay lương CT/TV mẫu, hiểu phụ cấp và chi tiết thưởng/trừ |
| 20 phút | Tự hỏi lý thuyết, làm một bài code chưa nhìn lời giải |

### 10.2. Những câu dễ nói sai

| Đừng trả lời | Nên trả lời |
|---|---|
| “Mảng tự tăng độ dài.” | “Em tạo mảng mới lớn hơn rồi sao chép và gán lại tham chiếu.” |
| “Có extends là đã dùng đa hình.” | “Em còn gọi hàm override qua biến NhanVien trong lapBangLuong.” |
| “Hàm thêm chắc chắn O(1).” | “Hàm có tìm mã trùng O(n); riêng bước chèn cuối thường O(1).” |
| “Getter sao chép nên object cũng hoàn toàn độc lập.” | “Chỉ sao chép mảng; các phần tử vẫn tham chiếu cùng object.” |
| “Interface không thể có code.” | “Interface có thể có default/static method, nhưng interface của em chủ yếu nêu hợp đồng.” |
| “Static method được override động.” | “Static không được chọn theo object thật như instance method.” |
| “TV nhận lương cơ bản nhân hệ số.” | “TV nhận theo giờ hưởng lương nhân đơn giá giờ.” |
| “Giờ dự kiến mỗi ngày là giờ để trả lương tháng.” | “Tháng lấy giờ hưởng lương từ chấm công; giờ dự kiến là thông tin hợp đồng.” |
| “Sửa lương cơ bản là tất cả kỳ cũ tự đổi.” | “Bảng lương lưu bản chụp; phải tính lại kỳ muốn cập nhật.” |
| “Tất cả điều kiện đều chặn ngay cả khi sửa tay file.” | “Có kiểm tra tại nhập liệu và kiểm tra nghiệp vụ; ví dụ giới hạn ngày công tương lai hiện nằm ở luồng nhập terminal.” |
| “File báo cáo là database.” | “Báo cáo là file text để đọc; file nguồn của hệ thống là data/qlns.txt.” |

### 10.3. Tự kiểm tra trước khi đi bảo vệ

Em nên tự trả lời được các câu sau mà không nhìn tài liệu:

- Chương trình bắt đầu ở đâu và file nào đang lưu dữ liệu?
- Kể ít nhất 15 class cùng nhiệm vụ thật của chúng.
- Chỉ constructor cha/con, một trường private và một hàm kiểm tra dữ liệu.
- Chỉ một phương thức abstract, một interface và một thành viên static được dùng.
- Phân biệt cài đặt override với chỗ gọi đa hình trong đồ án.
- Nêu đúng n, sức chứa, cách thêm/xóa/tìm trên mảng và bản sao mảng nông.
- Tính được một phiếu lương CT và một phiếu TV.
- Gõ được ít nhất tìm theo mã, đếm theo phòng, xóa khỏi mảng và tìm lớn nhất.
- Giải thích được một lỗi nhập và cách hủy thao tác.
- Nói rõ giới hạn của bản hiện tại và hướng phát triển, không nhận những chức năng chưa cài đặt.
