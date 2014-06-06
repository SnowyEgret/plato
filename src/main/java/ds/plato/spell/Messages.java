package ds.plato.spell;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	// Generic single pick for any spell
	public static String spell_pick;
	public static String spell_modifier_deleteOriginal;

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
	public static String spell_sphere_picks;

	public static String spell_copy_name;
	public static String spell_copy_description;
	public static String spell_copy_picks;
	public static String spell_copy_modifier;

	public static String spell_grow_all_name;
	public static String spell_grow_all_description;
	public static String spell_grow_all_picks;
	public static String spell_grow_all_modifier_0;
	public static String spell_grow_all_modifier_1;

	public static String spell_rotate_90_name;
	public static String spell_rotate_90_description;
	public static String spell_rotate_90_picks;
	public static String spell_rotate_90_modifier_0;
	public static String spell_rotate_90_modifier_1;
	public static String spell_rotate_90_modifier_2;
	public static String spell_rotate_90_modifier_3;

	public static String spell_mirror_name;
	public static String spell_mirror_description;
	public static String spell_mirror_picks;

	static {
		NLS.initializeMessages("ds.plato.spell.messages", Messages.class);
	}
}
