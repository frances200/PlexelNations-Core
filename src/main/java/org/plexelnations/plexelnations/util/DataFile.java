package org.plexelnations.plexelnations.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public abstract class DataFile {

    public final File file;
    public final FileConfiguration fileConfiguration;
    private static final String FILE_PATH = "plugins/PlexelNations/";
    private final String defaultSection;

    public DataFile(String fileName, String fileExtension, String defaultSection){
        file = new File(FILE_PATH + fileName + "." + fileExtension);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        this.defaultSection = defaultSection;

        if (!exists()) {
            createDefaultSection(defaultSection);
            flush();
        }
    }

    public String getDefaultSection() {
        return defaultSection + ".";
    }

    public void createDefaultSection(String section){
        fileConfiguration.createSection(section);
    }

    public boolean exists(){
        return file.exists();
    }

    public void saveData(String pathToKey, Object value){
        fileConfiguration.set(pathToKey, value);
    }

    public Object getData(String pathToKey){
        return fileConfiguration.get(pathToKey);
    }

    public Integer getInteger(String pathToKey) {
        return Integer.parseInt(Objects.requireNonNull(fileConfiguration.get(pathToKey)).toString());
    }

    public Long getLong(String pathToKey) {
        return Long.parseLong(Objects.requireNonNull(fileConfiguration.get(pathToKey)).toString());
    }

    public Double getDouble(String pathToKey) {
        return Double.parseDouble(Objects.requireNonNull(fileConfiguration.get(pathToKey)).toString());
    }

    public Float getFloat(String pathToKey) {
        return Float.parseFloat(Objects.requireNonNull(fileConfiguration.get(pathToKey)).toString());
    }

    public Boolean getBoolean(String pathToKey) {
        return Boolean.parseBoolean(Objects.requireNonNull(fileConfiguration.get(pathToKey)).toString());
    }

    public ArrayList<String> getStringList(String path){
        final ConfigurationSection section = getConfigurationSection(path);
        if (section == null) {
            return new ArrayList<>();
        }
        final Set<String> set = section.getKeys(false);
        return new ArrayList<>(set);
    }

    public void flush(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConfigurationSection getConfigurationSection(String pathToSection) {
        return fileConfiguration.getConfigurationSection(pathToSection);
    }
}