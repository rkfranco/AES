// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String password = "20,1,94,33,199,0,48,9,31,94,112,40,59,30,100,248";
        // StateMatrix matrix = StateMatrix.fromKeyString(password);
        // List<StateMatrix> roundKeys = KeyExpansion.expandKeys(matrix);
        // roundKeys.stream().map(m -> m.toString().concat("\n----------------------")).forEach(System.out::println);


        String teste = "aaaaaaaaaab";
        String bloco = BlockFilling.pkcs7(teste, 11);
        System.out.println("Bloco: " + bloco + "\nTamanho: " + bloco.length());
    }
}