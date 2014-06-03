package ds.plato.spell;

import org.eclipse.osgi.util.NLS;


public class Messages extends NLS {
	
	public static String spell_delete_name;
	public static String spell_delete_description;
	public static String spell_delete_pick;
	public static String spell_fill_name;
	public static String spell_fill_description;
	public static String spell_fill_pick;

	static {
		NLS.initializeMessages("ds.plato.spell.messages", Messages.class);
	}
}
