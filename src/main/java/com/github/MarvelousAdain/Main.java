package com.github.MarvelousAdain;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.permission.PermissionState;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String token = "NTI2ODcyNjM0MzI0MDI1MzQ0.DwLhMg.MhE5odWzS35c5y_JEa5onex5EIE";
        long channelID = 0;
        Scanner scan = new Scanner(System.in);
        final String VERSION = "1.0";
        System.out.println("Welcome to Garond Bot " + VERSION);
        System.out.println("Do you wish to load(1) your last ChannelID or enter(2) a new one?");
        String choice = scan.next();
        if (choice.equalsIgnoreCase("1")) {
            try {
                channelID = Utilities.loadInformationLong();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (choice.equalsIgnoreCase("2")) {
                System.out.println("Please enter the channel ID from which the Bot should operate.");
                channelID = Long.parseLong(scan.next());
                Utilities.saveInformation(channelID);
                System.out.println("Channel ID has been saved. Now starting up the bot.");
            } else {
                System.out.println("Not recognized. Please restart the bot.");
            }
        }


        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        long finalChannelID = channelID;
        api.addServerMemberJoinListener(event -> {
            String greeting = "<@" + event.getUser().getId() + ">" + " Woher kommst du? Du gehörst weder zu den Schürftruppen, noch zu meinen Leuten. Also?!";
            api.getServerTextChannelById(finalChannelID).get().sendMessage(greeting);
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
                        if (line.equalsIgnoreCase("/joinURL")) {
                            PermissionsBuilder permissionsBuilder = new PermissionsBuilder();
                            permissionsBuilder.setState(PermissionType.ADMINISTRATOR, PermissionState.ALLOWED);
                            api.getServerTextChannelById(channelID).get().sendMessage(api.createBotInvite(permissionsBuilder.build()));
                        } else {
                            for (int i = 1; i < line.length(); i++) {
                                char charCheck = line.charAt(i);
                                if (Character.isDigit(charCheck)) {
                                    numStr += charCheck;
                                }
                            }
                            try {
                                channelID = Long.parseLong(numStr);
                                System.out.println("Bot now active in: " + api.getChannelById(channelID));
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong input.");
                                continue;
                            }
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
