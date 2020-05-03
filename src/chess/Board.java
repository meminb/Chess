/*
* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

/**
 *
 * @author muham
 */
public class Board {
    static Pieces[] testPieces;
    public Pieces lastMoved;//last moved piece
    public String lastMove;//last moved place
    
    
    Hashtable<Integer, Pieces> kings;
    boolean isCheck;
    int turn;//white's turn -> 1
    private BufferedImage board;
    BufferedImage BishopBlack;
    BufferedImage BishopWhite;
    BufferedImage KnightBlack;
    BufferedImage KnightWhite;
    BufferedImage RookBlack;
    BufferedImage RookWhite;
    BufferedImage KingBlack;
    BufferedImage KingWhite;
    BufferedImage QueenBlack;
    BufferedImage QueenWhite;
    BufferedImage PawnWhite;
    BufferedImage PawnBlack; 
    BufferedImage OnClicked; 
    //BufferedImage Check; 
    BufferedImage Checked; 
    
    Pieces diMap[][];
    
    public Board(){
        isCheck=false;
        turn=1;
       diMap=new Pieces[8][8];
       lastMove=null;
       lastMoved=null;
       
        try {
            board=ImageIO.read(new FileImageInputStream(new File("Board.jpg")));
            BishopBlack=ImageIO.read(new FileImageInputStream(new File("BishopBlack.png")));
            BishopWhite=ImageIO.read(new FileImageInputStream(new File("BishopWhite.png")));
            KnightBlack=ImageIO.read(new FileImageInputStream(new File("KnightBlack.png")));
            KnightWhite=ImageIO.read(new FileImageInputStream(new File("KnightWhite.png")));
            RookBlack=ImageIO.read(new FileImageInputStream(new File("RookBlack.png")));
            RookWhite=ImageIO.read(new FileImageInputStream(new File("RookWhite.png")));
            KingBlack=ImageIO.read(new FileImageInputStream(new File("KingBlack.png")));
            KingWhite=ImageIO.read(new FileImageInputStream(new File("KingWhite.png")));
            QueenBlack=ImageIO.read(new FileImageInputStream(new File("QueenBlack.png")));
            QueenWhite=ImageIO.read(new FileImageInputStream(new File("QueenWhite.png")));
            PawnWhite=ImageIO.read(new FileImageInputStream(new File("PawnWhite.png")));
            PawnBlack=ImageIO.read(new FileImageInputStream(new File("PawnBlack.png")));
            OnClicked=ImageIO.read(new FileImageInputStream(new File("OnClicked.png")));
            //Check=ImageIO.read(new FileImageInputStream(new File("check.png")));
            Checked=ImageIO.read(new FileImageInputStream(new File("Check.png")));
        } catch (Exception e) {
            System.out.println("bulunamadı");
        }
        
        diMap[6][0]=new Pawn( -1,0,6);
        diMap[6][1]=new Pawn( -1,1,6);
        diMap[6][2]=new Pawn( -1,2,6);
        diMap[6][3]=new Pawn( -1,3,6);
        diMap[6][4]=new Pawn( -1,4,6);
        diMap[6][5]=new Pawn( -1,5,6);
        diMap[6][6]=new Pawn( -1,6,6);
        diMap[6][7]=new Pawn( -1,7,6);
        
        diMap[7][0]=new Rook(-1, 0, 7);
        diMap[7][1]=new Knight(-1, 1, 7);
        diMap[7][2]=new Bishop(-1, 2, 7);
        diMap[7][3]=new Queen(-1, 3, 7);
         diMap[7][6]=new Knight(-1, 6, 7);
        diMap[7][5]=new Bishop(-1, 5, 7);
        diMap[7][7]=new Rook(-1, 7, 7);
        diMap[7][4]=new KING(-1, 4, 7);
        
  
         
        diMap[1][0]=new Pawn( 1,0,1);
        diMap[1][1]=new Pawn( 1,1,1);
        diMap[1][2]=new Pawn( 1,2,1);
        diMap[1][3]=new Pawn( 1,3,1);
        diMap[1][4]=new Pawn( 1,4,1);
        diMap[1][5]=new Pawn( 1,5,1);
        diMap[1][6]=new Pawn( 1,6,1);
        diMap[1][7]=new Pawn( 1,7,1);
        diMap[0][0]=new Rook(1, 0, 0);
        diMap[0][1]=new Knight(1, 1, 0);
        diMap[0][2]=new Bishop(1, 2, 0);
        diMap[0][3]=new Queen(1, 3, 0);
        diMap[0][4]=new KING(1, 4, 0);
        diMap[0][6]=new Knight(1, 6, 0);
        diMap[0][5]=new Bishop(1, 5, 0);
        diMap[0][7]=new Rook(1, 7, 0);
        
        kings=new Hashtable<>();//dictionary for the kings,-1 is black because -1 turn is blacks turn...
        kings.put(1, diMap[0][4]);
        kings.put(-1, diMap[7][4]);
    
        
        
        
        
        //diMap[0][0]=new Knight(1,0, 0);
        
         testPieces=new Pieces[]{new Pawn(1, 0, 0),new Knight(1, 0, 0),new Bishop(1, 0, 0),new Rook(1, 0, 0),new Queen(1, 0, 0)};//test object for testing check
     
        
    }
    
