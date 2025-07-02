package ru.taiufun.tCleanerJob.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.taiufun.tCleanerJob.TCleanerJob;

public class ReloadCommand implements CommandExecutor {

    private final TCleanerJob plugin;

    public ReloadCommand(TCleanerJob plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("tcleaner.reload")) {
                sender.sendMessage("§cУ вас нет прав.");
            }
            plugin.getPluginConfig().load();
            sender.sendMessage("§aКонфиг TCleanerJob был перезагружен!");
            return true;
        }
        sender.sendMessage("§cАргументы: reload");
        return true;
    }
}
