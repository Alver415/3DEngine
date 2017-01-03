package game;

import geometry.Triangle;
import geometry.Vector;
import rendering.Color;

public class Light extends Object{

	protected double intensity;
	
	protected Light(){
		setBody(null);
		setIntensity(1);
		setColor(Color.WHITE);
	}
	protected Light(double power, Color color){
		setBody(null);
		setIntensity(power);
		setColor(color);
	}
	
	public static Light newAmbient(double intensity, Color color){
		return new Ambient(intensity, color);
	}
	public static Light newDirectional(double intensity, Color color){
		return new Directional(intensity, color, Vector.DOWN());
	}
	public static Light newPoint(double intensity, Color color){
		return new Point(intensity, color, Vector.ZERO(), 1);
	}
	
	public double getIntensity(Triangle t){
		return intensity;
	}
	
	public void setIntensity(double power){
		this.intensity = power;
	}
	public void setColor(Color color){
		this.color = color;
	}
	
	@Override
	public void update(double dt){
		super.update(dt);
		
	}
	
}
