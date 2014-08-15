package ds.plato.spell.draw;

import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.geom.IDrawable;
import ds.plato.geom.surface.Cone;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.ModifierDescriptor;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;

public class SpellCone extends AbstractSpellDraw {

	public SpellCone(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(3, undoManager, selectionManager, pickManager);
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = I18n.format("item.spellCone.name");
		d.description = I18n.format("item.spellCone.description");
		d.picks = new PickDescriptor(I18n.format("item.spellCone.pick.0"), I18n.format("item.spellCone.pick.1"),
				I18n.format("item.spellCone.pick.2"));
		d.modifiers = new ModifierDescriptor(SHIFT + I18n.format("modifier.isHollow"));
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry... slotEntries) {
		selectionManager.clearSelections();
		Pick[] picks = pickManager.getPicks();
		IDrawable d = new Cone(picks[0].point3d(), picks[1].point3d(), picks[2].point3d());
		boolean isHollow = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		draw(d, world, slotEntries[0].block, slotEntries[0].metadata, isHollow);
		pickManager.clearPicks();
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}
}