    public BufferedImage getBoard() {
        return board;
    }
    
    public String getNotation(int x,int y){
        String s="abcdefgh";
        return s.charAt(x)+String.valueOf(y+1);
    }
    
    public int[] getCoords(String s){//converts chess notation to coordinates
      int[] ar=new int[2];
      String  row="abcdefgh";
      
      ar[0]=row.indexOf(s.charAt(0));
      ar[1]=Character.getNumericValue(s.charAt(1)-1);
      
      return ar;
  }
    

    
    public boolean virtualCheck(int x,int y){// can any opposite piece captuer x,y?
        for (Pieces test : testPieces) {
            test.X=x; test.Y=y; test.team=turn;
            ArrayList<String> moves =test.checkMovesFor();
            for (String move : moves) {
                int[] i =getCoords(move);
                if (diMap[i[1]][i[0]]!=null) {
                
                    if (diMap[i[1]][i[0]].getClass().equals(test.getClass())) {   
                        return true;
                    }
                
                }
            }
        
        }
        return false;
    }
    
     public boolean check(int team){//that changes already created piece object's coordinates and team to king which checking for Check and use this object's checkMovesFor method for checking Check
              int x=kings.get(team).X;
              int y =kings.get(team).Y;
        
       return virtualCheck(x, y);
    }  
    
    
    
    
    
   //discovered check
    
    

    
    
    
    
class Pieces {
    
    private boolean hasMoved;
        
    public int team;
    private boolean onClick;
    private BufferedImage img;
    public int X;
    public int Y;
    
    
    public Pieces(int side,int x,int y){
        this.hasMoved=false;
        this.X=x;
        this.Y=y;
        onClick=false;
        this.team=side;
    }

    
    public int getTeam() {
        return team;
    }
    
    public boolean isOnClick() {
        return onClick;
    }

    public void setOnClick(boolean onClick) {
        this.onClick = onClick;
    }

    public BufferedImage getImg() {
            return img;
        }

    public void setImg(BufferedImage img) {
            this.img = img;
        }
    
    public boolean isHasMoved() {
            return hasMoved;
        }

    public void setHasMoved(boolean hasMoved) {
            this.hasMoved = hasMoved;
        }

    
    public boolean gettingOutOfCheck(String targetNotation){//eğer this.piece belirtilen kareye oynar ise şahtan kurtulunur mu veya,bu taş açmazda mı
   
        int[] coords=getCoords(targetNotation);
     
        
        Pieces target=diMap[coords[1]][coords[0]];
        
        int tempX=this.X;
        int tempY=this.Y;
        
        diMap[Y][X]=null;
        
        this.X=coords[0];
        this.Y=coords[1];
        
        diMap[coords[1]][coords[0]]=this;
        boolean canMove=check(team);
        
        this.X=tempX;
        this.Y=tempY;
        diMap[coords[1]][coords[0]]=target;
        diMap[Y][X]=this;
     
        return !canMove;
     
    }
    
   public ArrayList<String> checkMovesFor(){ 
       ArrayList<String> moves=new ArrayList<>();
       
   return moves;
   }
   
