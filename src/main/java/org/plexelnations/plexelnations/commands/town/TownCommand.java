package org.plexelnations.plexelnations.commands.town;

import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.commands.BaseCommand;
import org.plexelnations.plexelnations.gui.TownsMenu;

import java.util.List;

public class TownCommand extends BaseCommand {
    public TownCommand() {
        super("town", List.of("t", "towns"), null);
        getSubCommands().put("create", new TownCreateSubCommand());
    }

    @Override
    protected boolean onBaseCommandEntered(Player player) {
        new TownsMenu().displayMenu(player);
        return true;
    }
}
