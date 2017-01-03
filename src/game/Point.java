package game;

import geometry.Triangle;
import geometry.Vector;
import rendering.Color;

public class Point extends Light{
	public double range;
	
	public Point(){
		super();
		setPosition(Vector.ZERO());
		setRange(10);
	}
	public Point(double intensity, Color color, Vector position, double range){
		super(intensity, color);
		setRange(range);
		setPosition(position);
	}
	public void setRange(double range){
		this.range = range;
	}
	public double getRange(){
		return range;
	}
	
	@Override
	public double getIntensity(Triangle t){
		double angle = getPosition().subtract(t.getCenter()).normal().dot(t.getNormal());
		double dist2 = t.a.distanceSquare(getPosition());
		double att = Math.max(0, 1 - dist2 / (range * range)) * Math.max(0, 1 - dist2 / (range * range));
		return Math.min(Math.max(intensity * angle * att, 0), 1);
		
	}
	
}