package ds.plato.api;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public interface IItem {

	public abstract IModelCustom getModel();

	public abstract ResourceLocation getTextureResourceLocation();

	public abstract Object[] getRecipe();

	public abstract boolean hasRecipe();

}