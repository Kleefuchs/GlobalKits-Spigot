package dev.kleefuchs.globalkits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.kleefuchs.globalkits.config.PluginConfiguration;
import dev.kleefuchs.globalkits.kits.PlayerKitsManager;

public class DeleteKitCommand implements CommandExecutor {

    PluginConfiguration plcfg;
    PlayerKitsManager kitManager;

    public DeleteKitCommand(PluginConfiguration plcfg, PlayerKitsManager kitManager) {
        this.plcfg = plcfg;
        this.kitManager = kitManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Please specify a kit name!");
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("globalkits.deletekit")) {
            sender.sendMessage("You lack the globalkits.deletekit permission");
            return true;
        }
        if (!this.plcfg.isWorldEnabled(player.getWorld().getName())) {
            sender.sendMessage("This world is not enabled!");
            return true;
        }
        return true;
    }
}
