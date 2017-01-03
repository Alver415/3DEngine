package events;

public class Timer {
	
	private static long init = System.currentTimeMillis();
	
	private Timer(){}

	public static long getTime(){
		return System.currentTimeMillis() - init;
	}
	public static long getInit(){
		return init;
	}

}