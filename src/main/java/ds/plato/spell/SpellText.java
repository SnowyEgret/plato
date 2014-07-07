package ds.plato.spell;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point3i;

import ds.plato.core.IWorld;
import ds.plato.core.SlotEntry;
import ds.plato.pick.IPick;
import ds.plato.pick.Pick;
import ds.plato.select.ISelect;
import ds.plato.spell.descriptor.SpellDescriptor;
import ds.plato.undo.IUndo;
import ds.plato.undo.SetBlock;
import ds.plato.undo.Transaction;

public class SpellText extends Spell {

	public SpellText(IUndo undoManager, ISelect selectionManager, IPick pickManager) {
		super(2, undoManager, selectionManager, pickManager);
	}

	@Override
	public Object[] getRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpellDescriptor getDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(IWorld world, SlotEntry[] slotEntries) {
		Pick[] picks = pickManager.getPicksArray();
		Point3i d = new Point3i();
		d.sub(picks[0], picks[1]);
//		int width = Math.abs(d.x);
//		int height = Math.abs(d.z);
		int width = 128;
		int height = 32;
		
		int fontSize = 24;
		String fontName = "URW Chancery L";
		int fontStyle = Font.PLAIN;
		Font font = new Font(fontName, fontStyle, fontSize);
		//font = font.deriveFont(32);
		
		String s = "Oceane";
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(font);
		Graphics2D graphics = (Graphics2D) g;
		// graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString(s, 6, fontSize + 6);
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
