package ds.plato.spell.draw;

import net.minecraft.block.BlockAir;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.surface.Sphere;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.Messages;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.undo.IUndo;

public class SpellSphere extends AbstractSpellDraw {

	public SpellSphere(IUndo undo, ISelect select, IPick pick) {
		super(2, undo, select, pick);
	}

	@Override
	public void invoke(IWorld world, final SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		boolean isHemisphere = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		IDrawable d = new Sphere(picks[0].toPoint3d(), picks[1].toPoint3d(), isHemisphere);
		draw(d, world, slotEntries[0].block, slotEntries[0].metadata);
		pickManager.clearPicks();
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_sphere_name;
		d.description = Messages.spell_sphere_description;
		d.picks = new PickDescriptor(Messages.spell_sphere_picks);
		d.modifiers = new ModifierDescriptor(Messages.spell_sphere_modifier);
		return d;
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
