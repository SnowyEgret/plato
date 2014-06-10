package ds.plato.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionsRowList;
import net.minecraft.client.gui.GuiOptionsRowList.Row;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ds.plato.spell.SpellRestore;

public class GuiRestore extends GuiScreen {

	private int w = 200;
	private int h = 100;
	private String fileName;
	private EntityPlayer player;
	private GuiOptionsRowList saves;

	public GuiRestore(EntityPlayer player) {
		this.player = player;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		Row row;
		switch (button.id) {
		case 0:
			row = saves.getListEntry(0);
			//fileName = row.;
			SpellRestore s = (SpellRestore) player.getHeldItem().getItem();
			s.readFile(fileName);
			break;
		case 1:
			break;
		}
		mc.displayGuiScreen(null);
	}

	@Override
	public void initGui() {
		int posX = (width - w) / 2;
		int posY = (height - h) / 2;
		this.buttonList.clear();
		buttonList.add(new GuiButton(0, posX + 20, posY + 40, 80, 20, "Ok"));
		buttonList.add(new GuiButton(1, posX + 120, posY + 40, 80, 20, "Cancel"));

		saves = new GuiOptionsRowList(mc, posY, posY, posY, posY, posY, null);
		// label = new GuiLabel();
		// labelList.add(label);
	}

	@Override
	public void drawScreen(int x, int y, float par3) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation("gui/restore.png"));
		int posX = (width - w) / 2;
		int posY = (height - h) / 2;
		drawTexturedModalRect(posX, posY, 0, 0, w, h);

		//textField.drawTextBox();
		// label.drawCenteredString(mc.fontRenderer, "Hello", 0, 0, 0);

		super.drawScreen(x, y, par3);
	}

}
