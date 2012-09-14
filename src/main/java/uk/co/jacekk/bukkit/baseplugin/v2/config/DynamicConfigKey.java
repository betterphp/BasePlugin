package uk.co.jacekk.bukkit.baseplugin.v2.config;


public class DynamicConfigKey implements PluginConfigKey {
	
	private String key;
	private Object defaultValue;
	
	public DynamicConfigKey(String key, Object defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public Object getDefault(){
		return this.defaultValue;
	}
	
}
