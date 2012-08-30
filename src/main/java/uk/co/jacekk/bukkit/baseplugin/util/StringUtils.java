package uk.co.jacekk.bukkit.baseplugin.util;

import java.util.HashMap;

public class StringUtils {
	
	private static final HashMap<String, Integer> stringValues = new HashMap<String, Integer>();
	
	static{
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
		
		return -1;
	}
	
}
