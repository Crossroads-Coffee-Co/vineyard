package net.mwforrest7.vineyard.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class WineBottleItem extends Item {
    // Time it takes to consume the wine
    private static int MAX_USE_TIME = 40;

    public WineBottleItem(Settings settings) {
        super(settings);
    }

    public WineBottleItem(Settings settings, int maxUseTime) {
        super(settings);
        MAX_USE_TIME = maxUseTime;
    }

    /**
     * This function is executed when the item is finished being used
     */
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);

        // The ServerPlayerEntity handles all the player data
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            // This appears to be related to triggering advancements - is not necessary
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);

            // Increments relevant player stats - also not necessary
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        // If the item stack in hand is empty, put an empty bottle there
        if (stack.isEmpty()) {
            return new ItemStack(ModItems.WINE_BOTTLE);
        }
        // Else, if player is not in creative mode
        else {
            if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
                ItemStack serverPlayerEntity = new ItemStack(ModItems.WINE_BOTTLE);
                PlayerEntity playerEntity = (PlayerEntity)user;

                // If there is no space to insert the bottles into player inventory, drop on ground
                if (!playerEntity.getInventory().insertStack(serverPlayerEntity)) {
                    playerEntity.dropItem(serverPlayerEntity, false);
                }
            }

            return stack;
        }
    }

    public int getMaxUseTime(ItemStack stack) {
        return MAX_USE_TIME;
    }

    // Determines what type of action it is when using this item
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    // Determines the sound when performing a Drink action
    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    // Determines the sound made when finished consuming the item
    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }
}
