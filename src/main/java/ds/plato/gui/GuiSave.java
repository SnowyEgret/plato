package ds.plato.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ds.plato.spell.SpellSave;

public class GuiSave extends GuiScreen {
	// public class GuiSave extends GuiContainer {

	private int w = 200;
	private int h = 100;
	int posX;
	int posY;
	private GuiTextField textField;
	private String fileName;
	private EntityPlayer player;
	private GuiLabel label;

	public GuiSave(EntityPlayer player) {
		this.player = player;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			fileName = textField.getText();
			SpellSave s = (SpellSave) player.getHeldItem().getItem();
			s.writeFile(fileName);
			break;
		case 1:
			break;
		}
		mc.displayGuiScreen(null);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		textField.textboxKeyTyped(par1, par2);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		textField.mouseClicked(par1, par2, par3);
	}

	@Override
	public void initGui() {
		posX = (width - w) / 2;
		posY = (height - h) / 2;
		this.buttonList.clear();
		buttonList.add(new GuiButton(0, posX + 20, posY + 40, 80, 20, "Ok"));
		buttonList.add(new GuiButton(1, posX + 120, posY + 40, 80, 20, "Cancel"));

		textField = new GuiTextField(mc.fontRenderer, posX, posY, 150, 20);
		// label = new GuiLabel();
		// labelList.add(label);
	}

	@Override
	public void drawScreen(int x, int y, float par3) {
		drawDefaultBackground();
		// drawWorldBackground(0);

		// ITextureObject texture = mc.renderEngine.getTexture(l);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("gui/save.png"));
		drawTexturedModalRect(posX, posY, 0, 0, w, h);

		textField.drawTextBox();
		// label.drawCenteredString(mc.fontRenderer, "Hello", 0, 0, 0);

		super.drawScreen(x, y, par3);
	}

	// @Override
	// protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
	// // TODO Auto-generated method stub
	//
	// }

}
