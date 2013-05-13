package uk.co.jacekk.bukkit.baseplugin.profiler;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

/**
 * A wrapper for event handlers that times execution.
 */
public class EventProfiler extends MethodProfiler implements EventExecutor {
	
	private transient EventExecutor executor;
	private transient Listener listener;
	private transient Event event;
	
	public EventProfiler(EventExecutor executor, Listener listener){
		super();
		
		this.executor = executor;
		this.listener = listener;
	}
	
	@Override
	public void execute(Listener listener, Event event) throws EventException {
		if (this.event == null){
			this.event = event;
		}
		
		long start = System.nanoTime();
		
		this.executor.execute(listener, event);
		
		long time = System.nanoTime() - start;
		
		++this.totalCalls;
		this.totalTime += time;
		this.minTime = (this.minTime == 0L) ? time : Math.min(this.minTime, time);
		this.maxTime = (this.maxTime == 0L) ? time : Math.max(this.maxTime, time);
	}
	
	@Override
	public String toString(){
		if (this.event == null){
			return null;
		}
		
		StringBuilder result = new StringBuilder();
		
		result.append("[Event][");
		
		result.append("listener=");
		result.append(this.listener.getClass().getName());
		result.append(",");
		
		result.append("event=");
		result.append((this.event != null) ? this.event.getEventName() : "UnknownEvent");
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
