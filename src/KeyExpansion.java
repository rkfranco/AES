import java.util.ArrayList;
import java.util.List;

public class KeyExpansion {

    public static List<StateMatrix> expandKeys(StateMatrix initialMatrix) {
        List<StateMatrix> roundKeys = new ArrayList<>();
        roundKeys.add(initialMatrix);

        for (int i = 1; i < 11; i++) {
            StateMatrix previousMatrix = roundKeys.get(i - 1);

            int[] firstKey = expandFirstKey(previousMatrix, i);
            int[] secondKey = Utils.applyXOR(firstKey, previousMatrix.getWords().get(1));
            int[] thirdKey = Utils.applyXOR(secondKey, previousMatrix.getWords().get(2));
            int[] fourthKey = Utils.applyXOR(thirdKey, previousMatrix.getWords().get(3));

            roundKeys.add(StateMatrix.fromRoundKeys(firstKey, secondKey, thirdKey, fourthKey));
        }

        return roundKeys;
    }


    private static int[] expandFirstKey(StateMatrix previousMatrix, int index) {
        // (1) Copiar última palavra da round key anterior
        int[] lastWord = previousMatrix.getWords().get(3);

        // (2) Rotacionar bytes (RotWord)
        int[] rotWord = rotateWord(lastWord);

        // (3) Substituir bytes (SubWord)
        int[] subWord = Utils.substituteWord(rotWord);

        // (4) Gerar RoundConstant
        int[] roundConstant = Utils.getRoundConstant(index);

        // (5) XOR de (3) com (4)
        int[] appliedXOR = Utils.applyXOR(subWord, roundConstant);

        // (6) XOR de (5) com 1ª palavra da roundkey anterior
        return Utils.applyXOR(appliedXOR, previousMatrix.getWords().get(0));
    }

    private static int[] rotateWord(int[] word) {
        return new int[]{word[1], word[2], word[3], word[0]};
    }
}
