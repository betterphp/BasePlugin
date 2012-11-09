package uk.co.jacekk.bukkit.baseplugin.v5.event;

import org.bukkit.event.Listener;

import uk.co.jacekk.bukkit.baseplugin.v5.BaseObject;

/**
 * The base class that all listeners should extend.
 * 
 * @author Jacek Kuzemczak
 *
 * @param <Type>	The plugin that this listener belongs to.
 */
public abstract class BaseListener<Type> extends BaseObject<Type> implements Listener {
	
	/**
	 * @param plugin	The plugin that this listener belongs to.
	 */
	public BaseListener(Type plugin){
		super(plugin);
	}
	
}
