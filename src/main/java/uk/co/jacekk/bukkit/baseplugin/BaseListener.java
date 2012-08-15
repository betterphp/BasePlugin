package uk.co.jacekk.bukkit.baseplugin;

import org.bukkit.event.Listener;

/**
 * The base class that all listeners should extend.
 * 
 * @author Jacek Kuzemczak
 *
 * @param <Type>	The plugin that this listener belongs to.
 */
public abstract class BaseListener<Type> implements Listener {
	
	/**
	 * The plugin that created this listener.
	 */
	protected Type plugin;
	
	/**
	 * @param plugin	The plugin that this listener belongs to.
	 */
	public BaseListener(Type plugin){
		this.plugin = plugin;
	}
	
}
