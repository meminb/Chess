import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtificialIntelligence {




    public static String pickAndMoveRandomPiece(Board board){

        List<String> allMoves=new ArrayList<>();


        for (Board.Pieces[] rows: board.stateOfBoard) {
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


        System.out.println(allMoves.toString());
        Random r=new Random();
        if(allMoves.size()==0){
            System.out.println("Check mate!");
            return null;
        }


        return allMoves.get(r.nextInt(allMoves.size()));

    }







}
