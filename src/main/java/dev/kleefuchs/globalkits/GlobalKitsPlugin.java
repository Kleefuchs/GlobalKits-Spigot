package dev.kleefuchs.globalkits;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GlobalKitsPlugin extends JavaPlugin {

    private static GlobalKitsPlugin instance;

    public static GlobalKitsPlugin getInstance() {
        return instance;
    }

    public void init() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
