package uk.co.jacekk.bukkit.baseplugin.v2.permissions;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

/**
 * Represents a single permission that is used by the plugin.
 * 
 * @author Jacek Kuzemczak
 */
public interface PluginPermission {
	
	/**
	 * Gets the node for this permission.
	 * 
	 * @return	The node.
	 */
	public String getNode();
	
	/**
	 * Gets the {@link PermissionDefault} for this permission.
	 * 
	 * @return The default.
	 */
	public PermissionDefault getDefault();
	
	/**
	 * Gets a short description of what this permission allows.
	 * 
	 * @return The description.
	 */
	public String getDescription();
	
	/**
	 * Gets a list of all players on the server with this permission.
	 * 
	 * @return The list of players.
	 */
	public List<Player> getPlayersWith();
	
	/**
	 * Checks to see if a {@link CommandSender} has this permission.
	 * 
	 * @param sender	The sender to be checked, often a {@link Player}
	 * @return			True if the sender has this permission false if not.
	 */
	public boolean has(CommandSender sender);
	
}
