package com.java.myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ASR9010_PNRN {

    public static void main(String[] args) {

        Dialog.setLAF();
        Selectfile Select = new Selectfile();
        int index = -1;
        File[] files = Select.getFile().listFiles();
        Wait wait = new Wait();
        try {
            if (Select.getChooser().getSelectedFile().getName().contains(".txt")) {
                if (Select.getChooser().getSelectedFile().getName().contains("description.txt")) {
                    BufferedReader br = new BufferedReader(new FileReader(Select.getFile()));
                    String pathOutput = Select.getFile().getPath();
                    new File(Select.getChooser().getCurrentDirectory() + "\\Total_description.csv").delete();

                    Sub(br, pathOutput, Select.getChooser().getCurrentDirectory());
                    wait.dispose();
                    Dialog.Success();
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            } else {
                new File(Select.getChooser().getSelectedFile() + "\\Total_description.csv").delete();
                Arrays.sort(files, new Comparator<File>() {
                    public int compare(File f1, File f2) {
                        return Long.compare(f1.lastModified(), f2.lastModified());
                    }
                });
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().contains("description.txt")) {
                        index = i;
                        BufferedReader br = new BufferedReader(new FileReader(files[i]));
                        String pathOutput = files[i].getPath();
                        Sub(br, pathOutput, Select.getChooser().getSelectedFile());
                    }
                }
                wait.dispose();
                Dialog.Success();
                System.exit(0);
            }
        } catch (NullPointerException ex) {
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (Select.getChooser().getSelectedFile().getName().contains("description.txt")) {
                Dialog.FileError(Select.getFile());
            } else {
                Dialog.FileError(files[index].getName());
            }
        }
    }

    private static void Sub(BufferedReader br, String path, File Directory) throws IOException {
        String line;
        String all = "";
        String[] w = new String[10000];
        int[][] Port = new int[20][200];

        int c = 0;
        int[][] total_slot = new int[20][200];
        int Total_Port = 0;
        int Total_Free_Port = 0;
        int Total_Free_Port_1G = 0;
        int Total_Free_Port_10G = 0;
        int SLOT = 0;
        int SUBSLOT = 0;
        String str = "";
        int main = 0;
        int slot = 0;
        int max_main = 0;
        int max_slot = 0;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split("/");
            String[] arr2 = line.split(" ");
            if (arr.length > 1 && (arr[0].contains("Gi") || arr[0].contains("Te"))) {

                if (arr[3].charAt(0) != '.' && arr[3].charAt(1) != '.' && arr[3].charAt(2) != '.' && arr[3].charAt(3) != '.' && arr[3].charAt(4) != '.' && arr[3].charAt(5) != '.') {
                    Total_Port++;
                    str = arr2[0] + "," + line.substring(19, 24) + "," + line.substring(31, 36) + "," + line.substring(43);
                    String[] arr3 = str.split(",");
                    if (str.length() <= 25) {
                        str = str + "false";

                    }
                    String[] arr4 = str.split(",");
                    String[] t = arr4[0].split("/");

                    int a0 = Integer.valueOf(t[1]);
                    int a1 = Integer.valueOf(t[2]);
                    max_main = max_main < a0 ? a0 : max_main;
                    max_slot = max_slot < a1 ? a1 : max_slot;
                    if (arr4[3].contains("false")) {
                        String[] a = arr4[0].split("/");
                        if (a[0].contains("Gi")) {
                            Total_Free_Port_1G++;
                        } else if (a[0].contains("Te")) {
                            Total_Free_Port_10G++;
                        }

                        main = Integer.valueOf(a[1]);
                        slot = Integer.valueOf(a[2]);
                        total_slot[main][slot]++;
                    }

                }
                String[] arr4 = str.split(",");
                String[] a = arr4[0].split("/");
                SLOT = Integer.valueOf(a[1]);
                SUBSLOT = Integer.valueOf(a[2]);
                if (a[0].contains("Gi")) {
                    Port[SLOT][SUBSLOT] = 1;
                } else if (a[0].contains("Te")) {
                    Port[SLOT][SUBSLOT] = 10;
                }

            }
        }

        br.close();

        Total_Free_Port = Total_Free_Port_10G + Total_Free_Port_1G;
        String[] PATH = path.split("\\.");
        String p = PATH[0].substring(PATH[0].lastIndexOf('\\') + 1) + "." + PATH[1] + "." + PATH[2] + "." + PATH[3].substring(0, PATH[3].indexOf('-'));
        String Loopback = "";
        String Str = "";
        Loopback = "Loopback : " + p + "\n" + "Total_Port = "
                + Total_Port + "\n" + "Total_Free_Port = "
                + Total_Free_Port + "\n" + "Total_Free_Port_10G = "
                + Total_Free_Port_10G + "\n" + "Total_Free_Port_1G = "
                + Total_Free_Port_1G;
        for (int i = 0; i <= max_main; i++) {
            for (int j = 0; j <= max_slot; j++) {

                if (Port[i][j] != 0) {
                    Str += "\n" + Port[i][j] + "G_" + String.format("Slot[%d] Sub_Slot[%d] Free = ", i, j) + total_slot[i][j];
                }
            }
        }
        all = Loopback + Str + "\n\n";
        System.out.println(all);
        FileWriter CardAllocation;
        CardAllocation = new FileWriter(Directory + "\\Total_description.csv", true);
        Writer.CardAllocation(CardAllocation, all);

    }

}
