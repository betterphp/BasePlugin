package uk.co.jacekk.bukkit.baseplugin.v9_1.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the tab completion format for each argument of this command.
 * 
 * The format of this can be either a | seperated list of possible values for
 * the argument at that position or one of the available special values:
 * <ul>
 *   <li>&lt;online_player&gt; - A player that is currently online.</li>
 *   <li>&lt;player&gt; - A player that has connected at some point.</li>
 *   <li>[methodName] - The name of a method in the same class that provides the list, this method should have the arguments methodName(CommandSender sender, String[] args)</li>
 * </ul>
 * 
 * @author Jacek Kuzemczak
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandTabCompletion {
	
	String[] value();
	
}
