package uk.co.jacekk.bukkit.baseplugin.v3.command;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import uk.co.jacekk.bukkit.baseplugin.v3.BasePlugin;

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
	private String[] tabCompletion;
	
	public PluginCommand(BasePlugin plugin, BaseCommandExecutor<? extends BasePlugin> handler, Method handlerMethod, String[] names, String description, String usage, String[] tabCompletion){
		super(names[0], description, "/<command> " + usage, Arrays.asList(names));
		
		this.plugin = plugin;
		this.handler = handler;
		this.handlerMethod = handlerMethod;
		this.tabCompletion = tabCompletion;
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
	
//TODO: @Override
	public List<String> tabComplete(CommandSender sender, String message){
		LinkedHashSet<String> completions = new LinkedHashSet<String>();
		
		if (message.contains(" ")){
			String[] args = message.split(" ");
			boolean empty = message.endsWith(" ");
			
			if (args.length <= this.tabCompletion.length){
				String tab = this.tabCompletion[args.length - ((empty) ? 1 : 2)];
				String last = args[args.length - 1].toLowerCase();
				
				if (tab.equalsIgnoreCase("<online_player>")){
					for (Player player : plugin.server.getOnlinePlayers()){
				 		String playerName = player.getName();
				 		String testName = playerName.toLowerCase();
				 		
				 		if (empty || testName.startsWith(last)){
				 			completions.add(playerName);
				 		}
					}
				}else if (tab.equalsIgnoreCase("<player>")){
					for (OfflinePlayer player : plugin.server.getOfflinePlayers()){
				 		String playerName = player.getName();
				 		String testName = playerName.toLowerCase();
				 		
				 		if (empty || testName.startsWith(last)){
				 			completions.add(playerName);
				 		}
					}
				}else{
					for (String value : tab.split("\\|")){
						String testValue = value.toLowerCase();
						
						if (empty || value.startsWith(testValue)){
							completions.add(value);
						}
					}
				}
			}
		}
		
		return Arrays.asList((String[]) completions.toArray());
	}
	
}
