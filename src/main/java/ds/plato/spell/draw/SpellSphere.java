package ds.plato.spell.draw;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.surface.Sphere;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellSphere extends AbstractSpellDraw {

	public SpellSphere(IUndo undo, ISelect select, IPick pick) {
		super(2, undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry... slotEntries) {
		selectionManager.clearSelections();
		Pick[] picks = pickManager.getPicks();
		boolean isHemisphere = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		IDrawable d = new Sphere(picks[0].point3d(), picks[1].point3d(), isHemisphere);
		draw(d, world, slotEntries[0].block, slotEntries[0].metadata);
		pickManager.clearPicks();
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = I18n.format("item.spellSphere.name");
		d.description = I18n.format("item.spellSphere.description");
		d.picks = new PickDescriptor(I18n.format("item.spellSphere.pick.0"), I18n.format("item.spellSphere.pick.1"));
		d.modifiers = new ModifierDescriptor(SHIFT + I18n.format("item.spellSphere.modifier.0"));
		return d;
	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { " A ", "A A", " A ", 'A', Items.bone };
	}
}
