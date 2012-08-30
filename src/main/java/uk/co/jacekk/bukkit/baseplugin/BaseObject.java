package uk.co.jacekk.bukkit.baseplugin;

public abstract class BaseObject<Type> {
	
	/**
	 * The plugin that created this object.
	 */
	protected Type plugin;
	
	public BaseObject(Type plugin){
		this.plugin = plugin;
	}
	
}
