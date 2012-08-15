package uk.co.jacekk.bukkit.baseplugin;

import org.bukkit.command.CommandExecutor;

public abstract class BaseCommandExecutor<Type> implements CommandExecutor {
	
	protected Type plugin;
	
	public BaseCommandExecutor(Type plugin){
		this.plugin = plugin;
	}
	
}
