package dev.kleefuchs.globalkits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.kleefuchs.globalkits.config.PluginConfiguration;
import dev.kleefuchs.globalkits.kits.KitManager;
import dev.kleefuchs.globalkits.utils.GenerateKeyForPlayerKit;

public class DeleteKitCommand implements CommandExecutor {

    PluginConfiguration plcfg;
    KitManager kitManager;

    public DeleteKitCommand(PluginConfiguration plcfg, KitManager kitManager) {
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
            sender.sendMessage("You lack the globalkits.loadkit permission");
            return true;
        }
        if (!this.plcfg.isWorldEnabled(player.getWorld().getName())) {
            sender.sendMessage("This world is not enabled!");
            return true;
        }
        String key = GenerateKeyForPlayerKit.generate(player.getName(), args[0]);
        if (!this.kitManager.getKits().containsKey(key)) {
            sender.sendMessage("There is no such kit");
            return true;
        }
        this.kitManager.getKits().remove(key);
        return true;
    }
}
