package geometry;

public class Mesh {

	protected Triangle[] faces;
	
	public Mesh(){
		this.faces = new Triangle[0];
	}
	
	public Mesh(Triangle[] faces){
		this.faces = faces;
	}
	
	public Triangle[] getFaces(){
		return faces;
	}
	
	public void refine(){
		if (faces.length > 1000){
			return;
		}
		Triangle[] new_faces = new Triangle[faces.length * 4];

		for (int i = 0; i < faces.length; i++){
		    Vector d = Vector.midpoint(faces[i].a, faces[i].b);
		    Vector e = Vector.midpoint(faces[i].b, faces[i].c);
		    Vector f = Vector.midpoint(faces[i].c, faces[i].a);
			
		    new_faces[4 * i + 0] = new Triangle(faces[i].a, d, f);
		    new_faces[4 * i + 1] = new Triangle(faces[i].b, e, d);
		    new_faces[4 * i + 2] = new Triangle(faces[i].c, f, e);
		    new_faces[4 * i + 3] = new Triangle(d, e, f);
		}
	    faces = new_faces;
	      
	}
}
