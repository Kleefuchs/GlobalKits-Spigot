package dev.kleefuchs.globalkits.commands;

import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.kleefuchs.globalkits.config.PluginConfiguration;
import dev.kleefuchs.globalkits.kits.PlayerKitsManager;

public class LoadKitCommand implements CommandExecutor {

    PluginConfiguration plcfg;
    PlayerKitsManager playerKitsManager;

    public LoadKitCommand(PluginConfiguration plcfg, PlayerKitsManager playerKitsManager) {
        this.plcfg = plcfg;
        this.playerKitsManager = playerKitsManager;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Please specify a kit name!");
            return false;
        }
        if (!(sender instanceof org.bukkit.entity.Player)) {
            sender.sendMessage("You can only use this command as a player!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("globalkits.loadkit")) {
            sender.sendMessage("You lack the globalkits.loadkit permission");
            return true;
        }
        if (!this.plcfg.isWorldEnabled(player.getWorld().getName())) {
            sender.sendMessage("This world is not enabled!");
            return true;
        }

        String[] keys = args[0].split(Pattern.quote("/"));

        if (keys.length < 2) {
            sender.sendMessage("Invalid Format");
            return true;
        }

        if (!this.playerKitsManager.getPlayerKits().containsKey(keys[0])) {
            sender.sendMessage("There are no kits by following player: " + keys[0]);
            return true;
        }

        if (!this.playerKitsManager.getPlayersKits(keys[0]).getKits().containsKey(keys[1])) {
            sender.sendMessage("The Player did not create a kit with the following name: " + keys[1]);
        }

        player.getInventory().setContents(this.playerKitsManager.getPlayersKits(keys[0]).getKit(keys[1]).getItems());

        return true;
    }
}
