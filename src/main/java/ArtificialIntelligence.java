
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtificialIntelligence {


     Tree possibilities;

     private int depth;

    public ArtificialIntelligence(Board board,int depth) throws IOException, CloneNotSupportedException {
        this.depth=depth;
        this.possibilities = new Tree(board);
        generateTree();

    }




    public  void generateTree() throws CloneNotSupportedException,IOException {

        rawCalculator(possibilities.getRoot(),depth);

    }

    public Tree.Node getMove(){
        //String bestMove=possibilities.getRoot().getMin();



        //System.out.println(" _"+bestMove);
        //return possibilities.miniMax(null,0,depth);
         return possibilities.minimaxAlphaBeta(depth);
        // return possibilities.getRoot().getMin();

    }



    public Tree.Node generateTreeAndGetMiniMax(Tree.Node parent,int depth){



return null;
    }



    private  void rawCalculator(Tree.Node parent,int depth) throws CloneNotSupportedException, IOException {
        if (depth==0){
            return;
        }

        Board board=  parent.getData();

        List<String> allMoves=getAllPossibleMoves(board);

        //System.out.println(allMoves.toString());

        for (String move: allMoves) {

            Board currentBoard= (Board) board.clone();
            currentBoard.setStateOfBoard(board.getStateOfBoard());
            //currentBoard.turn= currentBoard.turn*-1;

                currentBoard.moveFromNotation(move,false);
                parent.setChild(currentBoard);

        }
        List<Tree.Node> children=parent.getChildren();


        for (Tree.Node nextParent: children) {

            rawCalculator(nextParent,depth-1);


        }

    }




    public  void refreshTree(){

    }

public void setNewRoot(Tree.Node root){this.possibilities.setRoot(root);}



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








