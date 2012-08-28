package uk.co.jacekk.bukkit.baseplugin.update;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.bukkit.plugin.Plugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BukkitDevUpdateChecker {
	
	private Plugin plugin;
	private URL filesFeed;
	
	private String version;
	private String link;
	private String jarLink;
	
	private HashMap<String, Integer> stringValues;
	
	public BukkitDevUpdateChecker(Plugin plugin, String url){
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
	
	private int versionCompare(String a, String b){
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
	
	public boolean updateNeeded(){
		try{
			InputStream input = this.filesFeed.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			
			Node latestFile = document.getElementsByTagName("item").item(0);
			NodeList children = latestFile.getChildNodes();
			
			this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
			this.link = children.item(3).getTextContent();
			
			input.close();
			
			input = (new URL(this.link)).openConnection().getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line;
			
			while ((line = reader.readLine()) != null){
				if (line.trim().startsWith("<li class=\"user-action user-action-download\">")){
					this.jarLink = line.substring(line.indexOf("href=\"") + 6, line.lastIndexOf("\""));
					
					break;
				}
			}
			
			reader.close();
			input.close();
			
			if (this.versionCompare(plugin.getDescription().getVersion(), this.version) == -1){
				return true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return false;
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
	
}
