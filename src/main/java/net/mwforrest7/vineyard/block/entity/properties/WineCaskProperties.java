package net.mwforrest7.vineyard.block.entity.properties;

public final class WineCaskProperties {
    public static final String DISPLAY_NAME = "Wine Cask";
    public static final String NBT_KEY_PROGRESS = "wine_cask.progress";
    public static final int INVENTORY_SIZE = 1;
    public static final int DELEGATE_PROPERTY_SIZE = 2;
    public static final int NUM_OF_INGREDIENT_SLOTS = 1;
    public static final int MAX_CRAFTING_PROGRESS = 600;
    public static final int DEFAULT_CRAFTING_PROGRESS = 0;

    public enum InventorySlots {
        INGREDIENT_SLOT_1(0);

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
        MAX_PROGRESS(1);

        private final int value;

        DelegateProperties(int value) {
            this.value = value;
        }

        public int toInt(){
            return this.value;
        }
    }

}
