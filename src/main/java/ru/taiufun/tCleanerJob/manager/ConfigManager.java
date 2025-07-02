package ru.taiufun.tCleanerJob.manager;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.taiufun.tCleanerJob.util.RandomUtil;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final JavaPlugin plugin;

    private String regionName;
    private String respawnDelayRaw;
    private String rewardCommand;
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
        rewardCommand = config.getString("reward.command", "eco give {player} {amount}");

        ConfigurationSection plantSection = config.getConfigurationSection("plants");
        if (plantSection != null) {
            for (String key : plantSection.getKeys(false)) {
                try {
                    Material mat = Material.valueOf(key.toUpperCase());
                    String rewardValue = plantSection.getString(key + ".reward", "10");

                    if (RandomUtil.tryParseRandomOrFixed(rewardValue, true).isEmpty()) {
                        plugin.getLogger().warning("Неверный формат награды для " + mat.name() + ": \"" + rewardValue + "\". Используется значение по умолчанию: 10.");
                        rewardValue = "10";
                    }

                    plantRewardMap.put(mat, rewardValue);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Неверный материал в конфиге: " + key);
                }
            }
        }
    }

    public String getRewardCommand() {
        return rewardCommand;
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

}
