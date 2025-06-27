package ru.taiufun.tCleanerJob.list;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.taiufun.tCleanerJob.util.RegionChecker;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (block.getType() != Material.GRASS && block.getType() != Material.TALL_GRASS) return;

        if (RegionChecker.isInRegion(block, "tjobs_cleaner")) {
            event.setCancelled(true);
            player.sendMessage("§aуспешно");
        }
    }
}
