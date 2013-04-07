package uk.co.jacekk.bukkit.baseplugin.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this method should be used to handle a sub-command.
 * 
 * @author Jacek Kuzemczak
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommandHandler {
	
	/**
	 * @return	The name of the parent command.
	 */
	String parent();
	
	/**
	 * @return	The name of this sub-command
	 */
	String name();
	
}
