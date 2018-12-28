package com.github.MarvelousAdain;

public class Secret {

    public static String getToken() {
        try {
            String file = "Token";
            String Token = Utilities.loadInformationString(file);
            return Token;
        } catch (NullPointerException e) {
            System.out.println("File not Found");
        }
        return "fuck you, fuck you, fuck you";
    }
}
