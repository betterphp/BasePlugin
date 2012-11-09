package uk.co.jacekk.bukkit.baseplugin.v5.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionUtils {
	
	/**
	 * Gets the value of a field from an object.
	 * 
	 * @param src	The class where the field is defined.
	 * @param name	The name of the field.
	 * @param type	The type of the field.
	 * @param from	The object to get the field value from.
	 * 
	 * @return	The value of the field.
	 */
	public static <Type> Type getFieldValue(Class<?> src, String name, Class<Type> type, Object from){
		try{
			Field field = src.getDeclaredField(name);
			field.setAccessible(true);
			
			return type.cast(field.get(from));
		}catch (Exception e){
			e.printStackTrace();
			
			return null;
		}
	}
	
	/**
	 * Sets the value of a field for an object. Note that this will attempt to
	 * override final fields as well, so be careful where it's used. For a static
	 * field the <i>in</i> parameter can be null.
	 * 
	 * @param src		The class where the field is defined.
	 * @param name		The name of the field.
	 * @param in		The object to set the value in.
	 * @param value		The value to set for the field. 
	 */
	public static void setFieldValue(Class<?> src, String name, Object in, Object value){
		try{
			Field field = src.getDeclaredField(name);
			field.setAccessible(true);
			
			if (Modifier.isFinal(field.getModifiers())){
				Field modifiers = Field.class.getDeclaredField("modifiers");
				modifiers.setAccessible(true);
				
				modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
			}
			
			field.set(in, value);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