   public  ArrayList<String> AvailableMoves(){//if a move cause of check,that s gonna be removed
     ArrayList<String> moves=checkMovesFor();
       
     
       for (int i = 0; i < moves.size(); i++) {
            if (!gettingOutOfCheck(moves.get(i))) {
                moves.remove(i);
                i--;
            } 
       }
        
       
       return moves;
   }
   

       
    public boolean moveOrCapture(int targetX,int targetY) {//moving an object
     
        
      if (diMap[targetY][targetX]==null||this.team*diMap[targetY][targetX].team==-1) {//if target possition is empty or there is an enemy piece
        lastMove=getNotation(X,Y);
    	int tempx=X;
        int tempy=Y;
        setHasMoved(true);
        diMap[targetY][targetX]=this;//new placement for moved object
        diMap[tempy][tempx]=null;//delete the moved object
        diMap[targetY][targetX].X=targetX;
        diMap[targetY][targetX].Y=targetY;
        lastMoved=this;
        turn*=-1;//change turn
        isCheck=check((turn));
        return true;
        }
      
      return false;
      
  }
    
    public ArrayList<String> moveOfDiagonal(){
        ArrayList<String> moves =new ArrayList<>();
        boolean nortWest=true,southWest=true,southEast=true,northEast=true;
        for (int i = 1; i < 7; i++) {
             
            if ((X+i)<8&&Y+i<8&&nortWest) {//northwest
                
                if (diMap[Y+i][X+i]==null||diMap[Y+i][X+i].getTeam()*this.getTeam()==-1) {//
                    moves.add(getNotation(X+i, Y+i));
                }
                if (diMap[Y+i][X+i]!=null) {// if there is a piece at checking square,dont need to check other squares of diagonal
                    nortWest=false;
                }
                
            }
            if ((X+i)<8&&Y-i>=0&&southWest) {//southhwest
                if (diMap[Y-i][X+i]==null||diMap[Y-i][X+i].getTeam()*this.getTeam()==-1) {
                    moves.add(getNotation(X+i, Y-i));
                }
                if (diMap[Y-i][X+i]!=null) {
                    southWest=false;
                }
            }
            if ((X-i)>=0&&Y-i>=0&&southEast) {//southeast
                if (diMap[Y-i][X-i]==null||diMap[Y-i][X-i].getTeam()*this.getTeam()==-1) {
                    moves.add(getNotation(X-i, Y-i));
                }
                if (diMap[Y-i][X-i]!=null) {
                    southEast=false;
                }
            }
            if ((X-i)>=0&&Y+i<8&&northEast) {//northeast
                if (diMap[Y+i][X-i]==null||diMap[Y+i][X-i].getTeam()*this.getTeam()==-1) {
                    moves.add(getNotation(X-i, Y+i));
                }
                if (diMap[Y+i][X-i]!=null) {
                    northEast=false;
                }
            }
        }
        return moves;
        
    }
    
    public ArrayList<String> MoveofGambits(){
        ArrayList<String> moves =new ArrayList<>();
        boolean north=true,West=true,south=true,East=true;
        
        for (int i = 1; i < 8; i++) {
             
            if (Y+i<8&&north) {//north
                
                if (diMap[Y+i][X]==null||diMap[Y+i][X].getTeam()*this.getTeam()==-1) {//
                    moves.add(getNotation(X, Y+i));
                }
                if (diMap[Y+i][X]!=null) {// if there is a piece at checking square,dont need to check other squares of diagonal
                    north=false;
                }
                
            }
            if ((X+i)<8&&West) {//west
                if (diMap[Y][X+i]==null||diMap[Y][X+i].getTeam()*this.getTeam()==-1) {
                    moves.add(getNotation(X+i, Y));
                }
                if (diMap[Y][X+i]!=null) {
                    West=false;
                }
            }
            if (Y-i>=0&&south) {//south
                if (diMap[Y-i][X]==null||diMap[Y-i][X].getTeam()*this.getTeam()==-1) {
                    moves.add(getNotation(X, Y-i));
                }
                if (diMap[Y-i][X]!=null) {
                    south=false;
                }
            }
            if ((X-i)>=0&&East) {//east
                if (diMap[Y][X-i]==null||diMap[Y][X-i].getTeam()*this.getTeam()==-1) {
                    moves.add(getNotation(X-i, Y));
                }
                if (diMap[Y][X-i]!=null) {
                    East=false;
                }
            }            
        }
        
        
        return moves;
    }
    
    
   /**
      @Override
    protected Object clone() throws CloneNotSupportedException {
        Pieces p=(Pieces)super.clone();
        return p; //To change body of generated methods, choose Tools | Templates.
    }**/  
}


class Pawn extends Pieces{
    
