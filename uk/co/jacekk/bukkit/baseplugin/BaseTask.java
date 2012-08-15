package uk.co.jacekk.bukkit.baseplugin;

public abstract class BaseTask<Type> implements Runnable {
	
	protected Type plugin;
	
	public BaseTask(Type plugin){
		this.plugin = plugin;
	}
	
}
