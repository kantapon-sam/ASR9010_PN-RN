package com.java.myapp;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Dialog {

    public static void setLAF() {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            System.err.println("Failed to set LookAndFeel");

        }
    }

    public static void FileError(String name) {
        JOptionPane.showMessageDialog(null,
                "File " + name + "\nError !!!",
                "Error!",
                JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    public static void FileError(File file) {
        JOptionPane.showMessageDialog(null,
                "File " + file.getName() + "\nError !!!",
                "Error!",
                JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    public static void Success() {
        java.awt.Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null,
                "Success ",
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }

}
