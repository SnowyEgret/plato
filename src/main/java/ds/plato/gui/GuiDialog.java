package ds.plato.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiDialog extends GuiScreen {

	private int w = 200;
	private int h = 100;
	private int buttonWidth = 80;
	private int buttonHeight = 20;
	private int padding = 20;
	private EntityPlayer player;
	private String[] buttons;

	public GuiDialog(EntityPlayer player, String...buttons) {
		this.player = player;
		this.buttons = buttons;
	}

	@Override
	public void initGui() {
		System.out.println("[GuiDialog.initGui] buttons=" + buttons);
		int posX = (width - w) / 2;
		int posY = (height - h) / 2;
		this.buttonList.clear();
		int x = padding;
		for (int i = 0; i < buttons.length; i++) {
			buttonList.add(new GuiButton(i, posX + x, posY + padding, buttonWidth, buttonHeight, buttons[i]));
			x += buttonWidth + padding;
		}
	}

	@Override
	public void drawScreen(int x, int y, float par3) {
		drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("gui/textInputDialog.png"));
		int posX = (width - w) / 2;
		int posY = (height - h) / 2;
		drawTexturedModalRect(posX, posY, 0, 0, w, h);
		super.drawScreen(x, y, par3);
	}
}
