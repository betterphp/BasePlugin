package uk.co.jacekk.bukkit.baseplugin.command;

/**
 * The base class that all command executors should extend. 
 * 
 * @author Jacek Kuzemczak
 *
 * @param <Type>	The plugin that this executor belongs to.
 */
public abstract class BaseCommandExecutor<Type> {
	
	/**
	 * The plugin that created this listener.
	 */
	protected Type plugin;
	
	/**
	 * @param plugin	The plugin that this executor belongs to.
	 */
	public BaseCommandExecutor(Type plugin){
		this.plugin = plugin;
	}
	
}
