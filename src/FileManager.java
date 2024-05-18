import java.io.*;

public class FileManager {

    public static boolean saveContentFile(String path, char[] content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static int[] readContentFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().flatMapToInt(String::chars).toArray();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar chaves");
        }
    }
}
