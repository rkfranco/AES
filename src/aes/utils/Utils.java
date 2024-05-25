package aes.utils;

import aes.StateMatrix;
import aes.tables.SBox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Utils {
    public static final List<int[]> MULTIPLACATION_MATRIX_ROWS = StateMatrix.fromKey("2,1,1,3,3,2,1,1,1,3,2,1,1,1,3,2").getRows();

    public static int[] getMultiMatrixRow(int index) {
        return MULTIPLACATION_MATRIX_ROWS.get(index);
    }

    public static int[] substituteWord(int[] words) {
        return Arrays.stream(words).map(SBox::getTableValue).toArray();
    }

    public static int[] rotateArray(int[] array) {
        return new int[]{array[1], array[2], array[3], array[0]};
    }

    public static int[] getRoundConstant(int index) {
        return new int[]{RoundConstant.getRoundConstant(index), 0, 0, 0};
    }

    public static int[] applyXOR(int[] first, int[] second) {
        return IntStream.range(0, 4).map(i -> first[i] ^ second[i]).toArray();
    }
}
