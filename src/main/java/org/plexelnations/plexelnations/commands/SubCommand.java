package org.plexelnations.plexelnations.commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public abstract class SubCommand {

    private final int minArgs;
    private final int maxArgs;
    private final String usage;
    private final String description;
    private final List<String> permissions;

    public SubCommand(int minArgs, int maxArgs, String usage, String description, List<String> permissions) {
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.usage = usage;
        this.description = description;
        this.permissions = permissions;
    }

    /**
     * Execute the command based on custom implementation
     * @param player The {@code CommandSender} that sent the command
     * @param args The arguments passed on the command as {@code String[]}
     */
    public abstract void execute(Player player, String[] args);

    protected abstract List<String> onTabComplete(CommandSender sender, String[] args);

    /**
     * Check if a player put in the right amount of arguments
     *
     * @param args The arguments a player put in the chat
     * @return {@code boolean}
     */
    public boolean checkArgs(String @NotNull [] args) {
        return args.length >= minArgs && (args.length <= maxArgs || maxArgs == -1);
    }

    /**
     * Check whether the {@code CommandSender} has permission to execute this command
     * @param player The {@code CommandSender} that sent the command
     * @return {@code boolean}
     */
    public boolean checkPermissions(Player player) {
        if (permissions == null || permissions.isEmpty()) {
            return true;
        }
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }
}
