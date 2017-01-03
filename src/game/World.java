package game;

import events.RepeatEvent;
import geometry.Plane;
import geometry.Transform;
import geometry.Vector;

import java.util.ArrayList;

import physics.Physics;
import physics.RigidBody;
import rendering.Color;


public class World {

	private static ArrayList<Object> objects = new ArrayList<Object>();
	private static Camera camera = new Camera(1920,1080);
	
	public static final Transform GLOBAL_TRANSFORM = new Transform();
	
	private World(){}
	
	static{
		new RepeatEvent(15){
        	@Override
        	public void action(){
        		update((double) getDelta() / 1000);
        	}
        };
	}
	static{
		
		Plane p = new Plane();
        for (int i = -10; i < 10; i++){
        for (int j = -10; j < 10; j++){
			Object n = new Object();
			n.setBody(p);
			n.color = Color.GRAY;
			double x = i;
			double z = j;
			n.setPosition(x,0,z);
			World.getObjects().add(n);
		}
        }

        World.getObjects().add(new Ambient(0.2f,Color.WHITE));
        World.getObjects().add(new Directional(0.5f, Color.WHITE, Vector.DOWN()));
        World.getObjects().add(new Point(1, Color.WHITE, 	new Vector(0,1,0), 	10));

        for (int i = 0; i <= 360; i += 30){
        	Vector v = new Vector(	10 * Math.sin(i * Math.PI / 180), 
        							1, 
        							10 * Math.cos(i * Math.PI / 180));
        	World.getObjects().add(new Point(1, Color.hsv((double)i,1,1), v, 10));
        }
	}
	
	public static void update(double dt){
		if (dt == 0)
			return;
		for (int i = 0; i < objects.size(); i++){	
			objects.get(i).update(dt);
		}
		ArrayList<Object> o_list = new ArrayList<Object>();
		for (int i = 0; i < objects.size(); i++){
			if (objects.get(i).rigidBody != null){
				o_list.add(objects.get(i));
			}
		}
		Physics.HandleCollisions(o_list);
	}
	
	public static void add(Object object){
		objects.add(object);
	}
	public static void remove(Object object){
		objects.remove(object);
	}
	
	public static Camera getCamera(){
		return camera;
	}
	public static ArrayList<Object> getObjects(){
		return objects;
	}
	
}

