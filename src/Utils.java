import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Utils {
    static final List<int[]> multiplacationMatrixRows = StateMatrix.fromKeyString("2,1,1,3,3,2,1,1,1,3,2,1,1,1,3,2").getRows();

    static int[] getMultiMatrixRow(int index) {
        return multiplacationMatrixRows.get(index);
    }

    static int[] substituteWord(int[] words) {
        return Arrays.stream(words).map(SBox::getTableValue).toArray();
    }

    static int[] rotateArray(int[] array) {
        return new int[]{array[1], array[2], array[3], array[0]};
    }

    static int[] getRoundConstant(int index) {
        return new int[]{RoundConstant.getRoundConstant(index), 0, 0, 0};
    }

    static int[] applyXOR(int[] first, int[] second) {
        return IntStream.range(0, 4).map(i -> first[i] ^ second[i]).toArray();
    }
}
