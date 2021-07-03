import java.util.ArrayList;
import java.util.List;

public class  Tree{
    private Node root;


    public Tree( Board board) {
        root = new Node(board);
        root.board = board;
        root.children = new ArrayList<Node>();
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }





    public  class Node {
        private Board board;
        private Node parent;
        private List<Node> children;

         Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;children=new ArrayList<>();
        }


        public String getMax(){

             String move=null;
             int maxValue=-999;
            int currentValue;
            for (Node node:children) {
                 currentValue=node.getData().calculateValueOfBoard();
                if(currentValue>maxValue){
                    maxValue=currentValue;

                    move=node.getData().notationOfLastMove;
                }



            }
             return move;
        }
        public String getMin(){

            String move=null;
            int minValue=999;
            int currentValue;
            for (Node node:children) {
                 currentValue=node.getData().calculateValueOfBoard();
                if(currentValue < minValue){
                    minValue=currentValue;
                    move=node.getData().notationOfLastMove;
                    System.out.print(move+":"+ node.board.calculateValueOfBoard()+"  // ");
                }



            }
            return move;
        }



        public Node(Board board) {

            this.board = board;
        }

        public void setChild(Board board){
            children.add(new Node(board,this));

        }

        public Board getData() {
            return board;
        }

        public Node getParent() {
            return parent;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }
    }

}