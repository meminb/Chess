/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author muham
 */
public class Chess extends JFrame{

     public Chess (String title) throws HeadlessException{
        super(title);
       }
    
    
    public static void main(String[] args) {
       
        Chess win=new Chess("chess");
        
        win.setSize(865,895);
        
        GamePlay game=new GamePlay();
        game.addMouseListener(game);
        win.add(game);
        
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
        
        
        
    }
    
   
       
    
    
}
