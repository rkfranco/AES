import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {

    public static boolean saveContentFile(String path, byte[] content) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(content);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static byte[] readContentFile(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            return fis.readAllBytes();
            // return reader.lines().flatMapToInt(String::chars).toArray();
        } catch (IOException e) {
            throw new RuntimeException("Error when trying to read the selected file");
        }
    }
}
