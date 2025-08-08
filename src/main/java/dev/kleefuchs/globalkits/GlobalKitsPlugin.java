package dev.kleefuchs.globalkits;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.kleefuchs.globalkits.commands.DeleteKitCommand;
import dev.kleefuchs.globalkits.commands.LoadKitCommand;
import dev.kleefuchs.globalkits.commands.SaveKitCommand;
import dev.kleefuchs.globalkits.config.PluginConfiguration;
import dev.kleefuchs.globalkits.kits.PlayerKitsManager;

public class GlobalKitsPlugin extends JavaPlugin {

    private static GlobalKitsPlugin instance;

    public static GlobalKitsPlugin getInstance() {
        return instance;
    }

    private PluginConfiguration cfg;

    public PluginConfiguration getCFG() {
        return this.cfg;
    }

    PlayerKitsManager kitManager;

    public PlayerKitsManager getKitManager() {
        return this.kitManager;
    }

    private void initCFG(File file) throws IOException, InvalidConfigurationException {
        YamlConfiguration ymlcfg = new YamlConfiguration();
        ymlcfg.load(file);
        this.cfg = new PluginConfiguration(ymlcfg);
        this.cfg.readConfig();
    }

    private void initKitManager(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        this.kitManager = new PlayerKitsManager();
        this.kitManager.loadKits(file);
    }

    private void initCommands() {
        HashMap<String, CommandExecutor> commandExecutors = new HashMap<String, CommandExecutor>();
        commandExecutors.put("loadkit", new LoadKitCommand(this.cfg, this.kitManager));
        commandExecutors.put("savekit", new SaveKitCommand(this.cfg, this.kitManager));
        commandExecutors.put("deletekit", new DeleteKitCommand(this.cfg, this.kitManager));
        commandExecutors.forEach((name, commandExecutor) -> {
            this.getCommand(name).setExecutor(commandExecutor);
        });
    }

    public void init() {
        instance = this;
        try {
            this.initCFG(new File("plugins/GlobalKits/config.yml"));
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "Could not load config.yml");
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            this.getLogger().log(Level.SEVERE, "Invalid config.yml");
            e.printStackTrace();
        }

        try {
            this.initKitManager(new File("plugins/GlobalKits/kits.yml"));
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "Could not load kits.yml");
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            this.getLogger().log(Level.SEVERE, "Invalid kits.yml");
            e.printStackTrace();
        }
        this.initCommands();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.init();
        this.getLogger().info("GlobalKits plugin enabled");
        this.getLogger().info("GlobalKits version: " + this.getDescription().getVersion());
        this.getLogger().info("The following Worlds are enabled:");
        for (String world : this.cfg.getWorldNames()) {
            this.getLogger().info("World: " + world);
        }
    }

    public void deconstruct() throws IOException {
        try {
            this.kitManager.saveKits(new File("plugins/GlobalKits/kits.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            this.deconstruct();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
