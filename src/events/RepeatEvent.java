package events;

public abstract class RepeatEvent extends Event{

	protected long last = init;
	protected long curr = init;
	

	public RepeatEvent(){
		super();
	}
	public RepeatEvent(long delay){
		super(delay);
	}
	
	
	@Override
	public boolean isReady(){
		return System.currentTimeMillis() - curr >= delay;
	}
	
	@Override
	public void run(){
		last = curr;
		curr = System.currentTimeMillis();
		action();
	}
	public abstract void action();
	
	public long getDelta(){
		return curr - last;
	}
	
	public void consume(){
		consumed = true;
	}
	
	
}
