package ru.taiufun.tCleanerJob.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.block.Block;

public class RegionChecker {

    public static boolean isInRegion(Block block, String regionId) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(BukkitAdapter.adapt(block.getWorld()));
        if (regionManager == null) return false;

        ApplicableRegionSet regions = regionManager.getApplicableRegions(
                BukkitAdapter.asBlockVector(block.getLocation())
        );

        for (ProtectedRegion region : regions) {
            if (region.getId().equalsIgnoreCase(regionId)) {
                return true;
            }
        }

        return false;
    }
}
