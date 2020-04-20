/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author muham
 */
public class GamePlay extends JPanel implements MouseListener{
    
    static Board.Pieces onClicked;
    int coordinates[];
    Board board;
    public GamePlay(){
        onClicked=null;
        coordinates=new int[]{45,145,245,345,445,545,645,745};//coordinat for window
        
        board=new Board();
        
        
      
   
    }
    
     
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(board.getBoard(), 0, 0, 845, 845, this);
        
        
        if (board.isCheck) {
            g.drawImage(board.Checked, coordinates[board.kings.get(board.turn).X], coordinates[7-board.kings.get(board.turn).Y], 100, 100, this);
        }
       
        for (int i = 7; i >=0; i--) {//drawing all Pieces-> down to top
            for (int j = 0; j < 8; j++) {
                if (board.diMap[i][j]!=null) {
                     g.drawImage(board.diMap[i][j].getImg(), coordinates[j], coordinates[7-i], 100, 100, this);
                }   
            }
        }
      
        
        if (onClicked!=null) {
            for (String notes : onClicked.AvailableMoves()) {
                int x[]=board.getCoords(notes);
                g.drawImage(board.OnClicked, coordinates[x[0]], coordinates[7-x[1]], this);
            }
        }
        
     }
        
       
    
    
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) throws ArrayIndexOutOfBoundsException{

  

  

      
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
      
        
        
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
 /**for (String not : availableMoves) {
                     if (!onClicked.gettingOutOfCheck(not)) {
                        availableMoves.remove(not);
                        }
                 }**/
        
    }
static ArrayList<String> moves;
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getX()<845&&e.getY()<845) {//if mouse in the map
            int indexX=(e.getX()-45)/100;
            int indexY=7-(e.getY()-45)/100;
            
            ArrayList<String> availableMoves;
          
             if (onClicked!=null) {//if there is a picked piece
                
                moves=onClicked.AvailableMoves();
                String s=board.getNotation(indexX, indexY);
                
                if (moves.contains(s)) {//and clicked square in the picked pieces avaible moves
                 
                    onClicked.moveOrCapture(indexX, indexY);//play
                    onClicked=null;
                    
                    
                }else{
                    onClicked=null;
                }
                
            }else if (board.diMap[indexY][indexX]!=null&&board.turn*board.diMap[indexY][indexX].getTeam()==1) {//if clicked square has a piece//and if the clicked pieces turn
                onClicked=board.diMap[indexY][indexX];
                
            } else{
                onClicked=null;
            }
        
        }
        
        
        
        repaint();
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }



   
    
}
