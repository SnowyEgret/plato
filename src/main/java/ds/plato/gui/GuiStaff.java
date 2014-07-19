package ds.plato.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

//http://www.minecraftforge.net/wiki/Containers_and_GUIs
public class GuiStaff extends GuiContainer {

	private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/generic_54.png");
	private InventoryPlayer inventoryPlayer;
	private IInventory inventoryStaff;
	private int inventoryRows;

	public GuiStaff(InventoryPlayer inventoryPlayer, IInventory inventoryStaff) {
		super(new GuiStaffContainer(inventoryPlayer, inventoryStaff));
		this.inventoryPlayer = inventoryPlayer;
		this.inventoryStaff = inventoryStaff;
		this.allowUserInput = false;
		short short1 = 222;
		int i = short1 - 108;
		this.inventoryRows = inventoryStaff.getSizeInventory() / 9;
		this.ySize = i + this.inventoryRows * 18;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		//TODO Name a staff?
		fontRendererObj.drawString(inventoryStaff.getInventoryName(), 8, 6, 4210752);
		fontRendererObj.drawString(
				inventoryPlayer.hasCustomInventoryName() ? inventoryPlayer.getInventoryName() : I18n.format(
						inventoryPlayer.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, inventoryRows * 18 + 17);
		this.drawTexturedModalRect(k, l + inventoryRows * 18 + 17, 0, 126, xSize, 96);
	}

	// @Override
	// protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	// //draw text and stuff here
	// //the parameters for drawString are: string, x, y, color
	// fontRenderer.drawString("Tiny", 8, 6, 4210752);
	// //draws "Inventory" or your regional equivalent
	// fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	// }
	//
	// @Override
	// protected void drawGuiContainerBackgroundLayer(float par1, int par2,
	// int par3) {
	// //draw your Gui here, only thing you need to change is the path
	// int texture = mc.renderEngine.getTexture("/gui/trap.png");
	// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	// this.mc.renderEngine.bindTexture(texture);
	// int x = (width - xSize) / 2;
	// int y = (height - ySize) / 2;
	// this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	// }

}
