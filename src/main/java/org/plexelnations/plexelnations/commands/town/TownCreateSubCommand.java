package org.plexelnations.plexelnations.commands.town;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.commands.SubCommand;
import org.plexelnations.plexelnations.models.Town;
import org.plexelnations.plexelnations.models.enums.Nations;
import org.plexelnations.plexelnations.worldguard.WorldGuardUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TownCreateSubCommand extends SubCommand {

    private static final String INSIDE_REGION = ChatColor.RED + "Not inside region";
    private static final String REGION_ALREADY_LINKED = ChatColor.RED + "A town with this region already exists";
    private static final String TOWN_EXISTS = ChatColor.RED + "A town with this ID already exists";
    private static final String TOWN_ADDED = ChatColor.GREEN + "Town created";

    public TownCreateSubCommand() {
        super(2, 2, "create [town-id]", "Create a town connected to the region you are in", null);
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

        final ProtectedRegion region = WorldGuardUtils.getRegionInside(player);
        if (region == null) {
            player.sendMessage(INSIDE_REGION);
            return;
        }
        if (Town.hasRegionAll(region)) {
            player.sendMessage(REGION_ALREADY_LINKED);
            return;
        }
        if (Town.exists(args[1])) {
            player.sendMessage(TOWN_EXISTS);
            return;
        }

        final List<Location> attackLocations = new ArrayList<>();
        final List<Location> defendLocations = new ArrayList<>();
        attackLocations.add(new Location(player.getWorld(), 0,0,0,0,0));
        defendLocations.add(new Location(player.getWorld(), 0,0,0,0,0));

        final Town town = Town.builder()
                .id(args[1])
                .world(player.getWorld().getName())
                .bank(0)
                .tier(0)
                .region(region)
                .spawnLocation(new Location(player.getWorld(), 0,0,0,0,0))
                .attackSpawnLocations(attackLocations)
                .defendSpawnLocations(defendLocations)
                .nation(Nations.NONE)
                .displayName("Town " + (Town.getTowns().size() + 1))
                .build();

        Town.addTown(town);
        player.sendMessage(TOWN_ADDED);
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
