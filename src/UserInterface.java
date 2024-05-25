import javax.swing.*;
import java.io.File;

public class UserInterface {
    private static final File PROJECT_DIR = new File(".");
    private static final JFileChooser FILE_CHOOSER = new JFileChooser();

    static File getFileToEncrypt() {
        FILE_CHOOSER.setCurrentDirectory(PROJECT_DIR);
        FILE_CHOOSER.setDialogTitle("Select one file to encrypt");
        FILE_CHOOSER.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FILE_CHOOSER.showOpenDialog(null);
        return FILE_CHOOSER.getSelectedFile();
    }

    static String getPassword() {
        String password = JOptionPane.showInputDialog("Enter the password to encrypt the file (128 bits):");
        while (password.split(",").length != 16) {
            JOptionPane.showMessageDialog(null, "The key length is wrong: the key must be 16 characters long.");
            password = JOptionPane.showInputDialog("Enter the password to encrypt the file (128 bits):");
        }
        return password;
    }

    static String getDirectoryPath() {
        FILE_CHOOSER.setCurrentDirectory(PROJECT_DIR);
        FILE_CHOOSER.setDialogTitle("Select one folder to save the encrypt file");
        FILE_CHOOSER.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        FILE_CHOOSER.showSaveDialog(null);
        return FILE_CHOOSER.getSelectedFile().getPath();
    }

    static String getNewFileName() {
        String fileName = JOptionPane.showInputDialog("Enter the file name:");
        return fileName.contains(".") ? fileName : fileName.concat(".txt");
    }
}
