package ds.plato.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.spell.SpellRestore;

public class GuiSavesList extends GuiListExtended {

	//private List<String> strings;
	private List<Row> rows = new ArrayList<Row>();
	private EntityPlayer player;

	public GuiSavesList(Minecraft mc, int w, int h, int ww, int hh, int a,
			EntityPlayer player, String... filenames) {
		super(mc, w, h, ww, hh, a);
		this.player = player;
		//this.strings = Lists.newArrayList(strings);
		for (String filename : filenames) {
			GuiButton b = new GuiButton(a, a, a, filename);
			rows.add(new Row(b));
		}
	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		return rows.get(index);
	}

	@Override
	protected int getSize() {
		return rows.size();
	}

	@SideOnly(Side.CLIENT)
	private class Row implements IGuiListEntry {

		private GuiButton button;
		private final Minecraft mc = Minecraft.getMinecraft();

		public Row(GuiButton button) {
			this.button = button;
		}

		@Override
		public void drawEntry(int p1, int p2, int y, int p4, int p5,
				Tessellator p6, int p7, int p8, boolean p9) {
			button.yPosition = y;
			button.drawButton(mc, p7, p8);
		}

		@Override
		public boolean mousePressed(int p1, int p2, int p3, int p4, int p5,
				int p6) {
			System.out.println(button.displayString);
			SpellRestore s = (SpellRestore) player.getHeldItem().getItem();
			s.readFile(button.displayString);
			return button.mousePressed(mc, p2, p3);
			// if (button.mousePressed(mc, p2, p3)) {
			// System.out.println(button.displayString);
			// return true;
			// }
			// return false;
		}

		@Override
		public void mouseReleased(int p1, int p2, int p3, int p4, int p5, int p6) {
			button.mouseReleased(p2, p3);
		}

	}
}
