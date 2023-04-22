package org.plexelnations.plexelnations.commands.nation;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.commands.SubCommand;
import org.plexelnations.plexelnations.models.NPlayer;
import org.plexelnations.plexelnations.models.enums.Message;
import org.plexelnations.plexelnations.models.enums.Nations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JoinSubCommand extends SubCommand {

    public JoinSubCommand() {
        super(2, 2, "join [nation]", "Join a nation", null);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!this.checkPermissions(player)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
            return;
        }
        if (!this.checkArgs(args)) {
            player.sendMessage(ChatColor.RED + "Invalid arguments");
            return;
        }

        final Nations nation = Nations.getNationByShortName(args[1]);
        if (nation == null) {
            player.sendMessage(ChatColor.RED + "Couldn't join that nation");
            return;
        }

        final NPlayer nPlayer = NPlayer.getNPlayer(player);
        if (isInNation(nPlayer) && nPlayer.isChangedNation()) {
            player.sendMessage(Message.ALREADY_IN_NATION.format(nPlayer.getNation().getNationColor(), nPlayer.getNation().getNationName()));
            return;
        }

        nPlayer.setNation(nation);
        nPlayer.setChangedNation(true);
        player.sendMessage(Message.JOIN_NATION.format(nation.getNationColor(), nation.getNationName()));
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args) {
        final List<String> nations = List.of("Crimson", "Oceanic", "Forest", "Golden");
        if (args.length == 1)
            return nations.stream()
                    .filter(subCommand -> subCommand.startsWith(args[0]))
                    .collect(Collectors.toList());
        return Collections.emptyList();
    }

    private boolean isInNation(NPlayer player) {
        return player.getNation() != Nations.NONE;
    }
}
