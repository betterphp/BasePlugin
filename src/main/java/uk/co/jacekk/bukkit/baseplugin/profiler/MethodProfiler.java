package uk.co.jacekk.bukkit.baseplugin.profiler;

public abstract class MethodProfiler {
	
	protected long totalCalls;
	protected long totalTime;
	protected long minTime;
	protected long maxTime;
	
	public MethodProfiler(){
		this.reset();
	}
	
	public void reset(){
		this.totalCalls = 0L;
		this.totalTime = 0L;
		this.minTime = 0L;
		this.maxTime = 0L;
	}
	
	/**
	 * Gets the total number of time this method was executed.
	 * 
	 * @return The number.
	 */
	public synchronized long getTotalCalls(){
		return this.totalCalls;
	}
	
	/**
	 * Gets the total time of execution in nanoseconds.
	 * 
	 * @return The time.
	 */
	public synchronized long getTotalTime(){
		return this.totalTime;
	}
	
	/**
	 * Gets the shortest execution time.
	 * 
	 * @return The time.
	 */
	public synchronized long getMinTime(){
		return this.minTime;
	}
	
	/**
	 * Gets the longest execution time.
	 * 
	 * @return The time.
	 */
	public synchronized long getMaxTime(){
		return this.maxTime;
	}
	
}
