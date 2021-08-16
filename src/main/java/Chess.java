import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Chess extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public Chess (String title) throws HeadlessException {
        super(title);
    }


    public static void main(String[] args) throws CloneNotSupportedException, IOException {

        Chess win=new Chess("chess");

        win.setSize(865,895);

        GamePlay game=new GamePlay(true,3);
        game.addMouseListener(game);
        win.add(game);

        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);



    }





}
