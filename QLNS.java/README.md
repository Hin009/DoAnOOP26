# Quản lý nhân sự và tính lương — Java OOP, chạy terminal

Ứng dụng theo sơ đồ UML được cung cấp: nhân viên chính thức/thời vụ, phòng ban, chức vụ, chấm công, trạng thái, phụ cấp thâm niên, thưởng, khấu trừ và bảng lương tháng. Không dùng database, Maven hoặc thư viện ngoài. Dữ liệu được lưu trong file text UTF-8.

## Chạy trên Windows

Cần **JDK 17 trở lên** (có cả `java` và `javac` trong PATH). Đã kiểm tra bằng JDK 24.0.1 trên máy hiện tại.

PowerShell:

```powershell
cd D:\OOP\DoAn\QLNS.java
.\run.bat
```

Hoặc dùng script PowerShell (nếu máy cho phép chạy script):

```powershell
.\run.ps1
.\run.ps1 -Demo
.\run.ps1 -Test
```

Biên dịch/chạy thủ công:

```powershell
New-Item -ItemType Directory -Force build
$sources = Get-ChildItem -File -Filter '*.java' | ForEach-Object FullName
javac -encoding UTF-8 -d build $sources
java '-Dfile.encoding=UTF-8' -cp build Main
```

Trong CMD, có thể dùng `javac -encoding UTF-8 -d build *.java`. Trên Linux/macOS: `mkdir -p build`, `javac -encoding UTF-8 -d build *.java`, `java -cp build Main`.

Các tùy chọn:

```powershell
.\run.bat --demo
.\run.bat --data data\cong-ty.txt
.\run.bat --data data\trong.txt --trong
java -cp build TestHeThong
```

`--demo` chỉ chạy mẫu trong bộ nhớ rồi thoát. `--trong` bắt đầu rỗng khi file chỉ định chưa tồn tại; không xóa file đã có. Mặc định dùng `data/qlns.txt`, đường dẫn tính từ thư mục chương trình khi dùng script. Lần chạy đầu tự tạo dữ liệu minh họa tháng **11/2026**, gồm 2 nhân viên và 52 lượt chấm công. Dữ liệu minh họa có cả những ngày sau ngày hiện tại để trình diễn một tháng đủ công.

## Chức năng

| Menu | Nội dung |
|---|---|
| 1–7, 9 | Thêm, sửa, xóa, xem, tìm theo mã đối với nhân viên, phòng ban, chức vụ, chấm công, trạng thái, thâm niên, danh mục thưởng và quy định trừ lương |
| 8, 10 | Ghi thưởng/khấu trừ theo danh mục; xem, sửa, xóa hoặc nhập khoản được duyệt riêng |
| 11 | Lập/tính lại lương tháng; xem bảng lương; xem phiếu lương từng nhân viên |
| 12 | Xuất báo cáo text tại `reports/bang-luong-yyyy-MM.txt` |
| 13 | Tìm tên/mã/phòng, sắp xếp tên, thống kê phòng, tổng hợp công tháng và ca chưa chấm ra |
| 0 | Lưu và thoát |

Trong menu quản lý, chọn `1 Xem`, `2 Thêm`, `3 Sửa`, `4 Xóa`, `5 Tìm`, `0 Quay lại`. Khi sửa, nhập lại các trường còn lại và giữ nguyên mã. Các ô ngày nhận ngày/tháng/năm như `9/10/2003`, `09/10/2003` hoặc `9-10-2003`; vẫn nhận định dạng cũ `2003-10-09`. Nhập ngày sai sẽ được yêu cầu nhập lại ngay tại ô đó. Các lỗi nhập khác được thông báo và đưa về menu chính. Mỗi thay đổi qua menu được lưu ngay; đọc file hỏng lúc khởi động sẽ báo lỗi và dừng để giữ file gốc.

Để nhập từ đầu, tạo phòng ban (trưởng phòng `-`) và chức vụ, tiếp theo tạo nhân viên, rồi sửa phòng để gán trưởng phòng. Tạo trạng thái trước khi chấm công, tạo danh mục trước khi ghi thưởng/khấu trừ. Không thể xóa đối tượng còn được tham chiếu. Nhân viên có lịch sử nên chuyển `NGHI_VIEC` để giữ dữ liệu cũ.

