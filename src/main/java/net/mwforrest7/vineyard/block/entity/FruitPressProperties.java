package net.mwforrest7.vineyard.block.entity;

public final class FruitPressProperties {
    public static final String DISPLAY_NAME = "Fruit Press";
    public static final String NBT_KEY_PROGRESS = "fruit_press.progress";
    public static final String NBT_KEY_FUEL_TIME = "fruit_press.fuelTime";
    public static final String NBT_KEY_MAX_FUEL_TIME = "fruit_press.maxFuelTime";
    public static final int INVENTORY_SIZE = 4;
    public static final int MAX_CRAFTING_PROGRESS = 72;
    public static final int DEFAULT_CRAFTING_PROGRESS = 0;
    public static final int MAX_FUEL_TIME = 0;
    public static final int DEFAULT_FUEL_TIME = 0;

    public enum InventorySlots {
        FUEL_SLOT(0),
        INGREDIENT_SLOT_1(1),
        INGREDIENT_SLOT_2(2),
        OUTPUT_SLOT(3);

        private final int value;

        InventorySlots(int value) {
            this.value = value;
        }

        public int toInt(){
            return this.value;
        }
    }

    public enum DelegateProperties {
        PROGRESS(0),
        MAX_PROGRESS(1),
        FUEL_TIME(2),
        MAX_FUEL_TIME(3);

        private final int value;

        DelegateProperties(int value) {
            this.value = value;
        }

        public int toInt(){
            return this.value;
        }
    }

}
