package aes.utils;

public class BlockFilling {
    public static String pkcs7(String simpleText, int blockSize) {
        int qdtCharAppend = blockSize - simpleText.length() % blockSize;
        return simpleText.concat(String.valueOf((char) qdtCharAppend).repeat(Math.max(0, qdtCharAppend)));
    }

    public static String pkcs7(int[] simpleText, int blockSize) {
        int qdtCharAppend = blockSize - simpleText.length % blockSize;

        StringBuilder sb = new StringBuilder();

        for (int i : simpleText) {
            sb.append((char) i);
        }

        sb.append(String.valueOf((char) qdtCharAppend).repeat(Math.max(0, qdtCharAppend)));

        return sb.toString();
    }
}
