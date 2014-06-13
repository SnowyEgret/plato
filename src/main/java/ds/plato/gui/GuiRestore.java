package ds.plato.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ds.plato.spell.SpellRestore;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiOptionsRowList.Row;
import net.minecraft.entity.player.EntityPlayer;

public class GuiRestore extends GuiScreen {

	private GuiSavesList list;
	private EntityPlayer player;

	public GuiRestore(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public void initGui() {
		List<String> filenames = new ArrayList<>();
		File folder = new File("saves");
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				String filename = file.getName();
				if (filename.endsWith(".json")) {
					filenames.add(filename.substring(0, filename.length()-5));
				}
			}
		}
		System.out.println("[GuiRestore.initGui] filenames=" + filenames);
		String[] saves = new String[filenames.size()];
		int spacing = 25;
		int header = 30;
		int footer = 40;
		list = new GuiSavesList(mc, width, height, header, height - footer, spacing, player, filenames.toArray(saves));

		this.buttonList.clear();
		int bw = 80;
		int bh = 20;
		buttonList.add(new GuiButton(1, width / 2 - bw / 2, height - (footer / 2) + (bh / 2), bw, bh, "Cancel"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		Row row;
		switch (button.id) {
		case 0:
			//int i = list.func_148135_f();
			break;
		case 1:
			break;
		}
		mc.displayGuiScreen(null);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		list.func_148179_a(par1, par2, par3);
		mc.displayGuiScreen(null);
	}

	@Override
	protected void mouseMovedOrUp(int p1, int p2, int p3) {
		super.mouseMovedOrUp(p1, p2, p3);
		list.func_148181_b(p1, p2, p3);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		list.drawScreen(par1, par2, par3);
		super.drawScreen(par1, par2, par3);
	}
}
