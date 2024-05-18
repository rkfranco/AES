package aes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BlockCipher {

    public static int[] encryptString(String simpleText, String key) {
        List<StateMatrix> roundKeys = KeyExpansion.expandKeys(StateMatrix.fromKey(key));
        List<StateMatrix> textBlocks = StateMatrix.fromSimpleText(simpleText);
        return encrypt(roundKeys, textBlocks);
    }

    public static int[] encryptArray(int[] simpleText, int[] key) {
        List<StateMatrix> roundKeys = KeyExpansion.expandKeys(StateMatrix.fromKey(key));
        List<StateMatrix> textBlocks = StateMatrix.fromSimpleText(simpleText);
        return encrypt(roundKeys, textBlocks);
    }

    private static int[] encrypt(List<StateMatrix> roundKeys, List<StateMatrix> textBlocks) {
        Stream<StateMatrix> blockStream = textBlocks.stream().map(b -> b.applyRoundKey(roundKeys.get(0)));

        for (int i = 1; i < 10; i++) {
            StateMatrix roundKey = roundKeys.get(i);
            blockStream = blockStream
                    .map(StateMatrix::subBytes)
                    .map(StateMatrix::shiftRows)
                    .map(StateMatrix::mixColumns)
                    .map(b -> b.applyRoundKey(roundKey));
        }

        return blockStream
                .map(StateMatrix::subBytes)
                .map(StateMatrix::shiftRows)
                .map(b -> b.applyRoundKey(roundKeys.get(10)))
                .map(StateMatrix::toIntArray)
                .flatMapToInt(Arrays::stream).toArray();
    }
}
