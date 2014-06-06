package ds.plato.proxy;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ds.plato.Plato;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.Selection;

public class BlockPickedRenderer implements ISimpleBlockRenderingHandler {

	private int id;
	private IPick pickManager;

	public BlockPickedRenderer(int id, IPick pickManager) {
		this.id = id;
		this.pickManager = pickManager;
	}

	@Override
	public int getRenderId() {
		return id;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Block pickedBlock = null;
		//TODO get pickManager from plato when migrated to staffs and spells
		Point3i p = new Point3i(x, y, z);
		Pick pick = Plato.surfaceStick.getPickAt(p);
		if (pick == null) {
			pick = Plato.selectionStick.getPickAt(p);
		}
		if (pick == null) {
			pick = Plato.curveStick.getPickAt(p);
		}
		if (pick == null) {
			pick = Plato.solidStick.getPickAt(p);
		}
		if (pick == null) {
			pick = Plato.editStick.getPickAt(p);
		}
		if (pick == null) {
			pick = pickManager.getPickAt(p);
		}
		if (pick == null) {
			//renderer.renderStandardBlock(Blocks.ice, x, y, z);
			renderer.renderStandardBlock(block, x, y, z);
			return true;
		} else {
			pickedBlock = pick.block;
		}

		renderer.renderStandardBlockWithColorMultiplier(pickedBlock, x, y, z, .7f, .9f, .7f);
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

}
