package uk.co.jacekk.bukkit.baseplugin.v2.config;

/**
 * Represents a config file entry that may not have been in the initial config entry list.
 * 
 * @author Jacek Kuzemczak
 */
public class DynamicConfigKey implements PluginConfigKey {
	
	private String key;
	private Object defaultValue;
	
	/**
	 * Creates a new DynamicConfigKey
	 * 
	 * @param key			The path in the config file that points to this value.
	 * @param defaultValue	The default value for this entry.
	 */
	public DynamicConfigKey(String key, Object defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	/**
	 * Gets the key for this config entry.
	 */
	public String getKey(){
		return this.key;
	}
	
	/**
	 * Gets the default value for this config entry.
	 */
	public Object getDefault(){
		return this.defaultValue;
	}
	
}
