package ds.plato.spell;

import org.eclipse.osgi.util.NLS;


public class Messages extends NLS {
	
	public static String spell_delete_name;
	public static String spell_delete_description;
	public static String spell_delete_pick;

	static {
		NLS.initializeMessages("ds.plato.spell.messages", Messages.class);
	}
}
