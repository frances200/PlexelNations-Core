package org.plexelnations.plexelnations.commands.town;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.models.Command;
import org.plexelnations.plexelnations.models.Town;
import org.plexelnations.plexelnations.models.enums.Nations;
import org.plexelnations.plexelnations.util.CommandBuilder;
import org.plexelnations.plexelnations.worldguard.WorldGuardUtils;

import java.util.ArrayList;
import java.util.List;

public class CreateTownCommand extends Command {

    private static final String INSIDE_REGION = ChatColor.RED + "Not inside region";
    private static final String REGION_ALREADY_LINKED = ChatColor.RED + "A town with this region already exists";
    private static final String TOWN_EXISTS = ChatColor.RED + "A town with this ID already exists";
    private static final String TOWN_ADDED = ChatColor.GREEN + "Town created";

    public CreateTownCommand() {
        super(new CommandBuilder()
                .setParentCommand("town")
                .setDescription("Create a town")
                .setArguments(0, new String[]{"create"})
                .setUsage("/town create <town-id>")
                .setHasLimit(false)
                .isAdmin(true));
    }

    @Override
    public void execute(Player player, String[] args) {

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
}
