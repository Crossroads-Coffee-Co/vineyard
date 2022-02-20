package net.mwforrest7.vineyard.block.entity.properties;

public final class FermenterProperties {
    public static final String DISPLAY_NAME = "Fermenter";
    public static final String NBT_KEY_PROGRESS = "fermenter.progress";
    public static final int INVENTORY_SIZE = 3;
    public static final int DELEGATE_PROPERTY_SIZE = 2;
    public static final int NUM_OF_INGREDIENT_SLOTS = 2;
    public static final int MAX_CRAFTING_PROGRESS = 600;
    public static final int DEFAULT_CRAFTING_PROGRESS = 0;

    public enum InventorySlots {
        INGREDIENT_SLOT_1(0),
        INGREDIENT_SLOT_2(1),
        OUTPUT_SLOT(2);

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
