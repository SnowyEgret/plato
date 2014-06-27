package ds.plato.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockModel extends Block {

	public BlockModel() {
		super(Material.clay);
	}

	@Override
	public int getRenderType() {
		return BlockModelRenderer.id;
	}

}
