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

public class KitManager {

    int saveFileVersion = 0;

    private LinkedHashMap<String, Kit> kits;

    public LinkedHashMap<String, Kit> getKits() {
        return this.kits;
    }

    public void setKit(String name, Kit kit) {
        this.kits.put(name, kit);
    }

    private boolean loadKitsV0(YamlConfiguration cfg) {
        System.out.println(cfg.getName());
        this.kits = new LinkedHashMap<>();
        ConfigurationSection kitsSection = cfg.getConfigurationSection("kits");
        if (kitsSection==null) {
            return false;
        }
        for (String key : kitsSection.getKeys(false)) {
            ItemStack[] items = new ItemStack[41];
            int counter = 0;
            for (Object item : kitsSection.getList(key + ".items")) {
                items[counter] = (ItemStack) item;
                counter++;
            }
            this.kits.put(key, new Kit(items));
        }
        return true;
    }

    public boolean loadKitsV1(YamlConfiguration cfg) {
        return true;
    }

    public boolean loadKits(File kitFile) throws IOException, InvalidConfigurationException {
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.load(kitFile);
        this.saveFileVersion = cfg.getInt("version");
        if (!kitFile.exists()) {
            kitFile.createNewFile();
            this.saveFileVersion = 1;
        }
        GlobalKitsPlugin.getInstance().getLogger().log(Level.INFO, kitFile.getPath() + " has version: " + this.saveFileVersion);
        return switch (this.saveFileVersion) {
            case 0 -> this.loadKitsV0(cfg);
            case 1 -> this.loadKitsV1(cfg);
            default -> {
                         throw new InvalidConfigurationException("Version " + this.saveFileVersion + " does not exist");
            }
        };
    }

    public void saveKits(File kitFile) throws IOException {
        YamlConfiguration cfg = new YamlConfiguration();
        cfg.set("version", saveFileVersion);
        for (Entry<String, Kit> entry : this.kits.entrySet()) {
            cfg.set("kits." + entry.getKey() + ".items", entry.getValue().getItems());
        }
        cfg.save(kitFile);
    }

}