    ArrayList<String> availableMoves=new ArrayList<>();
    boolean  enPassantMove;
   
    
    public Pawn(int side,int x,int  y) {
        super(side,x,y);
        setImg((side==1)?PawnWhite:PawnBlack);
        enPassantMove=false;
        
    }
    
 
    
    @Override
    public boolean moveOrCapture(int targetX, int targetY) {
    	
    		if (enPassantMove&&lastMoved.Y-targetY==-team) {
				diMap[lastMoved.Y][lastMoved.X]=null;
			}
    	
    	boolean b=super.moveOrCapture(targetX, targetY);
    	
    	if ((team==-1&&targetY==0)||(team==1&&targetY==7)) {
			diMap[targetY][targetX]=new Queen(team, targetX, targetY);
		}
    	isCheck=check((turn));
    	return b;
    }
    
            
    
    @Override
    public ArrayList<String> checkMovesFor() {
        ArrayList<String> moves=new ArrayList<>();
      try {
        if(diMap[this.Y+this.getTeam()][this.X]==null){//if front square is null
            moves.add(getNotation(this.X, this.Y+this.getTeam()));
            if(isHasMoved()==false&&diMap[this.Y+this.getTeam()*2][this.X]==null  ){//if the piece haven't moved yet,can play 2 square
            moves.add(getNotation(this.X, this.Y+this.getTeam()*2));
            }
       
        }
         
        } catch (NullPointerException e) {
        }catch (ArrayIndexOutOfBoundsException e) {
        }
      
      
      
      try {
         if (diMap[this.Y+this.getTeam()][this.X+this.getTeam()].team*this.getTeam()==-1) {//capturing situations
                moves.add(getNotation(this.X+this.getTeam(), this.Y+this.getTeam()));
            }
       
      } catch (NullPointerException e) {
      }catch (ArrayIndexOutOfBoundsException e) {
      }
      
        try {
            if (diMap[this.Y+this.getTeam()][this.X-this.getTeam()].team*this.getTeam()==-1) {//capturing situations  2
                 moves.add(getNotation(this.X-this.getTeam(), this.Y+this.getTeam()));
            }
         
        } catch (NullPointerException e) {
        }catch (ArrayIndexOutOfBoundsException e) {
        }
        
        
        try {//enPassant
        	int[] i=getCoords(lastMove);
        	if ((diMap[Y][X+1].equals(lastMoved))
						&&Math.abs(lastMoved.Y-i[1])==2) {//if last moved piece is a pawn and placed 2 square
						moves.add(getNotation(lastMoved.X, lastMoved.Y-lastMoved.team));
						enPassantMove=true;
						System.out.println("girdi");
				}else {
					enPassantMove=false;
				}
			
        	
		} catch (Exception e) {
		}
        try {//enPassant
        	int[] i=getCoords(lastMove);
        	if ((diMap[Y][X-1].equals(lastMoved))
						&&Math.abs(lastMoved.Y-i[1])==2) {//if last moved piece is a pawn and placed 2 square
						moves.add(getNotation(lastMoved.X, lastMoved.Y-lastMoved.team));
						enPassantMove=true;
						System.out.println("girdi");
				}else {
					enPassantMove=false;
				}
			
        	
		} catch (Exception e) {
		}
        
        
        return moves;
    }
}


class Knight extends Pieces{
    int[][] steps;
    
        public Knight(int side, int x, int y) {
            super(side, x, y);
            setImg((side==1)?KnightWhite:KnightBlack);
            steps=new int[][]{{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1}};
        }

        @Override
        public ArrayList<String> checkMovesFor() {
            ArrayList<String> moves=new ArrayList<>();
            for (int[] step : steps) {
                int checkingX=this.X+step[0];
                int checkingY=this.Y+step[1];
                
                try {
                    if (diMap[checkingY][checkingX]==null||diMap[checkingY][checkingX].getTeam()*this.getTeam()==-1) {//checking coords
                        moves.add(getNotation(checkingX, checkingY));
                    } 
                } catch (IndexOutOfBoundsException e) {
                }
            }
            return moves;
        }
         
}


class Bishop extends  Pieces{

