import aes.AES;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File fileToEncrypt = UserInterface.getFileToEncrypt();
        String password = UserInterface.getPassword();
        String path = UserInterface.getDirectoryPath();
        String fileName = UserInterface.getNewFileName();

        int[] result = AES.encrypt(Utils.byteToIntArray(FileManager.readContentFile(fileToEncrypt.getPath())), password);

        boolean fileSave = FileManager.saveContentFile(path + "\\" + fileName, Utils.intToByteArray(result));

        UserInterface.showSaveFileResult(fileSave);
    }
}
