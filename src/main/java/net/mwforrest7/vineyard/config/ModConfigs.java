package net.mwforrest7.vineyard.config;
import com.mojang.datafixers.util.Pair;
import net.mwforrest7.vineyard.VineyardMod;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    // The values loaded from the config - to be used anywhere in the mod
    public static int WILD_RED_GRAPE_SPAWN_CHANCE;
    public static int RED_GRAPE_HUNGER;
    public static float RED_GRAPE_SATURATION;
    public static int RED_GRAPE_BUNCH_HUNGER;
    public static float RED_GRAPE_BUNCH_SATURATION;
    public static int RED_GRAPE_JUICE_HUNGER;
    public static float RED_GRAPE_JUICE_SATURATION;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(VineyardMod.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    // Creates config file if it doesn't already exist
    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("wild_red_grape.spawn_chance", 5), "Int - smaller value results in more frequent patches of vines");
        configs.addKeyValuePair(new Pair<>("red_grape.hunger", 1), "int");
        configs.addKeyValuePair(new Pair<>("red_grape.saturation", 0.1f), "float");
        configs.addKeyValuePair(new Pair<>("red_grape_bunch.hunger", 2), "int");
        configs.addKeyValuePair(new Pair<>("red_grape_bunch.saturation", 0.2f), "float");
        configs.addKeyValuePair(new Pair<>("red_grape_juice.hunger", 3), "int");
        configs.addKeyValuePair(new Pair<>("red_grape_juice.saturation", 0.3f), "float");
    }

    // Reads config file. Takes default value if missing value in the config.
    private static void assignConfigs() {
        WILD_RED_GRAPE_SPAWN_CHANCE = CONFIG.getOrDefault("wild_red_grape.spawn_chance", 5);
        RED_GRAPE_HUNGER = CONFIG.getOrDefault("red_grape.hunger", 1);
        RED_GRAPE_SATURATION = (float) CONFIG.getOrDefault("red_grape.saturation", 0.1f);
        RED_GRAPE_BUNCH_HUNGER = CONFIG.getOrDefault("red_grape_bunch.hunger", 2);
        RED_GRAPE_BUNCH_SATURATION = (float) CONFIG.getOrDefault("red_grape_bunch.saturation", 0.2f);
        RED_GRAPE_JUICE_HUNGER = CONFIG.getOrDefault("red_grape_juice.hunger", 3);
        RED_GRAPE_JUICE_SATURATION = (float) CONFIG.getOrDefault("red_grape_juice.saturation", 0.3f);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
