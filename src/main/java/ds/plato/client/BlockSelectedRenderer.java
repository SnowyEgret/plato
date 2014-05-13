package ds.plato.client;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ds.plato.common.Plato;
import ds.plato.common.Selection;
import ds.plato.pick.Pick;

public class BlockSelectedRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return ClientProxy.blockSelectedRenderId;
		//return MOD.blockSelectedRenderId;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Block selectedOrPickedBlock = null;
		Point3i p = new Point3i(x, y, z);
		Selection sel = Plato.selectionManager.selectionAt(p);
		if (sel == null) {
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
				// Block 
				renderer.renderStandardBlock(Blocks.ice, x, y, z);
				return true;
			} else {
				selectedOrPickedBlock = pick.block;
			}
		} else {
			selectedOrPickedBlock = sel.block;
		}
		
		if (selectedOrPickedBlock == null) {
			throw new RuntimeException("Block is not selected or picked at voxel "+p);
		}
		
		if (block == Plato.blockSelected) {
			renderer.renderStandardBlockWithColorMultiplier(selectedOrPickedBlock, x, y, z, .9f, .7f, .7f);
		} else if (block == Plato.blockPick0) {
			renderer.renderStandardBlockWithColorMultiplier(selectedOrPickedBlock, x, y, z, .7f, .9f, .7f);
			// } else if (block instanceof BlockPick1) {
			// renderer.renderStandardBlockWithColorMultiplier(selectedOrPickedBlock, x, y, z, .7f, .7f, .9f);
			// } else if (block instanceof BlockPick2) {
			// renderer.renderStandardBlockWithColorMultiplier(selectedOrPickedBlock, x, y, z, .9f, .7f, .9f);
		} else {
			throw new RuntimeException("Unexpected block type: "+block);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}
}
