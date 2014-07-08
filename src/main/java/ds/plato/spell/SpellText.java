package ds.plato.spell;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point3i;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import ds.plato.Plato;
import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.gui.ITextSetable;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.PickDescriptor;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellText extends Spell implements ITextSetable {

	private Font font;
	private IWorld world;
	private SlotEntry[] slotEntries;

	public SpellText(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
		
		int fontSize = 24;
		String fontName = "URW Chancery L";
		int fontStyle = Font.PLAIN;
		font = new Font(fontName, fontStyle, fontSize);
		// font = font.deriveFont(32);

	}

	@Override
	public Object[] getRecipe() {
		return new Object[] { "   ", "BAB", "   ", 'A', Items.feather, 'B', Items.dye };
	}

	@Override
	public SpellDescriptor getDescriptor() {
		SpellDescriptor d = new SpellDescriptor();
		d.name = Messages.spell_text_name;
		d.description = Messages.spell_text_description;
		d.picks = new PickDescriptor(Messages.spell_text_picks);
		// d.modifiers = new ModifierDescriptor(Messages.spell_thicken_modifier_0, Messages.spell_thicken_modifier_1,
		// Messages.spell_thicken_modifier_2);
		return d;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		this.world = world;
		this.slotEntries = slotEntries;
		Minecraft.getMinecraft().thePlayer.openGui(Plato.instance, -1, world.getWorld(), 0, 0, 0);
	}
	
	@Override
	public void setText(String text) {
		
		System.out.println("[SpellText.doText] text=" + text);
		System.out.println("[SpellText.doText] font=" + font);
		// boolean chooseFont = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		// System.out.println("[SpellText.invoke] chooseFont=" + chooseFont);
		// if (chooseFont) {
		// JFontChooser chooser = new JFontChooser();
		// chooser.showDialog(null);
		// font = chooser.getSelectedFont();
		// pickManager.clearPicks();
		// return;
		// }

		Pick[] picks = pickManager.getPicks();
		Point3i d = new Point3i();
		d.sub(picks[0], picks[1]);
		// int width = Math.abs(d.x);
		// int height = Math.abs(d.z);
		int width = 128;
		int height = 32;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(font);
		Graphics2D graphics = (Graphics2D) g;
		// graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString(text, 6, 24 + 6);
		// ImageIO.write(image, "png", new File("text.png"));

		Set<Point3i> points = new HashSet<>();
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				int pixel = image.getRGB(w, h);
				// if (pixel == -16777216) {
				if (pixel == -1) {
					Point3i p = new Point3i(w, 0, h);
					p.add(picks[0].getPoint3i());
					points.add(p);
				}
			}
		}

		System.out.println("[SpellText.invoke] points.size()=" + points.size());
		selectionManager.clearSelections();
		Transaction t = undoManager.newTransaction();
		for (Point3i p : points) {
			t.add(new SetBlock(world, selectionManager, p.x, p.y, p.z, slotEntries[0].block, slotEntries[0].metadata)
					.set());
		}
		t.commit();
		pickManager.clearPicks();
		selectionManager.clearSelections();
	}

}
