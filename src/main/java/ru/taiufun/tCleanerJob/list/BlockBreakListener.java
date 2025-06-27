package ru.taiufun.tCleanerJob.list;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.taiufun.tCleanerJob.util.RegionChecker;

import java.util.Random;

public class BlockBreakListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (block.getType() != Material.GRASS && block.getType() != Material.TALL_GRASS) return;

        if (!RegionChecker.isInRegion(block, "tjobs_cleaner")) return;

        event.setDropItems(false);

        final var location = block.getLocation().clone();

        Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin("TCleanerJob"),
                () -> {
                    Material newType = random.nextBoolean() ? Material.GRASS : Material.TALL_GRASS;
                    location.getBlock().setType(newType);
                },
                20L * 10
        );
    }
}
