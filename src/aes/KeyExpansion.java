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

            int[] firstKey = expandFirstKey(previousMatrix, i);
            int[] secondKey = Utils.applyXOR(firstKey, previousMatrix.getWords().get(1));
            int[] thirdKey = Utils.applyXOR(secondKey, previousMatrix.getWords().get(2));
            int[] fourthKey = Utils.applyXOR(thirdKey, previousMatrix.getWords().get(3));

            roundKeys.add(StateMatrix.fromRoundKeys(firstKey, secondKey, thirdKey, fourthKey));
        }

        return roundKeys;
    }

    private static int[] expandFirstKey(StateMatrix previousMatrix, int index) {
        return Optional.of(previousMatrix.getWords().get(3))                            // (1) Copiar última palavra da round key anterior
                .map(Utils::rotateArray)                                                // (2) Rotacionar bytes (RotWord)
                .map(Utils::substituteWord)                                             // (3) Substituir bytes (SubWord)
                .map(w -> Utils.applyXOR(w, Utils.getRoundConstant(index)))             // (4) Gerar aes.utils.RoundConstant // (5) XOR de (3) com (4)
                .map(w -> Utils.applyXOR(w, previousMatrix.getWords().get(0)))          // (6) XOR de (5) com 1ª palavra da roundkey anterior
                .orElseThrow(() -> new RuntimeException("Error when trying to expand the first key of the block"));
    }
}
