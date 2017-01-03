package game;

import geometry.Triangle;
import geometry.Vector;
import rendering.Color;

public class Directional extends Light{
	public Vector direction;
	
	public Directional(){
		super();
		setDirection(Vector.UP());
	}
	public Directional(double intensity, Color color, Vector direction){
		super(intensity, color);
		setDirection(direction);
	}
	
	public void setDirection(Vector direction){
		this.direction = direction;
	}
	
	public Vector getDirection(){
		return direction;
	}
	@Override
	public double getIntensity(Triangle t){
		return Math.min(Math.max((intensity * t.getNormal().dot(getDirection().scalar(-1).normal())), 0), 1);
	}
	
}