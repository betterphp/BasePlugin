package uk.co.jacekk.bukkit.baseplugin.profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.scheduler.BukkitTask;

import uk.co.jacekk.bukkit.baseplugin.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;

/**
 * Represents the method profiler for a plugin.
 */
public class PluginProfiler extends BaseObject<BasePlugin> {
	
	private ArrayList<TaskProfiler> taskProfilers;
	private ArrayList<EventProfiler> eventProfilers;
	private ArrayList<CommandProfiler> commandProfilers;
	
	private long lastReport;
	
	public PluginProfiler(BasePlugin plugin){
		super(plugin);
		
		this.taskProfilers = new ArrayList<TaskProfiler>();
		this.eventProfilers = new ArrayList<EventProfiler>();
		this.commandProfilers = new ArrayList<CommandProfiler>();
		
		this.lastReport = System.currentTimeMillis();
		
		try{
			for (BukkitTask task : this.plugin.scheduler.getPendingTasks()){
				if (task.getOwner().equals(this.plugin)){
					Class<?> craftTaskClass = Class.forName("org.bukkit.craftbukkit.v1_5_R3.scheduler.CraftTask");
					
					Runnable runnable = ReflectionUtils.getFieldValue(craftTaskClass, "task", Runnable.class, task);
					
					TaskProfiler profiled = new TaskProfiler(runnable);
					
					ReflectionUtils.setFieldValue(craftTaskClass, "task", task, profiled);
					
					this.taskProfilers.add(profiled);
				}
			}
		}catch (Exception e){
			plugin.log.warn("Unable to profile tasks: " + e.getMessage());
		}
		
		try{
			for (RegisteredListener listener : HandlerList.getRegisteredListeners(this.plugin)){
				EventExecutor executor = ReflectionUtils.getFieldValue(RegisteredListener.class, "executor", EventExecutor.class, listener);
				
				EventProfiler profiled = new EventProfiler(executor, listener.getListener());
				
				ReflectionUtils.setFieldValue(RegisteredListener.class, "executor", listener, profiled);
				
				this.eventProfilers.add(profiled);
			}
		}catch (Exception e){
			plugin.log.warn("Unable to profile events: " + e.getMessage());
		}
		
		try{
			SimpleCommandMap commandMap = ReflectionUtils.getFieldValue(SimplePluginManager.class, "commandMap", SimpleCommandMap.class, plugin.pluginManager);
			HashMap<String, Command> knownCommands = ReflectionUtils.getFieldValue(SimpleCommandMap.class, "knownCommands", HashMap.class, commandMap);
			
			for (Entry<String, Command> entry : knownCommands.entrySet()){
				String name = entry.getKey();
				Command command = entry.getValue();
				
				if (command instanceof PluginIdentifiableCommand && ((PluginIdentifiableCommand) command).getPlugin().equals(this.plugin)){
					CommandProfiler profiled = new CommandProfiler(this.plugin, command);
					
					knownCommands.put(name, profiled);
					
					this.commandProfilers.add(profiled);
				}
			}
		}catch (Exception e){
			plugin.log.warn("Unable to profile commands: " + e.getMessage());
		}
	}
	
	/**
	 * Generates a timing report from the collected data.
	 * 
	 * @return The report
	 */
	public String generateReport(){
		StringBuilder report = new StringBuilder();
		
		report.append("[Info][");
		
		report.append("pluginName=");
		report.append(this.plugin.getName());
		report.append(",");
		
		report.append("pluginVersion=");
		report.append(this.plugin.getDescription().getVersion());
		report.append(",");
		
		report.append("duration=");
		report.append(System.currentTimeMillis() - this.lastReport);
		
		report.append("]");
		report.append('\n');
		
		for (TaskProfiler taskProfiler : this.taskProfilers){
			report.append(taskProfiler.toString());
			report.append('\n');
			
			taskProfiler.reset();
		}
		
		for (EventProfiler eventProfiler : this.eventProfilers){
			String result = eventProfiler.toString();
			
			if (result != null){
				report.append(result);
				report.append('\n');
			}
			
			eventProfiler.reset();
		}
		
		for (CommandProfiler commandProfiler : this.commandProfilers){
			report.append(commandProfiler.toString());
			report.append('\n');
			
			commandProfiler.reset();
		}
		
		this.lastReport = System.currentTimeMillis();
		
		return report.toString();
	}
	
}
