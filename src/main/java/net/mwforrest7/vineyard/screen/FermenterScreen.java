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
public class FermenterScreen extends HandledScreen<FermenterScreenHandler> {
    // The image to use for the GUI
    private static final Identifier TEXTURE = new Identifier(VineyardMod.MOD_ID, "textures/gui/fermenter.png");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 27, 26, 24, 22, 20, 18, 16, 13, 11, 9, 6, 3, 0};

    public FermenterScreen(FermenterScreenHandler handler, PlayerInventory inventory, Text title) {
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
            // Draw arrow progress
            // x & y correspond to the background location to which the progress texture should be drawn
            // u & v correspond to the progress texture location
            drawTexture(matrices, x + 89, y + 33, 177, 0, handler.getScaledProgress(), 17);

            // Draw bubble animation
            // x & y correspond to the background location to which the progress texture should be drawn
            // u & v correspond to the progress texture location
            int m = handler.getProgress();
            int n = BUBBLE_PROGRESS[m / 2 % 14];
            drawTexture(matrices, x + 51, y + 24 + 29 - (29-n), 200, 29 - (29-n), 11, (29-n));
        }
    }

    /**
     * Magic render stuff. No idea how this works.
     */
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
