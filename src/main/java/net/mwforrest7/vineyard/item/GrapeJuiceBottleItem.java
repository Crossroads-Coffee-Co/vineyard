package net.mwforrest7.vineyard.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class GrapeJuiceBottleItem extends Item {
    // Time it takes to consume the juice
    private static final int MAX_USE_TIME = 40;

    public GrapeJuiceBottleItem(Settings settings) {
        super(settings);
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

        // If the item stack in hand is empty, put an empty glass bottle there
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        // Else, if player is not in creative mode
        else {
            if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
                ItemStack serverPlayerEntity = new ItemStack(Items.GLASS_BOTTLE);
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
