package ds.plato.common;

import org.eclipse.osgi.util.NLS;

@Deprecated
public class Messages extends NLS {
	
	public static String selection_expand_all;
	public static String selection_expand_bottom;
	public static String selection_expand_ceiling;
	public static String selection_expand_ceiling_edge;
	public static String selection_expand_ceiling_pick_1;
	public static String selection_expand_down;
	public static String selection_expand_floor;
	public static String selection_expand_floor_edge;
	public static String selection_expand_floor_pick_1;
	public static String selection_expand_horizontal;
	public static String selection_expand_top;
	public static String selection_expand_up;
	public static String selection_expand_xy;
	public static String selection_expand_zy;
	
	static {
		NLS.initializeMessages("ds.plato.common.messages", Messages.class);
	}
}
