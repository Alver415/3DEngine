package rendering;

import events.RepeatEvent;
import events.Timer;
import game.*;
import game.Object;
import geometry.*;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import physics.*;

@SuppressWarnings({"serial"})
public class Window extends JFrame implements KeyListener, MouseMotionListener{

	public BufferedImage image;
	public Timer clock;
	
	public int width;
	public int height;
	
	
	
	public Window(){

    	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    	width  = (int) (screen.getWidth()  / 1f);
    	height = (int) (screen.getHeight() / 1f);
    	try {
			r = new Robot();
			r.mouseMove(width / 2, height / 2);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("3DEngine");
        setPreferredSize(new Dimension(width,height));
        setLocation((int)((screen.getWidth() - width) / 2), (int)((screen.getHeight() - height) / 2));
        
        pack();
        addKeyListener(this);
        addMouseMotionListener(this);
        setVisible(true);

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        setContentPane(new JPanel(){
        	@Override 
        	public void paintComponent(Graphics g) {
        		g.drawImage(image, 0, 0, null);
        	}
        });
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new java.awt.Point(0, 0), "blank cursor");
		getContentPane().setCursor(blankCursor);
        
        new RepeatEvent(0){
        	@Override
        	public void action(){
        		World.getCamera().update((double) getDelta() / 1000);
                GraphicsPipeLine.renderWorld();
                image.setRGB(0, 0, width, height, Canvas.getIntArray(), 0, width);
                repaint();

        	}
        };
        

		
		
	}

	
	
	public double cam_speed = 10f;
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case (KeyEvent.VK_W):
			World.getCamera().vel.set(0, 0, -cam_speed);
			break;
		case (KeyEvent.VK_S):
			World.getCamera().vel.set(0, 0, cam_speed);
			break;
		case (KeyEvent.VK_A):
			World.getCamera().vel.set(-cam_speed, 0, 0);
			break;
		case (KeyEvent.VK_D):
			World.getCamera().vel.set( cam_speed, 0, 0);
			break;
		case (KeyEvent.VK_Q):
			World.getCamera().vel.set(0, -cam_speed, 0);
			break;
		case (KeyEvent.VK_E):
			World.getCamera().vel.set(0, cam_speed, 0);
			break;
		
		case (KeyEvent.VK_LEFT):
			Physics.damping = Math.min(Math.max(Physics.damping - 0.1f,0),1);
			break;
		case (KeyEvent.VK_RIGHT):
			Physics.damping = Math.min(Math.max(Physics.damping + 0.1f,0),1);
			break;
			
		case (KeyEvent.VK_N):
			Object n = new Object();
			n.rigidBody = new RigidBody(n, 1);
			double x = (double) (0.1 * (Math.random() - 0.5));
			double y = (double) (0.1 * (Math.random() - 0.5));
			double z = (double) (0.1 * (Math.random() - 0.5));
			n.setPosition(0,5,0);
			n.setVelocity(x,y,z);
			World.getObjects().add(n);
			break;
		case (KeyEvent.VK_R):
			for (int i = 0; i < World.getObjects().size(); i++){
				if (World.getObjects().get(i).rigidBody != null)
				World.getObjects().get(i).getShape().refine();
			}
			break;
		case (KeyEvent.VK_BACK_SPACE):
			World.getObjects().clear();
			break;

		case (KeyEvent.VK_ESCAPE):
			System.exit(0);
			break;
		
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {	
		switch(e.getKeyCode()){
		case (KeyEvent.VK_W):
			World.getCamera().vel.set(0,0,0);
			break;
		case (KeyEvent.VK_S):
			World.getCamera().vel.set(0,0,0);
			break;
		case (KeyEvent.VK_A):
			World.getCamera().vel.set(0,0,0);
			break;
		case (KeyEvent.VK_D):
			World.getCamera().vel.set(0,0,0);
			break;
		case (KeyEvent.VK_Q):
			World.getCamera().vel.set(0,0,0);
			break;
		case (KeyEvent.VK_E):
			World.getCamera().vel.set(0,0,0);
			break;
		}}
	@Override
	public void keyTyped(KeyEvent e) {	}

	@Override
	public void mouseDragged(MouseEvent e) {	}

	private Robot r;
	@Override
	public void mouseMoved(MouseEvent e) {

		double dx = ((double)e.getX() - width / 2) / 1000;
		double dy = ((double)e.getY() - height / 2) / 1000;
			
		World.getCamera().pitch(-dx);
		World.getCamera().yaw(-dy);
		
		r.mouseMove(width / 2, height / 2);
	}

}
