package ds.plato.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.plato.client.ClientProxy;

public class BlockSelected extends Block {

	public BlockSelected() {
		super(Material.ice);
		setHardness(-1F);
		setStepSound(soundTypeGravel);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String s = Plato.ID + ":" + getUnlocalizedName().substring(5); // Removes "tile."
		this.blockIcon = iconRegister.registerIcon(s);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return ClientProxy.blockSelectedRenderId;
	}
}
