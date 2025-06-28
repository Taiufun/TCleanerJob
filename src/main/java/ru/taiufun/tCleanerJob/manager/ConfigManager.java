package ru.taiufun.tCleanerJob.manager;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.taiufun.tCleanerJob.util.RandomUtil;

import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private final JavaPlugin plugin;
    private final Set<Material> plantTypes = new HashSet<>();
    private String regionName;
    private String respawnDelayRaw;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        regionName = config.getString("region", "tjobs_cleaner");
        respawnDelayRaw = config.getString("respawn-delay", "10");

        for (String name : config.getStringList("plants")) {
            try {
                plantTypes.add(Material.valueOf(name.toUpperCase()));
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Неверный материал в конфиге: " + name);
            }
        }
    }

    public boolean isTrackedPlant(Material mat) {
        return plantTypes.contains(mat);
    }

    public String getRegionName() {
        return regionName;
    }

    public int getRespawnDelayTicks() {
        return RandomUtil.tryParseRandomOrFixed(respawnDelayRaw)
                .orElseGet(() -> {
                    plugin.getLogger().warning("Неверный формат respawn-delay в config.yml. Используется 10 сек по умолчанию.");
                    return 10;
                }) * 20;
    }

    public Set<Material> getPlantTypes() {
        return plantTypes;
    }
}
