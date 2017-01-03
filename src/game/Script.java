package game;

public abstract class Script {

	Object object;
	
	public Script(){}
	
	public abstract void update(double dt);
}
