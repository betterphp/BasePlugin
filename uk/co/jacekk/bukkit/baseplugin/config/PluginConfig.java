package uk.co.jacekk.bukkit.baseplugin.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import uk.co.jacekk.bukkit.baseplugin.util.PluginLogger;

public class PluginConfig {
	
	private YamlConfiguration config;
	private PluginConfigKey[] configDefaults;
	
	public PluginConfig(File configFile, PluginConfigKey[] configDefaults, PluginLogger log){
		this.config = new YamlConfiguration();
		this.configDefaults = configDefaults;
		
		if (configFile.exists()){
			try {
				this.config.load(configFile);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		boolean updateNeeded = false;
		
		for (PluginConfigKey entry : this.configDefaults){
			String key = entry.getKey();
			
			if (this.config.contains(key) == false){
				this.config.set(key, entry.getDefault());
				
				updateNeeded = true;
			}
		}
		
		if (updateNeeded){
			try {
				this.config.save(configFile);
				log.info("The " + configFile.getName() + " file has been updated.");
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean containsKey(PluginConfigKey configKey){
		return ArrayUtils.contains(this.configDefaults, configKey);
	}
	
	public int getInt(PluginConfigKey configKey){
		if (!this.containsKey(configKey)){
			return 0;
		}
		
		return this.config.getInt(configKey.getKey(), (Integer) configKey.getDefault());
	}
	
	public long getLong(PluginConfigKey configKey){
		if (!this.containsKey(configKey)){
			return 0L;
		}
		
		return this.config.getLong(configKey.getKey(), (Long) configKey.getDefault());
	}
	
	public boolean getBoolean(PluginConfigKey configKey){
		if (!this.containsKey(configKey)){
			return false;
		}
		
		return this.config.getBoolean(configKey.getKey(), (Boolean) configKey.getDefault());
	}
	
	public String getString(PluginConfigKey configKey){
		if (!this.containsKey(configKey)){
			return "";
		}
		
		return this.config.getString(configKey.getKey(), (String) configKey.getDefault());
	}
	
}