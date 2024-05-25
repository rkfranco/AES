package aes;

import aes.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KeyExpansion {

    public static List<StateMatrix> expandKeys(StateMatrix initialMatrix) {
        List<StateMatrix> roundKeys = new ArrayList<>();
        roundKeys.add(initialMatrix);

        for (int i = 1; i < 11; i++) {
            StateMatrix previousMatrix = roundKeys.get(i - 1);
            List<int[]> words = previousMatrix.getWords();

            int[] firstKey = expandFirstKey(words, i);
            int[] secondKey = Utils.applyXOR(firstKey, words.get(1));
            int[] thirdKey = Utils.applyXOR(secondKey, words.get(2));
            int[] fourthKey = Utils.applyXOR(thirdKey, words.get(3));

            roundKeys.add(StateMatrix.fromRoundKeys(firstKey, secondKey, thirdKey, fourthKey));
        }

        return roundKeys;
    }

    private static int[] expandFirstKey(List<int[]> previousMatrixWords, int index) {
        return Optional.of(previousMatrixWords.get(3))                            // (1) Copiar última palavra da round key anterior
                .map(Utils::rotateArray)                                          // (2) Rotacionar bytes (RotWord)
                .map(Utils::substituteWord)                                       // (3) Substituir bytes (SubWord)
                .map(w -> Utils.applyXOR(w, Utils.getRoundConstant(index)))       // (4) Gerar RoundConstant // (5) XOR de (3) com (4)
                .map(w -> Utils.applyXOR(w, previousMatrixWords.get(0)))          // (6) XOR de (5) com 1ª palavra da roundkey anterior
                .orElseThrow(() -> new RuntimeException("Error when trying to expand the first key of the block"));
    }
}
