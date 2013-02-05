package uk.co.jacekk.bukkit.baseplugin.v9.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A key-value pair list that is saved to a file.
 * 
 * @author Jacek Kuzemczak
 */
public class DataStore {
	
	private File storageFile;
	
	private HashMap<String, String> data;
	private boolean caseSensitive;
	
	/**
	 * @param file				The file where the list will be stored.
	 * @param caseSensitive		If set to false all comparison will be done in lower-case.
	 */
	public DataStore(File file, boolean caseSensitive){
		this.storageFile = file;
		
		this.data = new HashMap<String, String>();
		this.caseSensitive = caseSensitive;
		
		if (this.storageFile.exists() == false){
			try{
				this.storageFile.createNewFile();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads the current lust entries from the file.
	 */
	public void load(){
		try{
			DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			String line, key, value;
			String info[];
			
			while ((line = reader.readLine()) != null){
				info = line.split(":", 2);
				key = (this.caseSensitive) ? info[0] : info[0].toLowerCase();
				value = info[1];
				
				if (this.data.containsKey(key) == false){
					this.data.put(key, value);
				}
			}
			
			reader.close();
			input.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the list entries to the file.
	 */
	public void save(){
		try{
			FileWriter stream = new FileWriter(this.storageFile);
			BufferedWriter out = new BufferedWriter(stream);
			
			for (Entry<String, String> entry : this.data.entrySet()){
				out.write(entry.getKey() + ":" + entry.getValue());
				out.newLine();
			}
			
			out.close();
			stream.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Check to see if the list contains a key.
	 * 
	 * @param key	The key to check for.
	 * @return		True of the key exists false if not.
	 */
	public boolean contains(String key){
		return this.data.containsKey((this.caseSensitive) ? key : key.toLowerCase());
	}
	
	/**
	 * Gets all of the entries in the list.
	 * 
	 * @return	The entries.
	 */
	public Set<Entry<String, String>> getAll(){
		return this.data.entrySet();
	}
	
	/**
	 * Gets all of the keys in the list.
	 * 
	 * @return	The keys.
	 */
	public Set<String> getKeys(){
		return this.data.keySet();
	}
	
	/**
	 * Gets the data that the given key points to.
	 * 
	 * @param key	The key to get the data for,
	 * @return		The data or an empty string if the key was not found.
	 */
	public String getData(String key){
		if (!this.caseSensitive){
			key = key.toLowerCase();
		}
		
		if (this.data.containsKey(key) == false){
			return "";
		}
		
		return this.data.get(key);
	}
	
	/**
	 * Gets the number of elements in the list.
	 * 
	 * @return The number.
	 */
	public int size(){
		return this.data.size();
	}
	
	/**
	 * Puts a key-value pair into the list.
	 * 
	 * @param key		The key (NOTE: Must not contain ':')
	 * @param value		The value.
	 */
	public void put(String key, String value){
		this.data.put((this.caseSensitive) ? key : key.toLowerCase(), value);
	}
	
	/**
	 * Adds a key-value pair to the list.
	 * 
	 * @param key		The key (NOTE: Must not contain ':')
	 * @param value		The value.
	 */
	public void add(String key, String value){
		if (!this.caseSensitive){
			key = key.toLowerCase();
		}
		
		if (this.data.containsKey(key) == false){
			this.data.put(key, value);
		}
	}
	
	/**
	 * Removed a entry from the list.
	 * 
	 * @param key	The key for the entry to remove.
	 */
	public void remove(String key){
		this.data.remove((this.caseSensitive) ? key : key.toLowerCase());
	}
	
	/**
	 * Removes all entries from the list.
	 */
	public void removeAll(){
		this.data.clear();
	}
	
}
