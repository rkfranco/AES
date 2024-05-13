import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class StateMatrix {
    private List<int[]> words;

    private StateMatrix(int[] text) {
        if (text.length != 16) {
            throw new IllegalArgumentException("Password must be 128 bits");
        }

        words = new ArrayList<>();
        words.add(Arrays.copyOfRange(text, 0, 4));
        words.add(Arrays.copyOfRange(text, 4, 8));
        words.add(Arrays.copyOfRange(text, 8, 12));
        words.add(Arrays.copyOfRange(text, 12, 16));
    }

    private StateMatrix(int[] firstKey, int[] secondKey, int[] thirdKey, int[] fourthKey) {
        if (firstKey.length != 4 || secondKey.length != 4 || thirdKey.length != 4 || fourthKey.length != 4) {
            throw new IllegalArgumentException("Password must be 128 bits: all the words must have 32 bits");
        }

        words = List.of(firstKey, secondKey, thirdKey, fourthKey);
    }

    public static StateMatrix fromKeyString(String password) {
        return new StateMatrix(Arrays.stream(password.split(",")).mapToInt(Integer::parseInt).toArray());
    }

    public static List<StateMatrix> fromSimpleText(String simpleText) {
        List<StateMatrix> result = new ArrayList<>();

        for (int i = 0; i < simpleText.length(); i += 16) {
            result.add(new StateMatrix(simpleText.substring(i, i+16).chars().toArray()));
        }

        return result;
    }

    public static StateMatrix fromRoundKeys(int[] firstKey, int[] secondKey, int[] thirdKey, int[] fourthKey) {
        return new StateMatrix(firstKey, secondKey, thirdKey, fourthKey);
    }

    public StateMatrix shiftRows() {
        IntStream.range(0, 4).forEach(i -> words.set(i, rotateWord(words.get(i), i)));
        return this;
    }

    private static int[] rotateWord(int[] word, int index) {
        int secondItem = 1 + index;
        int thirdItem = 2 + index;
        int fourthItem = 3 + index;

        if (secondItem > 3) secondItem -= 3;

        if (thirdItem > 3) thirdItem -= 3;

        if (fourthItem > 3) fourthItem -= 3;

        return new int[]{word[index], word[secondItem], word[thirdItem], word[fourthItem]};
    }

    public List<int[]> getWords() {
        return words;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("000");
        StringBuilder[] lines = new StringBuilder[4];

        for (int i = 0; i < 4; i++) {
            lines[i] = new StringBuilder();
        }

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                lines[i].append(formatter.format(words.get(j)[i])).append(" | ");
            }
        }

        return String.join("\n", lines);
    }

}
