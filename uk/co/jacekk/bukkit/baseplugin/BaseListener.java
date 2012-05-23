package uk.co.jacekk.bukkit.baseplugin;

import org.bukkit.event.Listener;

public abstract class BaseListener<Type> implements Listener {
	
	protected Type plugin;
	
	public BaseListener(Type plugin){
		this.plugin = plugin;
	}
	
}
