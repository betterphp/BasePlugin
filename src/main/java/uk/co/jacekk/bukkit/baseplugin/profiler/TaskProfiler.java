package uk.co.jacekk.bukkit.baseplugin.profiler;

/**
 * A wrapper around any scheduled task that times execution.
 */
public class TaskProfiler extends MethodProfiler implements Runnable {
	
	private transient Runnable runnable;
	
	public TaskProfiler(Runnable runnable){
		super();
		
		this.runnable = runnable;
	}
	
	@Override
	public void run(){
		long start = System.nanoTime();
		
		this.runnable.run();
		
		long time = System.nanoTime() - start;
		
		++this.totalCalls;
		this.totalTime += time;
		this.minTime = (this.minTime == 0L) ? time : Math.min(this.minTime, time);
		this.maxTime = (this.maxTime == 0L) ? time : Math.max(this.maxTime, time);
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		
		result.append("[Task][");
		
		result.append("runnable=");
		result.append(this.runnable.getClass().getName());
		result.append(",");
		
		result.append("totalCalls=");
		result.append(this.getTotalCalls());
		result.append(",");
		
		result.append("totalTime=");
		result.append(this.getTotalTime());
		result.append(",");
		
		result.append("minTime=");
		result.append(this.getMinTime());
		result.append(",");
		
		result.append("maxTime=");
		result.append(this.getMaxTime());
		
		result.append("]");
		
		return result.toString();
	}
	
}
