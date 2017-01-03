package events;

public abstract class Event{

	protected long init = System.currentTimeMillis();
	protected long delay;
	
	protected boolean consumed = false;
	
	public Event(){
		this.delay = 0;
		EventManager.add(this);
	}
	
	public Event(long delay){
		this.delay = delay;
		EventManager.add(this);
	}
	
	public boolean isReady(){
		return System.currentTimeMillis() - init >= delay;
	}
	
	public void run(){
		action();
		consumed = true;
	}
	
	public boolean isConsumed(){
		return consumed;
	}
	public abstract void action();
	
	
}
