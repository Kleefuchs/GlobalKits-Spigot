package dev.kleefuchs.globalkits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.kleefuchs.globalkits.config.PluginConfiguration;
import dev.kleefuchs.globalkits.kits.Kit;
import dev.kleefuchs.globalkits.kits.PlayerKits;
import dev.kleefuchs.globalkits.kits.PlayerKitsManager;

public class SaveKitCommand implements CommandExecutor {

    PluginConfiguration plcfg;
    PlayerKitsManager playerKitsManager;

    public SaveKitCommand(PluginConfiguration plcfg, PlayerKitsManager playerKitsManager) {
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
        if (!player.hasPermission("globalkits.savekit")) {
            sender.sendMessage("You lack the globalkits.savekit permission");
            return true;
        }
        if (!this.plcfg.isWorldEnabled(player.getWorld().getName())) {
            sender.sendMessage("This world is not enabled!");
            return true;
        }
        if (!this.playerKitsManager.getPlayerKits().containsKey(player.getName())) {
            this.playerKitsManager.putPlayersKits(player.getName(), new PlayerKits());     //Create Section for Player in playerKitsManager
        }

        this.playerKitsManager.getPlayersKits(player.getName()).putKit(args[0], new Kit(player.getInventory().getContents()));

        return true;
    }
}
