package ds.plato.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

//Started with GuiChest
//http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class GuiStaff extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
	private int rowHeight = 35;

	public GuiStaff(InventoryPlayer inventoryPlayer, IInventory inventoryStaff) {
		super(new GuiStaffContainer(inventoryPlayer, inventoryStaff));
		//allowUserInput = false;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int p1, int p2) {
		// TODO Name a staff?
		fontRendererObj.drawString("Staff", 8, 6, 4210752);
		fontRendererObj.drawString("Inventory", 8, rowHeight + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p1, int p2, int p3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
//		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x, y,             0, 0,   xSize, rowHeight);
		drawTexturedModalRect(x, y + rowHeight, 0, 126, xSize, 96);
	}

	// @Override
	// protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	// //draw text and stuff here
	// //the parameters for drawString are: string, x, y, color
	// fontRenderer.drawString("Tiny", 8, 6, 4210752);
	// //draws "Inventory" or your regional equivalent
	// fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	// }

//	@Override
//	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
//		// draw your Gui here, only thing you need to change is the path
//		//int texture = mc.renderEngine.getTexture("/gui/trap.png");
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		mc.renderEngine.bindTexture(texture);
//		int x = (width - xSize) / 2;
//		int y = (height - ySize) / 2;
//		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
//	}

}
