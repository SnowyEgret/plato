package ds.plato.test;

import static org.mockito.Mockito.when;
import net.minecraft.block.Block;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ds.plato.block.BlockPicked;
import ds.plato.block.BlockSelected;
import ds.plato.core.IWorld;
import ds.plato.pick.IPick;
import ds.plato.pick.PickManager;
import ds.plato.select.ISelect;
import ds.plato.select.SelectionManager;
import ds.plato.undo.IUndo;
import ds.plato.undo.UndoManager;

public class PlatoIntegrationTest {
	
	protected IUndo undoManager;
	protected ISelect selectionManager;
	protected IPick pickManager;
	protected IWorld world;
	protected Block blockSelected;
	protected Block blockPicked;
	protected Block dirt;
	
	@Before public void setUp() {
		blockSelected = new BlockSelected();
		blockPicked = new BlockPicked();
		dirt = new StubBlockDirt();
		world = new StubWorld(dirt);
		undoManager = new UndoManager();
		selectionManager = new SelectionManager(blockSelected);//.setWorld(world);
		pickManager = new PickManager(blockPicked);//.setWorld(world);
	}
	
	protected class StubBlockDirt extends BlockSelected {}

}
