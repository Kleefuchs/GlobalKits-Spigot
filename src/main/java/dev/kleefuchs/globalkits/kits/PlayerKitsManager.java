package dev.kleefuchs.globalkits.kits;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.kleefuchs.globalkits.GlobalKitsPlugin;

public class PlayerKitsManager {

    int saveFileVersion = 0;

    private LinkedHashMap<String, PlayerKits> playerKits;

    public LinkedHashMap<String, PlayerKits> getPlayerKits() {
        return this.playerKits;
    }

    public PlayerKits getPlayersKits(String playerKey) {
        return this.playerKits.get(playerKey);
    }

    public void putPlayersKits(String playerKey, PlayerKits kit) {
        this.playerKits.put(playerKey, kit);
    }

    private boolean loadKitsV0(YamlConfiguration cfg) {
        System.out.println(cfg.getName());
        this.playerKits = new LinkedHashMap<>();
        ConfigurationSection playerSection = cfg.getConfigurationSection("playerkits");
        if (playerSection == null) {
            return false;
        }
        for (String playerKey : playerSection.getKeys(false)) {
            ConfigurationSection playerKitsSection = playerSection.getConfigurationSection(playerKey);
            PlayerKits playerKits = new PlayerKits();
            for (String kitKey : playerKitsSection.getKeys(false)) {
                ConfigurationSection kitSection = playerKitsSection.getConfigurationSection(kitKey);
                ItemStack[] items = new ItemStack[41];
                int counter = 0;
                for (Object item : kitSection.getList(".items")) {
                    items[counter] = (ItemStack) item;
                    counter++;
                }
                playerKits.putKit(kitKey, new Kit(items));
            }
            this.putPlayersKits(playerKey, playerKits);
        }
        return true;
    }

    public boolean loadKits(File kitFile) throws IOException, InvalidConfigurationException {
        if (!kitFile.exists()) {
            kitFile.createNewFile();
            this.saveFileVersion = 0;
        }
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.load(kitFile);
        this.saveFileVersion = cfg.getInt("version");
        GlobalKitsPlugin.getInstance().getLogger().log(Level.INFO,
                kitFile.getPath() + " has version: " + this.saveFileVersion);
        return switch (this.saveFileVersion) {
            case 0 -> this.loadKitsV0(cfg);
            default -> {
                throw new InvalidConfigurationException("Version " + this.saveFileVersion + " does not exist");
            }
        };
    }

    public void saveKits(File kitFile) throws IOException {
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.set("version", this.saveFileVersion);
        for (Entry<String, PlayerKits> entry : this.playerKits.entrySet()) {
            for (Entry<String, Kit> kit : entry.getValue().getKits().entrySet()) {
                cfg.set("playerkits." + entry.getKey() + '.' + kit.getKey() + ".items", kit.getValue().getItems());
            }
        }
        //TODO Implement shit
        // for (Entry<String, Kit> entry : this.playerKits.entrySet()) {
        //     cfg.set("kits." + entry.getKey() + ".items", entry.getValue().getItems());
        // }
        cfg.save(kitFile);
    }

}
