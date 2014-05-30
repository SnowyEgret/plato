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

	private int id;

	public BlockSelectedRenderer(int id) {
		this.id = id;
	}

	@Override
	public int getRenderId() {
		return id;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Block selectedBlock = null;
		Selection sel = Plato.selectionManager.selectionAt(x, y, z);
		if (sel != null) {
			selectedBlock = sel.block;
		} else {
			renderer.renderStandardBlock(Blocks.ice, x, y, z);
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
