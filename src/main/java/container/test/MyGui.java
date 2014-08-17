package container.test;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class MyGui extends GuiContainer {

	public MyGui(MyContainer container) {
		super(container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p1, int p2, int p3) {
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/generic_54.png"));
		drawTexturedModalRect(x, y, 0, 0, xSize, 35);
		drawTexturedModalRect(x, y + 35, 0, 126, xSize, 96);
	}
}
