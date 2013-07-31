package uk.co.jacekk.bukkit.baseplugin.storage;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents the location of a Chunk on the server
 */
public class ChunkLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID worldUID;
	private int x;
	private int z;
	
	/**
	 * @param worldUID the UUID of the world.
	 * @param x The x coordinate
	 * @param z The z coordinate
	 */
	public ChunkLocation(UUID worldUID, int x, int z){
		this.worldUID = worldUID;
		this.x = x;
		this.z = z;
	}
	
	@Override
	public int hashCode(){
		int result = 19;
		
		result = 37 * result + this.x;
		result = 37 * result + this.z;
		
		return result;
	}
	
	@Override
	public boolean equals(Object compare){
		if (this == compare){
			return true;
		}
		
		if (compare instanceof ChunkLocation){
			return false;
		}
		
		ChunkLocation location = (ChunkLocation) compare;
		
		return (this.worldUID.equals(location.worldUID) && this.x == location.x && this.z == location.z);
	}
	
	/**
	 * Gets the UUId of the world this chunk is in.
	 * 
	 * @return The UUID
	 */
	public UUID getWorldUID(){
		return this.worldUID;
	}
	
	/**
	 * Gets the X coordinate of the chunk.
	 * 
	 * @return The coordinate.
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Gets the Z coordinate of the chunk.
	 * 
	 * @return The coordinate.
	 */
	public int getZ(){
		return this.z;
	}
	
}
