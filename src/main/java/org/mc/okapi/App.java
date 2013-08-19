package org.mc.okapi;

import java.awt.EventQueue;
import javax.swing.UIManager;
import org.mc.okapi.MainFrame;
import org.mc.okapi.Splash;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	

   	 try { 
   	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
   	    } 
   	    catch(Exception e){ 
   	    }
   	 
   	 Splash s=new Splash();
     s.setVisible(true);
     Thread t=Thread.currentThread();
     t.sleep(1000);
     s.dispose();

     

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
    }
}
