// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String password = "20,1,94,33,199,0,48,9,31,94,112,40,59,30,100,248";
        // StateMatrix simpleText = StateMatrix.fromArray(new int[]{0x44, 0x45, 0x53, 0x45, 0x4e, 0x56, 0x4f, 0x4c, 0x56, 0x49, 0x4d, 0x45, 0x4e, 0x54, 0x4f, 0x21});
        // StateMatrix matrix = StateMatrix.fromArray(new int[]{0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50});
        int[] simpleText = new int[]{0x44, 0x45, 0x53, 0x45, 0x4e, 0x56, 0x4f, 0x4c, 0x56, 0x49, 0x4d, 0x45, 0x4e, 0x54, 0x4f, 0x21};
        int[] matrix = new int[]{0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50};


        // StateMatrix matrix = StateMatrix.fromKeyString(password);
        //List<StateMatrix> roundKeys = KeyExpansion.expandKeys(matrix);
        // roundKeys.stream().map(m -> m.toString().concat("\n----------------------")).forEach(System.out::println);

        // System.out.println(BlockCipher.encryptString("teste", password));
        System.out.println(BlockCipher.encryptArray(simpleText, matrix));

        // String teste = "aaaaaaaaaab";
        // String bloco = BlockFilling.pkcs7(teste, 11);
        // System.out.println("Bloco: " + bloco + "\nTamanho: " + bloco.length());
    }
}