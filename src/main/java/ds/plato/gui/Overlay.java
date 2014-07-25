package ds.plato.gui;

import javax.vecmath.Vector3d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import ds.plato.core.Player;
import ds.plato.core.SlotDistribution;
import ds.plato.select.ISelect;
import ds.plato.spell.Spell;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.transform.SpellFillRandom;
import ds.plato.staff.Staff;

public class Overlay {

	private ISelect selectionManager;
	private Vector3d displacement;
	private final int white = 0xffffff;
	private final int red = 0xffaaaa;
	private final int green = 0xaaffaa;
	private final int blue = 0xaaaaff;

	public Overlay(ISelect selectionManager) {
		this.selectionManager = selectionManager;
	}

	public void setDisplacement(Vector3d displacement) {
		this.displacement = displacement;
	}

	
	public void drawSpell(Spell spell) {
		int x = 10;
		int y = x;
		FontRenderer r = Minecraft.getMinecraft().fontRenderer;
		int dy = r.FONT_HEIGHT + 5;
		SpellDescriptor descriptor = spell.getDescriptor();
		if (descriptor != null) {
			if (descriptor.name != null) {
				r.drawStringWithShadow(descriptor.name.toUpperCase() + " spell", x, y, white);
			}
			if (descriptor.description != null) {
				r.drawStringWithShadow(descriptor.description, x, y += dy, white);
			}
			if (descriptor.picks != null) {
				r.drawStringWithShadow(descriptor.picks.toString(), x, y += dy, green);
			}
			if (descriptor.modifiers != null) {
				r.drawStringWithShadow(descriptor.modifiers.toString(), x, y += dy, blue);
			}
		}

		if (spell.isPicking() || (!spell.isPicking() && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
			if (displacement != null) {
				int dx = (int) displacement.x;
				int dz = (int) displacement.z;
				// Add 1 to get distance instead of displacement
				r.drawStringWithShadow(((dx >= 0) ? "East" : "West") + ": " + (Math.abs(dx) + 1) + "  "
						+ ((dz >= 0) ? "North" : "South") + ": " + (Math.abs(dz) + 1), x, y += dy, red);
				r.drawStringWithShadow("Height: " + (Math.abs((int) displacement.y) + 1), x, y += dy, red);
			}
		}

		r.drawStringWithShadow("Selection size: " + selectionManager.size(), x, y += dy, red);

		// TODO SpellFillRandom should set message
		if (spell instanceof SpellFillRandom) {
			SlotDistribution d = Player.client().slotDistribution();
			r.drawStringWithShadow(d.toString(), x, y += dy, blue);
		}

		String message = spell.getMessage();
		if (message != null) {
			r.drawStringWithShadow(message, x, y += dy, green);
		}
	}

	public void drawStaff(Staff staff, ItemStack stack) {
		int x = 10;
		int y = x;
		FontRenderer r = Minecraft.getMinecraft().fontRenderer;
		String staffName = staff.getItemStackDisplayName(stack);
		r.drawStringWithShadow(staffName + " has no spells", x, y, white);
	}

}
