package dev.kleefuchs.globalkits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.kleefuchs.globalkits.config.PluginConfiguration;
import dev.kleefuchs.globalkits.kits.Kit;
import dev.kleefuchs.globalkits.kits.KitManager;

public class SaveKitCommand implements CommandExecutor {

    PluginConfiguration plcfg;
    KitManager kitManager;

    public SaveKitCommand(PluginConfiguration plcfg, KitManager kitManager) {
        this.plcfg = plcfg;
        this.kitManager = kitManager;
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
        StringBuilder key = new StringBuilder();
        key.append(player.getName());
        key.append('/');
        key.append(args[0]);
        this.kitManager.getKits().put(key.toString(), new Kit(player.getInventory().getContents()));
        return true;
    }
}
