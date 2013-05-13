package uk.co.jacekk.bukkit.baseplugin;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import uk.co.jacekk.bukkit.baseplugin.command.CommandManager;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.logging.PluginLogger;
import uk.co.jacekk.bukkit.baseplugin.permissions.PermissionManager;
import uk.co.jacekk.bukkit.baseplugin.profiler.PluginProfiler;
import uk.co.jacekk.bukkit.baseplugin.profiler.ProfilerReportThread;

/**
 * The base class that the main plugin class should extend.
 * 
 * @author Jacek Kuzemczak
 */
public abstract class BasePlugin extends JavaPlugin {
	
	/**
	 * The version of the BasePlugin library
	 */
	public static final String VERSION = "11.0";
	private static final String PACKAGE_NAME = "11_0";
	
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
	
	public Server server;
	public PluginManager pluginManager;
	public PermissionManager permissionManager;
	public CommandManager commandManager;
	public BukkitScheduler scheduler;
	
	/**
	 * The plugin's configuration this may be null if no config file is defined.
	 */
	public PluginConfig config;
	
	/**
	 * The display name of the plugin.
	 */
	public String displayName;
	
	/**
	 * The method profiler.
	 */
	private PluginProfiler profiler;
	
	/**
	 * Sets up the default fields for the plugin.
	 * 
	 * @param createFolder	If this is true then the plugin's data folder will be created if it does not exist.
	 * @param minVersion	The minimum version of the BasePlugin library that is required.
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
		
		this.server = this.getServer();
		this.pluginManager = this.server.getPluginManager();
		this.permissionManager = new PermissionManager(this);
		this.commandManager = new CommandManager(this);
		this.scheduler = this.server.getScheduler();
		
		if (createFolder && !this.baseDir.exists()){
			this.baseDir.mkdirs();
		}
		
		this.displayName = this.description.getName();
	}
	
	/**
	 * Formats a message for the plugin.
	 * 
	 * @param message	The message to be formatted.
	 * @param colour	If set to true the prefix is coloured blue.
	 * @param version	If set to true the version is included in the prefix.
	 * @return			The formatted message.
	 */
	public String formatMessage(String message, boolean colour, boolean version){
		StringBuilder line = new StringBuilder();
		
		if (colour){
			line.append(ChatColor.BLUE);
		}
		
		line.append("[");
		line.append(this.getDisplayName());
		
		if (version){
			line.append(" v");
			line.append(this.description.getVersion());
		}
		
		line.append("] ");
		line.append(ChatColor.RESET);
		line.append(message);
		
		return line.toString();
	}
	
	/**
	 * Formats a message for the plugin.
	 * <p />
	 * NOTE: The version number is included in the prefix if  colour is disabled.
	 * 
	 * @param message	The message to be formatted.
	 * @param colour	If set to true the prefix is coloured blue.
	 * @return			The formatted message.
	 */
	public String formatMessage(String message, boolean colour){
		return this.formatMessage(message, colour, !colour);
	}
	
	/**
	 * Formats a message for the plugin.
	 * <p />
	 * NOTE: Colour is enabled and the version number is not in the prefix.
	 * 
	 * @param message	The message to be formatted.
	 * @return			The formatted message.
	 */
	public String formatMessage(String message){
		return this.formatMessage(message, true, false);
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
	 * Gets the plugin's display name, this defaults to the name and is
	 * used for the formatMessage() method.
	 * 
	 * @return The display name.
	 */
	public String getDisplayName(){
		return this.displayName;
	}
	
	/**
	 * Sets the plugin's display name, this defaults to the name and is
	 * used for the formatMessage() method.
	 * 
	 * @param displayName The display name.
	 */
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
	
	/**
	 * Enables the method profiler for this plugin.
	 */
	protected void enableProfiling(){
		if (this.profiler != null){
			return;
		}
		
		this.profiler = new PluginProfiler(this);
		
		(new ProfilerReportThread(this.profiler)).start();
	}
	
}
