package uk.co.jacekk.bukkit.baseplugin.v9.command;

import java.lang.reflect.Method;

import uk.co.jacekk.bukkit.baseplugin.v9.BasePlugin;

public class PluginSubCommand {
	
	protected BaseCommandExecutor<? extends BasePlugin> handler;
	protected Method handlerMethod;
	protected String[] tabCompletion;
	
	public PluginSubCommand(BaseCommandExecutor<? extends BasePlugin> handler, Method handlerMethod, String[] tabCompletion){
		this.handler = handler;
		this.handlerMethod = handlerMethod;
		this.tabCompletion = tabCompletion;
	}
	
}
