package org.plexelnations.plexelnations.util;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.YamlConfiguration;
import org.plexelnations.plexelnations.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@UtilityClass
public class ConfigFile {

    private File file;
    private YamlConfiguration fileConfiguration;

    public void init(Main plugin){
        file = new File(plugin.getDataFolder() + File.separator + "config.yml");

        if(!file.exists())
            plugin.saveResource("config.yml", true);

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public Object getData(String path){
        return fileConfiguration.get(path);
    }

    public ArrayList<String> getStringList(String path){
        return (ArrayList<String>) fileConfiguration.getStringList(path);
    }

    public ArrayList<String> getConfigurationSection(String path){
        Set<String> set = fileConfiguration.getConfigurationSection(path).getKeys(false);
        return new ArrayList<>(set);
    }

    public void saveData(String path, Object value){
        fileConfiguration.set(path, value);
    }

    public void saveConfig(){
        try{
            fileConfiguration.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getAutosaveInterval() {
        return (int) getData("config.autosave-interval");
    }
}