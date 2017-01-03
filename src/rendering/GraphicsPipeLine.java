package rendering;

import java.util.ArrayList;

import game.Light;
import game.Object;
import game.World;
import geometry.*;

public class GraphicsPipeLine {

	private static Transform camera_inv_pos;
	private static ArrayList<Light> lights = new ArrayList<Light>();
		
	public static void renderWorld(){
		Canvas.clear();
		GraphicsPipeLine.camera_inv_pos = World.getCamera().pos.invert();
		
		ArrayList<Thread> threadList = new ArrayList<Thread>();

		lights.clear();
		for (int i = 0; i < World.getObjects().size(); i++){
			if (World.getObjects().get(i) instanceof Light)
				lights.add((Light)World.getObjects().get(i));
		}
		for (int i = 0; i < World.getObjects().size(); i++){
			if (World.getObjects().get(i).getShape() != null){
				final int j = i;
				Thread t = new Thread(new Runnable(){
					@Override
					public void run(){
						renderObject(World.getObjects().get(j));
					}
				});
				threadList.add(t);
				t.start();
			}	
		}
	
		for (Thread t : threadList){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		drawCursor();
	}
	
	public static void drawCursor(){
		int w = Canvas.getWidth();
		int h = Canvas.getHeight();
		int x = w/2;
		int y = h/2;
		int s = 30;
		Canvas.drawLine(new Vector(x, y + 10, -Double.MAX_VALUE), new Vector(x, y + s, -Double.MAX_VALUE), Color.WHITE);
		Canvas.drawLine(new Vector(x, y - 10, -Double.MAX_VALUE), new Vector(x, y - s, -Double.MAX_VALUE), Color.WHITE);
		Canvas.drawLine(new Vector(x + 10, y, -Double.MAX_VALUE), new Vector(x + s, y, -Double.MAX_VALUE), Color.WHITE);
		Canvas.drawLine(new Vector(x - 10, y, -Double.MAX_VALUE), new Vector(x - s, y, -Double.MAX_VALUE), Color.WHITE);
	}
	
	
	public static void renderObject(Object object){
		Triangle[] body = object.getModel();
		for (int i = 0; i < body.length; i++){
			renderFace(body[i], object.color);
		}
	}

	public static void renderFace(Triangle t, Color color){
		Vector a = worldToView(t.a);
		Vector b = worldToView(t.b);
		Vector c = worldToView(t.c);
		if(backfaceCull(new Triangle(a,b,c))){
			return;
		}
		color = applyLighting(color, t, lights);
		
		a = viewToClip(a);
		b = viewToClip(b);
		c = viewToClip(c);

		if (a.getZ() < 1	|| 
			b.getZ() < 1	|| 
			c.getZ() < 1)
				return;
		clip(a, b);
	    clip(b, c);
		clip(c, a);
		
		a = clipToNorm(a);
		b = clipToNorm(b);
		c = clipToNorm(c);
		
		a = normToScreen(a);
		b = normToScreen(b);
		c = normToScreen(c);

		Vector A = new Triangle(a,b,c).getCenter();
		if ((a.getX() < 0	|| 
			a.getX() > 1920 ||
			a.getY() < 0	|| 
			a.getY() > 1080)	&&
			(b.getX() < 0	|| 
			b.getX() > 1920 ||
			b.getY() < 0	|| 
			b.getY() > 1080)	&&
			(c.getX() < 0	|| 
			c.getX() > 1920 ||
			c.getY() < 0	|| 
			c.getY() > 1080))
			return;
		
		Canvas.fillTriangle(a, b, c, color);
		
	}
	
	public static void renderLine(Line l, Color color){
		renderLine(l.getStart(), l.getEnd(), color);
	}
	
	public static void renderLine(Vector a, Vector b, Color color){
		a = worldToView(a);
		b = worldToView(b);
		
		a = viewToClip(a);
		b = viewToClip(b);
		
		a = clipToNorm(a);
		b = clipToNorm(b);

		a = normToScreen(a);
		b = normToScreen(b);
		
		Canvas.drawLine(a, b, color);
		
	}
	
	public static Vector worldToView(Vector world){
		return camera_inv_pos.multiply(world);
	}
	public static Vector viewToClip(Vector view){
		return World.getCamera().perspective.multiply(view);
	}
	public static Vector clipToNorm(Vector clip){
		return new Vector(clip.getX() / clip.getW(), clip.getY() / clip.getW(), clip.getZ() / clip.getW());
	}
	public static Vector normToScreen(Vector normalized){
		return new Vector((1 - normalized.getX()) * Canvas.getWidth() / 2, (1 - normalized.getY()) * Canvas.getHeight() / 2, normalized.getZ());
	}

	public static Color applyLighting(Color color, Triangle triangle, ArrayList<Light> lights){
		double r = 0;
		double g = 0;
		double b = 0;
		for (int i = 0; i < lights.size(); i++){
			Light l = lights.get(i);
			double intensity = l.getIntensity(triangle);

			r += intensity * l.color.getRed();
			g += intensity * l.color.getGreen();
			b += intensity * l.color.getBlue();
		}

		r *= color.getRed();
		g *= color.getGreen();
		b *= color.getBlue();
		
		return Color.rgb(r,g,b);
	}

	public static boolean backfaceCull(Triangle t){
		return t.getCenter().dot(t.getNormal()) >= 0;
	}
	
	public static void clip(Vector a, Vector b) {
        double u1 = 0;
        double u2 = 1;
        int x0 = (int) a.getX();
        int y0 = (int) a.getY();
        int z0 = (int) a.getZ();
        int x1 = (int) b.getX();
        int y1 = (int) b.getY();
        int z1 = (int) b.getZ();
        
        int xMax = 1;
        int xMin = -1;
        int yMax = 1;
        int yMin = -1;
        int zMax = 1;
        int zMin = -1;
        
        int dx = x1 - x0;
        int dy = y1 - y0;
        int dz = z1 - z0;
        int p[] = {-dx, dx, -dy, dy, -dz, dz};
        int q[] = {x0 - xMin, xMax - x0, y0 - yMin, yMax - y0, z0 - zMin, zMax - z0};
        for (int i = 0; i < 6; i++) {
            if (p[i] == 0) {
                if (q[i] < 0) {
                    return;
                }
            } else {
                double u = (double) q[i] / p[i];
                if (p[i] < 0) {
                    u1 = Math.max(u, u1);
                } else {
                    u2 = Math.min(u, u2);
                }
            }
        }
        if (u1 > u2) {       
            return;
        }
        int nx0, nx1, ny0, ny1, nz0, nz1;
        nx0 = (int) (x0 + u1 * dx);
        nx1 = (int) (x0 + u2 * dx);
        ny0 = (int) (y0 + u1 * dy);
        ny1 = (int) (y0 + u2 * dy);
        nz0 = (int) (z0 + u1 * dz);
        nz1 = (int) (z0 + u2 * dz);
        a.set(nx0, ny0, nz0);
        b.set(nx1, ny1, nz1);
        
        return;
    }
	
}