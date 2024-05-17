import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockCipher {

    public static String encryptString(String simpleText, String key) {
        List<StateMatrix> roundKeys = KeyExpansion.expandKeys(StateMatrix.fromKeyString(key));
        List<StateMatrix> textBlocks = StateMatrix.fromSimpleText(BlockFilling.pkcs7(simpleText, 16));
        return encrypt(roundKeys, textBlocks);
    }

    public static String encryptArray(int[] simpleText, int[] key) {
        List<StateMatrix> roundKeys = KeyExpansion.expandKeys(StateMatrix.fromArray(key));
        List<StateMatrix> textBlocks = StateMatrix.fromSimpleText(BlockFilling.pkcs7(simpleText, 16));
        return encrypt(roundKeys, textBlocks);
    }

    private static String encrypt(List<StateMatrix> roundKeys, List<StateMatrix> textBlocks) {
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
                .map(StateMatrix::toString)
                .collect(Collectors.joining("\n-------------------------\n"));
    }

    private static StateMatrix applyXOR(StateMatrix matrix, StateMatrix roundKey) {
        List<int[]> words = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            words.add(Utils.applyXOR(matrix.getWords().get(i), roundKey.getWords().get(i)));
        }

        return StateMatrix.fromRoundKeys(words.get(0), words.get(1), words.get(2), words.get(3));
    }
}
