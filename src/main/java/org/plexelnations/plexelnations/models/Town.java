package org.plexelnations.plexelnations.models;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Location;
import org.plexelnations.plexelnations.files.TownFile;
import org.plexelnations.plexelnations.models.enums.Nations;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Town {

    private String id;
    private String displayName;
    private long bank;
    private Nations nation;
    private String world;
    private int tier;
    private ProtectedRegion region;
    private Location spawnLocation;
    private List<Location> attackSpawnLocations;
    private List<Location> defendSpawnLocations;

    private static List<Town> towns = new ArrayList<>();

    public int increaseTier() {
        return tier++;
    }

    public static void addTown(Town town) {
        towns.add(town);
    }

    public static List<Town> getTowns() {
        return towns;
    }

    public static void forceSave() {
        final TownFile townFile = TownFile.getInstance();
        for (Town town : towns) {
            townFile.saveTown(town);
        }
        townFile.flush();
    }

    public static void load() {
        TownFile.getInstance().loadTowns();
    }

    public static boolean hasRegionAll(ProtectedRegion protectedRegion) {
        return towns.stream().anyMatch(town -> town.getRegion().getId().equalsIgnoreCase(protectedRegion.getId()));
    }

    public static boolean exists(String id) {
        return towns.stream().anyMatch(town -> town.getId().equalsIgnoreCase(id));
    }

    public static void unload() {
        towns.clear();
    }
}
