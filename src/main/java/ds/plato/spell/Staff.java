package ds.plato.spell;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import ds.plato.core.IToggleable;
import ds.plato.core.IWorld;
import ds.plato.pick.IPick;
import ds.plato.spell.descriptor.SpellDescriptor;

public class Staff extends Item implements IClickable, IToggleable, IHoldable {

	protected List<Spell> spells = new ArrayList<>();
	protected int ordinal = 0;
	private IPick pickManager;
	private IWorld world;
	private Property propertyOrdinal;

	public Staff(Property propertyOrdinal, IPick pickManager) {
		this.pickManager = pickManager;
		this.propertyOrdinal = propertyOrdinal;
	}

	public Staff setWorld(IWorld world) {
		this.world = world;
		return this;
	}

	@Override
	public Spell getSpell() {
		return currentSpell();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer player) {
		System.out.println("[Staff.onItemRightClick] w=" + w);
		if (currentSpell() != null)
			currentSpell().onItemRightClick(is, w, player);
		return is;
	}

//	@Override
//	public void onClickLeft(PlayerInteractEvent e) {
//		if (currentSpell() != null)
//			currentSpell().onClickLeft(e);
//	}

	@Override
	public void onMouseClickLeft(MovingObjectPosition position) {
		if (currentSpell() != null)
			currentSpell().onMouseClickLeft(position);
	}

	@Override
	public void onMouseClickRight(MovingObjectPosition position) {
		if (currentSpell() != null)
			currentSpell().onMouseClickRight(position);
	}

//	@Override
//	public void onClickRight(PlayerInteractEvent e) {
//		if (currentSpell() != null)
//			currentSpell().onClickRight(e);
//	}

//	@Override
//	public void onClickRightAir(PlayerInteractEvent e) {
//		if (currentSpell() != null)
//			currentSpell().onClickRightAir(e);
//	}

	@Override
	public void reset() {
		if (currentSpell() != null)
			currentSpell().reset();
	}

	@Override
	public boolean isPicking() {
		Spell s = currentSpell();
		if (s == null) {
			return false;
		} else {
			return s.isPicking();
		}
	}

	@Override
	public SpellDescriptor getDescriptor() {
		Spell s = currentSpell();
		if (s == null) {
			return new SpellDescriptor() {
			};
		} else {
			return s.getDescriptor();
		}
	}

	@Override
	public void toggle() {
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			previousSpell();
		} else {
			nextSpell();
		}
	}

	public Spell nextSpell() {
		if (ordinal == spells.size() - 1) {
			ordinal = 0;
		} else {
			ordinal++;
		}
		Spell currentSpell = currentSpell();
		if (currentSpell != null) {
			pickManager.reset(currentSpell.getNumPicks());
		}
		return currentSpell;
	}

	public Spell previousSpell() {
		if (ordinal == 0) {
			ordinal = spells.size() - 1;
		} else {
			ordinal--;
		}
		Spell currentSpell = currentSpell();
		pickManager.reset(currentSpell.getNumPicks());
		return currentSpell;
	}

	public Spell currentSpell() {
		if (spells.isEmpty()) {
			return null;
		} else {
			return spells.get(ordinal);
		}
	}

	public void addSpell(Spell spell) {
		if (!spells.contains(spell)) {
			spells.add(spell);
		}
	}

	public int numSpells() {
		return spells.size();
	}

	public void clearPicks() {
		pickManager.clearPicks();
	}

	@Override
	public String toString() {
		return "Staff [spells=" + spells + ", ordinal=" + ordinal + ", pickManager=" + pickManager + "]";
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public void save() {
		propertyOrdinal.set(ordinal);
		System.out.println("[Staff.save] propertyOrdinal=" + propertyOrdinal);
	}

	@Override
	public String getMessage() {
		Spell s = currentSpell();
		if (s == null) {
			return null;
		} else {
			return s.getMessage();
		}
	}
}
