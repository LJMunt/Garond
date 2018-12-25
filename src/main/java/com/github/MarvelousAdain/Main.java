package com.github.MarvelousAdain;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String token = "NTI2ODcyNjM0MzI0MDI1MzQ0.DwLhMg.MhE5odWzS35c5y_JEa5onex5EIE";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();


        long greetingChannelID = 526874223868641313L;
        api.addServerMemberJoinListener(event -> {
            String greeting = "<@" + event.getUser().getId() + ">" + " Woher kommst du? Du gehörst weder zu den Schürftruppen, noch zu meinen Leuten. Also?!";
            api.getServerTextChannelById(greetingChannelID).get().sendMessage(greeting);
            System.out.println(event.getUser() + " has joined the server.");
        });

        api.addMessageCreateListener(event -> {
            String command = event.getMessage().getContent();

            switch (command.toLowerCase()) {
                case "!help":
                    event.getChannel().sendMessage("Folgende Befehle sind verfügbar: \n!Busse\n!Innos\n!frage" +
                            "\n!ehrenmann");
                    break;
                case "!busse":
                    event.getChannel().sendMessage("150 Goldstücke!");
                    break;
                case "!innos":
                    event.getChannel().sendMessage("Bei Innos, du nervst!");
                    break;
                case "!frage":
                    MessageBuilder mb = new MessageBuilder();
                    mb.append("Falls du noch weitere Fragen hast, ");
                    mb.append("<@" + event.getMessageAuthor().getId() + ">");
                    mb.append(", richte diese an Parcival. Und jetzt nerv nicht mehr!");
                    mb.send(event.getChannel());
                    break;
                case "!ehrenmann":
                    event.getChannel().sendMessage("Man sagt Isgaroth sei ein Ehrenmann. Ich bin mir dessen " +
                            "nicht sicher. Der wahre Ehrenmann bin natürlich immernoch ich.");
                    break;
            }
        });

        Scanner scan = new Scanner(System.in);
        long channelID = 526874223868641313L;
        while (true) {
            String line = scan.nextLine();
            String numStr = "";
            if (line.startsWith("/")) {
                if (line.equalsIgnoreCase("/DeveloperInfo")) {
                    System.out.println(api);
                    System.out.println(api.getActivity());
                    System.out.println(api.getAccountType());
                    System.out.println(api.getCachedMessages());
                    System.out.println(api.getOwner());
                    System.out.println(api.getChannels());
                    System.out.println(api.getYourself());
                    System.out.println(System.getenv());
                    System.out.println(api.getRoles());
                } else {
                    if (line.equalsIgnoreCase("/exit")) {
                        System.out.println("shutdown bot? (y/n)");
                        if (scan.nextLine().equalsIgnoreCase("y")) {
                            System.exit(0);
                        }
                    } else {
                        for (int i = 1; i < line.length(); i++) {
                            char charCheck = line.charAt(i);
                            if (Character.isDigit(charCheck)) {
                                numStr += charCheck;
                            }
                        }
                        try {
                            channelID = Long.parseLong(numStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong input.");
                            continue;
                        }
                    }
                }
            } else {
                if (api.getServerTextChannelById(channelID).get().asServerTextChannel().isPresent()) {
                    api.getServerTextChannelById(channelID).get().sendMessage(line);
                } else {
                    System.out.println("The Channel with the ID " + channelID + " doesn't exist.");
                }

            }
        }
    }


}
