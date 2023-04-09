package org.plexelnations.plexelnations.models;

import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.util.CommandBuilder;

import java.util.HashMap;

public abstract class Command {
    private final String parentCommand;
    private final String description;
    private final HashMap<Integer, String[]> arguments;
    private final boolean hasLimit;
    private final String usage;
    private final boolean admin;

    public Command(CommandBuilder builder) {
        this.parentCommand = builder.getParentCommand();
        this.description = builder.getDescription();
        this.arguments = builder.getArguments();
        this.hasLimit = builder.isHasLimit();
        this.usage = builder.getUsage();
        this.admin = builder.isAdmin();
    }

    public String getParentCommand() {
        return parentCommand;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<Integer, String[]> getArguments() {
        return arguments;
    }

    public boolean hasLimit() {
        return hasLimit;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isAdmin() {
        return admin;
    }

    public abstract void execute(Player player, String[] args);
}
