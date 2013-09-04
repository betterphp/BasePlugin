package uk.co.jacekk.bukkit.baseplugin.util;

import java.util.List;
import java.util.Random;

/**
 * A collection of useful methods for working with lists.
 * 
 * @author Jacek Kuzemczak
 */
public class ListUtils {
	
	private static final Random rand = new Random();
	
	/**
	 * Joins all of the elements in the list together with a separator string.
	 * 
	 * @param sep		The string to use as a separator.
	 * @param values	The values.
	 * @return			A string of elements with the separator between them.
	 */
	public static String implode(String sep, List<?> values){
		if (values.size() == 0){
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(values.get(0).toString());
		
		for (int i = 1; i < values.size(); ++i){
			builder.append(sep);
			builder.append(values.get(i).toString());
		}
		
		return builder.toString();
	}
	
	/**
	 * Gets a random entry from the list.
	 * 
	 * @param items		The list to use.
	 * @return			A random entry from the list.
	 */
	public static <T> T getRandom(List<T> items){
		return items.get(rand.nextInt(items.size()));
	}
	
	/**
	 * Sums all of the numbers in the list.
	 * 
	 * @param numbers The list
	 * @return The sum.
	 */
	public static int sumInts(List<Integer> numbers){
		int result = 0;
		
		for (Integer number : numbers){
			result += number;
		}
		
		return result;
	}
	
	/**
	 * Sums all of the numbers in the list.
	 * 
	 * @param numbers The list
	 * @return The sum.
	 */
	public static long sumLongs(List<Long> numbers){
		long result = 0;
		
		for (Long number : numbers){
			result += number;
		}
		
		return result;
	}
	
	/**
	 * Sums all of the numbers in the list.
	 * 
	 * @param numbers The list
	 * @return The sum.
	 */
	public static double sumDoubles(List<Double> numbers){
		double result = 0;
		
		for (Double number : numbers){
			result += number;
		}
		
		return result;
	}
	
	/**
	 * Sums all of the numbers in the list.
	 * 
	 * @param numbers The list
	 * @return The sum.
	 */
	public static float sumFloats(List<Float> numbers){
		float result = 0;
		
		for (Float number : numbers){
			result += number;
		}
		
		return result;
	}
	
}
