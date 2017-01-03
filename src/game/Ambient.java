package game;

import geometry.Triangle;
import rendering.Color;

public class Ambient extends Light{
	
	public Ambient(){
		super();
	}
	public Ambient(double intensity, Color color){
		super(intensity, color);
	}
	
	@Override
	public double getIntensity(Triangle t){
		return intensity;
	}
	
}