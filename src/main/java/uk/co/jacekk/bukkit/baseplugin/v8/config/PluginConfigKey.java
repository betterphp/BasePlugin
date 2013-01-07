package uk.co.jacekk.bukkit.baseplugin.v8.config;

/**
 * Represents a config value for a plugin.
 * 
 * @author Jacek Kuzemczak
 */
public class PluginConfigKey {
	
	private String key;
	private Object defaultValue;
	private boolean dynamic;
	
	public PluginConfigKey(String key, Object defaultValue, boolean dynamic){
		this.key = key;
		this.defaultValue = defaultValue;
		this.dynamic = dynamic;
	}
	
	public PluginConfigKey(String key, Object defaultValue){
		this(key, defaultValue, false);
	}
	
	/**
	 * Gets the key for this config entry.
	 * 
	 * @return	The key.
	 */
	public String getKey(){
		return this.key;
	}
	
	/**
	 * Gets the default value for this config entry.
	 * 
	 * @return The value.
	 */
	public Object getDefault(){
		return this.defaultValue;
	}
	
	/**
	 * Checks if this is a dynamic config key, a dynamic key is one which is not defined in the file by default.
	 * 
	 * @return	True if the key is dynamic.
	 */
	public boolean isDynamic(){
		return this.dynamic;
	}
	
}
