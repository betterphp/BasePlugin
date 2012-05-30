package uk.co.jacekk.bukkit.baseplugin.storage;

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

public class DataStore {
	
	private File storageFile;
	
	private HashMap<String, String> data;
	private boolean caseSensitive;
	
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
	
	public boolean contains(String key){
		return this.data.containsKey((this.caseSensitive) ? key : key.toLowerCase());
	}
	
	public Set<Entry<String, String>> getAkk(){
		return this.data.entrySet();
	}
	
	public Set<String> getKeys(){
		return this.data.keySet();
	}
	
	public String getData(String key){
		if (!this.caseSensitive){
			key = key.toLowerCase();
		}
		
		if (this.data.containsKey(key) == false){
			return "";
		}
		
		return this.data.get(key);
	}
	
	public int size(){
		return this.data.size();
	}
	
	public void add(String key, String value){
		if (!this.caseSensitive){
			key = key.toLowerCase();
		}
		
		if (this.data.containsKey(key) == false){
			this.data.put(key, value);
		}
	}
	
	public void remove(String key){
		this.data.remove((this.caseSensitive) ? key : key.toLowerCase());
	}
	
	public void removeAll(){
		this.data.clear();
	}
	
}
