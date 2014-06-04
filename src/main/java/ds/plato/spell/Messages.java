package ds.plato.spell;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	// Generic single pick for any spell
	public static String spell_pick;

	public static String spell_delete_name;
	public static String spell_delete_description;

	public static String spell_fill_name;
	public static String spell_fill_description;

	public static String spell_fill_checker_name;
	public static String spell_fill_checker_description;

	public static String spell_fill_random_name;
	public static String spell_fill_random_description;

	public static String spell_sphere_name;
	public static String spell_sphere_description;


	static {
		NLS.initializeMessages("ds.plato.spell.messages", Messages.class);
	}
}
