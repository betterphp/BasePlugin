package uk.co.jacekk.bukkit.baseplugin.command;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.SimplePluginManager;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;

/**
 * Used to manage the commands for this plugin.
 * 
 * @author Jacek Kuzemczak
 */
public class CommandManager {
	
	private BasePlugin plugin;
	private CommandMap commandMap;
	
	/**
	 * @param plugin	The plugin that this manager is for.
	 */
	public CommandManager(BasePlugin plugin){
		this.plugin = plugin;
		
		try{
			this.commandMap = ReflectionUtils.getFieldValue(SimplePluginManager.class, "commandMap", CommandMap.class, plugin.pluginManager);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private boolean registerCommand(PluginCommand command){
		return this.commandMap.register(plugin.description.getName(), command);
	}
	
	/**
	 * Registers a new command executor.
	 * 
	 * @param executor	The command executor to register.
	 * @throws CommandRegistrationException if the registration fails
	 */
	public void registerCommandExecutor(BaseCommandExecutor<? extends BasePlugin> executor){
		Class<BaseCommandExecutor<? extends BasePlugin>> cls = (Class<BaseCommandExecutor<? extends BasePlugin>>) executor.getClass();
		
		for (Method method : cls.getDeclaredMethods()){
			CommandHandler commandInfo = method.getAnnotation(CommandHandler.class);
			CommandTabCompletion tabInfo = method.getAnnotation(CommandTabCompletion.class);
			
			if (commandInfo != null){
				if (!method.getReturnType().equals(Void.TYPE)){
					throw new CommandRegistrationException("Incorrect return type for command method " + method.getName() + " in " + cls.getName());
				}else if (!Arrays.equals(method.getParameterTypes(), new Class<?>[]{CommandSender.class, String.class, String[].class})){
					throw new CommandRegistrationException("Incorrect arguments for command method " + method.getName() + " in " + cls.getName());
				}else{
					PluginCommand command = new PluginCommand(plugin, executor, method, commandInfo.names(), commandInfo.description(), commandInfo.usage(), ((tabInfo == null) ? new String[0] : tabInfo.value()));
					
					if (!this.registerCommand(command)){
						throw new CommandRegistrationException("Failed to register command for method " + method.getName() + " in " + cls.getName());
					}
				}
			}
		}
		
		for (Method method : cls.getDeclaredMethods()){
			SubCommandHandler subCommandInfo = method.getAnnotation(SubCommandHandler.class);
			CommandTabCompletion tabInfo = method.getAnnotation(CommandTabCompletion.class);
			
			if (subCommandInfo != null){
				if (!method.getReturnType().equals(Void.TYPE)){
					throw new CommandRegistrationException("Incorrect return type for command method " + method.getName() + " in " + cls.getName());
				}else if (!Arrays.equals(method.getParameterTypes(), new Class<?>[]{CommandSender.class, String.class, String[].class})){
					throw new CommandRegistrationException("Incorrect arguments for command method " + method.getName() + " in " + cls.getName());
				}else{
					PluginCommand parent = (PluginCommand) this.commandMap.getCommand(subCommandInfo.parent());
					
					if (parent == null){
						throw new CommandRegistrationException("Attempted to register sub-command of " + subCommandInfo.parent() + " before main handler.");
					}
					
					parent.registerSubCommandHandler(subCommandInfo.name(), new PluginSubCommand(executor, method, ((tabInfo == null) ? new String[0] : tabInfo.value())));
				}
			}
		}
	}
	
}
