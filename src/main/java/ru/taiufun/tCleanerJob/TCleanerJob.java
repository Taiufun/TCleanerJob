package ru.taiufun.tCleanerJob;

import org.bukkit.plugin.java.JavaPlugin;
import ru.taiufun.tCleanerJob.list.BlockBreakListener;
import ru.taiufun.tCleanerJob.manager.ConfigManager;

public final class TCleanerJob extends JavaPlugin {

    private ConfigManager pluginConfig;

    @Override
    public void onEnable() {
        pluginConfig = new ConfigManager(this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
    }

    public ConfigManager getPluginConfig() {
        return pluginConfig;
    }
}
