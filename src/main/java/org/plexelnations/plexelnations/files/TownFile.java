package org.plexelnations.plexelnations.files;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.plexelnations.plexelnations.models.Town;
import org.plexelnations.plexelnations.models.enums.Nations;
import org.plexelnations.plexelnations.util.DataFile;
import org.plexelnations.plexelnations.worldguard.WorldGuardUtils;

import java.util.ArrayList;
import java.util.List;

public class TownFile extends DataFile {

    private static TownFile townFile;

    public static TownFile getInstance() {
        if (townFile == null) {
            townFile = new TownFile("towns", "yml", "towns");
        }
        return townFile;
    }

    private TownFile(String fileName, String fileExtension, String defaultSection) {
        super(fileName, fileExtension, defaultSection);
    }

    public void saveTown(Town town) {
        saveMetadata(town);
        saveAttackSpawnLocations(town);
        saveDefendSpawnLocations(town);
    }

    private void saveMetadata(Town town) {
        saveData(getDefaultSection() + town.getId() + ".display-name", town.getDisplayName());
        saveData(getDefaultSection() + town.getId() + ".bank", town.getBank());
        saveData(getDefaultSection() + town.getId() + ".nation", town.getNation().toString());
        saveData(getDefaultSection() + town.getId() + ".tier", town.getTier());
        saveData(getDefaultSection() + town.getId() + ".region", town.getRegion().getId());
        saveData(getDefaultSection() + town.getId() + ".world", town.getWorld());
        saveData(getDefaultSection() + town.getId() + ".world", town.getWorld());
        saveData(getDefaultSection() + town.getId() + ".spawn-location.x", town.getSpawnLocation().getBlockX());
        saveData(getDefaultSection() + town.getId() + ".spawn-location.y", town.getSpawnLocation().getBlockY());
        saveData(getDefaultSection() + town.getId() + ".spawn-location.z", town.getSpawnLocation().getBlockZ());
        saveData(getDefaultSection() + town.getId() + ".spawn-location.yaw", town.getSpawnLocation().getYaw());
        saveData(getDefaultSection() + town.getId() + ".spawn-location.pitch", town.getSpawnLocation().getPitch());
    }

    private void saveAttackSpawnLocations(Town town) {
        for (int i = 0; i < town.getAttackSpawnLocations().size(); i++) {
            final Location location = town.getAttackSpawnLocations().get(i);
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-attack."+ i + ".x", location.getBlockX());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-attack."+ i + ".y", location.getBlockY());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-attack."+ i + ".z", location.getBlockZ());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-attack."+ i + ".yaw", location.getYaw());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-attack."+ i + ".pitch", location.getPitch());
        }
    }

    private void saveDefendSpawnLocations(Town town) {
        for (int i = 0; i < town.getDefendSpawnLocations().size(); i++) {
            final Location location = town.getDefendSpawnLocations().get(i);
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-defend."+ i + ".x", location.getBlockX());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-defend."+ i + ".y", location.getBlockY());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-defend."+ i + ".z", location.getBlockZ());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-defend."+ i + ".yaw", location.getYaw());
            saveData(getDefaultSection() + town.getId() + ".spawn-locations-defend."+ i + ".pitch", location.getPitch());
        }
    }

    public void loadTowns() {
        for (String id : getStringList("towns")) {
            final Town.TownBuilder builder = Town.builder();
            final World world = Bukkit.getWorld(getData(getDefaultSection() + id + ".world").toString());

            loadMetadata(id, builder, world);
            loadDefendSpawnLocations(id, builder, world);
            loadAttackSpawnLocations(id, builder, world);
            Town.getTowns().add(builder.build());
        }
    }

    private void loadMetadata(String id, Town.TownBuilder builder, World world) {
        final String regionId = getData(getDefaultSection() + id + ".region").toString();
        final ProtectedRegion region = WorldGuardUtils.getProtectedRegion(world, regionId);
        final Location spawnLocation = new Location(world,
                getDouble(getDefaultSection()+id+".spawn-location.x"),
                getDouble(getDefaultSection()+id+".spawn-location.y"),
                getDouble(getDefaultSection()+id+".spawn-location.z"),
                getFloat(getDefaultSection()+id+".spawn-location.yaw"),
                getFloat(getDefaultSection()+id+".spawn-location.pitch"));

        builder.id(id)
                .displayName(getData(getDefaultSection() + id + ".display-name").toString())
                .bank(getLong(getDefaultSection() + id + ".bank"))
                .nation(Nations.valueOf(getData(getDefaultSection() + id + ".nation").toString()))
                .tier(getInteger(getDefaultSection() + id + ".tier"))
                .world(Bukkit.getWorld(getData(getDefaultSection() + id + ".world").toString()).getName())
                .region(region)
                .spawnLocation(spawnLocation);
    }

    private void loadAttackSpawnLocations(String id, Town.TownBuilder builder, World world) {
        final List<Location> attackLocations = new ArrayList<>();
        for (String location : getStringList(getDefaultSection() + id + ".spawn-locations-attack")) {
            final Location attackLocation = new Location(world,
                    getDouble(getDefaultSection()+id+".spawn-locations-attack."+location+".x"),
                    getDouble(getDefaultSection()+id+".spawn-locations-attack."+location+".y"),
                    getDouble(getDefaultSection()+id+".spawn-locations-attack."+location+".z"),
                    getFloat(getDefaultSection()+id+".spawn-locations-attack."+location+".yaw"),
                    getFloat(getDefaultSection()+id+".spawn-locations-attack."+location+".pitch"));
            attackLocations.add(attackLocation);
        }
        builder.attackSpawnLocations(attackLocations);
    }

    private void loadDefendSpawnLocations(String id, Town.TownBuilder builder, World world) {
        final List<Location> defendLocations = new ArrayList<>();
        for (String location : getStringList(getDefaultSection() + id + ".spawn-locations-defend")) {
            final Location defendLocation = new Location(world,
                    getDouble(getDefaultSection()+id+".spawn-locations-defend."+location+".x"),
                    getDouble(getDefaultSection()+id+".spawn-locations-defend."+location+".y"),
                    getDouble(getDefaultSection()+id+".spawn-locations-defend."+location+".z"),
                    getFloat(getDefaultSection()+id+".spawn-locations-defend."+location+".yaw"),
                    getFloat(getDefaultSection()+id+".spawn-locations-defend."+location+".pitch"));
            defendLocations.add(defendLocation);
        }
        builder.defendSpawnLocations(defendLocations);
    }
}
