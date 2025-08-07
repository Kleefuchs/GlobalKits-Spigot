package dev.kleefuchs.globalkits.utils;

public class GenerateKeyForPlayerKit {
    public static String generate(String playerName, String kitName) {
        StringBuilder key = new StringBuilder();
        key.append(playerName);
        key.append('/');
        key.append(kitName);
        return key.toString();
    }
}
