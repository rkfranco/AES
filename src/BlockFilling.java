public class BlockFilling {
    public static String pkcs7(String simpleText, int blockSize) {
        int qdtCharAppend = blockSize - simpleText.length() % blockSize;
        return simpleText.concat(String.valueOf((char) qdtCharAppend).repeat(Math.max(0, qdtCharAppend)));
    }
}