Số điện thoại bắt buộc có đúng **10 hoặc 11 chữ số**, giữ nguyên số 0 đầu; nhập sai sẽ được nhập lại ngay. Các ô nhập tiền có đơn vị **VND**, nhận số nguyên liền hoặc có dấu phân nhóm hàng nghìn (`50000`, `50.000`, `50,000`). Chương trình xác nhận số tiền theo dạng `50.000 VND`. Dấu chấm/phẩy trong ô tiền dùng phân nhóm hàng nghìn, không dùng cho phần thập phân; ô hệ số lương và giờ vẫn nhập số như trước.

Trong `data/qlns.txt`, các cột tiền được ghi theo dạng Việt Nam (`10.000.000`), không dùng ký hiệu khoa học (`1.0E7`). Khi đọc vẫn hỗ trợ số kiểu cũ; các cột hệ số, ngày công và giờ giữ định dạng số riêng. File có khoản tiền lẻ thì dùng dấu phẩy thập phân để giữ nguyên giá trị.

Để hủy thao tác đang nhập ở bất kỳ chức năng nào, gõ **`/huy`** rồi Enter. Chương trình bỏ dữ liệu đang nhập dở và quay về menu chính; các thao tác đã hoàn tất trước đó vẫn được giữ. Lệnh này dùng được cả trong vòng nhập lại ngày, số điện thoại hoặc tiền. `0` vẫn là giá trị số hợp lệ trong ô dữ liệu và chỉ có nghĩa quay lại/thoát tại menu tương ứng.

Riêng ô nhập ngày cũng chấp nhận `huy` hoặc `Huy` không cần dấu `/`. Ở ô họ tên vẫn dùng `/huy` để không nhầm với người tên Huy.

## Quy tắc tính lương

```text
Giờ làm = khoảng thời gian từ giờ vào đến giờ ra
Giờ hưởng lương = (giờ mặc định nếu > 0, ngược lại giờ làm) × hệ số trạng thái
Ngày công hưởng lương = tổng min(giờ hưởng lương từng ngày / 8, 1)

Chính thức: lương theo công = LCB × hệ số lương / ngày chuẩn × ngày công hưởng lương
Thời vụ:   lương theo công = tổng giờ hưởng lương × đơn giá giờ

Thực nhận = lương theo công + phụ cấp chức vụ + phụ cấp thâm niên
            + tổng thưởng trong tháng − tổng khấu trừ trong tháng
```

