package org.plexelnations.plexelnations.util;

import java.util.HashMap;

public class CommandBuilder {
    private String parentCommand;
    private String description;
    private final HashMap<Integer, String[]> arguments = new HashMap<>();
    private boolean hasLimit;
    private String usage;
    private boolean admin;

    public CommandBuilder setParentCommand(String parentCommand) {
        this.parentCommand = parentCommand;
        return this;
    }

    public CommandBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder setArguments(final int index, final String[] arguments) {
        this.arguments.put(index, arguments);
        return this;
    }

    public CommandBuilder setHasLimit(boolean hasLimit) {
        this.hasLimit = hasLimit;
        return this;
    }

    public CommandBuilder setUsage(String usage) {
        this.usage = usage;
        return this;
    }

    public CommandBuilder isAdmin(boolean value) {
        this.admin = value;
        return this;
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

    public boolean isHasLimit() {
        return hasLimit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getUsage() {
        return usage;
    }
}
