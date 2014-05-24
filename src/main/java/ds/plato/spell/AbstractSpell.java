package ds.plato.spell;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.google.inject.Inject;

import ds.plato.IWorld;
import ds.plato.common.ISelect;
import ds.plato.common.Selection;
import ds.plato.common.Transformer;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.undo.IUndo;
import ds.plato.undo.Transaction;

public abstract class AbstractSpell {

	public SpellDescriptor descriptor;
	protected IWorld world;
	protected IUndo undoManager;
	protected ISelect selectionManager;

	public AbstractSpell(SpellDescriptor descriptor, IUndo undoManager, ISelect selectionManager) {
		this.descriptor = descriptor;
		this.undoManager = undoManager;
		this.selectionManager = selectionManager;
	}

	// Spell can only be partially constructed during FML initialization. The world is only available after the player
	// joins the game.
	public AbstractSpell setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	public SpellDescriptor getDescriptor() {
		return descriptor;
	}

	// For Staff.addSpell(). Only one spell of each class in spells list
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() == obj.getClass())
			return true;
		return false;
	}

	public abstract void invoke(Pick[] picks);

	public abstract int getNumPicks();

}
