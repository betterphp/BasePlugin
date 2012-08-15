package uk.co.jacekk.bukkit.baseplugin;

import org.bukkit.command.CommandExecutor;

/**
 * The base class that all command executors should extend.
 * 
 * @author Jacek Kuzemczak
 *
 * @param <Type>	The plugin that this executor belongs to.
 */
public abstract class BaseCommandExecutor<Type> implements CommandExecutor {
	
	/**
	 * The plugin that this command belongs to.
	 */
	protected Type plugin;
	
	/**
	 * @param plugin	The plugin that this command belongs to.
	 */
	public BaseCommandExecutor(Type plugin){
		this.plugin = plugin;
	}
	
}
