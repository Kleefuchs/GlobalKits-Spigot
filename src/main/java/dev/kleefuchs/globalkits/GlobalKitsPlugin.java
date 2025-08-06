package dev.kleefuchs.globalkits;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.kleefuchs.globalkits.commands.LoadKitCommand;
import dev.kleefuchs.globalkits.config.PluginConfiguration;

public class GlobalKitsPlugin extends JavaPlugin {

    private static GlobalKitsPlugin instance;

    public static GlobalKitsPlugin getInstance() {
        return instance;
    }

    private PluginConfiguration cfg;

    public PluginConfiguration getCFG() {
        return cfg;
    }

    private void initCFG(String path) throws IOException, InvalidConfigurationException {
        YamlConfiguration ymlcfg = new YamlConfiguration();
        ymlcfg.load(path);
        this.cfg = new PluginConfiguration(ymlcfg);
        this.cfg.readConfig();
    }

    private void initCommands() {
        HashMap<String, CommandExecutor> commandExecutors = new HashMap<String, CommandExecutor>();
        commandExecutors.put("loadkit", new LoadKitCommand(this.cfg));
        commandExecutors.forEach((name, commandExecutor) -> {
            this.getCommand(name).setExecutor(commandExecutor);
        });
    }

    public void init() {
        instance = this;
        try {
            this.initCFG("plugins/GlobalKits/config.yml");
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load config.yml");
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Invalid config.yml");
            e.printStackTrace();
        }
        this.initCommands();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.init();
        Bukkit.getLogger().info("GlobalKits plugin enabled");
        Bukkit.getLogger().info("GlobalKits version: " + this.getDescription().getVersion());
        Bukkit.getLogger().info("The following Worlds are enabled:");
        for (String world : this.cfg.getWorldNames()) {
            Bukkit.getLogger().info("World: " + world);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
