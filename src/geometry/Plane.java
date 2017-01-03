package geometry;

public class Plane extends Mesh{
	
	public Plane(){
		super();
		this.faces = new Triangle[2];
		float t = 0.5f;
		Vector a = new Vector(  t, 0,  t);
		Vector b = new Vector(  t, 0, -t);
		Vector c = new Vector( -t, 0,  t);
		Vector d = new Vector( -t, 0, -t);

		faces[0]  = new Triangle(a, b, c);
		faces[1]  = new Triangle(d, c, b);

	}
}
