package aes;

import aes.utils.BlockFilling;

public class AES {
    private static final int BLOCK_SIZE = 16;

    public static int[] encrypt(int[] simpleText, String password) {
        int[] pkcsText = BlockFilling.pkcs7(simpleText, BLOCK_SIZE);
        return BlockCipher.encryptString(pkcsText, password);
    }
}
