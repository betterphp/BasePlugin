package uk.co.jacekk.bukkit.baseplugin.v5.permissions;

import org.bukkit.permissions.Permission;

import uk.co.jacekk.bukkit.baseplugin.v5.BaseObject;
import uk.co.jacekk.bukkit.baseplugin.v5.BasePlugin;

public class PermissionManager extends BaseObject<BasePlugin> {
	
	public PermissionManager(BasePlugin plugin){
		super(plugin);
	}
	
	/**
	 * Registers the default permissions for a plugin
	 * 
	 * @param permissions	An array of {@link PluginPermission}s to register.
	 */
	public void registerPermissions(PluginPermission[] permissions){
		for (PluginPermission permission : permissions){
			plugin.pluginManager.addPermission(new Permission(permission.getNode(), permission.getDescription(), permission.getDefault()));
		}
	}
	
}