- Ngày chuẩn mặc định gợi ý 26, cho phép nhập lớn hơn 0 đến 31 khi lập lương.
- Giờ ra nhỏ hơn giờ vào được hiểu là ca qua đêm; ca tính vào ngày bắt đầu. Không tự trừ giờ nghỉ giữa ca. Giờ vào bằng giờ ra là 0 giờ; ca chưa có giờ ra hưởng 0 giờ.
- Mỗi nhân viên có tối đa một bản ghi/ngày. Nhân viên chính thức hưởng tối đa 1 công/ngày; thời vụ tính theo giờ. Không thêm hệ số tăng ca riêng.
- Khi thêm/sửa nhân viên thời vụ (TV), không hỏi lương cơ bản; bảng nhân viên hiển thị `-` ở cột này. `Số giờ dự kiến mỗi ngày theo hợp đồng` mô tả lịch làm dự kiến, còn lương tháng lấy giờ hưởng lương từ chấm công nhân với đơn giá giờ.
- Mã chấm công dùng cách đặt dễ nhận biết `CC_<MaNV>_<so thu tu>`, chẳng hạn `CC_NV01_1`, `CC_NV02_2`, `CC_NV3_1`. Số thứ tự phải tạo mã duy nhất trong toàn bộ danh sách, không tự bắt đầu lại khi sang tháng khác.
- Khi khởi động và khi lưu, chương trình tự chuyển các mã cũ như `CC_A_1`, `CC_B_2`, `CC_B20` sang dạng chứa mã nhân viên. Nếu số đó đã dùng, chương trình chọn số còn trống; ngày, giờ và trạng thái không thay đổi.
- Khi thêm/sửa chấm công trên terminal, ngày chấm công không được sau hôm nay (múi giờ Asia/Bangkok). Ngày tương lai bị báo ngay tại ô nhập và yêu cầu nhập lại; vẫn dùng được `/huy`. Dữ liệu mẫu và bản ghi đã lưu từ trước không tự động bị xóa hoặc đổi ngày.
- Trạng thái mẫu: `LV`, `MUON` có hệ số 1, giờ mặc định 0; `VANG` hệ số 0; `PHEP` hệ số 1, mặc định 8 giờ. Với trạng thái nghỉ/vắng, nhập `-` cho cả hai giờ.
- Hai trường giờ làm và giờ hưởng lương trong UML được **tính từ dữ liệu nguồn**, tránh lưu trùng rồi sai lệch. `TrangThai` bổ sung hệ số và giờ mặc định để hỗ trợ nghỉ phép.
- Thâm niên tính đủ năm đến cuối tháng; khoảng năm bao gồm hai đầu và không được chồng nhau. Ngoài các khoảng cấu hình thì phụ cấp bằng 0.
- Có ít nhất một giờ hưởng lương trong tháng thì nhận đủ phụ cấp tháng theo cấu hình; không có giờ hưởng lương thì không nhận phụ cấp. Không chia tỷ lệ phụ cấp theo ngày công.
- Chỉ tính chi tiết thưởng/trừ có ngày thuộc tháng đang lập. Điều kiện thưởng là mô tả để người quản lý duyệt; không tự phân tích văn bản. Khi ghi theo danh mục, chương trình chụp số tiền hiện tại.
- Quy định trừ có đơn vị `LAN` (số lượng nguyên) hoặc `GIO` (cho phép lẻ). Điều kiện là mã trạng thái hoặc `CHUNG`; chức năng ghi theo quy định kiểm tra chấm công cùng ngày khi điều kiện là mã trạng thái. Khoản nhập số tiền riêng là khoản người quản lý duyệt, không tự suy ra từ giờ đi muộn. Chấm công **không tự tạo khoản phạt**.
- Làm tròn lương theo công và thực nhận đến VND. Tiền sử dụng `double` với kiểm tra hữu hạn; phạm vi đồ án không mô phỏng toàn bộ nghiệp vụ tiền tệ thực tế.
- Thực nhận âm được hiển thị để thể hiện khoản còn thiếu; không tự ép về 0.
- Bảng lương giữ thông số tại lúc lập. Sau khi sửa công, lương hoặc thưởng/phạt, vào menu 11 để **tính lại** tháng liên quan. Tính lại thay bảng lương cùng tháng, giữ các tháng khác. Nhân viên nghỉ việc vẫn được lập lương nếu tháng đó có công/thưởng/khấu trừ.

Kết quả mẫu tháng 11/2026:

| Nhân viên | Theo công | PC chức vụ | PC thâm niên | Thưởng | Trừ | Thực nhận |
|---|---:|---:|---:|---:|---:|---:|
| NV01 chính thức, 26 công | 10.400.000 | 1.000.000 | 500.000 | 500.000 | 0 | **12.400.000** |
| NV02 thời vụ, 207,5 giờ | 6.225.000 | 300.000 | 200.000 | 0 | 50.000 | **6.675.000** |
| Tổng | | | | | | **19.075.000** |

## Đối chiếu tiêu chí chấm

| Tiêu chí | Bằng chứng mã nguồn |
|---|---|
| Ít nhất 15 class | **33 class ứng dụng**, cộng 2 class kiểm thử; 3 interface không tính vào số class |
| Constructor | Tất cả class đều khai báo constructor; lớp con gọi `super(...)` |
| Kế thừa hợp lý | `NhanVienChinhThuc`, `NhanVienThoiVu` kế thừa `NhanVien`; 11 danh sách kế thừa lớp mảng `DanhSach<T>` |
| Cài đặt và sử dụng đa hình | `QuanLyNhanSu.lapBangLuong` gọi `nv.tinhLuongTheoCong(...)` qua biến `NhanVien`; mỗi loại thực thi công thức riêng |
| Lớp mảng các đối tượng | `DanhSach<T>` lưu `T[] ds`, có `n`, mở rộng bằng `Arrays.copyOf`, dịch mảng khi xóa và insertion sort; `DanhSachNhanVien` truyền `new NhanVien[0]` |
| Đọc/ghi file text | `FileStore` đọc toàn bộ khi mở chương trình, tự lưu sau thay đổi và khi thoát; UTF-8, phát hiện lỗi theo dòng, thay thế bằng file tạm |
| Static | `QuanLyNhanSu.NGAY_CONG_TIEU_CHUAN`, `ChamCong.SO_GIO_MOT_CONG`, `FileStore.PHIEN_BAN`; các hàm `KiemTra.*`, `FileStore.ghiNguyenTu` được sử dụng thực tế |
| Abstract class/method | `NhanVien` là abstract; khai báo abstract `tinhLuong()`, `tinhLuongTheoCong(...)`, `getLoai()` |
| Interface | `TinhLuong`, `IFileText`, `BanGhi`; `QuanLyNhanSu implements IFileText`, `NhanVien implements TinhLuong, BanGhi` |
| Sáng tạo | Ca qua đêm, nghỉ phép hưởng lương, báo cáo/phiếu lương, tìm kiếm/thống kê, bảo vệ liên kết, giữ dữ liệu khi file lỗi, kiểm thử tự động |

