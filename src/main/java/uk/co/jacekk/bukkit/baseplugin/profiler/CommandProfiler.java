package uk.co.jacekk.bukkit.baseplugin.profiler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

/**
 * A command wrapper that times execution
 */
public class CommandProfiler extends Command implements PluginIdentifiableCommand {
	
	private transient Plugin plugin;
	private transient Command command;
	
	private long totalCalls;
	private long totalTime;
	private long minTime;
	private long maxTime;
	
	public CommandProfiler(Plugin plugin, Command command){
		super(command.getName(), command.getDescription(), command.getUsage(), command.getAliases());
		
		this.plugin = plugin;
		this.command = command;
		
		this.reset();
	}
	
	public void reset(){
		this.totalCalls = 0L;
		this.totalTime = 0L;
		this.minTime = 0L;
		this.maxTime = 0L;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args){
		long start = System.nanoTime();
		
		boolean result = this.command.execute(sender, label, args);
		
		long time = System.nanoTime() - start;
		
		++this.totalCalls;
		this.totalTime += time;
		this.minTime = (this.minTime == 0L) ? time : Math.min(this.minTime, time);
		this.maxTime = (this.maxTime == 0L) ? time : Math.max(this.maxTime, time);
		
		return result;
	}
	
	@Override
	public Plugin getPlugin(){
		return this.plugin;
	}
	
	/**
	 * Gets the total number of time this method was executed.
	 * 
	 * @return The number.
	 */
	public synchronized long getTotalCalls(){
		return this.totalCalls;
	}
	
	/**
	 * Gets the total time of execution in nanoseconds.
	 * 
	 * @return The time.
	 */
	public synchronized long getTotalTime(){
		return this.totalTime;
	}
	
	/**
	 * Gets the shortest execution time.
	 * 
	 * @return The time.
	 */
	public synchronized long getMinTime(){
		return this.minTime;
	}
	
	/**
	 * Gets the longest execution time.
	 * 
	 * @return The time.
	 */
	public synchronized long getMaxTime(){
		return this.maxTime;
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		
		result.append("[Command][");
		
		result.append("command=");
		result.append(this.command.getName());
		result.append(",");
		
		result.append("totalCalls=");
		result.append(this.getTotalCalls());
		result.append(",");
		
		result.append("totalTime=");
		result.append(this.getTotalTime());
		result.append(",");
		
		result.append("minTime=");
		result.append(this.getMinTime());
		result.append(",");
		
		result.append("maxTime=");
		result.append(this.getMaxTime());
		
		result.append("]");
		
		return result.toString();
	}

}
