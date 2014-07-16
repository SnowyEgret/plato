package ds.plato.core;

public class StringUtils {

	public static String lastWordInCamelCase(String camelCase) {
		String[] tokens = camelCase.split("(?=[A-Z])");
		return tokens[tokens.length - 1];
	}

}
