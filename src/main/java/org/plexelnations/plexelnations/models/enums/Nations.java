package org.plexelnations.plexelnations.models.enums;

import org.bukkit.ChatColor;

import java.util.Arrays;

public enum Nations {
    NATION1("Crimson Legion", ChatColor.RED),
    NATION2("Oceanic Empire", ChatColor.BLUE),
    NATION3("Forest Kingdom", ChatColor.GREEN),
    NATION4("Golden Alliance", ChatColor.YELLOW),
    NONE("None", ChatColor.WHITE);

    private String name;
    private ChatColor color;

    Nations(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public String getNationName() {
        return name;
    }

    public String getNationShortName() {
        return name.split(" ")[0];
    }

    public static Nations getNationByShortName(String shortName) {
        for (Nations nation : Nations.getNations()) {
            if (nation.getNationShortName().equalsIgnoreCase(shortName)) {
                return nation;
            }
        }
        return null;
    }

    public ChatColor getNationColor() {
        return color;
    }

    public static Nations[] getNations() {
        return Arrays.stream(Nations.values())
                .filter(nation -> !nation.equals(Nations.NONE))
                .toArray(Nations[]::new);
    }
}
