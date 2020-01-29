
/**
 * 
 * This file contains Skyline, Line, Pair classes.
 * 
 * Skyline reads given file (default 'data.txt') and fills list of Pair and Line.
 * 
 * 
 */



import java.io.* ;
import java.util.*;

public class Skyline
{
    private List<Pair> pairList ;

    private List<Line> lines = new ArrayList<Line>() ;
    
    private SortedSet<Pair> pairs = new TreeSet<Pair>() ;

    Skyline(){
        try{
            File inFile = new File("data.txt") ;
            
            Scanner scanner = new Scanner(inFile) ;

            makeLines(scanner) ;

            pairList = new ArrayList<Pair>(pairs) ;
        }
        catch(IOException e){ System.out.print("Exception in opening file\n") ; }
    }

    public List<Pair> getPairs() { return pairList ; }

    public void printLines() { System.out.println(lines); }    
    public void printPairs() { System.out.println(pairs); }

    private void makeLines(Scanner scanner){        //This method makes line object by reading from scanner object.
        int width, height, position ;

        while(scanner.hasNextInt())
        {
            width = scanner.nextInt() ;
            height = scanner.nextInt() ;
            position = scanner.nextInt() ;

            lines.add(new Line(width,height,position)) ;
        }
        makePairs();
    }

    private void makePairs(){               //This method creates Pair objects by using Line list.
        int x, y ;
    
        for(int i = 0 ; i < lines.size() ; ++i)
        {
            pairs.add(new Pair ( lines.get(i).getPos() , lines.get(i).getHeight() ) ) ;
            pairs.add(new Pair ( lines.get(i).getPos() + lines.get(i).getWidth() , lines.get(i).getHeight() ) ) ;
        }
    }
}

class Line{                                 //This class has width, height and position as fields.
    Line(int w, int h, int p){
        width = w ;
        height = h ;
        position = p ;
    }

    public int getWidth(){ return width ; }
    public int getHeight(){ return height ; }
    public int getPos(){ return position ; }

    public String toString(){ return "("+width+", "+height+", "+position+")" ; }
    private int width, height, position ;
}

class Pair implements Comparable<Pair>{         //This class implements compareTo mthod from Comperable interface.
    Pair(int nx, int ny){
        x = nx ;
        y = ny ;
    }

    public int getX(){ return x ; }
    public int getY(){return y ; }

    public int compareTo(Pair p){
        if(x>p.x)
            return 1 ;

        else if(x<p.x)
            return -1 ;
        
        else if(y>p.y)
            return 1 ;

        else if(y<p.y)
            return -1 ;

        else
            return 0 ;
    }

    public String toString(){ return "("+x+", "+y+")" ; }

    private int x, y ;
}