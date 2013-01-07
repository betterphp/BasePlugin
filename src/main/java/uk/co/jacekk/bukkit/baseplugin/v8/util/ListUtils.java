package uk.co.jacekk.bukkit.baseplugin.v8.util;

import java.util.Collection;
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
	 * @param numbers	The list of numbers to sum.
	 * @return			The sum of all the values in the list.
	 */
	public static long sumLongs(Collection<Long> numbers){
		long sum = 0L;
		
		for (long value : numbers){
			sum += value;
		}
		
		return sum;
	}
	
	/**
	 * @param numbers	The list of numbers to sum.
	 * @return			The sum of all the values in the list.
	 */
	public static int sumIntegers(Collection<Integer> numbers){
		int sum = 0;
		
		for (int value : numbers){
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
	public static double stddev(Collection<Long> numbers){
		double mean = (double) sumLongs(numbers) / (double) numbers.size();
		
		double stdDevSum = 0D;
		
		for (Long number : numbers){
			double diff = number - mean;
			stdDevSum += diff * diff;
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
	
}
