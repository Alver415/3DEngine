package game;

public class Collider {

	private Object object;
	private double radius;
	
	public Collider(Object object, double radius){
		this.object = object;
		this.radius = radius;
	}
	
	public static boolean collision(Collider a, Collider b){
		return a.object.distanceTo(b.object) <= a.radius + b.radius;
	}
	public boolean collision(Collider a){
		return a.object.distanceTo(object) <= a.radius + radius;
	}
	
}
