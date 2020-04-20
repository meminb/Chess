/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.util.ArrayList;

/**
 *
 * @author muham
 */
public class ArtificialIntelligence implements Cloneable{

 
    
    
    
    public ArrayList<String> KnightsTour(int steps,Board b,int [] coordinates){
        steps++;
        b.diMap[coordinates[1]][coordinates[0]]=b.new Knight(1,coordinates[0],coordinates[1]);
        
        
        ArrayList<String> allMoves=new ArrayList<String>();
      
        
        ArrayList<String>moves=b.diMap[coordinates[1]][coordinates[0]].checkMovesFor();
        
        
        //System.out.println(moves);
        for (String move : moves) {
            
            int[] newCoords=b.getCoords(move);
            
            allMoves=KnightsTour(steps, b, newCoords);
            
            if (allMoves!=null) {
               // System.out.println("2");
                allMoves.add(0, move);
                return allMoves;
            }  
        }
        if (steps<62) {
             b.diMap[coordinates[1]][coordinates[0]]=null;
            return null;
        } 
        //System.out.println("111");
        allMoves.add(b.getNotation(coordinates[1],coordinates[0]));
        return allMoves;
    }
    
    
    
    
    
    
    
    
   
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    
    
}