Các mức điểm trong yêu cầu cộng thành **11 điểm**; bảng này chỉ đối chiếu chức năng, việc cho điểm/quy đổi do giảng viên quyết định.

UML gốc dùng `ArrayList`. Bản triển khai chủ động dùng mảng đối tượng có kiểu để đáp ứng yêu cầu chấm “array”. `List`/`ArrayList` chỉ dùng cho bản sao đọc, kết quả tìm kiếm, báo cáo và việc xử lý dòng text, không làm kho lưu chính của các lớp danh sách.

## Cấu trúc

- `Main` → `UngDungTerminal`/`NhapLieu`: khởi động và menu.
- `QuanLyNhanSu`: điều phối nghiệp vụ, liên kết và tính lương.
- `NhanVien`, `NhanVienChinhThuc`, `NhanVienThoiVu`: trừu tượng, kế thừa, đa hình.
- `PhongBan`, `ChucVu`, `ChamCong`, `TrangThai`, `QuyDinhNamPhuCapThamNien`, `Thuong`, `ChiTietThuong`, `QuyDinhTruLuong`, `ChiTietTruLuong`, `BangLuong`: các thực thể UML.
- `DanhSach` và 11 lớp `DanhSach...`: mảng đối tượng theo từng thực thể.
- `FileStore`, `KiemTra`: file text và kiểm tra dữ liệu.
- `QuanLyNhanVien`, `BangChamCong`: giữ API module cũ để chạy riêng; menu chính sử dụng các lớp `DanhSach...` thống nhất. `QuanLyNhanVien` tái sử dụng mảng của `DanhSachNhanVien`.
- `TestHeThong`: kiểm thử tích hợp bằng Java thuần; `TestBangChamCong`: chương trình thử module chấm công cũ.
- [BAO_VE_DO_AN.md](BAO_VE_DO_AN.md): lý thuyết, câu hỏi về đồ án, bài tập gõ code và kịch bản demo.
- [SO_DO_LOP.mmd](SO_DO_LOP.mmd): sơ đồ quan hệ chính của mã hiện tại.

File `nhansu.txt` có sẵn được giữ nguyên. Nội dung cũ có loại nhân viên khác `CT`/`TV`, không phải dữ liệu của chương trình hoàn thiện. File mới có dòng đầu `QLNS_TEXT_V1`, tiếp theo mỗi dòng là `TênLớp;trường1;trường2;...`. Chuỗi không được chứa `;` hoặc xuống dòng; ngày dùng ISO `yyyy-MM-dd`. Cả tiếng Việt có dấu và tên chứa khoảng trắng đều được lưu bằng UTF-8.

## Kiểm thử

File `data/qlns.txt` được chia theo nhóm bản ghi, có tên cột và các cột căn thẳng để xem trong trình soạn thảo. Dòng bắt đầu bằng `#` là chú thích, dòng trống dùng để ngăn nhóm; chương trình bỏ qua các dòng này khi đọc. Dấu `;` vẫn phân cách trường, khoảng trắng căn cột được bỏ qua khi đọc. File cũ chưa căn cột vẫn đọc được; mỗi lần lưu sẽ dùng cách trình bày mới. Nên tắt Word Wrap khi xem các dòng nhân viên/bảng lương dài trong VS Code.

```powershell
.\run.ps1 -Test
# Hoặc, sau khi biên dịch:
java -cp build TestHeThong
```

Bộ kiểm thử kiểm tra kết quả lương mẫu, lọc tháng, tính lại không trùng, ca qua đêm, nghỉ phép/vắng, ca chưa chấm ra, phụ cấp theo mốc năm, khấu trừ vượt lương, chống trùng/khóa ngoại, thao tác mảng, lưu/đọc mọi loại bản ghi, UTF-8 và file sai. Chạy trong thư mục tạm rồi tự dọn dữ liệu kiểm thử.
