package dev.kleefuchs.globalkits.config;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;


/*
 * First call readConfig() to load the config
 */
public class PluginConfiguration {
    private final YamlConfiguration config;
    List<String> worldNames;

    public void readConfig() {
        this.worldNames = this.config.getStringList("worlds.active");
    }

    public List<String> getWorldNames() throws NullPointerException {
        if (this.worldNames == null) {
            throw new NullPointerException("World names are null may need to call readConfig()");
        }
        return this.worldNames;
    }

    public boolean isWorldEnabled(String world) throws NullPointerException {
        if (this.worldNames == null) {
            throw new NullPointerException("World names are null may need to call readConfig()");
        }
        return this.worldNames.contains(world);
    }

    public PluginConfiguration(YamlConfiguration config) {
        this.config = config;
    }
}
