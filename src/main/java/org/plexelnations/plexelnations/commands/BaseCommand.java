package org.plexelnations.plexelnations.commands;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class BaseCommand implements CommandExecutor, TabCompleter {
    private final String name;
    private final List<String> aliases;
    private final List<String> permissions;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public BaseCommand(String name, List<String> aliases, List<String> permissions) {
        this.name = name;
        this.aliases = aliases;
        this.permissions = permissions;
    }

    protected abstract boolean onBaseCommandEntered(Player player);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return true;
        final Player player = (Player) commandSender;

        if (!checkPermission(player)) {
            return true;
        }

        if (strings.length == 0) {
            return onBaseCommandEntered(player);
        }

        final SubCommand subCommand = getSubCommands().get(strings[0]);
        if (subCommand == null) {
            return displayHelp(player);
        }

        subCommand.execute(player, strings);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return subCommands.keySet().stream()
                    .filter(subCommand -> subCommand.startsWith(strings[0]))
                    .collect(Collectors.toList());
        } else if (strings.length > 1) {
            SubCommand subCommand = subCommands.get(strings[0]);
            if (subCommand != null) {
                return subCommand.onTabComplete(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
            }
        }
        return Collections.emptyList();
    }

    /**
     * Checks if the {@code CommandSender} has the correct permission
     *
     * @param player The {@code CommandSender} that could be any source
     * @return a {@code boolean} that represents whether the player has the correct command permissions
     */
    protected boolean checkPermission(Player player) {
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

    public boolean displayHelp(Player player) {
        getSubCommands().keySet().forEach(command -> {
            final SubCommand subCommand = getSubCommands().get(command);
            if (subCommand.checkPermissions(player)) {
                player.sendMessage("/" + getName() + " " + subCommand.getUsage() + " - " + subCommand.getDescription());
            }
        });
        return true;
    }
}
