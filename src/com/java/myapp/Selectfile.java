package com.java.myapp;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Selectfile {

    private final File file;
    private final File[] fileFolder;
    private String choosertitle;
    final JFileChooser chooser;

    public Selectfile() {
        
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
        } else {
            System.out.println("No Selection ");
            System.exit(0);
        }
        file = new File(getChooser().getSelectedFile().toString());
        fileFolder = file.listFiles();
    }

    public File getFile() {
        return file;
    }

    public File[] getFileFolder() {
        return fileFolder;
    }

    public JFileChooser getChooser() {
        return chooser;
    }

}
