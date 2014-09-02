package ds.plato.spell;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.model.IModelCustom;

public class SpellRenderer implements IItemRenderer {

	private IModelCustom model;
	private ResourceLocation textureResourceLocation;

	public SpellRenderer(Spell spell) {
		model = spell.getModel();
		textureResourceLocation = spell.getTextureResourceLocation();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case ENTITY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type) {
		case ENTITY:
			return (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.BLOCK_3D);
		case EQUIPPED:
			return (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
		case EQUIPPED_FIRST_PERSON:
			return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
		case INVENTORY:
			return (helper == ItemRendererHelper.INVENTORY_BLOCK);
		default:
			return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (model != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, .5);
			GL11.glRotated(-15, 0, 0, 1);
			Minecraft.getMinecraft().renderEngine.bindTexture(textureResourceLocation);
			model.renderAll();
			GL11.glPopMatrix();
		}
	}

}
