package uk.co.jacekk.bukkit.baseplugin.command;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.SimplePluginManager;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;

public class CommandManager {
	
	private BasePlugin plugin;
	
	public CommandManager(BasePlugin plugin){
		this.plugin = plugin;
	}
	
	private CommandMap getCommandMap(){
		try{
			Field commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
			commandMap.setAccessible(true);
			
			return (CommandMap) commandMap.get(plugin.pluginManager); 
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private boolean registerCommand(PluginCommand command){
		CommandMap commandMap = this.getCommandMap();
		
		if (commandMap == null){
			return false;
		}
		
		return commandMap.register(plugin.description.getName(), command);
	}
	
	public void registerCommandExecutor(BaseCommandExecutor<? extends BasePlugin> executor){
		@SuppressWarnings("unchecked")
		Class<BaseCommandExecutor<? extends BasePlugin>> cls = (Class<BaseCommandExecutor<? extends BasePlugin>>) executor.getClass();
		
		for (Method method : cls.getMethods()){
			CommandHandler commandInfo = method.getAnnotation(CommandHandler.class);
			
			if (commandInfo != null){
				if (!method.getReturnType().equals(Void.TYPE)){
					plugin.log.fatal("Incorrect return type for command method " + method.getName() + " in " + cls.getName());
				}else if (!Arrays.equals(method.getParameterTypes(), new Class<?>[]{CommandSender.class, String.class, String[].class})){
					plugin.log.fatal("Incorrect arguments for command method " + method.getName() + " in " + cls.getName());
				}else{
					PluginCommand command = new PluginCommand(plugin, executor, method, commandInfo.names(), commandInfo.description(), commandInfo.usage());
					
					if (!this.registerCommand(command)){
						plugin.log.fatal("Failed to register command for method " + method.getName() + " in " + cls.getName());
					}
				}
			}
		}
	}
	
}
