package uk.co.jacekk.bukkit.baseplugin.v2.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import uk.co.jacekk.bukkit.baseplugin.v2.logging.PluginLogger;

/**
 * The configuration of a plugin, this represents the config.yml (or other name) file.
 * 
 * @author Jacek Kuzemczak
 */
public class PluginConfig {
	
	private File configFile;
	private YamlConfiguration config;
	private PluginConfigKey[] configDefaults;
	
	/**
	 * Creates a new config object that can be used to fetch the value of a {@link PluginConfigKey}
	 * 
	 * @param configFile		The file to be used to store this configuration (usually config.yml).
	 * @param configDefaults	The default configuration options to be used if an entry cannot be found.
	 * @param log				The {@link PluginLogger} to be used for any messages.
	 */
	public PluginConfig(File configFile, PluginConfigKey[] configDefaults, PluginLogger log){
		this.configFile = configFile;
		this.config = new YamlConfiguration();
		this.configDefaults = configDefaults;
		
		if (configFile.exists()){
			this.reload();
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
	
	/**
	 * Reloads the values in this config from the file.
	 */
	public void reload(){
		try {
			this.config.load(this.configFile);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks to see if this config contains the given key.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to be checked.
	 * @return				True if the key was found false if not.
	 */
	public boolean containsKey(PluginConfigKey configKey){
		return ArrayUtils.contains(this.configDefaults, configKey);
	}
	
	/**
	 * Gets an integer value of the given key.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The integer value or 0 if the key does not exist.
	 */
	public int getInt(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return 0;
		}
		
		return this.config.getInt(configKey.getKey(), (Integer) configKey.getDefault());
	}
	
	/**
	 * Gets a list of integer values.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The list of integer values or an empty list if the key does no exist.
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getIntList(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return new ArrayList<Integer>();
		}
		
		if (!this.config.contains(configKey.getKey())){
			return (List<Integer>) configKey.getDefault();
		}
		
		return this.config.getIntegerList(configKey.getKey());
	}
	
	/**
	 * Gets a double value of the given key.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The double value or 0.0 if the key does not exist.
	 */
	public double getDouble(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return 0.0D;
		}
		
		return this.config.getDouble(configKey.getKey(), (Double) configKey.getDefault());
	}
	
	/**
	 * Gets a list of double values.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The list of double values or an empty list if the key does no exist.
	 */
	@SuppressWarnings("unchecked")
	public List<Double> getDoubleList(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return new ArrayList<Double>();
		}
		
		if (!this.config.contains(configKey.getKey())){
			return (List<Double>) configKey.getDefault();
		}
		
		return this.config.getDoubleList(configKey.getKey());
	}
	
	/**
	 * Gets a long value of the given key.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The long value or 0 if the key does not exist.
	 */
	public long getLong(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return 0L;
		}
		
		return this.config.getLong(configKey.getKey(), (Long) configKey.getDefault());
	}
	
	/**
	 * Gets a list of long values.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The list of long values or an empty list if the key does no exist.
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getLongList(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return new ArrayList<Long>();
		}
		
		if (!this.config.contains(configKey.getKey())){
			return (List<Long>) configKey.getDefault();
		}
		
		return this.config.getLongList(configKey.getKey());
	}
	
	/**
	 * Gets a boolean value of the given key.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The value or false if the key does not exist.
	 */
	public boolean getBoolean(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return false;
		}
		
		return this.config.getBoolean(configKey.getKey(), (Boolean) configKey.getDefault());
	}
	
	/**
	 * Gets a string value of the given key.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The value or an empty string if the key does not exist.
	 */
	public String getString(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return "";
		}
		
		return this.config.getString(configKey.getKey(), (String) configKey.getDefault());
	}
	
	/**
	 * Gets a list of string values.
	 * 
	 * @param configKey		The {@link PluginConfigKey} to get the value of.
	 * @return				The list of string values or an empty list if the key does no exist.
	 */
	@SuppressWarnings("unchecked")
	public List<String> getStringList(PluginConfigKey configKey){
		if (!(configKey instanceof DynamicConfigKey) && !this.containsKey(configKey)){
			return new ArrayList<String>();
		}
		
		if (!this.config.contains(configKey.getKey())){
			return (List<String>) configKey.getDefault();
		}
		
		return this.config.getStringList(configKey.getKey());
	}
	
}