package com.github.MarvelousAdain;

import java.io.FileNotFoundException;

public class Secret {

    public static String getToken() {
        String Token = "0";
        try {
            Token = Utilities.loadInformationString();
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
        return Token;
    }
}
