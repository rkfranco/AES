import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockCipher {
    public static String encrypt(String simpleText, String key) {
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

        return textBlocks.stream()
                .peek(b -> b.getWords().forEach(Utils::substituteWord))
                .map(StateMatrix::shiftRows)
                .map(b -> applyXOR(b, roundKeys.get(10)))
                .map(StateMatrix::toStringArray).collect(Collectors.joining());
    }

    private static StateMatrix mixColumns(StateMatrix matrix) {
        StateMatrix multiplacationMatrix = StateMatrix.fromKeyString("2,1,1,3,3,2,1,1,1,3,2,1,1,1,3,2");

        List<int[]> words = matrix.getWords();
        List<int[]> rows = multiplacationMatrix.getRows();

        int[] result = new int[16];
        int index = 0;

        int[] temp = words.stream().flatMap(w -> rows.stream().map(r -> calculateMixColumns(w, r))).mapToInt(i -> i).toArray();

        for (int[] word : words) {
            for (int[] row : rows) {
                result[index++] = calculateMixColumns(word, row);
            }
        }

        return StateMatrix.fromArray(result);
    }

    private static int calculateMixColumns(int[] column, int[] row) {
        int result = calculateGaloisValue(column[0], row[0]);

        for (int i = 1; i < 4; i++) {
            result ^= calculateGaloisValue(column[i], row[i]);
        }

        return result;
    }

    private static int calculateGaloisValue(int valueOne, int valueTwo) {
        if (valueOne == 0 || valueTwo == 0) return 0;

        if (valueOne == 1) {
            return valueTwo;
        }
        if (valueTwo == 1) {
            return valueOne;
        }

        int lTableResult = LTable.getTableValue(valueOne) + LTable.getTableValue(valueTwo);

        if (lTableResult > 0xFF) lTableResult -= 0xFF;

        return ETable.getTableValue(lTableResult);
    }

    private static StateMatrix applyXOR(StateMatrix matrix, StateMatrix roundKey) {
        List<int[]> words = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            words.add(Utils.applyXOR(matrix.getWords().get(i), roundKey.getWords().get(i)));
        }

        return StateMatrix.fromRoundKeys(words.get(0), words.get(1), words.get(2), words.get(3));
    }
}
