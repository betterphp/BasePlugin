package uk.co.jacekk.bukkit.baseplugin;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.jacekk.bukkit.baseplugin.util.PluginLogger;

public abstract class BasePlugin extends JavaPlugin {
	
	protected PluginDescriptionFile description;
	
	protected PluginLogger log;
	
	protected File baseDir;
	protected String baseDirPath;
	
	public void onEnable(boolean createFolder){
		this.description = this.getDescription();
		
		this.log = new PluginLogger(this);
		
		this.baseDir = this.getDataFolder();
		this.baseDirPath = this.baseDir.getAbsolutePath();
		
		if (createFolder && !this.baseDir.exists()){
			this.baseDir.mkdirs();
		}
	}
	
	public String formatMessage(String message, boolean colour, boolean version){
		StringBuilder line = new StringBuilder();
		
		if (colour){
			line.append(ChatColor.BLUE);
		}
		
		line.append("[");
		line.append(this.description.getName());
		
		if (version){
			line.append(" v");
			line.append(this.description.getVersion());
		}
		
		line.append("] ");
		line.append(message);
		
		return line.toString();
	}
	
	public String formatMessage(String message, boolean colour){
		return this.formatMessage(message, colour, !colour);
	}
	
	public String formatMessage(String message){
		return this.formatMessage(message, true, false);
	}
	
}
