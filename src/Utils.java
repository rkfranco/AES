import java.util.Arrays;
import java.util.stream.IntStream;

public class Utils {
    static int[] substituteWord(int[] words) {
        return Arrays.stream(words).map(SBox::getTableValue).toArray();
    }

    static int[] getRoundConstant(int index) {
        return new int[]{RoundConstant.getRoundConstant(index), 0, 0, 0};
    }

    static int[] applyXOR(int[] first, int[] second) {
        return IntStream.range(0, 4).map(i -> first[i] ^ second[i]).toArray();
    }
}
