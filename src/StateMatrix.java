import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StateMatrix {
    private final int[][] words;

    private StateMatrix(int[] text) {
        if (text.length != 16) {
            throw new IllegalArgumentException("Password must be 128 bits");
        }

        words = new int[4][4];
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

        words = new int[4][4];

        for (int i = 0; i < 4; i++) {
            words[i][0] = firstKey[i];
            words[i][1] = secondKey[i];
            words[i][2] = thirdKey[i];
            words[i][3] = fourthKey[i];
        }
    }

    public static StateMatrix fromKeyString(String password) {
        return new StateMatrix(Arrays.stream(password.split(",")).mapToInt(Integer::parseInt).toArray());
    }

    public static StateMatrix fromArray(int[] array) {
        return new StateMatrix(array);
    }

    public static List<StateMatrix> fromSimpleText(String simpleText) {
        List<StateMatrix> result = new ArrayList<>();

        for (int i = 0; i < simpleText.length(); i += 16) {
            result.add(new StateMatrix(simpleText.substring(i, i + 16).chars().toArray()));
        }

        return result;
    }

    public static StateMatrix fromRoundKeys(int[] firstKey, int[] secondKey, int[] thirdKey, int[] fourthKey) {
        return new StateMatrix(firstKey, secondKey, thirdKey, fourthKey);
    }

    public StateMatrix subBytes(){
        Arrays.stream(this.words).map(Arrays::stream).map(SBox::getTableValue);
    }

    public StateMatrix shiftRows() {
        List<int[]> rows = this.getRows();
        return new StateMatrix(IntStream.range(0, 4).flatMap(i -> Arrays.stream(rotateRow(rows.get(i), i))).toArray());
    }

    private static int[] rotateRow(int[] row, int index) {
        for (int i = 0; i < index; i++) {
            row = new int[]{row[1], row[2], row[3], row[0]};
        }
        return row;
    }

    public List<int[]> getRows() {
        return Arrays.stream(words, 0, 4).collect(Collectors.toList());
    }

    public List<int[]> getWords() {
        List<int[]> rows = new ArrayList<>();
        int[] row;

        for (int i = 0; i < 4; i++) {
            row = new int[4];

            for (int j = 0; j < 4; j++) {
                row[j] = words[j][i];
            }

            rows.add(row);
        }

        return rows;
    }

    public String toStringArray() {
        StringWriter sr = new StringWriter();
        this.getWords().forEach(w -> Arrays.stream(w).forEach(sr::write));
        return sr.toString();
    }

    public String toHex() {
        return this.getWords().stream()
                .flatMapToInt(Arrays::stream)
                .mapToObj(Integer::toHexString)
                .collect(Collectors.joining(" "));
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
                lines[i].append(Integer.toHexString(words[i][j])).append(" | ");
            }
        }

        return String.join("\n", lines);
    }
}
