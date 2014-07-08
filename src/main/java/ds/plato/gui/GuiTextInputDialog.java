package ds.plato.gui;

import org.lwjgl.opengl.GL11;

import ds.plato.spell.SpellText;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTextInputDialog extends GuiScreen {

	private int w = 200;
	private int h = 100;
	private GuiTextField textField;
	private String text;
	private EntityPlayer player;
	private GuiLabel label;

	public GuiTextInputDialog(EntityPlayer player) {
		this.player = player;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			text = textField.getText();
			ITextSetable s = (ITextSetable) player.getHeldItem().getItem();
			s.setText(text);
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
		int posX = (width - w) / 2;
		int posY = (height - h) / 2;
		this.buttonList.clear();
		buttonList.add(new GuiButton(0, posX + 20, posY + 40, 80, 20, "Ok"));
		buttonList.add(new GuiButton(1, posX + 120, posY + 40, 80, 20, "Cancel"));
		textField = new GuiTextField(mc.fontRenderer, posX, posY, 150, 20);
	}

	@Override
	public void drawScreen(int x, int y, float par3) {
		drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("gui/textInputDialog.png"));
		int posX = (width - w) / 2;
		int posY = (height - h) / 2;
		drawTexturedModalRect(posX, posY, 0, 0, w, h);
		textField.drawTextBox();
		super.drawScreen(x, y, par3);
	}
}
