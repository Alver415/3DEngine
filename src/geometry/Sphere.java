package geometry;

public class Sphere extends Mesh{


	public Sphere(){
		super();
		this.faces = new Triangle[20];
		
		double t = (double) ((1.0 + Math.sqrt(5.0)) / 2.0);
		
		Vector a = new Vector(-1,  t,  0);
		Vector b = new Vector( 1,  t,  0);
		Vector c = new Vector(-1, -t,  0);
		Vector d = new Vector( 1, -t,  0);
		Vector e = new Vector( 0, -1,  t);
		Vector f = new Vector( 0,  1,  t);
		Vector g = new Vector( 0, -1, -t);
		Vector h = new Vector( 0,  1, -t);
		Vector i = new Vector( t,  0, -1);
		Vector j = new Vector( t,  0,  1);
		Vector k = new Vector(-t,  0, -1);
		Vector l = new Vector(-t,  0,  1);

		a = a.scalar(1 / a.distance(Vector.ZERO()));
	    b = b.scalar(1 / b.distance(Vector.ZERO()));
	    c = c.scalar(1 / c.distance(Vector.ZERO()));
	    d = d.scalar(1 / d.distance(Vector.ZERO()));
	    e = e.scalar(1 / e.distance(Vector.ZERO()));
	    f = f.scalar(1 / f.distance(Vector.ZERO()));
	    g = g.scalar(1 / g.distance(Vector.ZERO()));
	    h = h.scalar(1 / h.distance(Vector.ZERO()));
	    i = i.scalar(1 / i.distance(Vector.ZERO()));
	    j = j.scalar(1 / j.distance(Vector.ZERO()));
	    k = k.scalar(1 / k.distance(Vector.ZERO()));
	    l = l.scalar(1 / l.distance(Vector.ZERO()));
		
		
		faces[0]  = new Triangle(a, l, f);
		faces[1]  = new Triangle(a, f, b);
		faces[2]  = new Triangle(a, b, h);
		faces[3]  = new Triangle(a, h, k);
		faces[4]  = new Triangle(a, k, l);

		faces[5]  = new Triangle(b, f, j);
		faces[6]  = new Triangle(f, l, e);
		faces[7]  = new Triangle(l, k, c);
		faces[8]  = new Triangle(k, h, g);
		faces[9]  = new Triangle(h, b, i);

		faces[10] = new Triangle(d, j, e);
		faces[11] = new Triangle(d, e, c);
		faces[12] = new Triangle(d, c, g);
		faces[13] = new Triangle(d, g, i);
		faces[14] = new Triangle(d, i, j);

		faces[15] = new Triangle(e, j, f);
		faces[16] = new Triangle(c, e, l);
		faces[17] = new Triangle(g, c, k);
		faces[18] = new Triangle(i, g, h);
		faces[19] = new Triangle(j, i, b);

	}
	
	public void refine(){
		super.refine();
		
		for (int i = 0; i < faces.length; i++){
			faces[i].a = faces[i].a.scalar(1 / faces[i].a.distance(Vector.ZERO()));
			faces[i].b = faces[i].b.scalar(1 / faces[i].b.distance(Vector.ZERO()));
			faces[i].c = faces[i].c.scalar(1 / faces[i].c.distance(Vector.ZERO()));
		}
	}
}
