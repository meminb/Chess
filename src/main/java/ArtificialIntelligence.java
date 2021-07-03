
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtificialIntelligence {


     Tree possibilities;

    public ArtificialIntelligence(Board board) {

        this.possibilities = new Tree(board);


    }




    public  void generateTree(int depth) throws CloneNotSupportedException,IOException {

        rawCalculator(possibilities.getRoot(),depth);

    }

    public  String getMove(){
String bestMove=possibilities.getRoot().getMin();
        System.out.println(" _"+bestMove);
        return bestMove;

    }



    private  void rawCalculator(Tree.Node parent,int depth) throws CloneNotSupportedException, IOException {
        if (depth==0){
            return;
        }

        Board board=  parent.getData();

        List<String> allMoves=getAllPossibleMoves(board);

        System.out.println(allMoves.toString());

        for (String move: allMoves) {

            Board currentBoard= new Board();
            currentBoard.setStateOfBoard(board.getStateOfBoard());

           try{
                currentBoard.moveFromNotation(move,false);
                parent.setChild(currentBoard);




            }catch (NullPointerException e){ System.out.println(move);

               System.out.println(e);

            Chess win=new Chess(move+" current");

                win.setSize(865,895);

                GamePlay game=new GamePlay(true);
                game.board=currentBoard;
                game.addMouseListener(game);
                win.add(game);

                win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                win.setVisible(true);

               Chess win2=new Chess(move);

               win2.setSize(865,895);

               GamePlay game2=new GamePlay(true);
               game2.board=board;
               game2.addMouseListener(game2);
               win2.add(game2);

               win2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               win2.setVisible(true);
              /*  /*/ break;
            }


        }
        List<Tree.Node> children=parent.getChildren();


        for (Tree.Node nextParent: children) {

            rawCalculator(nextParent,depth-1);


        }

    }




    public  void refreshTree(){

    }





    public  List<String >getAllPossibleMoves(Board board){
        List<String> allMoves=new ArrayList<>();

        for (Board.Pieces[] rows: board.getStateOfBoard()) {
            for (Board.Pieces piece: rows) {
                if(piece==null|| board.turn!=piece.team){
                    continue;
                }
                List<String> movesForCurrentPiece=piece.availableMoves();

                for (String targetPosition: movesForCurrentPiece) {
                    allMoves.add(board.getNotation(piece.X, piece.Y)+"-"+targetPosition);
                }
            }
        }
        return  allMoves;
    }


    public  String pickAndMoveRandomPiece(Board board){


        List<String> allMoves=getAllPossibleMoves(board);


        System.out.println(allMoves.toString());
        Random r=new Random();
        if(allMoves.size()==0){
            System.out.println("Check mate!");
            return null;
        }


        return allMoves.get(r.nextInt(allMoves.size()));

    }







}