        public Bishop(int side, int x, int y) {
            super(side, x, y);
             setImg((side==1)?BishopWhite:BishopBlack);
        }
    
        
        @Override
        public ArrayList<String> checkMovesFor (){
            ArrayList<String> moves=moveOfDiagonal();
            
            
            return moves;
        }
        
}


class Rook extends  Pieces{

        public Rook(int side, int x, int y) {
            super(side, x, y);
            setImg((side==1)?RookWhite:RookBlack);
        }

        @Override
        public ArrayList<String> checkMovesFor() {
            ArrayList<String> moves=MoveofGambits();
            return  moves;
        } 

        
}


class Queen extends Pieces{

        public Queen(int side, int x, int y) {
            super(side, x, y);
             setImg((side==1)?QueenWhite:QueenBlack);
        }

        @Override
        public ArrayList<String> checkMovesFor() {
           ArrayList<String> moves=MoveofGambits(); 
           ArrayList<String> demo=moveOfDiagonal(); 
           moves.addAll(demo);
           return moves;
            
        }
        
        
    
}


class KING extends Pieces{
      int[][] m;
      
      ArrayList<String> movesForCastling;
    
      
        public KING(int side, int x, int y) {
            super(side, x, y);
            setImg((side>0)?KingWhite:KingBlack);//king's teams are not "1" and "-1",their teams are "99" and "-99" because they can not be captured
            m=new int[][]{{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1},{1,0}};//moves for king
            movesForCastling=new ArrayList<>();
        }

        
        
        
        
        
        @Override
        public ArrayList<String> checkMovesFor() {
           
            ArrayList<String> moves=new ArrayList<>();
            
            
           
            for (int[] step : m) {
                int checkingX=this.X+step[0];
                int checkingY=this.Y+step[1];
                
                try {
                    if (diMap[checkingY][checkingX]==null||diMap[checkingY][checkingX].getTeam()*this.getTeam()==-1) {//checking coords
                        moves.add(getNotation(checkingX, checkingY));
                    } 
                } catch (IndexOutOfBoundsException e) {
                }
            }
            try {
                moves.addAll(castling());
            } catch (NullPointerException e) {
            }
            
            return moves;
            
        }

        
        
        public ArrayList<String> castling(){
            movesForCastling.clear();
            
             boolean longCastling=true,shortCastling=true;
             if (isHasMoved()) {
                return null;
              }
             
            for (int i = 1; i < 4; i++) {
                if (i<3&&(shortCastling&&diMap[Y][X+i]!=null||(virtualCheck(X+i, Y)))) {
                        shortCastling=false;
                    
                }
                if (longCastling&&diMap[Y][X-i]!=null||(i<3&&virtualCheck(X-i, Y))) {
                    longCastling=false;
                } 
            }
          try { 
           if (diMap[Y][X+3].isHasMoved()) {
                shortCastling=false;
            }
           if (diMap[Y][X-4].isHasMoved()) {
                shortCastling=false;
            }
          } catch (NullPointerException e) {}
            
            if (shortCastling) {
                movesForCastling.add(getNotation(X+2, Y));
            }
            if (longCastling) {
                movesForCastling.add(getNotation(X-2, Y));
            }
            
            return movesForCastling;
        }
        
        

        @Override
        public boolean moveOrCapture(int targetX, int targetY) {
               if (movesForCastling.contains(getNotation(targetX, targetY))) {//if clicked place is castling place
                
                if (targetX==6) {//short castling
                    
                    diMap[Y][5]=diMap[Y][7];
                    diMap[Y][5].X=5;
                    diMap[Y][7]=null;
                }
                 if (targetX==2) {//long castling
                    
                    diMap[Y][3]=diMap[Y][0];
                    diMap[Y][3].X=3;
                    diMap[Y][0]=null;
                }
                
                
            }
            
            
            
            return super.moveOrCapture(targetX, targetY); //To change body of generated methods, choose Tools | Templates.
        }
 
        
        
        
        
}


  


}


