package aes;

import aes.tables.ETable;
import aes.tables.LTable;
import aes.tables.SBox;
import aes.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StateMatrix {
    private final int[][] words = new int[4][4];

    private StateMatrix(int[] text) {
        if (text.length != 16) {
            throw new IllegalArgumentException("Password must be 128 bits");
        }

        int index = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                words[j][i] = text[index++];
            }
        }
    }

    private StateMatrix(int[] firstKey, int[] secondKey, int[] thirdKey, int[] fourthKey) {
        if (firstKey.length != 4 || secondKey.length != 4 || thirdKey.length != 4 || fourthKey.length != 4) {
            throw new IllegalArgumentException("Password must be 128 bits: all the words must have 32 bits");
        }

        for (int i = 0; i < 4; i++) {
            words[i][0] = firstKey[i];
            words[i][1] = secondKey[i];
            words[i][2] = thirdKey[i];
            words[i][3] = fourthKey[i];
        }
    }

    public static StateMatrix fromKey(String password) {
        return new StateMatrix(Arrays.stream(password.split(",")).mapToInt(Integer::parseInt).toArray());
    }

    public static StateMatrix fromKey(int[] array) {
        return new StateMatrix(array);
    }

    public static List<StateMatrix> fromSimpleText(int[] simpleText) {
        List<StateMatrix> result = new ArrayList<>();

        for (int i = 0; i < simpleText.length; i += 16) {
            result.add(new StateMatrix(Arrays.copyOfRange(simpleText, i, i + 16)));
        }

        return result;
    }

    public static StateMatrix fromRoundKeys(int[] firstKey, int[] secondKey, int[] thirdKey, int[] fourthKey) {
        return new StateMatrix(firstKey, secondKey, thirdKey, fourthKey);
    }

    private static int[] rotateRow(int[] row, int index) {
        for (int i = 0; i < index; i++) {
            row = Utils.rotateArray(row);
        }
        return row;
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
        if (valueOne == 1) return valueTwo;
        if (valueTwo == 1) return valueOne;

        int lTableResult = LTable.getTableValue(valueOne) + LTable.getTableValue(valueTwo);

        if (lTableResult > 0xFF) lTableResult -= 0xFF;

        return ETable.getTableValue(lTableResult);
    }

    public StateMatrix subBytes() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                words[i][j] = SBox.getTableValue(words[i][j]);
            }
        }
        return this;
    }

    public StateMatrix shiftRows() {
        IntStream.range(0, 4).forEach(i -> words[i] = rotateRow(words[i], i));
        return this;
    }

    public StateMatrix mixColumns() {
        List<int[]> wordsList = getWords();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                words[i][j] = calculateMixColumns(wordsList.get(j), Utils.getMultiMatrixRow(i));
            }
        }

        return this;
    }

    public StateMatrix applyRoundKey(StateMatrix roundKey) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                words[i][j] ^= roundKey.words[i][j];
            }
        }
        return this;
    }

    public List<int[]> getRows() {
        return Arrays.stream(words, 0, 4).collect(Collectors.toList());
    }

    public List<int[]> getWords() {
        return IntStream.range(0, 4).mapToObj(i -> IntStream.range(0, 4).map(j -> words[j][i]).toArray()).collect(Collectors.toList());
    }

    public int[] toIntArray() {
        return getWords().stream().flatMapToInt(Arrays::stream).toArray();
    }

    private String formatHex(String hex) {
        return (hex.length() == 1 ? "0x0" : "0x").concat(hex);
    }

    @Override
    public String toString() {
        StringBuilder[] lines = new StringBuilder[4];
        IntStream.range(0, 4).forEach(i -> lines[i] = new StringBuilder());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                lines[j].append(formatHex(Integer.toHexString(words[j][i]))).append(" | ");
            }
        }

        return String.join("\n", lines);
    }
}
