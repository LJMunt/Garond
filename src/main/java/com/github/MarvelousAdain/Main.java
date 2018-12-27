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
        String token = Secret.getToken();
        if (token == "0") {
            System.out.println("The Bot lacks the Token. Shutting off Bot.");
            System.exit(0);
        }
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

        api.addServerJoinListener(event -> {
            System.out.println("the bot has joined a new server: " + event.getServer().getName());
        });

        api.addMessageCreateListener(event -> {
            String command = event.getMessage().getContent();

            switch (command.toLowerCase()) {
                case "!help":
                    event.getChannel().sendMessage("Folgende Befehle sind verfügbar: \n!Busse\n!Innos\n!Frage" +
                            "\n!Ehrenmann\n!PB\n!Stream\t(nur für Karstix)\nBei Fragen oder Anregungen, bitte sendet diese an" +
                            " <@237873960564424704>");
                    System.out.println("||Help|| " + event.getServer().get().getName() + " - " + event.getServer().get().getCreationTimestamp() +
                            " - " + event.getMessageAuthor().getDiscriminatedName());
                    break;
                case "!busse":
                    event.getChannel().sendMessage("150 Goldstücke!");
                    System.out.println("||Busse|| " + event.getServer().get().getName() + " - " + event.getServer().get().getCreationTimestamp() +
                            " - " + event.getMessageAuthor().getDiscriminatedName());
                    break;
                case "!innos":
                    System.out.println("||Innos|| " + event.getServer().get().getName() + " - " + event.getServer().get().getCreationTimestamp() +
                            " - " + event.getMessageAuthor().getName());
                    event.getChannel().sendMessage("Bei Innos, du nervst!");
                    break;
                case "!frage":
                    System.out.println("||Frage|| " + event.getServer().get().getName() + " - " + event.getServer().get().getCreationTimestamp() +
                            " - " + event.getMessageAuthor().getDiscriminatedName());
                    MessageBuilder mb = new MessageBuilder();
                    mb.append("Falls du noch weitere Fragen hast, ");
                    mb.append("<@" + event.getMessageAuthor().getId() + ">");
                    mb.append(", richte diese an Parcival. Und jetzt nerv nicht mehr!");
                    mb.send(event.getChannel());
                    break;
                case "!ehrenmann":
                    System.out.println("||Ehrenmann|| " + event.getServer().get().getName() + " - " + event.getServer().get().getCreationTimestamp() +
                            " - " + event.getMessageAuthor().getDiscriminatedName());
                    event.getChannel().sendMessage("Man sagt Isgaroth sei ein Ehrenmann. Ich bin mir dessen " +
                            "nicht sicher. Der wahre Ehrenmann bin natürlich immernoch ich.");
                    break;

                default:
                    if (command.startsWith("!stream") ^ command.startsWith("!Stream")) {
                        System.out.println("||Stream|| " + event.getServer().get().getName() + " - " + event.getServer().get().getCreationTimestamp() +
                                " - " + event.getMessageAuthor().getDiscriminatedName());
                        Utilities.announceStream(command, event);
                    }
                    if (command.startsWith("!pb") ^ command.startsWith("!Pb") ^ command.startsWith("!PB")) {
                        System.out.println("||PersonalBest|| " + event.getServer().get().getName() + " - " + event.getServer().get().getCreationTimestamp() +
                                " - " + event.getMessageAuthor().getDiscriminatedName());
                        Utilities.announcePersonalBests(event, command);
                    }
            }

        });

        while (true) {
            String line = scan.nextLine();
            String numStr = "";
            if (line.startsWith("/")) {
                if (line.equalsIgnoreCase("/developerinfo")) {
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
                            api.getServerTextChannelById(channelID).get().sendMessage("Zeit zum Schlafen. Ich werde wiederkehren!");
                            System.exit(0);
                        }
                    } else {
                        if (line.equalsIgnoreCase("/help")) {
                            System.out.println("The following commands are available \nDeveloperInfo Displays some info." +
                                    "\nexit  shuts of the bot. \njoinURL displays the Bot Invite (Admin Priv)");
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
                }
            } else {
                if (api.getServerTextChannelById(channelID).get().asServerTextChannel().isPresent()) {
                    api.getServerTextChannelById(channelID).get().sendMessage(line);
                    System.out.println("||Bot-Message|| " + api.getServerTextChannelById(channelID).get().getServer().getName() + " "
                            + api.getServerTextChannelById(channelID).get().getName());
                } else {
                    System.out.println("The Channel with the ID " + channelID + " doesn't exist.");
                }

            }
        }
    }


}
