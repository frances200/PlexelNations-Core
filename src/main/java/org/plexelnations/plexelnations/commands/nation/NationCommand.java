package org.plexelnations.plexelnations.commands.nation;

import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.commands.BaseCommand;

import java.util.List;

public class NationCommand extends BaseCommand {
    public NationCommand() {
        super("nations", List.of("n", "nation"), null);
        getSubCommands().put("join", new JoinSubCommand());
    }

    @Override
    protected boolean onBaseCommandEntered(Player player) {
        return this.displayHelp(player);
    }
}
