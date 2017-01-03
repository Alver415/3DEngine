package physics;

import game.Object;
import geometry.Vector;

import java.util.ArrayList;

public class Physics {

	public static double bounds = 10;
	public static double damping = 0.9f;
	
	private Physics(){}
	
	public static void HandleCollisions(ArrayList<Object> o_list){
		ArrayList<Collision> col_list = findCollisions(o_list);
		for (int i = 0; i < col_list.size(); i++){
			col_list.get(i).resolveCollision();
		}
	}
	
	
	public static ArrayList<Collision> findCollisions(ArrayList<Object> o_list){
		ArrayList<Collision> col_list = new ArrayList<Collision>();
		
		for (int i = 0; i < o_list.size(); i++){
			Object a = o_list.get(i);
			boundsCheck(a);
			for (int j = i + 1	; j < o_list.size(); j++){
				Object b = o_list.get(j);
				if (isCollision(a, b)){
					Collision c = new Collision(a, b);
					col_list.add(c);
				}
			}	
		}
		
		return col_list;
	}
	public static void boundsCheck(Object a){
		Vector p = a.getPosition();
		Vector v = a.getVelocity();
		//OutofBounds right
		if (p.getX() > bounds) {
			a.setPosition(bounds, p.getY(), p.getZ());
			a.setVelocity(-Math.abs(v.getX()) * damping, v.getY(), v.getZ());
		} 
		//OutofBounds left
		else if (p.getX() < -bounds) {
			a.setPosition(-bounds, p.getY(), p.getZ());
			a.setVelocity(Math.abs(v.getX()) * damping, v.getY(), v.getZ());
		} 
		//OutofBounds top
 		if (p.getY() > bounds) {
			a.setPosition(p.getX(), bounds, p.getZ());
			a.setVelocity(v.getX(), -Math.abs(v.getY()) * damping, v.getZ());
		} 	
		//OutofBounds bottom
		else if (p.getY() < 1f) {
			a.setPosition(p.getX(), 1f, p.getZ());
			a.setVelocity(v.getX(), Math.abs(v.getY()) * damping, v.getZ());
		} 
		//OutofBounds front
		if (p.getZ() > bounds) {
			a.setPosition(p.getX(), p.getY(), bounds);
			a.setVelocity(v.getX(), v.getY(), -Math.abs(v.getZ()) * damping);
		} 
		//OutofBounds back
		else if (p.getZ() < -bounds) {
			a.setPosition(p.getX(), p.getY(), -bounds);
			a.setVelocity(v.getX(), v.getY(), Math.abs(v.getZ()) * damping);
		} 
	}
	
	public static boolean isCollision(Object a, Object b){
		return a.distanceTo(b) < 2;
	}
	
	
}

