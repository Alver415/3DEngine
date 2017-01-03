package game;

import java.util.ArrayList;

import events.Timer;
import physics.RigidBody;
import rendering.Color;
import geometry.*;

public class Object {

	public Mesh model;
	public Color color;
	
	public Transform pos;
	public Vector vel;
	public Vector acc;

	public RigidBody rigidBody;
	public Collider collider;
	public ArrayList<Script> scripts = new ArrayList<Script>();
	
	
	public Object(){
		this.color = Color.WHITE;
		this.model = new Sphere();
		pos = new Transform();
		vel = new Vector();
		acc = new Vector();
	}
	
	public void update(double dt){
		
		Vector dv = acc.scalar(dt);
		Vector dp = vel.scalar(dt);

		vel = vel.add(dv);
		pos.translate(dp);
		
		for (Script s : scripts){
			s.update(dt);
		}
		if (rigidBody != null)
			rigidBody.update(dt);
		
	}
	public Transform getLocal(){
		return pos;
	}
	public Transform getGlobal(){
		return pos.getGlobal();
	}

	public double distanceTo(Object b){
		return pos.getGlobal().getTranslationVector().distance(b.getGlobal().getTranslationVector());
	}
	
	public void setPosition(double x, double y, double z){
		Transform t = getGlobal();
		t.setTranslation(x, y, z);
		pos = t;
	}
	public void setPosition(Vector position){
		Transform t = getGlobal();
		t.setTranslation(position);
		pos = t;
	}
	public Vector getPosition(){
		return getGlobal().getTranslationVector();
	}
	public void setVelocity(double x, double y, double z){
		vel.set(x, y, z);
	}
	public void setVelocity(Vector velocity){
		vel.set(velocity);
	}
	public Vector getVelocity(){
		return vel;
	}

	public void setAcceleration(double x, double y, double z){
		acc.set(x, y, z);
	}
	public void setAcceleration(Vector acceleration){
		acc.set(acceleration);
	}
	public Vector getAcceleration(){
		return acc;
	}
	public Mesh getShape(){
		return model;
	}
	
	public Triangle[] getModel(){
		Triangle[] t = new Triangle[model.getFaces().length];
		for (int i = 0; i < model.getFaces().length; i++){
			Vector a = (Vector) pos.getGlobal().multiply(model.getFaces()[i].a);
			Vector b = (Vector) pos.getGlobal().multiply(model.getFaces()[i].b);
			Vector c = (Vector) pos.getGlobal().multiply(model.getFaces()[i].c);
			t[i] = new Triangle(a,b,c);
		}
		return t;
	}
	
	public void setBody(Mesh body){
		this.model = body;
	}
}
