package ru.taiufun.tCleanerJob.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.taiufun.tCleanerJob.TCleanerJob;
import ru.taiufun.tCleanerJob.util.RandomUtil;

public class RewardEvent {

    private final TCleanerJob plugin;

    public RewardEvent(TCleanerJob plugin) {
        this.plugin = plugin;
    }

    public void giveReward(Player player) {
        String rawAmount = plugin.getConfig().getString("reward.amount", "10");
        double amount = RandomUtil.tryParseRandomOrFixed(rawAmount, true)
                .orElseGet(() -> {
                    plugin.getLogger().warning("Неверный формат reward.amount в config.yml. Используется 10 по умолчанию.");
                    return 10;
                });

        String commandTemplate = plugin.getConfig().getString("reward.command", "eco give {player} {amount}");
        String finalCommand = commandTemplate
                .replace("{player}", player.getName())
                .replace("{amount}", String.valueOf(amount));

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
    }
}
