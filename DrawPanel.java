/**
 * 
 * This file contains DrawPanel, MaxValues and Coordinates classes. 
 * 
 * 
 * 
 */

import java.awt.* ;
import java.util.* ;

import javax.swing.*;

public class DrawPanel extends JPanel{              //This class extends from Jpanel and creates a window.

    private ArrayList<Coordinates> coordinateList = new ArrayList<Coordinates>() ;

    private int top ;

    public void paintComponent (Graphics g){
        super.paintComponent(g) ;

        int width = getWidth() ;
        int height = getHeight() ;

        int wRate = width / (coordinateList.get(coordinateList.size()-1).x - coordinateList.get(0).x )  ;    //wRate makes skyline width same with window's width.

        findMaxLength();
        int lRate = height / (2*top) ;          //lRate makes longest building's length half of the window length. 

        for(int i = 1 ; i<coordinateList.size() ; i++)          //Prints the lines between every coordinate.
        {
            g.drawLine( (coordinateList.get(i-1).x-coordinateList.get(0).x) * wRate ,
                        height-coordinateList.get(i-1).y * lRate ,
                        (coordinateList.get(i).x - coordinateList.get(0).x) * wRate , 
                        height-coordinateList.get(i).y * lRate );
        }
    }

    private void findMaxLength()            //Finds longest building's length.
    {
        int temp = 1 ;

        for(int i = 0 ; i < coordinateList.size() ; ++i )
            if(coordinateList.get(i).y > temp )
                temp = coordinateList.get(i).y ;

        top = temp ;
    }

    public void print(){ System.out.print(coordinateList) ; }       //Prints coordinates.

    private void setCoordinates()                               //Removes unnecessary and repeated coordinates.
    {
        for(int i = 1 ; i< coordinateList.size() ; i ++)
            if(coordinateList.get(i-1).x == coordinateList.get(i).x && coordinateList.get(i-1).y == coordinateList.get(i).y )
                coordinateList.remove(i) ;

        for(int i = 2 ; i < coordinateList.size() ; i++)
            if(coordinateList.get(i).y == coordinateList.get(i-1).y  && coordinateList.get(i).y == coordinateList.get(i-2).y )
            {
                coordinateList.remove(i-1) ;
                i-- ;
            }
    }

    public void drawSkyline()                                       // Reads Pairs from skyline object and creates coordinates.
    {
        Skyline skyline = new Skyline() ;

        TreeSet<Integer> maxValues = new TreeSet<Integer>() ;           //Holds the lengths of buildings.

        int lastHeight = 0 , lastPos = 0 ;                                  

        maxValues.add( 0 ) ;

        coordinateList.add(new Coordinates( skyline.getPairs().get(0).getX() , 0 ) ) ;      //Creates a coordinate on bottom and left lines corner of leftmost building.
        lastPos = skyline.getPairs().get(0).getX() ;

        if(skyline.getPairs().get(0).getY() > maxValues.last())                             //Creates leftmost lines top corner.
        {
            maxValues.add(skyline.getPairs().get(0).getY() ) ;

            coordinateList.add(new Coordinates( skyline.getPairs().get(0).getX() , skyline.getPairs().get(0).getY() ) ) ;
            lastHeight = skyline.getPairs().get(0).getY() ;
        }

        for(int i = 1 ; i<skyline.getPairs().size() ; ++i)                          //For every pair except first one which created above.
        {
            if( maxValues.last() == skyline.getPairs().get(i).getY() )
            {
                if(maxValues.last() != lastHeight)
                    coordinateList.add(new Coordinates(lastPos, skyline.getPairs().get(i).getY() ) ) ;

                coordinateList.add(new Coordinates(skyline.getPairs().get(i).getX(), skyline.getPairs().get(i).getY() ) ) ;
                maxValues.remove(maxValues.last() ) ;

                lastHeight = skyline.getPairs().get(i).getY() ;
            }

            else if(skyline.getPairs().get(i).getY() > maxValues.last())
            {
                if(maxValues.last() != lastHeight)
                    coordinateList.add(new Coordinates(lastPos,maxValues.last()) ) ;

                coordinateList.add(new Coordinates(skyline.getPairs().get(i).getX(), maxValues.last() ) ) ;

                coordinateList.add(new Coordinates(skyline.getPairs().get(i).getX(), skyline.getPairs().get(i).getY() ) )  ;

                maxValues.add(skyline.getPairs().get(i).getY() ) ;

                lastHeight = skyline.getPairs().get(i).getY() ;
            }

            else if(skyline.getPairs().get(i).getY() < maxValues.last())
            {
                coordinateList.add(new Coordinates(lastPos, maxValues.last() ) ) ;

                coordinateList.add(new Coordinates(skyline.getPairs().get(i).getX() , maxValues.last() ) ) ;

                lastHeight = maxValues.last() ;
            
                if(!maxValues.contains(skyline.getPairs().get(i).getY()))
                    maxValues.add(skyline.getPairs().get(i).getY() ) ;

                else 
                    maxValues.remove(skyline.getPairs().get(i).getY() ) ;
            }
            lastPos = skyline.getPairs().get(i).getX() ;
        }
        coordinateList.add(new Coordinates(lastPos, 0) ) ;                  //Creates last coordinate on rightmost lines bottom.
        setCoordinates();
    }
}

class MaxValues implements Comparable<MaxValues>                    //MacValues class implements Comperable to be assigned in sorted set.
{
    public MaxValues(int m){ max = m ;}                                 //It holds top length's of buildings. 
    public int compareTo(MaxValues m){ return max-m.max ; }
    int max ;
}

class Coordinates                                  //Each Coordinates class object holds x and y coordinates.
{
    public Coordinates(int x1 , int y1){
        x = x1 ;
        y = y1 ;
    }

    public String toString(){ return "("+x+", "+y+")" ; }
    int x, y ;    
}
