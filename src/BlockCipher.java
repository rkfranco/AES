import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockCipher {
    public static int[] encrypt(String simpleText, String key) {
        List<StateMatrix> roundKeys = KeyExpansion.expandKeys(StateMatrix.fromKeyString(key));
        List<StateMatrix> textBlocks = StateMatrix.fromSimpleText(simpleText);

        textBlocks = textBlocks.stream().map(b -> applyXOR(b, roundKeys.get(0))).collect(Collectors.toList());

        for (int i = 1; i < 10; i++) {
            StateMatrix roundKey = roundKeys.get(i);
            textBlocks = textBlocks.stream()
                    .peek(b -> b.getWords().forEach(Utils::substituteWord))
                    .map(StateMatrix::shiftRows)
                    .map(BlockCipher::mixColumns)
                    .map(b -> applyXOR(b, roundKey))
                    .collect(Collectors.toList());
        }

        return null;
    }

    private static StateMatrix mixColumns(StateMatrix matrix) {

        return matrix;
    }

    private static StateMatrix applyXOR(StateMatrix matrix, StateMatrix roundKey) {
        List<int[]> words = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            words.add(Utils.applyXOR(matrix.getWords().get(i), roundKey.getWords().get(i)));
        }

        return StateMatrix.fromRoundKeys(words.get(0), words.get(1), words.get(2), words.get(3));
    }
}
