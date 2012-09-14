package uk.co.jacekk.bukkit.baseplugin.v2.util;

import java.util.Collection;
import java.util.List;

/**
 * A collection of useful methods for working with lists.
 * 
 * @author Jacek Kuzemczak
 */
public class ListUtils {
	
	/**
	 * @param numbers	The list of numbers to sum.
	 * @return			The sum of all the values in the list.
	 */
	public static Long sumLongs(Collection<Long> numbers){
		Long sum = 0L;
		
		for (Long value : numbers){
			sum += value;
		}
		
		return sum;
	}
	
	/**
	 * @param numbers	The list of numbers to sum.
	 * @return			The sum of all the values in the list.
	 */
	public static Long sumLongs(List<Long> numbers){
		Long sum = 0L;
		
		for (Long value : numbers){
			sum += value;
		}
		
		return sum;
	}
	
	/**
	 * @param numbers	The list of numbers to sum.
	 * @return			The sum of all the values in the list.
	 */
	public static Integer sumIntegers(Collection<Integer> numbers){
		Integer sum = 0;
		
		for (Integer value : numbers){
			sum += value;
		}
		
		return sum;
	}
	
	/**
	 * @param numbers	The list of numbers to sum.
	 * @return			The sum of all the values in the list.
	 */
	public static Integer sumIntegers(List<Integer> numbers){
		Integer sum = 0;
		
		for (Integer value : numbers){
			sum += value;
		}
		
		return sum;
	}
	
	/**
	 * Calculates the standard deviation of the values in the list.
	 * 
	 * @param numbers	The numbers to use for the calculation.
	 * @return			The standard deviation.
	 */
	public static Double stddev(List<Long> numbers){
		Double mean = new Double(sumLongs(numbers)) / new Double(numbers.size());
		
		Double stdDevSum = 0D;
		
		for (Long number : numbers){
			stdDevSum += Math.pow(number - mean, 2);
		}
		
		return Math.sqrt(stdDevSum / numbers.size());
	}
	
	/**
	 * Joins all of the elements in the list together with a separator string.
	 * 
	 * @param sep		The string to use as a separator.
	 * @param values	The values.
	 * @return			A string of elements with the separator between them.
	 */
	public static String implode(String sep, List<?> values){
		StringBuilder builder = new StringBuilder();
		
		if (values.size() == 0){
			return "";
		}
		
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
	public static Object getRandom(List<?> items){
		return items.get((int) Math.random() * items.size());
	}
	
}
