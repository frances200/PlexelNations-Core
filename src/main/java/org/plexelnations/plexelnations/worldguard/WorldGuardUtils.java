package org.plexelnations.plexelnations.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WorldGuardUtils {
    public static ProtectedRegion getProtectedRegion(org.bukkit.World world, String id) {
        final RegionManager regions = getWoldRegions(world);

        return regions.getRegion(id);
    }

    public static void setRegionTitleColor(ProtectedRegion region, ChatColor color) {
        String message = region.getFlag(Flags.GREET_TITLE);
        region.setFlag(Flags.GREET_TITLE, color+message);
    }

    public static ProtectedRegion getRegionInside(Player player) {
        final RegionManager regions = getWoldRegions(player.getWorld());

        if (regions == null)
            return null;

        final BlockVector3 playerLocation = BlockVector3.at(player.getLocation().getBlockX(),
                player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        final ApplicableRegionSet regionSet = regions.getApplicableRegions(playerLocation);

        if (regionSet.getRegions().size() == 0)
            return null;

        return (ProtectedRegion) regionSet.getRegions().toArray()[0];
    }

    public static RegionManager getWoldRegions(org.bukkit.World world) {
        final World worldGuardWorld = BukkitAdapter.adapt(world);
        final RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();

        return regionContainer.get(worldGuardWorld);
    }
}
