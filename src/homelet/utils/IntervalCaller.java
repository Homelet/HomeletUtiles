package homelet.utils;

public class IntervalCaller{
	
	private long   interval;
	private int    callTime;
	private Caller caller;
	// util
	private long   lastCall   = 0;
	private int    calledTime = 0;
	
	public IntervalCaller(Caller caller, long interval_in_mili){
		this(caller, interval_in_mili, -1);
	}
	
	public IntervalCaller(Caller caller, int callTime){
		this(caller, -1L, callTime);
	}
	
	public IntervalCaller(Caller caller, long interval, int callTime){
		this.interval = interval;
		this.callTime = callTime;
		this.caller = caller;
	}
	
	public long tick(){
		return checkTime();
	}
	
	private long checkTime(){
		calledTime++;
		long currentTime = System.currentTimeMillis();
		if(lastCall == 0){
			lastCall = currentTime;
		}
		long passedTime = currentTime - lastCall;
		if((callTime >= 0 && calledTime >= callTime) || (interval >= 0L && passedTime >= interval)){
			caller.callAble();
			if(calledTime >= callTime)
				calledTime = 0;
			if(passedTime >= interval){
				lastCall = System.currentTimeMillis();
				return passedTime - interval;
			}
		}
		return 0L;
	}
	
	public long getInterval(){
		return interval;
	}
	
	public Caller getCaller(){
		return caller;
	}
	
	public long getLastCall(){
		return lastCall;
	}
	
	public interface Caller{
		
		void callAble();
	}
}
