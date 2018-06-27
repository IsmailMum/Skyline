/**
 * 
 * CSE241 HW7 The Skyline Problem
 * 
 * This is a driver test class.
 * Main method is in that file.
 * 
 
 */

import java.util.Scanner ;

import javax.swing.JFrame ;

public class Driver
{
    public static void main(String[] args)
    {
        DrawPanel panel = new DrawPanel() ;
        JFrame application = new JFrame() ;

        panel.drawSkyline();        //

        panel.print();      //Prints Coordinates as corners.

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

        application.add(panel) ;

        application.setSize(500,500) ;

        application.setVisible(true) ;
    }
}
