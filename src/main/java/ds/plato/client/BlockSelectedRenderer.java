package ds.plato.client;

import javax.vecmath.Point3i;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ds.plato.common.Plato;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.select.Selection;

public class BlockSelectedRenderer implements ISimpleBlockRenderingHandler {

	private int id;
	private ISelect selectionManager;

	public BlockSelectedRenderer(int id, ISelect selectionManager) {
		this.id = id;
		this.selectionManager = selectionManager;
	}

	@Override
	public int getRenderId() {
		return id;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Block selectedBlock = null;
		Selection sel = selectionManager.selectionAt(x, y, z);
		if (sel != null) {
			selectedBlock = sel.block;
		} else {
			//renderer.renderStandardBlock(Blocks.ice, x, y, z);
			renderer.renderStandardBlock(block, x, y, z);
			return true;
		}

		renderer.renderStandardBlockWithColorMultiplier(selectedBlock, x, y, z, .9f, .7f, .7f);
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}
}
