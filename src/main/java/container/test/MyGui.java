package container.test;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class MyGui extends GuiContainer {

	public MyGui(InventoryPlayer playerInventory, IInventory inventory) {
		super(new MyContainer(playerInventory, inventory));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p1, int p2, int p3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
		drawTexturedModalRect(x, y, 0, 0, xSize, 35);
		drawTexturedModalRect(x, y + 35, 0, 126, xSize, 96);
	}
}