package ds.plato.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiSave extends GuiScreen {

	private int w = 200;
	private int h = 100;

	@Override
	protected void actionPerformed(GuiButton button) {
		System.out.println(button);
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		int posX = (this.width - w) / 2;
		int posY = (this.height - h) / 2;
		this.buttonList.add(new GuiButton(0, posX+ 40, posY + 40, 100, 20, "no use"));
	}

	public GuiSave(EntityPlayer player) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawScreen(int x, int y, float par3) {
		drawDefaultBackground();

		// ITextureObject texture = mc.renderEngine.getTexture(l);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("gui/save.png"));
		int posX = (width - w) / 2;
		int posY = (height - h) / 2;
		drawTexturedModalRect(posX, posY, 0, 0, w, h);

		super.drawScreen(x, y, par3);
	}

}
