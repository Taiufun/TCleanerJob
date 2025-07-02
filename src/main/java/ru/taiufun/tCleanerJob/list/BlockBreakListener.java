package ru.taiufun.tCleanerJob.list;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.taiufun.tCleanerJob.TCleanerJob;
import ru.taiufun.tCleanerJob.events.RewardEvent;
import ru.taiufun.tCleanerJob.util.RegionChecker;

public class BlockBreakListener implements Listener {
    private final RewardEvent rewardEvent;

    private final TCleanerJob plugin;

    public BlockBreakListener(TCleanerJob plugin) {
        this.plugin = plugin;
        this.rewardEvent = new RewardEvent(plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Material type = block.getType();


        if (!plugin.getPluginConfig().isTrackedPlant(type)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;

        String region = plugin.getPluginConfig().getRegionName();
        if (!RegionChecker.isInRegion(block, region)) return;

        event.setDropItems(false);
        rewardEvent.giveReward(player, type);
        final var location = block.getLocation().clone();
        int delayTicks = plugin.getPluginConfig().getRespawnDelayTicks();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            location.getBlock().setType(type);
        }, delayTicks);
    }
}
