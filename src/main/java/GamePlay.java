/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author muham
 */
public class GamePlay extends JPanel implements MouseListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static Board.Pieces onClicked;
    int coordinates[];
    Board board;
    boolean isAIon;

    ArtificialIntelligence artificialDecisions;
    public GamePlay(boolean isAIon) throws CloneNotSupportedException {
        onClicked=null;
        coordinates=new int[]{45,145,245,345,445,545,645,745};//coordinat for window

        board=new Board();
        artificialDecisions=new ArtificialIntelligence(board);
        //demo of performance

        long startTime = System.currentTimeMillis();
            List<Board> boards=new ArrayList<>();
/*
        long loop=5000000;
        for (int i = 0; i < loop; i++) {
            Board b=board.clone();
            boards.add(b);
            if(i%100000==0){
                System.out.println("%"+(((i+1)* 100L /loop)));
            }

        }
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in milliseconds: " + timeElapsed);
*/
        this.isAIon=isAIon;



    }
    static int counter =0;
    static ArrayList<String> moves;
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(board.getBoard(), 0, 0, 845, 845, this);


        if (board.isCheck) {
            g.drawImage(ImageOfPieces.imagesInstance.getChecked(), coordinates[board.kings.get(board.turn).X], coordinates[7-board.kings.get(board.turn).Y], 100, 100, this);
        }

        for (int i = 7; i >=0; i--) {//drawing all Pieces-> down to top
            for (int j = 0; j < 8; j++) {
                if (board.getStateOfBoard()[i][j]!=null) {
                    g.drawImage(board.getStateOfBoard()[i][j].getImg(), coordinates[j], coordinates[7-i], 100, 100, this);
                }
            }
        }


        if (onClicked!=null) {// draws circles for available moves
            List<String> availableMoves=onClicked.availableMoves();
            for (String notes : availableMoves) {
                int x[]=board.getCoords(notes);
                g.drawImage(ImageOfPieces.imagesInstance.getOnClicked(), coordinates[x[0]], coordinates[7-x[1]], this);
            }
        }
        //System.out.println(counter++);
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

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getX()<845&&e.getY()<845) {//if mouse in the map
            int indexX=(e.getX()-45)/100;
            int indexY=7-(e.getY()-45)/100;


            if (onClicked!=null) {//if there is a picked piece

                moves=onClicked.availableMoves();
                String s=board.getNotation(indexX, indexY);

                if (moves.contains(s)) {//and clicked square in the picked pieces available moves

                    onClicked.moveOrCapture(indexX, indexY);//play
                    //let ai think
                    try {
                        artificialDecisions=new ArtificialIntelligence(board);
                        artificialDecisions.generateTree(1);
                    } catch (CloneNotSupportedException | IOException cloneNotSupportedException) {
                        cloneNotSupportedException.printStackTrace();
                    }
                    if(isAIon){

                        String chosenMove;
                       // chosenMove =ArtificialIntelligence.pickAndMoveRandomPiece(board);
                        chosenMove=artificialDecisions.getMove();
                        if(chosenMove==null){
                            return ;
                        }
                        board.moveFromNotation(chosenMove,true);



                    }

                }
                onClicked=null;



            } if (board.getStateOfBoard()[indexY][indexX]!=null&&board.turn*board.getStateOfBoard()[indexY][indexX].getTeam()==1) {//if clicked square has a piece//and if the clicked pieces turn
                onClicked=board.getStateOfBoard()[indexY][indexX];

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
