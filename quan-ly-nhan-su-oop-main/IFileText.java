import java.io.IOException;

/**
 * Interface quy dinh cac lop quan ly du lieu phai co chuc nang doc/ghi tep text.
 */
public interface IFileText {
    void ghiFile(String tenFile) throws IOException;
    void docFile(String tenFile) throws IOException;
}
