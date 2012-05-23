package uk.co.jacekk.bukkit.baseplugin;

import org.bukkit.command.CommandExecutor;

public abstract class BaseCommandExecutor implements CommandExecutor {
	
	protected BasePlugin plugin;
	
	public BaseCommandExecutor(BasePlugin plugin){
		this.plugin = plugin;
	}
	
}
