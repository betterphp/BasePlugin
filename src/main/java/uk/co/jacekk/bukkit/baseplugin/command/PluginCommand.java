package uk.co.jacekk.bukkit.baseplugin.command;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;

/**
 * used internally to call the method that should handle a command, this would
 * normally call the onCommand() method.
 * 
 * @author Jacek Kuzemczak
 */
public class PluginCommand extends Command implements PluginIdentifiableCommand {
	
	private BasePlugin plugin;
	private BaseCommandExecutor<? extends BasePlugin> handler;
	private Method handlerMethod;
	
	public PluginCommand(BasePlugin plugin, BaseCommandExecutor<? extends BasePlugin> handler, Method handlerMethod, String[] names, String description, String usage){
		super(names[0], description, usage, Arrays.asList(names));
		
		this.plugin = plugin;
		this.handler = handler;
		this.handlerMethod = handlerMethod;
	}
	
	@Override
	public Plugin getPlugin(){
		return this.plugin;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args){
		try{
			this.handlerMethod.invoke(this.handler, sender, label, args);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
}
