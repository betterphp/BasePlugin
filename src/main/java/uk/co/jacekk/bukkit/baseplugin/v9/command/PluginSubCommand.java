package uk.co.jacekk.bukkit.baseplugin.v9.command;

import java.lang.reflect.Method;

import org.bukkit.command.CommandSender;

import uk.co.jacekk.bukkit.baseplugin.v9.BasePlugin;

public class PluginSubCommand {
	
	private BaseCommandExecutor<? extends BasePlugin> handler;
	private Method handlerMethod;
	
	public PluginSubCommand(BaseCommandExecutor<? extends BasePlugin> handler, Method handlerMethod){
		this.handler = handler;
		this.handlerMethod = handlerMethod;
	}
	
	public boolean execute(CommandSender sender, String label, String[] args){
		try{
			this.handlerMethod.invoke(this.handler, sender, label, args);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
}
