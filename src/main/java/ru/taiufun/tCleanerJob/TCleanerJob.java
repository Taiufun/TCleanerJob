package ru.taiufun.tCleanerJob;

import org.bukkit.plugin.java.JavaPlugin;
import ru.taiufun.tCleanerJob.list.BlockBreakListener;

public final class TCleanerJob extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
