import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String simpleText = "abcdefg";
        String password = "20,1,94,33,199,0,48,9,31,94,112,40,59,30,100,248";
        StateMatrix stateMatrix = StateMatrix.fromArray(new int[]{0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50});
        int[] simpleTextArray = new int[]{0x44, 0x45, 0x53, 0x45, 0x4e, 0x56, 0x4f, 0x4c, 0x56, 0x49, 0x4d, 0x45, 0x4e, 0x54, 0x4f, 0x21};
        int[] passwordArray = new int[]{0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50};

        List<StateMatrix> roundKeys = KeyExpansion.expandKeys(stateMatrix);

        System.out.println(roundKeys.stream().map(Objects::toString).collect(Collectors.joining("\n-----------------------\n")));
        System.out.println("---------------------------------------------------");
        System.out.println(BlockCipher.encryptString(simpleText, password));
        System.out.println("---------------------------------------------------");
        System.out.println(BlockCipher.encryptArray(simpleTextArray, passwordArray));
    }
}