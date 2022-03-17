package net.mwforrest7.vineyard.config;
import com.mojang.datafixers.util.Pair;
import net.mwforrest7.vineyard.VineyardMod;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;
    public static final String BIOME_CATEGORY_DELIMITER = ":";

    // The values loaded from the config - to be used anywhere in the mod
    public static int WILD_RED_GRAPE_PATCH_SIZE;
    public static String WILD_RED_GRAPE_COMMON_BIOME_CATEGORIES;
    public static int WILD_RED_GRAPE_COMMON_SPAWN_CHANCE;
    public static String WILD_RED_GRAPE_RARE_BIOME_CATEGORIES;
    public static int WILD_RED_GRAPE_RARE_SPAWN_CHANCE;
    public static int RED_GRAPE_HUNGER;
    public static float RED_GRAPE_SATURATION;
    public static int RED_GRAPE_BUNCH_HUNGER;
    public static float RED_GRAPE_BUNCH_SATURATION;
    public static int RED_GRAPE_JUICE_HUNGER;
    public static float RED_GRAPE_JUICE_SATURATION;
    public static int FERMENTED_RED_GRAPE_JUICE_HUNGER;
    public static float FERMENTED_RED_GRAPE_JUICE_SATURATION;
    public static int RED_WINE_HUNGER;
    public static float RED_WINE_SATURATION;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(VineyardMod.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    // Creates config file if it doesn't already exist
    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("wild_red_grape.patch_size", 20), "Int - larger value results in larger patches of vines");
        configs.addKeyValuePair(new Pair<>("wild_red_grape.common.biome_categories", "FOREST:MUSHROOM:PLAINS"), "String - possible values: NONE, TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, THE_END, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM, NETHER, UNDERGROUND, MOUNTAIN");
        configs.addKeyValuePair(new Pair<>("wild_red_grape.common.spawn_chance", 48), "Int - smaller value results in more frequent patches of vines");
        configs.addKeyValuePair(new Pair<>("wild_red_grape.rare.biome_categories", "EXTREME_HILLS:TAIGA"), "String - possible values: NONE, TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, THE_END, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM, NETHER, UNDERGROUND, MOUNTAIN");
        configs.addKeyValuePair(new Pair<>("wild_red_grape.rare.spawn_chance", 384), "Int - smaller value results in more frequent patches of vines");
        configs.addKeyValuePair(new Pair<>("red_grape.hunger", 1), "int");
        configs.addKeyValuePair(new Pair<>("red_grape.saturation", 0.1f), "float");
        configs.addKeyValuePair(new Pair<>("red_grape_bunch.hunger", 2), "int");
        configs.addKeyValuePair(new Pair<>("red_grape_bunch.saturation", 0.2f), "float");
        configs.addKeyValuePair(new Pair<>("red_grape_juice.hunger", 3), "int");
        configs.addKeyValuePair(new Pair<>("red_grape_juice.saturation", 0.3f), "float");
        configs.addKeyValuePair(new Pair<>("fermented_red_grape_juice.hunger", 3), "int");
        configs.addKeyValuePair(new Pair<>("fermented_red_grape_juice.saturation", 0.3f), "float");
        configs.addKeyValuePair(new Pair<>("red_wine.hunger", 4), "int");
        configs.addKeyValuePair(new Pair<>("red_wine.saturation", 0.5f), "float");
    }

    // Reads config file. Takes default value if missing value in the config.
    private static void assignConfigs() {
        WILD_RED_GRAPE_PATCH_SIZE = CONFIG.getOrDefault("wild_red_grape.patch_size", 20);
        WILD_RED_GRAPE_COMMON_BIOME_CATEGORIES = CONFIG.getOrDefault("wild_red_grape.common.biome_categories", "FOREST:MUSHROOM:PLAINS");
        WILD_RED_GRAPE_COMMON_SPAWN_CHANCE = CONFIG.getOrDefault("wild_red_grape.common.spawn_chance", 48);
        WILD_RED_GRAPE_RARE_BIOME_CATEGORIES = CONFIG.getOrDefault("wild_red_grape.rare.biome_categories", "EXTREME_HILLS:TAIGA");
        WILD_RED_GRAPE_RARE_SPAWN_CHANCE = CONFIG.getOrDefault("wild_red_grape.rare.spawn_chance", 384);
        RED_GRAPE_HUNGER = CONFIG.getOrDefault("red_grape.hunger", 1);
        RED_GRAPE_SATURATION = (float) CONFIG.getOrDefault("red_grape.saturation", 0.1f);
        RED_GRAPE_BUNCH_HUNGER = CONFIG.getOrDefault("red_grape_bunch.hunger", 2);
        RED_GRAPE_BUNCH_SATURATION = (float) CONFIG.getOrDefault("red_grape_bunch.saturation", 0.2f);
        RED_GRAPE_JUICE_HUNGER = CONFIG.getOrDefault("red_grape_juice.hunger", 3);
        RED_GRAPE_JUICE_SATURATION = (float) CONFIG.getOrDefault("red_grape_juice.saturation", 0.3f);
        RED_GRAPE_JUICE_HUNGER = CONFIG.getOrDefault("fermented_red_grape_juice.hunger", 3);
        RED_GRAPE_JUICE_SATURATION = (float) CONFIG.getOrDefault("fermented_red_grape_juice.saturation", 0.3f);
        RED_WINE_HUNGER = CONFIG.getOrDefault("red_wine.hunger", 4);
        RED_WINE_SATURATION = (float) CONFIG.getOrDefault("red_wine.saturation", 0.5f);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
