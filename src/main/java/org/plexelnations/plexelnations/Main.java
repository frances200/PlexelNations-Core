package org.plexelnations.plexelnations;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;
import org.plexelnations.plexelnations.commands.nation.NationCommand;
import org.plexelnations.plexelnations.commands.town.TownCommand;
import org.plexelnations.plexelnations.models.NPlayer;
import org.plexelnations.plexelnations.models.Town;
import org.plexelnations.plexelnations.models.enums.Message;
import org.plexelnations.plexelnations.models.enums.Nations;
import org.plexelnations.plexelnations.tasks.AutosaveTask;
import org.plexelnations.plexelnations.util.ConfigFile;
import org.plexelnations.plexelnations.util.Logger;
import org.plexelnations.plexelnations.util.Wars;

public final class Main extends JavaPlugin {

    private static final String LOG_CREATE_CONFIG = "Creating config/data file(s)...";
    private static final String LOG_INIT_CONFIG = "Initializing config/data file(s)...";
    private static final String LOG_REGISTER_COMMANDS = "Registering commands...";
    private static final String LOG_PLUGIN_ENABLED = "Plugin has been enabled!";
    private static final String LOG_PLUGIN_DISABLED = "Plugin has been disabled!";

    @Override
    public void onEnable() {
        Logger.getInstance().info(LOG_CREATE_CONFIG);
        ConfigFile.init(this);

        Logger.getInstance().info(LOG_INIT_CONFIG);
        initNations();
        Message.init();
        NPlayer.load();
        Town.load();

        Logger.getInstance().info(LOG_REGISTER_COMMANDS);
        this.getCommand("nations").setExecutor(new NationCommand());
        this.getCommand("town").setExecutor(new TownCommand());

        Wars.getInstance().init(this);

        final int saveTicks = ConfigFile.getAutosaveInterval() * 20 * 60;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AutosaveTask(), saveTicks, saveTicks);
        Bukkit.getPluginManager().registerEvents(new NEvents(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        Logger.getInstance().success(LOG_PLUGIN_ENABLED);
    }

    @Override
    public void onDisable() {
        NPlayer.forceSave();
        Town.forceSave();
        NPlayer.unload();
        Town.unload();
        Logger.getInstance().error(LOG_PLUGIN_DISABLED);
    }

    private void initNations() {
        for (Nations nation : Nations.getNations()) {
            final String path = "nations." + nation.name().toLowerCase();
            final String name = ConfigFile.getData(path + ".name").toString();
            final String color = ConfigFile.getData(path + ".color").toString();

            nation.setName(name);
            nation.setColor(ChatColor.valueOf(color.toUpperCase()));
        }
    }
}
