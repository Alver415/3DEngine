package physics;
import game.Object;
import geometry.*;

public class RigidBody{

	public double mass;
	public double drag;
	public Object object;

	public RigidBody(Object object, double mass){
		
		this.object = object;
		this.mass = mass;
		this.drag = 0;
		this.object.setAcceleration(Vector.DOWN().scalar(9.8f));
	}
	
	public void update(double dt){
		if(drag > 0)
			object.setVelocity(object.getVelocity().scalar(Math.min(Math.max((double)(1 - dt * getDrag()), 0), 1)));
	}
	
	public double getMass(){
		return mass;
	}
	public void setMass(double mass){
		this.mass = Math.max(mass, 0);
	}
	public double getDrag(){
		return drag;
	}
	public void setDrag(double drag){
		this.drag = Math.max(drag, 0);
	}
	
	public Vector getPosition(){
		return object.getPosition();
	}
	public void setPosition(Vector position){
		object.setPosition(position);
	}
	public void setPosition(double x, double y, double z){
		object.setPosition(x, y, z);
	}
	public Vector getVelocity(){
		return object.getVelocity();
	}
	public void setVelocity(Vector velocity){
		object.setVelocity(velocity);
	}
	public void setVelocity(double x, double y, double z){
		object.setVelocity(x, y, z);
	}
}
