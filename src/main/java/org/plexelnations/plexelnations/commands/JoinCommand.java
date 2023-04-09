package org.plexelnations.plexelnations.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.models.Command;
import org.plexelnations.plexelnations.models.NPlayer;
import org.plexelnations.plexelnations.models.enums.Message;
import org.plexelnations.plexelnations.models.enums.Nations;
import org.plexelnations.plexelnations.util.CommandBuilder;

public class JoinCommand extends Command {

    public JoinCommand() {
        super(new CommandBuilder()
                .setParentCommand("nations")
                .setDescription("Join a nation")
                .setUsage("/nations join <nation>")
                .setArguments(0, new String[]{"join"})
                .setArguments(1, new String[]{
                        Nations.NATION1.getNationShortName(),
                        Nations.NATION2.getNationShortName(),
                        Nations.NATION3.getNationShortName(),
                        Nations.NATION4.getNationShortName()})
                .setHasLimit(true)
                .isAdmin(false));
    }

    @Override
    public void execute(Player player, String[] args) {
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

    private boolean isInNation(NPlayer player) {
        return player.getNation() != Nations.NONE;
    }
}
