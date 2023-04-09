package org.plexelnations.plexelnations.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Logger {
    private final String prefix = "["+ChatColor.BOLD+ChatColor.YELLOW+"Plexel"+ChatColor.RED+ChatColor.BOLD+"MC"+ChatColor.RESET+"] ";

    private static Logger logger;
    private final ConsoleCommandSender console;

    public Logger() {
        this.console = Bukkit.getServer().getConsoleSender();
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public void info(String message) {
        console.sendMessage(prefix + ChatColor.BLUE + message);
    }

    public void warn(String message) {
        console.sendMessage(prefix + ChatColor.GOLD + message);
    }

    public void error(String message) {
        console.sendMessage(prefix + ChatColor.RED + message);
    }

    public void success(String message) {
        console.sendMessage(prefix + ChatColor.GREEN + message);
    }
}
