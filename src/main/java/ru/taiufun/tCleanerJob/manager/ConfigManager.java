package ru.taiufun.tCleanerJob.manager;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.taiufun.tCleanerJob.util.RandomUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConfigManager {

    private final JavaPlugin plugin;
    private final Set<Material> plantTypes = new HashSet<>();
    private String regionName;
    private String respawnDelayRaw;
    private final Map<Material, String> plantRewardMap = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        regionName = config.getString("region", "tjobs_cleaner");
        respawnDelayRaw = config.getString("respawn-delay", "10");

        ConfigurationSection plantSection = config.getConfigurationSection("plants");
        if (plantSection != null) {
            for (String key : plantSection.getKeys(false)) {
                try {
                    Material mat = Material.valueOf(key.toUpperCase());
                    String rewardValue = plantSection.getString(key + ".reward", "10");
                    plantRewardMap.put(mat, rewardValue);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Неверный материал в конфиге: " + key);
                }
            }
        }
    }

    public boolean isTrackedPlant(Material mat) {
        return plantRewardMap.containsKey(mat);
    }

    public String getRewardForPlant(Material mat) {
        return plantRewardMap.getOrDefault(mat, "10");
    }

    public String getRegionName() {
        return regionName;
    }

    public int getRespawnDelayTicks() {
        return (int) (RandomUtil.tryParseRandomOrFixed(respawnDelayRaw, false)
                        .orElseGet(() -> {
                            plugin.getLogger().warning("Неверный формат respawn-delay в config.yml. Используется 10 сек по умолчанию.");
                            return 10;
                        }) * 20);
    }

    public Set<Material> getPlantTypes() {
        return plantTypes;
    }
}
