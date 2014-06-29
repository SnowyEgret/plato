package ds.plato.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockModelRenderer implements ISimpleBlockRenderingHandler {

	public static int id;
	private IModelCustom model;

	public BlockModelRenderer() {
		this.id = RenderingRegistry.getNextAvailableRenderId();
		model = AdvancedModelLoader.loadModel(new ResourceLocation("plato", "models/sphere.obj"));
	}

	public void setModel(String modelName) {
		model = AdvancedModelLoader.loadModel(new ResourceLocation("plato", "models/" + modelName + ".obj"));
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		GL11.glPushMatrix();
		model.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		Tessellator tes = Tessellator.instance;
		// To solve "already rendering" exception. See http://www.minecraftforge.net/forum/index.php?topic=16380.0
		tes.draw();
		GL11.glPushMatrix();
		GL11.glTranslated(x + .5, y + .5, z + .5);
		// GL11.glScaled(.5, .5, .5);
		try {
			model.renderAll();
		} catch (Exception e) {
			System.out.println("[BlockModelRenderer.renderWorldBlock] e=" + e);
			e.printStackTrace();
		}
		GL11.glPopMatrix();
		tes.startDrawingQuads();
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}

}
