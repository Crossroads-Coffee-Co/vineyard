package net.mwforrest7.vineyard.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mwforrest7.vineyard.VineyardMod;

/**
 * This class draws the GrapePress GUI
 */
public class FruitPressScreen extends HandledScreen<FruitPressScreenHandler> {
    // The image to use for the GUI
    private static final Identifier TEXTURE = new Identifier(VineyardMod.MOD_ID, "textures/gui/fruit_press_gui.png");

    public FruitPressScreen(FruitPressScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        // Display the title (provided by the BlockEntity)
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    /**
     * Draws the background
     *
     * @param matrices
     * @param delta
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // Shader settings
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        // Background dimensions
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        // Draw the background
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // If there is crafting in-progress, draw the progress texture with incremental progress
        if(handler.isCrafting()) {
            // x & y correspond to the background location to which the progress texture should be drawn
            // u & v correspond to the progress texture location
            drawTexture(matrices, x + 84, y + 22, 176, 14, handler.getScaledProgress(), 36);
        }

        // If there is fuel time, draw the fuel burn-down texture with incremental progress
        if(handler.hasFuel()) {
            // x & y correspond to the background location to which the progress texture should be drawn
            // u & v correspond to the progress texture location
            drawTexture(matrices, x + 18, y + 33 + 14 - handler.getScaledFuelProgress(), 176,
                    14 - handler.getScaledFuelProgress(), 14, handler.getScaledFuelProgress());
        }
    }

    /**
     * Magic render stuff. No idea how this works.
     *
     * @param matrices
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
