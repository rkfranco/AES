package aes.utils;

public class BlockFilling {
    public static int[] pkcs7(int[] simpleText, int blockSize) {
        int textLength = simpleText.length;
        int qdtCharAppend = blockSize - textLength % blockSize;

        int[] result = new int[textLength + qdtCharAppend];

        System.arraycopy(simpleText, 0, result, 0, textLength);

        for (int i = 0; i < qdtCharAppend; i++) result[i + textLength] = qdtCharAppend;

        return result;
    }
}
