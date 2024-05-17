import java.io.StringWriter;

public class BlockFilling {
    public static String pkcs7(String simpleText, int blockSize) {
        int qdtCharAppend = blockSize - simpleText.length() % blockSize;
        return simpleText.concat(String.valueOf((char) qdtCharAppend).repeat(Math.max(0, qdtCharAppend)));
    }

    public static String pkcs7(int[] simpleText, int blockSize) {
        int qdtCharAppend = blockSize - simpleText.length % blockSize;

        StringWriter sw = new StringWriter();

        for (int i : simpleText) {
            sw.write(i);
        }

        for (int i = 0; i < qdtCharAppend; i++) {
            sw.write(qdtCharAppend);
        }

        return sw.toString();
    }
}
