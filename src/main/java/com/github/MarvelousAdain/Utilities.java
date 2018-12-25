package com.github.MarvelousAdain;

import java.io.*;
import java.util.Scanner;

public abstract class Utilities {

    public static void saveInformation(long l) {
        try {
            long fileContent = l;

            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/github/MarvelousAdain/ChannelID"));
            writer.write(fileContent + "");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static long loadInformationLong() throws FileNotFoundException {
        //Default Channel, when everything goes to shit.
        long information = 424330978039824405L;
        Scanner fileReader = new Scanner(new File("src/main/java/com/github/MarvelousAdain/ChannelID"));
        if (fileReader.hasNextLong()) {
            information = fileReader.nextLong();
        } else {
            System.out.println("File is empty, please restart the bot and enter a valid Channel ID.");
        }
        return information;
    }

    public static String loadInformationString() throws FileNotFoundException {
        //Default Channel, when everything goes to shit.
        Scanner fileReader = new Scanner(new File("src/main/java/com/github/MarvelousAdain/Token"));
        String information = "";
        if (fileReader.hasNextLine()) {
            information = fileReader.nextLine();
        } else {
            System.out.println("File is empty, please restart the bot and enter a valid Channel ID.");
        }
        return information;
    }

}

