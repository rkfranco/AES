package aes.utils;

import java.util.Map;

public class RoundConstant {
    private static final Map<Integer, Integer> VALUES = Map.of(
            1, 0x01,
            2, 0x02,
            3, 0x04,
            4, 0x08,
            5, 0x10,
            6, 0x20,
            7, 0x40,
            8, 0x80,
            9, 0x1B,
            10, 0x36
    );

    public static int getRoundConstant(int index) {
        return VALUES.get(index);
    }
}
