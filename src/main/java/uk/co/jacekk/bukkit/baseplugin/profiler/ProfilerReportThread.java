package uk.co.jacekk.bukkit.baseplugin.profiler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Reports timing data back to the server for logging.
 */
public class ProfilerReportThread extends Thread implements Runnable {
	
	private PluginProfiler profiler;
	private URL reportUrl;
	
	public ProfilerReportThread(PluginProfiler profiler){
		super("ProfilerReportThread");
		
		this.profiler = profiler;
		
		try{
			this.reportUrl = new URL("http://bukkit.jacekk.co.uk/plugin_profiler.php");
		}catch (MalformedURLException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while (true){
			try{
				//Thread.sleep(3600000); // 1 hour
				
				Thread.sleep(1000); // 10 seconds
				
				String report = this.profiler.generateReport();
				
				HttpURLConnection connection = (HttpURLConnection) this.reportUrl.openConnection();
				
				connection.setConnectTimeout(10000);
				connection.setReadTimeout(10000);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				
				OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
				output.write(report);
				output.close();
				
				BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while (input.readLine() != null);
				input.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
}