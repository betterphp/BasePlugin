package uk.co.jacekk.bukkit.baseplugin.v5.command;

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
 * </ul>
 * 
 * @author Jacek Kuzemczak
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandTabCompletion {
	
	String[] value();
	
}
