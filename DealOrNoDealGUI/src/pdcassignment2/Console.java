/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcassignment2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Mridula 17980560
 * @author Grace 16946441
 */
public class Console 
{
    /**
     * method for when the j frame is closed, exits the program
     * @param frame 
     */
    public static void setupClosing(JFrame frame)
    {
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    
    /**
     * method for opening and running the frame when the code is run
     * @param frame
     * @param width
     * @param height 
     */
    public static void run(JFrame frame, int width, int height)
    {
        setupClosing(frame);
        frame.setSize(width,height);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
