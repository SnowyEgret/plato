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

	public static String spell_copy_name;
	public static String spell_copy_description;
	public static String spell_copy_picks;
	public static String spell_copy_modifier;
	
	//Draww spells
	
	public static String spell_sphere_name;
	public static String spell_sphere_description;
	public static String spell_sphere_picks;
	public static String spell_sphere_modifier;

	public static String spell_cone_name;
	public static String spell_cone_description;
	public static String spell_cone_picks;
	public static String spell_cone_modifier;

	//Grow spells
	public static String spell_grow_picks;
	public static String spell_grow_modifier_0;
	public static String spell_grow_modifier_1;

	public static String spell_grow_all_name;
	public static String spell_grow_all_description;

	public static String spell_grow_horizontal_name;
	public static String spell_grow_horizontal_description;

	public static String spell_grow_up_name;
	public static String spell_grow_up_description;
	
	public static String spell_grow_down_name;
	public static String spell_grow_down_description;
	
	public static String spell_grow_above_name;
	public static String spell_grow_above_description;
	
	public static String spell_grow_bellow_name;
	public static String spell_grow_bellow_description;
	
	//Transform spells

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
	
	public static String spell_save_name;
	public static String spell_save_description;
	public static String spell_save_picks;
	public static String spell_save_modifier;
	
	public static String spell_restore_name;
	public static String spell_restore_description;
	public static String spell_restore_modifier;

	static {
		NLS.initializeMessages("ds.plato.spell.messages", Messages.class);
	}
}
