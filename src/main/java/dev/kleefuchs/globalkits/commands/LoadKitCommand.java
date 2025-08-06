package dev.kleefuchs.globalkits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.kleefuchs.globalkits.config.PluginConfiguration;

public class LoadKitCommand implements CommandExecutor {

    PluginConfiguration plcfg;

    public LoadKitCommand(PluginConfiguration plcfg) {
        this.plcfg = plcfg;
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
        if (!this.plcfg.isWorldEnabled(player.getWorld().getName())) {
            sender.sendMessage("This world is not enabled!");
            return true;
        }
        return true;
    }
}
