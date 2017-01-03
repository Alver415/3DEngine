package geometry;

public class Cube extends Mesh{
	
	public Cube(){
		super();
		this.faces = new Triangle[12];
		double t = 0.5;
		Vector a = new Vector( t,  t,  t);
		Vector b = new Vector( t,  t, -t);
		Vector c = new Vector( t, -t,  t);
		Vector d = new Vector( t, -t, -t);
		Vector e = new Vector(-t,  t,  t);
		Vector f = new Vector(-t,  t, -t);
		Vector g = new Vector(-t, -t,  t);
		Vector h = new Vector(-t, -t, -t);
		
		//right
		faces[0]  = new Triangle(a, b, c);
		faces[1]  = new Triangle(d, c, b);
		//Left
		faces[2]  = new Triangle(f, g, h);
		faces[3]  = new Triangle(e, f, g);
		//front
		faces[4]  = new Triangle(h, d, f);
		faces[5]  = new Triangle(b, f, d);
		//back
		faces[6]  = new Triangle(a, c, e);
		faces[7]  = new Triangle(g, e, c);
		//top
		faces[8]  = new Triangle(a, e, b);
		faces[9]  = new Triangle(f, b, e);
		//bottom
		faces[10] = new Triangle(g, c, h);
		faces[11] = new Triangle(d, h, c);

	}
}
