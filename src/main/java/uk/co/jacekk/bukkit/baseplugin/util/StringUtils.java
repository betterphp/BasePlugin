package uk.co.jacekk.bukkit.baseplugin.util;

import java.util.HashMap;

/**
 * A collection of useful methods for working with strings.
 * 
 * @author Jacek Kuzemczak
 */
public class StringUtils {
	
	private static final HashMap<String, Integer> stringValues = new HashMap<String, Integer>(4);
	
	static{
		stringValues.put("snapshot", -1);
		stringValues.put("dev", -1);
		stringValues.put("alpha", 1);
		stringValues.put("beta", 2);
		stringValues.put("release", 3);
	}
	
	private static int numericValueOfVersionPart(String part){
		try{
			return Integer.parseInt(part);
		}catch (NumberFormatException e){
			if (part.length() == 1){
				return part.getBytes()[0];
			}else if (stringValues.containsKey(part)){
				return stringValues.get(part);
			}
			
			return 0;
		}
	}
	
	/**
	 * Compares two version numbers.
	 * 
	 * @param a	The first version number.
	 * @param b	The second version number.
	 * @return	-1 if a is less than b, 1 if a is greater than b and 0 if a and b are equal.
	 */
	public static int versionCompare(String a, String b){
		a = a.trim().toLowerCase();
		b = b.trim().toLowerCase();
		
		if (a.equals(b)){
			return 0;
		}
		
		String[] partsa = a.replaceAll("([0-9]+)([a-z]+)", "$1.$2").replaceAll("([a-z]+)([0-9]+)", "$1.$2").split("[_ ,.+\\-]{1}");
		String[] partsb = b.replaceAll("([0-9]+)([a-z]+)", "$1.$2").replaceAll("([a-z]+)([0-9]+)", "$1.$2").split("[_ ,.+\\-]{1}");
		
		int max = Math.max(partsa.length, partsb.length);
		
		for (int i = 0; i < max; ++i){
			Integer vala = (i < partsa.length) ? numericValueOfVersionPart(partsa[i]) : 0;
			Integer valb = (i < partsb.length) ? numericValueOfVersionPart(partsb[i]) : 0;
			
			if (vala > valb){
				return 1;
			}else if (vala < valb){
				return -1;
			}
		}
		
		return 0;
	}
	
}
