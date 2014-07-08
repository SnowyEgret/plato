package ds.plato.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ds.plato.spell.SpellText;

public class GuiSpellText extends GuiTextInputDialog {

	public GuiSpellText(EntityPlayer player) {
		super(player);
		//TODO GuiTextInputDialog extends GuiDialog extends GuiScreen
		//button = addButton("Font");
	}

}
