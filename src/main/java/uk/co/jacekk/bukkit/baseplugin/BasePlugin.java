package uk.co.jacekk.bukkit.baseplugin;

import java.io.File;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.jacekk.bukkit.baseplugin.command.CommandManager;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.logging.PluginLogger;
import uk.co.jacekk.bukkit.baseplugin.permissions.PermissionManager;

/**
 * The base class that the main plugin class should extend.
 * 
 * @author Jacek Kuzemczak
 */
public abstract class BasePlugin extends JavaPlugin {
	
	/**
	 * The version of the BasePlugin library
	 */
	public static final String VERSION = "14";
	private static final String PACKAGE_NAME = "14";
	
	/**
	 * The {@link PluginDescriptionFile} for this plugin.
	 */
	public PluginDescriptionFile description;
	
	/**
	 * The {@link PluginLogger} for this plugin.
	 */
	public PluginLogger log;
	
	/**
	 * The plugin's data folder.
	 */
	protected File baseDir;
	
	/**
	 * The absolute path to the plugin's data folder.
	 */
	protected String baseDirPath;
	
	/**
	 * The plugin's configuration this may be null if no config file is defined.
	 */
	public PluginConfig config;
	
	private PermissionManager permissionManager;
	private CommandManager commandManager;
	
	/**
	 * Sets up the default fields for the plugin.
	 * 
	 * @param createFolder	If this is true then the plugin's data folder will be created if it does not exist.
	 */
	public void onEnable(boolean createFolder){
		// This prevents Maven Shade plugin from replacing the package name
		String packageName = new String(new char[]{'u', 'k', '.', 'c', 'o', '.', 'j', 'a', 'c', 'e', 'k', 'k', '.', 'b', 'u', 'k', 'k', 'i', 't', '.', 'b', 'a', 's', 'e', 'p', 'l', 'u', 'g', 'i', 'n'});
		
		if (!BasePlugin.class.getName().equals(packageName + ".v" + PACKAGE_NAME + ".BasePlugin")){
			throw new PackageNameException(BasePlugin.class.getName());
		}
		
		this.description = this.getDescription();
		this.log = new PluginLogger(this);
		
		this.baseDir = this.getDataFolder();
		this.baseDirPath = this.baseDir.getAbsolutePath();
		
		this.permissionManager = new PermissionManager(this);
		this.commandManager = new CommandManager(this);
		
		if (createFolder && !this.baseDir.exists()){
			this.baseDir.mkdirs();
		}
	}
	
	/**
	 * Gets the File for the base directory of the plugin.
	 * 
	 * @return	The File
	 */
	public File getBaseDir(){
		return this.baseDir;
	}
	
	/**
	 * Gets the path to the base directory of the plugin
	 * 
	 * @return	The path (does not end with a slash)
	 */
	public String getBaseDirPath(){
		return this.baseDirPath;
	}
	
	/**
	 * Gets the permission manager for the plugin.
	 * 
	 * @return The permission manager.
	 */
	public PermissionManager getPermissionManager(){
		return this.permissionManager;
	}
	
	/**
	 * Gets the command manager for the plugin.
	 * 
	 * @return The command manager.
	 */
	public CommandManager getCommandManager(){
		return this.commandManager;
	}
	
}
