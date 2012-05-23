package uk.co.jacekk.bukkit.baseplugin;

import org.bukkit.event.Listener;

public abstract class BaseListener implements Listener {
	
	protected BasePlugin plugin;
	
	public BaseListener(BasePlugin plugin){
		this.plugin = plugin;
	}
	
}
