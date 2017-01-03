package rendering;

import javax.swing.SwingUtilities;

public class Main extends Window{
	
	public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();   
            }
        });
        
	}
}
