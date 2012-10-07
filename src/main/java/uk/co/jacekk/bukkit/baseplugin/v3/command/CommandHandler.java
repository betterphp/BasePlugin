package uk.co.jacekk.bukkit.baseplugin.v3.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this method should be used to handle a command.
 * 
 * @author Jacek Kuzemczak
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler {
	
	/**
	 * @return	The names that can be used for this command.
	 */
	String[] names();
	
	/**
	 * @return	The long description of this command.
	 */
	String description();
	
	/**
	 * @return	The usage information for this command.
	 */
	String usage() default "";
	
}
