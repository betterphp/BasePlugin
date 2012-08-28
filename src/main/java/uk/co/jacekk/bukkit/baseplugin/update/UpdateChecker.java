package uk.co.jacekk.bukkit.baseplugin.update;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

public abstract class UpdateChecker {
	
	protected Plugin plugin;
	protected URL filesFeed;
	
	protected String version;
	protected String link;
	protected String jarLink;
	
	private HashMap<String, Integer> stringValues;
	
	public UpdateChecker(Plugin plugin, String url){
		this.plugin = plugin;
		
		try{
			this.filesFeed = new URL(url);
		}catch (MalformedURLException e){
			e.printStackTrace();
		}
		
		this.stringValues = new HashMap<String, Integer>();
		
		this.stringValues.put("dev", -1);
		this.stringValues.put("alpha", 1);
		this.stringValues.put("beta", 2);
		this.stringValues.put("release", 3);
	}
	
	private int numericValueOf(String part){
		Integer value = 0;
		
		try{
			value = Integer.parseInt(part);
		}catch (NumberFormatException e){
			if (part.length() == 1){
				return part.getBytes()[0];
			}else if (this.stringValues.containsKey(part)){
				return this.stringValues.get(part);
			}
			
			return 0;
		}
		
		return value;
	}
	
	protected int versionCompare(String a, String b){
		if (a.equalsIgnoreCase(b)){
			return 0;
		}
		
		String[] partsa = a.toLowerCase().replaceAll("([0-9]+)([a-z]+)", "$1.$2").replaceAll("([a-z]+)([0-9]+)", "$1.$2").split("[_ ,.+]{1}");
		String[] partsb = b.toLowerCase().replaceAll("([0-9]+)([a-z]+)", "$1.$2").replaceAll("([a-z]+)([0-9]+)", "$1.$2").split("[_ ,.+]{1}");
		
		int max = Math.max(partsa.length, partsb.length);
		
		for (int i = 0; i < max; ++i){
			Integer vala = (i < partsa.length) ? this.numericValueOf(partsa[i]) : 0;
			Integer valb = (i < partsb.length) ? this.numericValueOf(partsb[i]) : 0;
			
			if (vala > valb){
				return 1;
			}else if (vala < valb){
				return -1;
			}
		}
		
		return -1;
	}
	
	public String getVersion(){
		return this.version;
	}
	
	public String getLink(){
		return this.link;
	}
	
	public String getJarLink(){
		return this.jarLink;
	}
	
	public abstract boolean updateNeeded();
	
}
