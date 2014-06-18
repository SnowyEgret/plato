package ds.plato.gui;

import javax.vecmath.Vector3d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ds.plato.core.SlotDistribution;
import ds.plato.select.ISelect;
import ds.plato.spell.IHoldable;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.transform.SpellFillRandom;

public class Overlay {

	private ISelect selectionManager;
	private Vector3d displacement;
	private String message;

	public Overlay(ISelect selectionManager) {
		this.selectionManager = selectionManager;
	}

	public void setDisplacement(Vector3d displacement) {
		this.displacement = displacement;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void draw(IHoldable holdable) {
		int x = 10;
		int y = x;
		FontRenderer r = Minecraft.getMinecraft().fontRenderer;
		int dy = r.FONT_HEIGHT + 5;
		SpellDescriptor descriptor = holdable.getDescriptor();
		if (descriptor != null) {
			if (descriptor.name != null) {
				r.drawStringWithShadow(descriptor.name.toUpperCase() + " spell", x, y, 0xffffff);
			}
			if (descriptor.description != null) {
				r.drawStringWithShadow(descriptor.description, x, y += dy, 0xffffff);
			}
			if (descriptor.picks != null) {
				r.drawStringWithShadow(descriptor.picks.toString(), x, y += dy, 0xaaffaa);
			}
			if (descriptor.modifiers != null) {
				r.drawStringWithShadow(descriptor.modifiers.toString(), x, y += dy, 0xaaaaff);
			}
		}

		if (holdable.isPicking()) {
			if (displacement != null) {
				r.drawStringWithShadow(displacement.toString(), x, y += dy, 0xffaaaa);
			}
		}

		r.drawStringWithShadow("Selection size: " + selectionManager.size(), x, y += dy, 0xffaaaa);

		Spell s = holdable.getSpell();
		if (s != null) {
			if (s instanceof SpellFillRandom) {
				SlotDistribution d = new SlotDistribution(
						s.getSlotEntriesFromPlayer(Minecraft.getMinecraft().thePlayer));
				r.drawStringWithShadow(d.toString(), x, y += dy, 0xffaaaa);
			}
		}

		if (message != null) {
			r.drawStringWithShadow(message, x, y += dy, 0xffaaaa);
		}
	}

}
