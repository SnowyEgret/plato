package ds.plato.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;

public class BlockPickedRenderer implements ISimpleBlockRenderingHandler {

	private int id;
	private IPick pickManager;
	private ISelect selectionManager;

	public BlockPickedRenderer(int id, ISelect selectionManager, IPick pickManager) {
		this.id = id;
		this.pickManager = pickManager;
		this.selectionManager = selectionManager;
	}

	@Override
	public int getRenderId() {
		return id;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		Block pickedBlock = null;
		Pick pick = pickManager.getPickAt(x, y, z);
		if (pick == null) {
			renderer.renderStandardBlock(block, x, y, z);
			return true;
		} else {
			pickedBlock = pick.block;
		}

		// If the pick is selected we want to render the selection's block, not the BlockSelected. Fix for issue #6.
		if (pickedBlock instanceof BlockSelected) {
			Selection s = selectionManager.selectionAt(x, y, z);
			if (s != null) {
				pickedBlock = s.block;
			}
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
