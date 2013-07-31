package uk.co.jacekk.bukkit.baseplugin.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldSaveEvent;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;

/**
 * A chunk based data storage handler using block locations as keys.
 */
public class BlockDataStore<T extends Serializable> extends BaseListener<BasePlugin> {
	
	private File folder;
	private HashMap<ChunkLocation, HashMap<BlockLocation, T>> data;
	
	/**
	 * @param folder The folder that the data will be stored in.
	 */
	public BlockDataStore(BasePlugin plugin, File folder){
		super(plugin);
		
		if (!folder.exists()){
			folder.mkdirs();
		}
		
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	/**
	 * Gets a data value from a world location.
	 * 
	 * @param worldUID The UUID of the world
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @return The data value or null if none was found
	 */
	public T get(UUID worldUID, int x, int y, int z){
		int chunkX = x / 16;
		int chunkZ = z / 16;
		
		for (Entry<ChunkLocation, HashMap<BlockLocation, T>> entry : this.data.entrySet()){
			ChunkLocation chunkLocation = entry.getKey();
			
			if (chunkLocation.getWorldUID().equals(worldUID) && chunkLocation.getX() == chunkX && chunkLocation.getZ() == chunkZ){
				for (Entry<BlockLocation, T> dataEntry : entry.getValue().entrySet()){
					BlockLocation blockLocation = dataEntry.getKey();
					
					if (blockLocation.getX() == x && blockLocation.getY() == y && blockLocation.getZ() == z){
						return dataEntry.getValue();
					}
				}
				
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets a data value from a block.
	 * 
	 * @param block The block
	 * @return The data
	 */
	public T get(Block block){
		return this.get(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
	}
	
	/**
	 * Gets all of the values stored in a loaded chunk.
	 * 
	 * @param chunkLocation The location of the chunk
	 * @return The data
	 */
	public HashMap<BlockLocation, T> getAll(ChunkLocation chunkLocation){
		HashMap<BlockLocation, T> results = new HashMap<BlockLocation, T>();
		
		if (this.data.containsKey(chunkLocation)){
			results.putAll(this.data.get(chunkLocation));
		}
		
		return results;
	}
	
	/**
	 * Gets all of the values stored in the loaded chunks in a world.
	 * 
	 * @param world The world
	 * @return The data
	 */
	public HashMap<BlockLocation, T> getAll(World world){
		HashMap<BlockLocation, T> results = new HashMap<BlockLocation, T>();
		
		for (Entry<ChunkLocation, HashMap<BlockLocation, T>> entry : this.data.entrySet()){
			if (entry.getKey().getWorldUID().equals(world.getUID())){
				results.putAll(entry.getValue());
			}
		}
		
		return results;
	}
	
	/**
	 * Gets all of the data in loaded chunks.
	 * 
	 * @return The data
	 */
	public HashMap<BlockLocation, T> getAll(){
		HashMap<BlockLocation, T> results = new HashMap<BlockLocation, T>();
		
		for (HashMap<BlockLocation, T> entries : this.data.values()){
			results.putAll(entries);
		}
		
		return results;
	}
	
	/**
	 * Sets the data value for a world location.
	 * 
	 * @param worldUID The UUID of the world
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 * @param data The data value
	 */
	public void set(UUID worldUID, int x, int y, int z, T data){
		ChunkLocation chunkLocation = new ChunkLocation(worldUID, x / 16, z / 16);
		BlockLocation blockLocation = new BlockLocation(worldUID, x, y, z);
		
		HashMap<BlockLocation, T> chunkStore = this.data.get(chunkLocation);
		
		if (chunkStore == null){
			chunkStore = new HashMap<BlockLocation, T>();
			this.data.put(chunkLocation, chunkStore);
		}
		
		chunkStore.put(blockLocation, data);
	}
	
	/**
	 * Sets the data value for a block.
	 * 
	 * @param block The block
	 * @param data The data
	 */
	public void set(Block block, T data){
		this.set(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ(), data);
	}
	
	private File getChunkFile(ChunkLocation location){
		return new File(this.folder, location.getWorldUID().toString() + "_" + location.getX() + "_" + location.getZ());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onChunkLoad(ChunkLoadEvent event){
		Chunk chunk = event.getChunk();
		ChunkLocation chunkLocation = new ChunkLocation(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
		File chunkFile = this.getChunkFile(chunkLocation);
		
		if (chunkFile.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(chunkFile));
				
				this.data.put(chunkLocation, (HashMap<BlockLocation, T>) input.readObject());
				
				input.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	private void onChunkUnload(ChunkUnloadEvent event){
		Chunk chunk = event.getChunk();
		ChunkLocation chunkLocation = new ChunkLocation(chunk.getWorld().getUID(), chunk.getX(), chunk.getZ());
		HashMap<BlockLocation, T> chunkData = this.data.get(chunkLocation);
		
		if (chunkData != null){
			File chunkFile = this.getChunkFile(chunkLocation);
			
			try{
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(chunkFile));
				
				output.writeObject(chunkData);
				output.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		this.data.remove(chunkLocation);
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorldSave(WorldSaveEvent event){
		for (Entry<ChunkLocation, HashMap<BlockLocation, T>> entry : this.data.entrySet()){
			ChunkLocation chunkLocation = entry.getKey();
			File chunkFile = this.getChunkFile(chunkLocation);
			
			try{
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(chunkFile));
				
				output.writeObject(entry.getValue());
				output.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
