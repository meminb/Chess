/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author muham
 */
public class Board implements Cloneable {


    private Pieces stateOfBoard[][];// pieces of all board
    Pieces lastMoved;//last moved piece
    String notationOfLastMove;//last moved place
    Hashtable<Integer, Pieces> kings;
    boolean isCheck;
    int turn;//white's turn -> 1


    public Board() {
        isCheck = false;
        turn = 1;
        stateOfBoard = new Pieces[8][8];
        notationOfLastMove = null;
        lastMoved = null;

        stateOfBoard[6][0] = new Pawn(-1, 0, 6);
        stateOfBoard[6][1] = new Pawn(-1, 1, 6);
        stateOfBoard[6][2] = new Pawn(-1, 2, 6);
        stateOfBoard[6][3] = new Pawn(-1, 3, 6);
        stateOfBoard[6][4] = new Pawn(-1, 4, 6);
        stateOfBoard[6][5] = new Pawn(-1, 5, 6);
        stateOfBoard[6][6] = new Pawn(-1, 6, 6);
        stateOfBoard[6][7] = new Pawn(-1, 7, 6);

        stateOfBoard[7][0] = new Rook(-1, 0, 7);
        stateOfBoard[7][1] = new Knight(-1, 1, 7);
        stateOfBoard[7][2] = new Bishop(-1, 2, 7);
        stateOfBoard[7][3] = new Queen(-1, 3, 7);
        stateOfBoard[7][6] = new Knight(-1, 6, 7);
        stateOfBoard[7][5] = new Bishop(-1, 5, 7);
        stateOfBoard[7][7] = new Rook(-1, 7, 7);
        stateOfBoard[7][4] = new KING(-1, 4, 7);

        stateOfBoard[1][0] = new Pawn(1, 0, 1);
        stateOfBoard[1][1] = new Pawn(1, 1, 1);
        stateOfBoard[1][2] = new Pawn(1, 2, 1);
        stateOfBoard[1][3] = new Pawn(1, 3, 1);
        stateOfBoard[1][4] = new Pawn(1, 4, 1);
        stateOfBoard[1][5] = new Pawn(1, 5, 1);
        stateOfBoard[1][6] = new Pawn(1, 6, 1);
        stateOfBoard[1][7] = new Pawn(1, 7, 1);
        stateOfBoard[0][0] = new Rook(1, 0, 0);
        stateOfBoard[0][1] = new Knight(1, 1, 0);
        stateOfBoard[0][2] = new Bishop(1, 2, 0);
        stateOfBoard[0][3] = new Queen(1, 3, 0);
        stateOfBoard[0][4] = new KING(1, 4, 0);
        stateOfBoard[0][6] = new Knight(1, 6, 0);
        stateOfBoard[0][5] = new Bishop(1, 5, 0);
        stateOfBoard[0][7] = new Rook(1, 7, 0);

        kings = new Hashtable<>();//dictionary for the kings,-1 is black because -1 turn is blacks turn...
        kings.put(1, stateOfBoard[0][4]);
        kings.put(-1, stateOfBoard[7][4]);

        //stateOfBoard[0][0]=new Knight(1,0, 0);

    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int calculateValueOfBoard() {
        // TODO dolas puan topla
        int totalValue = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieces piece = stateOfBoard[i][j];
                if (piece == null) {
                    continue;
                }
                totalValue += piece.getValue() * piece.team;
            }
        }
        //System.out.println("The total value of board is: " + totalValue);
        return totalValue;
    }

    public void setStateOfBoard(Pieces[][] pieces) throws CloneNotSupportedException {
        Pieces[][] ownState = new Pieces[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {


                Pieces piece = pieces[i][j];

                //Pieces piece=(Board.Pieces)pieces[i][j].clone();
                Pieces clonePiece;

                if (piece instanceof Pawn) {
                    clonePiece = new Pawn(piece.team, piece.X, piece.Y);
                    clonePiece.hasMoved=piece.hasMoved;

                } else if (piece instanceof Rook) {
                    clonePiece = new Rook(piece.team, piece.X, piece.Y);clonePiece.hasMoved=piece.hasMoved;
                } else if (piece instanceof Knight) {
                    clonePiece = new Knight(piece.team, piece.X, piece.Y);clonePiece.hasMoved=piece.hasMoved;
                } else if (piece instanceof Bishop) {
                    clonePiece = new Bishop(piece.team, piece.X, piece.Y);clonePiece.hasMoved=piece.hasMoved;
                } else if (piece instanceof Queen) {
                    clonePiece = new Queen(piece.team, piece.X, piece.Y);clonePiece.hasMoved=piece.hasMoved;
                } else if (piece instanceof KING) {
                    clonePiece = new KING(piece.team, piece.X, piece.Y);clonePiece.hasMoved=piece.hasMoved;
                } else {
                    clonePiece = null;
                }


                ownState[i][j] = clonePiece;


            }
        }
        this.stateOfBoard = ownState;

    }

    public Pieces[][] getStateOfBoard() {
        return stateOfBoard;
    }

    public BufferedImage getBoard() {
        return ImageOfPieces.imagesInstance.getBoard();
    }


    public String getNotation(int x, int y) {
        String s = "abcdefgh";
        return s.charAt(x) + String.valueOf(y + 1);
    }

    public int[] getCoords(String s) {//converts chess notation to coordinates
        int[] ar = new int[2];
        String row = "abcdefgh";

        ar[0] = row.indexOf(s.charAt(0));
        ar[1] = Character.getNumericValue(s.charAt(1) - 1);

        return ar;
    }

    public boolean virtualCheck(int x, int y) {// can any opposite piece capture x,y?
        Pieces[] testPieces;// pieces that test for virtualization checks for the king moves
        testPieces = new Pieces[]{
                new Pawn(1, 0, 0),
                new Knight(1, 0, 0),
                new Bishop(1, 0, 0),
                new Rook(1, 0, 0),
                new Queen(1, 0, 0)};//test object for testing check

        for (Pieces test : testPieces) {
            test.X = x;
            test.Y = y;
            test.team = turn;
            ArrayList<String> moves = test.checkMovesFor();
           // System.out.println(test.getClass()+" "+x+y+moves);
            for (String move : moves) {
                int[] i = getCoords(move);
                if (stateOfBoard[i[1]][i[0]] != null) {

                    if (stateOfBoard[i[1]][i[0]].getClass().equals(test.getClass())) {
                        return true;
                    }

                }
            }

        }
        return false;
    }

    //discovered check
    public boolean check(int team) {//that changes already created piece object's coordinates and team to king which checking for Check and use this object's checkMovesFor method for checking Check
        int x = kings.get(team).X;
        int y = kings.get(team).Y;

        return virtualCheck(x, y);
    }


    public void moveFromNotation(String command, boolean ifReal) {
        String[] commands = command.split("-");

        int[] currentSquare = getCoords(commands[0]);
        int[] targetSquare = getCoords(commands[1]);

        if (ifReal) {
            //System.out.println(command);
          //  System.out.println(currentSquare[0] + " " + currentSquare[1]);
           // System.out.println(targetSquare[0] + " " + targetSquare[1]);
        }
        stateOfBoard[currentSquare[1]][currentSquare[0]].moveOrCapture(targetSquare[0], targetSquare[1]);


    }


    class Pieces implements Cloneable {

        private boolean hasMoved;
        public int team;
        public int X;
        public int Y;


        public Pieces(int side, int x, int y) {
            this.hasMoved = false;
            this.X = x;
            this.Y = y;
            this.team = side;
        }

        public int getValue() {
            return 0;
        }

        public int getTeam() {
            return team;
        }


        public BufferedImage getImg() {
            return null;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public boolean isHasMoved() {
            return hasMoved;
        }

        public void setHasMoved(boolean hasMoved) {
            this.hasMoved = hasMoved;
        }

        public boolean gettingOutOfCheck(String targetNotation) {//eğer this.piece belirtilen kareye oynar ise şahtan kurtulunur mu veya,bu taş açmazda mı

            int[] coords = getCoords(targetNotation);

            Pieces target = stateOfBoard[coords[1]][coords[0]];

            int tempX = this.X;
            int tempY = this.Y;

            stateOfBoard[Y][X] = null;

            this.X = coords[0];
            this.Y = coords[1];

            stateOfBoard[coords[1]][coords[0]] = this;
            boolean canMove = check(team);

            this.X = tempX;
            this.Y = tempY;
            stateOfBoard[coords[1]][coords[0]] = target;
            stateOfBoard[Y][X] = this;

            return !canMove;

        }

        public ArrayList<String> checkMovesFor() {

            return new ArrayList<>();
        }

        public ArrayList<String> availableMoves() {//if a move cause of check,that s gonna be removed
            ArrayList<String> moves = checkMovesFor();

            for (int i = 0; i < moves.size(); i++) {
                if (!gettingOutOfCheck(moves.get(i))) {
                    moves.remove(i);
                    i--;
                }
            }

            return moves;
        }

        public boolean moveOrCapture(int targetX, int targetY) {//moving an object
            
            try{// TODO: 3.07.2021 remove here too
                if (stateOfBoard[targetY][targetX] == null || this.team * stateOfBoard[targetY][targetX].team == -1) {//if target possition is empty or there is an enemy piece
                    notationOfLastMove = getNotation(X, Y) + "-" + getNotation(targetX, targetY);
                    int tempx = X;
                    int tempy = Y;
                    setHasMoved(true);
                    stateOfBoard[targetY][targetX] = this;//new placement for moved object
                    stateOfBoard[tempy][tempx] = null;//delete the moved object
                    stateOfBoard[targetY][targetX].X = targetX;
                    stateOfBoard[targetY][targetX].Y = targetY;
                    lastMoved = this;
                    turn *= -1;//change turn
                    isCheck = check((turn));
                    calculateValueOfBoard();
                    return true;
                }
            }catch (Exception e){
                System.out.println(targetX+"-"+targetY);
            }
        

            return false;

        }

        public ArrayList<String> moveOfDiagonal() {
            ArrayList<String> moves = new ArrayList<>();
            boolean nortWest = true, southWest = true, southEast = true, northEast = true;
            for (int i = 1; i < 7; i++) {

                if ((X + i) < 8 && Y + i < 8 && nortWest) {//northwest

                    if (stateOfBoard[Y + i][X + i] == null || stateOfBoard[Y + i][X + i].getTeam() * this.getTeam() == -1) {//
                        moves.add(getNotation(X + i, Y + i));
                    }
                    if (stateOfBoard[Y + i][X + i] != null) {// if there is a piece at checking square,dont need to check other squares of diagonal
                        nortWest = false;
                    }

                }
                if ((X + i) < 8 && Y - i >= 0 && southWest) {//southhwest
                    if (stateOfBoard[Y - i][X + i] == null || stateOfBoard[Y - i][X + i].getTeam() * this.getTeam() == -1) {
                        moves.add(getNotation(X + i, Y - i));
                    }
                    if (stateOfBoard[Y - i][X + i] != null) {
                        southWest = false;
                    }
                }
                if ((X - i) >= 0 && Y - i >= 0 && southEast) {//southeast
                    if (stateOfBoard[Y - i][X - i] == null || stateOfBoard[Y - i][X - i].getTeam() * this.getTeam() == -1) {
                        moves.add(getNotation(X - i, Y - i));
                    }
                    if (stateOfBoard[Y - i][X - i] != null) {
                        southEast = false;
                    }
                }
                if ((X - i) >= 0 && Y + i < 8 && northEast) {//northeast
                    if (stateOfBoard[Y + i][X - i] == null || stateOfBoard[Y + i][X - i].getTeam() * this.getTeam() == -1) {
                        moves.add(getNotation(X - i, Y + i));
                    }
                    if (stateOfBoard[Y + i][X - i] != null) {
                        northEast = false;
                    }
                }
            }
            return moves;

        }

        public ArrayList<String> MoveofGambits() {
            ArrayList<String> moves = new ArrayList<>();
            boolean north = true, West = true, south = true, East = true;

            for (int i = 1; i < 8; i++) {

                if (Y + i < 8 && north) {//north
                  //  System.out.println(Y+i+" "+X+" "+ i);
                    if (stateOfBoard[Y + i][X] == null || stateOfBoard[Y + i][X].getTeam() * this.getTeam() == -1) {//
                        moves.add(getNotation(X, Y + i));
                    }
                    if (stateOfBoard[Y + i][X] != null) {// if there is a piece at checking square,dont need to check other squares of diagonal
                        north = false;
                    }

                }
                if ((X + i) < 8 && West) {//west
                    if (stateOfBoard[Y][X + i] == null || stateOfBoard[Y][X + i].getTeam() * this.getTeam() == -1) {
                        moves.add(getNotation(X + i, Y));
                    }
                    if (stateOfBoard[Y][X + i] != null) {
                        West = false;
                    }
                }
                if (Y - i >= 0 && south) {//south
                    if (stateOfBoard[Y - i][X] == null || stateOfBoard[Y - i][X].getTeam() * this.getTeam() == -1) {
                        moves.add(getNotation(X, Y - i));
                    }
                    if (stateOfBoard[Y - i][X] != null) {
                        south = false;
                    }
                }
                if ((X - i) >= 0 && East) {//east
                    if (stateOfBoard[Y][X - i] == null || stateOfBoard[Y][X - i].getTeam() * this.getTeam() == -1) {
                        moves.add(getNotation(X - i, Y));
                    }
                    if (stateOfBoard[Y][X - i] != null) {
                        East = false;
                    }
                }
            }

            return moves;
        }

        /**
         * @Override protected Object clone() throws CloneNotSupportedException
         * { Pieces p=(Pieces)super.clone(); return p; //To change body of
         * generated methods, choose Tools | Templates.
        }*
         */
    }

    class Pawn extends Pieces {

        ArrayList<String> availableMoves = new ArrayList<>();
        boolean enPassantMove;

        public Pawn(int side, int x, int y) {
            super(side, x, y);
            //setImg((side == 1) ? ImageOfPieces.imagesInstance.getPawnWhite() : ImageOfPieces.imagesInstance.getPawnBlack());
            enPassantMove = false;

        }

        @Override
        public BufferedImage getImg() {
            return (this.team == 1) ? ImageOfPieces.imagesInstance.getPawnWhite() : ImageOfPieces.imagesInstance.getPawnBlack();
        }

        @Override
        public int getValue() {
            return 1;
        }

        @Override
        public boolean moveOrCapture(int targetX, int targetY) {

            if (enPassantMove && lastMoved.Y - targetY == -team) {
                stateOfBoard[lastMoved.Y][lastMoved.X] = null;
            }

            boolean b = super.moveOrCapture(targetX, targetY);

            if ((team == -1 && targetY == 0) || (team == 1 && targetY == 7)) {
                stateOfBoard[targetY][targetX] = new Queen(team, targetX, targetY);
            }
            isCheck = check((turn));
            return b;
        }

        @Override
        public ArrayList<String> checkMovesFor() {
            ArrayList<String> moves = new ArrayList<>();
            try {
                if (stateOfBoard[this.Y + this.getTeam()][this.X] == null) {//if front square is null
                    moves.add(getNotation(this.X, this.Y + this.getTeam()));
                    if (!isHasMoved() && stateOfBoard[this.Y + this.getTeam() * 2][this.X] == null) {//if the piece haven't moved yet,can play 2 square
                        moves.add(getNotation(this.X, this.Y + this.getTeam() * 2));
                    }

                }

            } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {
            }

            try {
                if (stateOfBoard[this.Y + this.getTeam()][this.X + this.getTeam()].team * this.getTeam() == -1) {//capturing situations
                    moves.add(getNotation(this.X + this.getTeam(), this.Y + this.getTeam()));
                }

            } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {
            }

            try {
                if (stateOfBoard[this.Y + this.getTeam()][this.X - this.getTeam()].team * this.getTeam() == -1) {//capturing situations  2
                    moves.add(getNotation(this.X - this.getTeam(), this.Y + this.getTeam()));
                }

            } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {
            }

            try {//enPassant
                int[] i = getCoords(notationOfLastMove.split("-")[1]);
                if ((stateOfBoard[Y][X + 1].equals(lastMoved))
                        && Math.abs(lastMoved.Y - i[1]) == 2) {//if last moved piece is a pawn and placed 2 square
                    moves.add(getNotation(lastMoved.X, lastMoved.Y - lastMoved.team));
                    enPassantMove = true;
                    System.out.println("girdi");
                } else {
                    enPassantMove = false;
                }

            } catch (Exception ignored) {
            }
            try {//enPassant
                int[] i = getCoords(notationOfLastMove.split("-")[1]);
                if ((stateOfBoard[Y][X - 1].equals(lastMoved))
                        && Math.abs(lastMoved.Y - i[1]) == 2) {//if last moved piece is a pawn and placed 2 square
                    moves.add(getNotation(lastMoved.X, lastMoved.Y - lastMoved.team));
                    enPassantMove = true;
                    System.out.println("girdi");
                } else {
                    enPassantMove = false;
                }

            } catch (Exception ignored) {
            }

            return moves;
        }
    }

    class Knight extends Pieces {

        int[][] steps;

        public Knight(int side, int x, int y) {
            super(side, x, y);
            // setImg((side == 1) ? ImageOfPieces.imagesInstance.getKnightWhite() : ImageOfPieces.imagesInstance.getKnightBlack());
            steps = new int[][]{{1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}};
        }

        @Override
        public BufferedImage getImg() {
            return (this.team == 1) ? ImageOfPieces.imagesInstance.getKnightWhite() : ImageOfPieces.imagesInstance.getKnightBlack();
        }

        @Override
        public int getValue() {
            return 3;
        }

        @Override
        public ArrayList<String> checkMovesFor() {
            ArrayList<String> moves = new ArrayList<>();
            for (int[] step : steps) {
                int checkingX = this.X + step[0];
                int checkingY = this.Y + step[1];

                try {
                    if (stateOfBoard[checkingY][checkingX] == null || stateOfBoard[checkingY][checkingX].getTeam() * this.getTeam() == -1) {//checking coords
                        moves.add(getNotation(checkingX, checkingY));
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            return moves;
        }

    }

    class Bishop extends Pieces {

        public Bishop(int side, int x, int y) {
            super(side, x, y);
            //setImg((side == 1) ? ImageOfPieces.imagesInstance.getBishopWhite() : ImageOfPieces.imagesInstance.getBishopBlack());
        }

        @Override
        public BufferedImage getImg() {
            return (this.team == 1) ? ImageOfPieces.imagesInstance.getBishopWhite() : ImageOfPieces.imagesInstance.getBishopBlack();
        }


        @Override
        public int getValue() {
            return 3;
        }

        @Override
        public ArrayList<String> checkMovesFor() {

            return moveOfDiagonal();
        }

    }

    class Rook extends Pieces {

        public Rook(int side, int x, int y) {
            super(side, x, y);
            // setImg((side == 1) ? ImageOfPieces.imagesInstance.getRookWhite() : ImageOfPieces.imagesInstance.getRookBlack());
        }

        @Override
        public BufferedImage getImg() {
            return (this.team == 1) ? ImageOfPieces.imagesInstance.getRookWhite() : ImageOfPieces.imagesInstance.getRookBlack();
        }


        @Override
        public int getValue() {
            return 5;
        }

        @Override
        public ArrayList<String> checkMovesFor() {
            return MoveofGambits();
        }

    }

    class Queen extends Pieces {

        public Queen(int side, int x, int y) {
            super(side, x, y);
            //  setImg((side == 1) ? ImageOfPieces.imagesInstance.getQueenWhite() : ImageOfPieces.imagesInstance.getQueenBlack());
        }

        @Override
        public BufferedImage getImg() {
            return (this.team == 1) ? ImageOfPieces.imagesInstance.getQueenWhite() : ImageOfPieces.imagesInstance.getQueenBlack();
        }


        @Override
        public int getValue() {
            return 9;
        }

        @Override
        public ArrayList<String> checkMovesFor() {
            ArrayList<String> moves = MoveofGambits();
            ArrayList<String> demo = moveOfDiagonal();
            moves.addAll(demo);
            return moves;

        }

    }

    class KING extends Pieces {

        int[][] m;
        
        ArrayList<String> movesForCastling;

        public KING(int side, int x, int y) {
            super(side, x, y);
            
            // setImg((side > 0) ? ImageOfPieces.imagesInstance.getKingWhite() : ImageOfPieces.imagesInstance.getKingBlack());
            //king's teams are not "1" and "-1",their teams are "99" and "-99" because they can not be captured
            m = new int[][]{{1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}};//moves for king
            movesForCastling = new ArrayList<>();
        }

        @Override
        public BufferedImage getImg() {
            return (this.team == 1) ? ImageOfPieces.imagesInstance.getKingWhite() : ImageOfPieces.imagesInstance.getKingBlack();
        }

        @Override
        public ArrayList<String> checkMovesFor() {

            ArrayList<String> moves = new ArrayList<>();

            for (int[] step : m) {
                int checkingX = this.X + step[0];
                int checkingY = this.Y + step[1];

                try {
                    if (stateOfBoard[checkingY][checkingX] == null || stateOfBoard[checkingY][checkingX].getTeam() * this.getTeam() == -1) {//checking coords
                        moves.add(getNotation(checkingX, checkingY));
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            try {
                moves.addAll(castling());
            } catch (NullPointerException ignored) {
            }

            return moves;

        }

        public ArrayList<String> castling() {
            movesForCastling.clear();

            boolean longCastling = true, shortCastling = true;
            if (isHasMoved()) {
                return null;
            }

            for (int i = 1; i < 4; i++) {
                try{// TODO: 3.07.2021 remove try catch 
                    if (i < 3 && (shortCastling && stateOfBoard[Y][X + i] != null || (virtualCheck(X + i, Y)))) {
                        shortCastling = false;

                    }
                    if (longCastling && stateOfBoard[Y][X - i] != null || (i < 3 && virtualCheck(X - i, Y))) {
                        longCastling = false;
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println(e +"  "+ isHasMoved() +i+" "+ X);
                    
                }
               
            }
            try {
                if (stateOfBoard[Y][X + 3].isHasMoved()) {
                    shortCastling = false;
                }
                if (stateOfBoard[Y][X - 4].isHasMoved()) {
                    shortCastling = false;
                }
            } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {
            }

            if (shortCastling) {
                movesForCastling.add(getNotation(X + 2, Y));
            }
            if (longCastling) {
                movesForCastling.add(getNotation(X - 2, Y));
            }

            return movesForCastling;
        }

        @Override
        public boolean moveOrCapture(int targetX, int targetY) {
            if (movesForCastling.contains(getNotation(targetX, targetY))) {//if clicked place is castling place

                if (targetX == 6) {//short castling

                    stateOfBoard[Y][5] = stateOfBoard[Y][7];
                    stateOfBoard[Y][5].X = 5;
                    stateOfBoard[Y][7] = null;
                }
                if (targetX == 2) {//long castling

                    stateOfBoard[Y][3] = stateOfBoard[Y][0];
                    stateOfBoard[Y][3].X = 3;
                    stateOfBoard[Y][0] = null;
                }

            }

            return super.moveOrCapture(targetX, targetY); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
