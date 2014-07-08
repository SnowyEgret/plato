package ds.plato.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiDialog extends GuiScreen {

	protected int w = 260;
	private int h = 100;
	private int buttonWidth = 60;
	protected int buttonHeight = 20;
	protected int padding = 10;
	protected EntityPlayer player;
	protected List<String> buttons = new ArrayList<>();
	private int dx = 0;
	private int buttonCount = 0;

	public GuiDialog(EntityPlayer player, String... buttons) {
		this.player = player;
		for (String b : buttons) {
			this.buttons.add(b);
		}
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		for (String b : buttons) {
			addButton(b);
		}
	}

	@Override
	public void drawScreen(int x, int y, float par3) {
		drawDefaultBackground();
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		//mc.renderEngine.bindTexture(new ResourceLocation("gui/textInputDialog.png"));
		//drawTexturedModalRect(topLeftX(), topLeftY(), 0, 0, w, h);
		super.drawScreen(x, y, par3);
	}

	protected int topLeftX() {
		return (width - w) / 2;
	}

	protected int topLeftY() {
		return (height - h) / 2;
	}

	protected void addExtraButton(String button) {
		this.buttons.add(button);
	}

	private void addButton(String button) {
		buttonList.add(new GuiButton(buttonCount, topLeftX() + (w / 2) - (widthOfButtons() / 2) + dx, topLeftY() + h
				- padding - buttonHeight, buttonWidth, buttonHeight, button));
		buttonCount++;
		dx += buttonWidth + padding;
	}

	private int widthOfButtons() {
		return buttons.size() * buttonWidth + (buttons.size() - 1) * padding;
	}
}
