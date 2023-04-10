package org.plexelnations.plexelnations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.plexelnations.plexelnations.gui.TownsMenu;
import org.plexelnations.plexelnations.models.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    public static final List<Command> commands = new ArrayList<>();

    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender,
            @NotNull org.bukkit.command.Command command,
            @NotNull String s,
            @NotNull String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            if (command.getName().equalsIgnoreCase("town")) {
                new TownsMenu().displayMenu(player);
                return true;
            }
            return displayHelp(player);
        }

        for (Command cmd : commands) {
            if (!cmd.getParentCommand().equalsIgnoreCase(command.getName()))
                continue;

            if (Arrays.stream(cmd.getArguments().get(0)).noneMatch(s1 -> s1.equalsIgnoreCase(strings[0])))
                continue;

            if (cmd.isAdmin() && !player.isOp())
                return true;

            if (cmd.getArguments().size() != strings.length && cmd.hasLimit()) {
                player.sendMessage("Invalid arguments");
                return true;
            }

            if (cmd.getArguments().size() >= strings.length && !cmd.hasLimit()) {
                player.sendMessage("Invalid arguments");
                return true;
            }

            for (int i = 0; i < cmd.getArguments().size(); i++) {
                String[] args = cmd.getArguments().get(i);
                boolean found = false;
                for (String arg : args) {
                    if (arg.equalsIgnoreCase(strings[i])) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    player.sendMessage("Invalid arguments");
                    return true;
                }
            }

            cmd.execute(player, strings);
            return true;
        }

        return displayHelp(player);
    }

    public static boolean displayHelp(Player player) {
        commands.forEach(command ->
                player.sendMessage(command.getUsage() + " - " + command.getDescription()));
        return true;
    }
}
