package ds.plato.gui;

import javax.vecmath.Vector3d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ds.plato.select.ISelect;
import ds.plato.spell.IHoldable;
import ds.plato.spell.descriptor.SpellDescriptor;

public class Overlay {

	private ISelect selectionManager;
	private Vector3d displacement = new Vector3d();

	public Overlay(ISelect selectionManager) {
		this.selectionManager = selectionManager;
	}

	public void setDisplacement(Vector3d displacement) {
		this.displacement = displacement;
	}

	public void draw(IHoldable holdable) {
		int x = 10;
		int y = x;
		SpellDescriptor d = holdable.getDescriptor();
		FontRenderer r = Minecraft.getMinecraft().fontRenderer;
		int dy = r.FONT_HEIGHT + 5;
		r.drawStringWithShadow(d.getName(), x, y, 0xffffff);
		if (d.description != null) {
			// TODO Capitalize description through getter
			r.drawStringWithShadow(d.description.toString(), x, y += dy, 0xffffff);
		}
		if (d.picks != null) {
			r.drawStringWithShadow(d.picks.toString(), x, y += dy, 0xaaffaa);
		}
		if (d.modifiers != null) {
			r.drawStringWithShadow(d.modifiers.toString(), x, y += dy, 0xaaaaff);
		}
		// if (describable.currentSpell().isPicking()) {
		if (holdable.isPicking()) {
			r.drawStringWithShadow(displacement.toString(), x, y += dy, 0xffaaaa);
		}
		r.drawStringWithShadow("Selection size: " + selectionManager.size(), x, y += dy, 0xffaaaa);
	}

}
