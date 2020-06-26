package io.github.teitss.dores.config;

import io.github.teitss.dores.DOres;
import io.github.teitss.dores.CustomOre;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Config {

    private static HashMap<Integer, LinkedHashSet<CustomOre>> chances = new HashMap<>();
    private static HashSet<String> blocksBlacklist = new HashSet<>();
    private static HashSet<String> whitelistItems = new HashSet<>();
    private static float dropRate;
    private static int dropQuantity;
    private static String smeltOnMessage;
    private static String smeltOffMessage;

    static {
        chances.put(1, new LinkedHashSet<>());
        chances.put(2, new LinkedHashSet<>());
        chances.put(3, new LinkedHashSet<>());
    }

    public static void install(Path path, ConfigurationLoader<CommentedConfigurationNode> configManager) {
        if (Files.notExists(path))
            setup(path);
        load(configManager);

    }

    public static void load(ConfigurationLoader<CommentedConfigurationNode> configManager) {
        try {
            ConfigurationNode configNode = configManager.load();
            for (int i = 1; i < 4; i++) {
                chances.get(i).clear();
            }
            int currentChance1 = 0;
            int currentChance2 = 0;
            int currentChance3 = 0;
            for (ConfigurationNode item : configNode.getNode("chances").getChildrenList()) {
                Map.Entry<Integer, CustomOre> entry = CustomOreSerializer.deserialize(item);
                switch(entry.getKey()) {
                    case 1: {
                        currentChance1 += entry.getValue().getChance();
                        entry.getValue().setChance(currentChance1);
                        break;
                    }
                    case 2: {
                        currentChance2 += entry.getValue().getChance();
                        entry.getValue().setChance(currentChance2);
                        break;
                    }
                    case 3: {
                        currentChance3 += entry.getValue().getChance();
                        entry.getValue().setChance(currentChance3);
                        break;
                    }
                }
                chances.get(entry.getKey()).add(entry.getValue());
            }
            blocksBlacklist.clear();
            configNode.getNode("blacklist").getChildrenList().forEach(item -> {
                blocksBlacklist.add(item.getString());
            });
            whitelistItems.clear();
            configNode.getNode("whitelist").getChildrenList().forEach(item -> {
                whitelistItems.add(item.getString());
            });
            dropRate = configNode.getNode("drop-rate").getFloat();
            dropQuantity = configNode.getNode("drop-quantity").getInt();
            smeltOffMessage = configNode.getNode("smeltOff").getString();
            smeltOnMessage = configNode.getNode("smeltOn").getString();
            DOres.getInstance().getLogger().info("Configuration loaded with success!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public static void setup(Path path) {
        try {
            Asset configFile = Sponge.getAssetManager().getAsset(DOres.getInstance(), "dores.conf").get();
            configFile.copyToFile(path);
            DOres.getInstance().getLogger().info("Configuration file installed with success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(ConfigurationLoader<CommentedConfigurationNode> configManager) {
        try {
            ConfigurationNode configNode = configManager.load();
            configNode.getNode("drop-rate").setValue(dropRate);
            configNode.getNode("drop-quantity").setValue(dropQuantity);
            configManager.save(configNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, LinkedHashSet<CustomOre>> getChances() {
        return chances;
    }

    public static HashSet<String> getBlocksBlacklist() {
        return blocksBlacklist;
    }

    public static HashSet<String> getWhitelistItems() {
        return whitelistItems;
    }

    public static float getDropRate() {
        return dropRate;
    }

    public static int getDropQuantity() {
        return dropQuantity;
    }

    public static String getSmeltOnMessage() {
        return smeltOnMessage;
    }

    public static String getSmeltOffMessage() {
        return smeltOffMessage;
    }

    public static void setDropRate(float dropRate) {
        Config.dropRate = dropRate;
    }

    public static void setDropQuantity(int dropQuantity) {
        Config.dropQuantity = dropQuantity;
    }
}
