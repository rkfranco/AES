import aes.AES;

import javax.swing.*;
import java.io.File;

public class Main {
    private static final File PROJECT_DIR = new File(".");
    private static final JFileChooser chooser = new JFileChooser();

    public static void main(String[] args) {
        File fileToEncrypt = getFileToEncrypt();
        String password = getPassword();
        String path = getDirectoryPath();
        String fileName = getNewFileName();

        AES cipher = new AES();

        int[] result = cipher.encrypt(FileManager.readContentFile(fileToEncrypt.getPath()), password);

        boolean fileSave = FileManager.saveContentFile(path + "\\" + fileName, intToCharArray(result));

        JOptionPane.showMessageDialog(null, fileSave ? "File saved successfully" : "Unable to save the file");
    }

    private static File getFileToEncrypt() {
        chooser.setCurrentDirectory(PROJECT_DIR);
        chooser.setDialogTitle("Select one file to encrypt");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    private static String getPassword() {
        String password = JOptionPane.showInputDialog("Enter the password to encrypt the file (128 bits):");
        while (password.split(",").length != 16) {
            JOptionPane.showMessageDialog(null, "The key length is wrong: the key must be 16 characters long.");
            password = JOptionPane.showInputDialog("Enter the password to encrypt the file (128 bits):");
        }
        return password;
    }

    private static String getDirectoryPath() {
        chooser.setCurrentDirectory(PROJECT_DIR);
        chooser.setDialogTitle("Select one folder to save the encrypt file");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showSaveDialog(null);
        return chooser.getSelectedFile().getPath();
    }

    private static String getNewFileName() {
        String fileName = JOptionPane.showInputDialog("Enter the file name:");
        if (!fileName.contains(".")) fileName = fileName.concat(".txt");
        return fileName;
    }

    private static char[] intToCharArray(int[] array) {
        char[] newArray = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = (char) array[i];
        }
        return newArray;
    }
}