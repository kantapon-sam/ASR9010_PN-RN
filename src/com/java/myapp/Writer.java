package com.java.myapp;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void CardAllocation(FileWriter CardAllocation, String str) {
        try {
            CardAllocation.write(str);
            CardAllocation.close();
            System.out.println("Write Success");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
