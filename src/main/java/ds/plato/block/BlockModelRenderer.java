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
		ResourceLocation l = new ResourceLocation("plato", "models/sphere.obj");
		System.out.println(l);
		System.out.println(l.getResourceDomain());
		System.out.println(l.getResourcePath());
		model = AdvancedModelLoader.loadModel(l);
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
		System.out.println("[BlockModelRenderer.renderWorldBlock] tes=" + tes);
		System.out.println("[BlockModelRenderer.renderWorldBlock] tes.renderingWorldRenderer="
				+ tes.renderingWorldRenderer);
		int mode = tes.draw();
		GL11.glPushMatrix();
		GL11.glTranslated(x + .5, y + .5 , z + .5);
		//GL11.glScalef(.5f, .5f, .5f);
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
