package org.plexelnations.plexelnations.models.enums;

import org.bukkit.ChatColor;
import org.plexelnations.plexelnations.util.ConfigFile;

public enum Message {

    JOIN_NATION("&7You have joined the %c%s&c!"),
    ALREADY_IN_NATION("&cYou are already in %c%s&c!"),
    INCORRECT_FACTION_NATION("&cThis faction is not part of your nation!"),
    JOIN_NATION_FIRST("&cPlease join a nation before creating a faction, use &7/nations join <nation>"),
    FRIENDLY_FIRE("&cPlease don't try to damage teammates");

    private String message;

    Message(String message) {
        this.message = message;
    }

    public static void init() {
        for (Message message : Message.values()) {
            final String path = "messages." + message.name().toLowerCase();

            message.message = ConfigFile.getData(path).toString();
        }
    }

    public String format(Object... args) {
        String formattedMessage = this.message;
        for (Object arg : args) {
            if (arg instanceof ChatColor) {
                formattedMessage = formattedMessage.replace("%c", ((ChatColor) arg).toString());
            } else if (arg instanceof String) {
                formattedMessage = formattedMessage.replace("%s", (String) arg);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', formattedMessage);
    }

}
