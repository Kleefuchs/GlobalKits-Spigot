package dev.kleefuchs.globalkits.kits;

import java.util.LinkedHashMap;

public class PlayerKits {
    private LinkedHashMap<String, Kit> kits;

    public LinkedHashMap<String, Kit> getKits() {
        return this.kits;
    }

    public Kit getKit(String key) {
        return this.kits.get(key);
    }

    public void putKit(String key, Kit kit) {
        this.kits.put(key, kit);
    }

    public PlayerKits() {
        kits = new LinkedHashMap<>();
    }
}
