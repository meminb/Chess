import java.util.ArrayList;
import java.util.List;

public class Tree {
    private Node root;


    public Tree(Board board) {
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


    public Node minimaxAlphaBeta(int maxDepth) {

        return miniMaxWithPruning(this.getRoot(), 0, maxDepth, -9999, 9999);
    }


    private Node miniMaxWithPruning(Node parent, int depth, int maxDepth, int alpha, int beta) {

        int minEval = 9999;
        int maxEval = -9999;

        if (depth + 1 == maxDepth && parent.getChildren().size() > 0) {// it is a leaf
            if (depth % 2 == 0) {//black's turn
                parent.setMiniMaxValue(parent.getMin().getData().calculateValueOfBoard());
            } else {//white's turn
                parent.setMiniMaxValue(parent.getMax().getData().calculateValueOfBoard());
            }
            return parent;
        }


        Node selectedNode = null;
        if (depth % 2 == 1) {//White's turn ==> maximize
            for (Node child : parent.getChildren()) {


                selectedNode = miniMaxWithPruning(child, depth + 1, maxDepth, alpha, beta);
                int value = selectedNode.getMiniMaxValue();

                if (maxEval < value) {
                    maxEval = value;
                }
                if (alpha < value) {
                    alpha = value;
                }
                if (beta <= alpha) {
                    break;
                }
            }


            parent.setMiniMaxValue(maxEval);
            return parent;
        } else {          // Black's turn ==> minimizing


            Node bestNode = null;
            for (Node child : parent.getChildren()) {

                selectedNode = miniMaxWithPruning(child, depth + 1, maxDepth, alpha, beta);
                int value = selectedNode.getMiniMaxValue();

                if (minEval > value) {
                    minEval = value;
                    bestNode = selectedNode;
                }
                if (beta > value) {
                    beta = value;
                }
                if (beta <= alpha) {
                    break;
                }

            }
            if (depth == 0) {
                // System.out.println(bestNode+" "+ selectedNode);
                return bestNode;
            }
            parent.setMiniMaxValue(minEval);
            return parent;

        }
    }


    public Node miniMax(Node parent, int depth, int maxDepth) {
        if (parent == null) {
            parent = root;
        }
        for (Node child : parent.getChildren()) {

            if (depth < maxDepth - 1) {
                miniMax(child, depth + 1, maxDepth);
            }


        }
        if (depth + 1 == maxDepth) {// it is a leaf
            if (depth % 2 == 0) {//black's turn
                parent.setMiniMaxValue(parent.getMin().getData().calculateValueOfBoard());
            } else {//white's turn
                parent.setMiniMaxValue(parent.getMax().getData().calculateValueOfBoard());
            }

        } else {

            if (depth % 2 == 0) {//black's turn
                parent.setMiniMaxValue(parent.getMinOfMiniMax());
            } else {//white's turn
                parent.setMiniMaxValue(parent.getMaxOfMiniMax());
            }
        }
        if (depth == 0) {
            int minimumMiniMax = 999;
            Node minimumNode = null;

            for (Node node : parent.getChildren()) {
                if (minimumMiniMax > node.getMiniMaxValue()) {
                    minimumMiniMax = node.getMiniMaxValue();
                    minimumNode = node;


                }

            }
            return minimumNode;
        }
        return null;
    }


    public class Node {
        private int miniMaxValue;
        private Board board;
        private Node parent;
        private List<Node> children;

        Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;
            children = new ArrayList<>();
        }

        public Node() {

        }


        public int getMaxOfMiniMax() {

            int maxValue = -999;
            int currentValue;

            for (Node node : children) {

                currentValue = node.getMiniMaxValue();

                if (currentValue > maxValue) {
                    maxValue = currentValue;
                }
            }
            return maxValue;
        }


        public int getMinOfMiniMax() {

            int minValue = 999;
            int currentValue;
            for (Node node : children) {

                currentValue = node.getMiniMaxValue();

                if (currentValue < minValue) {
                    minValue = currentValue;
                }
            }
            return minValue;
        }


        public Tree.Node getMax() {

            Node maxNode = null;
            int maxValue = -999;
            int currentValue;

            for (Node node : children) {

                currentValue = node.getData().calculateValueOfBoard();

                if (currentValue > maxValue) {
                    maxValue = currentValue;
                    maxNode = node;
                }


            }
            return maxNode;
        }


        public Node getMin() {

            Node minNode = null;
            int minValue = 999;
            int currentValue;
            for (Node node : children) {

                currentValue = node.getData().calculateValueOfBoard();

                if (currentValue < minValue) {
                    minValue = currentValue;
                    minNode = node;
                    // System.out.print(move+":"+ node.board.calculateValueOfBoard()+"  // ");
                }
            }
            //System.out.println("**"+move+"  "+minNode.getData().notationOfLastMove);
            return minNode;
        }


        public void setMiniMaxValue(int miniMaxValue) {
            this.miniMaxValue = miniMaxValue;
        }

        public int getMiniMaxValue() {
            return miniMaxValue;
        }

        public Node(Board board) {

            this.board = board;
        }

        public void setChild(Board board) {
            children.add(new Node(board, this));

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

        public void setBoard(Board board) {
            this.board = board;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }
    }

}