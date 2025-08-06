package dev.kleefuchs.globalkits.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
    private final YamlConfiguration config;

    public YamlConfiguration getConfig() {
        return this.config;
    }

    public ConfigManager(YamlConfiguration config) {
        this.config = config;
    }
}
