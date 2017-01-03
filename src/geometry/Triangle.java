package geometry;

public class Triangle {
	
	public Vector a;
	public Vector b;
	public Vector c;
	
	public Triangle(Vector a, Vector b, Vector c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Vector getNormal(){
		return Vector.cross(a.subtract(b), b.subtract(c)).normal();
	}
	
	public Vector getCenter(){
		double x = (a.getX() + b.getX() + c.getX()) / 3;
		double y = (a.getY() + b.getY() + c.getY()) / 3;
		double z = (a.getZ() + b.getZ() + c.getZ()) / 3;
		return new Vector(x,y,z);
	}
}
