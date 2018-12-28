package com.github.MarvelousAdain;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public abstract class Utilities {

    //Game PBS
    private static String ds3 = "1:26:52 IGT (Any%)";
    private static String bb = "41:05 IGT (Any%)";
    private static String ol = "13:39 (Any%)";
    private static String ds2 = "1:20:36 (Any%)";
    private static String ds = "1:49:38 IGT (Any%)";
    private static String g2ntr = "46:05 (Any%)";
    private static String dsrp = "36:10 IGT (Any%)";
    private static String dsrb = "1:18:52 IGT (All Bosses)";
    static final ArrayList<String> QUOTES = new ArrayList<>();

    public static void loadArray() {
        try {
            System.out.println("Called loadArray Method");
            BufferedReader br = new BufferedReader(new InputStreamReader(Utilities.class.getClassLoader().getResourceAsStream("Quotes.txt"), StandardCharsets.UTF_8));

            String line;
            while ((line = br.readLine()) != null) {
                QUOTES.add(line);
            }
            System.out.println("Quotes.txt loaded, no Problem.");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveInformation(long l) {
        try {
            long fileContent = l;

            BufferedWriter writer = new BufferedWriter(new FileWriter("./ChannelID"));
            writer.write(fileContent + "");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static long loadInformationLong() throws FileNotFoundException {
        //Default Channel, when everything goes to shit.
        long information;
        try {
            File fin = new File("./ChannelID");
            FileInputStream fis = new FileInputStream(fin.getAbsoluteFile());
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            // (Utilities.class.getClassLoader().getResourceAsStream("ChannelID")
            String temp;
            while ((temp = br.readLine()) != null) {
                information = Long.parseLong(temp);
                System.out.println("loaded ChannelID");
                return information;
            } //else {
            //System.out.println("File is empty, please restart the bot and enter a valid Channel ID.");
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 526874223868641313L;
    }

    public static String loadInformationString(String path) throws NullPointerException {
        //Default Channel, when everything goes to shit.
        try {
            System.out.println("Called loadInformationString Method");
            BufferedReader br = new BufferedReader(new InputStreamReader(Utilities.class.getClassLoader().getResourceAsStream(path)));

            String information;
            while ((information = br.readLine()) != null) {
                return information;
            }
            System.out.println("Token loaded, no Problem.");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static void announceStream(String command, MessageCreateEvent event) {
        if (event.getMessageAuthor().getDiscriminatedName().equalsIgnoreCase("Karstix#5645")) {
            MessageBuilder mb = new MessageBuilder();
            mb.append("@everyone ");
            mb.append("Bei Innos! Karstix streamt ");
            mb.append(command.substring(8) + ". ");
            mb.append("Jeder der nicht erscheint, muss eine Busse von 150 Goldstücken bezahlen! ");
            mb.append("https://www.twitch.tv/karstix");
            mb.send(event.getChannel());
        }
    }

    public static void announcePersonalBests(MessageCreateEvent event, String command) {
        if (command.substring(3).length() > 3) {
            MessageBuilder mb = new MessageBuilder();
            String gameName = command.substring(4);
            mb.append("Personal Best für: " + gameName.toLowerCase() + " ");
            switch (gameName.toLowerCase()) {
                case "dark souls 3":
                    mb.append(ds3);
                    break;
                case "bloodborne":
                    mb.append(bb);
                    break;
                case "outlast":
                    mb.append(ol);
                    break;
                case "dark souls 2":
                    mb.append(ds2);
                    break;
                case "dark souls":
                    mb.append(ds);
                    break;
                case "gothic 2 die nacht des raben":
                    mb.append(g2ntr);
                    break;
                case "gothic 2":
                    mb.append(g2ntr);
                    break;
                case "dark souls remastered":
                    mb.append("\n");
                    mb.append(dsrp);
                    mb.append("\n");
                    mb.append(dsrb);
                    break;
                case "dark souls remastered any%":
                    mb.append(dsrp);
                    break;
                case "dark souls remastered all bosses":
                    mb.append(dsrb);
                    break;
                default:
                    event.getChannel().sendMessage("Spiel nicht gefunden!");
                    return;
            }
            mb.send(event.getChannel());
        } else {
            event.getChannel().sendMessage("Bei Innos! Gib ein Spiel an!");
        }
    }

    public static void announceLeaderboard(String command, MessageCreateEvent event) {
        MessageBuilder mb = new MessageBuilder();
        mb.append("Dies ist das Leaderboard derjenigen, die mich getötet haben. Verdammt!");
        mb.append("\n------------------------------------------------------------------\n");
        mb.append("*                              Platz  |  Name  | Zeit                                     *\n");
        mb.append("*                                1.     Valerio   06:52                                     * \n");
        mb.append("*                               2.     Karstix   07:07                                     * ");
        mb.append("\n------------------------------------------------------------------\n");
        mb.send(event.getChannel());

    }

    public static void randomQuote(MessageCreateEvent event) {
        Random rnd = new Random();
        String quote = QUOTES.get(rnd.nextInt(QUOTES.size()));
        MessageBuilder mb = new MessageBuilder();
        mb.append("Das Zitat des Tages:");
        mb.appendNewLine();
        mb.append(quote);
        mb.send(event.getChannel());
    }
}

