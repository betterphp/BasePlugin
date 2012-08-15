package uk.co.jacekk.bukkit.baseplugin;

/**
 * The base class that any repeating or scheduled tasks should extend.
 * 
 * @author Jacek Kuzemczak
 *
 * @param <Type>	The plugin that this task belongs to.
 */
public abstract class BaseTask<Type> implements Runnable {
	
	/**
	 * The plugin that created this task.
	 */
	protected Type plugin;
	
	/**
	 * @param plugin 	The plugin that this task belongs to.
	 */
	public BaseTask(Type plugin){
		this.plugin = plugin;
	}
	
}
