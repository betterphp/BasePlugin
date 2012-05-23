package uk.co.jacekk.bukkit.baseplugin.util;

import java.util.LinkedHashMap;

public interface PluginConfigKey {
	
	public LinkedHashMap<String, Object> getAll();
	
	public String getKey();
	
	public Object getDefault();
	
}
