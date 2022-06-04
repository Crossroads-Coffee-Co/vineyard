package net.mwforrest7.vineyard.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mwforrest7.vineyard.VineyardMod;
import net.mwforrest7.vineyard.block.ModBlocks;
import net.mwforrest7.vineyard.config.ModConfigs;

public class ModItems {

    // Initialization of items
    public static final Item RED_GRAPE = registerItem("red_grape",
            new Item(new FabricItemSettings()
                    .food(new FoodComponent.Builder()
                            .hunger(ModConfigs.RED_GRAPE_HUNGER)
                            .saturationModifier(ModConfigs.RED_GRAPE_SATURATION)
                            .build())
                    .group(ModItemGroups.VINEYARD)));

    public static final Item RED_GRAPE_BUNCH = registerItem("red_grape_bunch",
            new Item(new FabricItemSettings()
                    .food(new FoodComponent.Builder()
                            .hunger(ModConfigs.RED_GRAPE_BUNCH_HUNGER)
                            .saturationModifier(ModConfigs.RED_GRAPE_BUNCH_SATURATION)
                            .build())
                    .group(ModItemGroups.VINEYARD)));

    public static final Item RED_GRAPE_SEEDS = registerItem("red_grape_seeds",
            new AliasedBlockItem(ModBlocks.RED_GRAPEVINE_TRUNK,
                    new FabricItemSettings().group(ModItemGroups.VINEYARD)));

    public static final Item COPPER_SPRING = registerItem("copper_spring",
            new Item(new FabricItemSettings()
                    .group(ModItemGroups.VINEYARD)));

    public static final Item WINE_BOTTLE = registerItem("wine_bottle",
            new Item(new FabricItemSettings()
                    .group(ModItemGroups.VINEYARD)));

    public static final Item RED_GRAPE_JUICE = registerItem("red_grape_juice",
            new JuiceBottleItem((new FabricItemSettings())
                    .food(new FoodComponent.Builder()
                            .hunger(ModConfigs.RED_GRAPE_JUICE_HUNGER)
                            .saturationModifier(ModConfigs.RED_GRAPE_JUICE_SATURATION)
                            .build())
                    .group(ModItemGroups.VINEYARD)));

    public static final Item FERMENTED_FRUITY_RED_GRAPE_JUICE = registerItem("fermented_fruity_red_grape_juice",
            new JuiceBottleItem((new FabricItemSettings())
                    .food(new FoodComponent.Builder()
                            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 500, 1, true, true, true), 1.0F)
                            .build())
                    .group(ModItemGroups.VINEYARD)));

    // Red wine effects: Nausea, slowness, big health bonus
    public static final Item FRUITY_RED_WINE = registerItem("fruity_red_wine",
            new WineBottleItem((new FabricItemSettings())
                    .food(new FoodComponent.Builder()
                            .alwaysEdible()
                            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 6000, 3, true, true, true), 1.0F)
                            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6000, 3, true, true, true), 1.0F)
                            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,6000, 1, true, true, true), 1.0F)
                            .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,6000, 1, true, true, true), 1.0F)
                            .build())
                    .group(ModItemGroups.VINEYARD)));

    // Registers items
    private static Item registerItem(String name, Item item){
        System.out.println("Registering " + VineyardMod.MOD_ID + ":" + name);
        return Registry.register(Registry.ITEM, new Identifier(VineyardMod.MOD_ID, name), item);
    }

    public static void registerModItems(){
        System.out.println("Registering mod items for " + VineyardMod.MOD_ID);
    }
}
