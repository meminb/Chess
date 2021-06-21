import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageOfPieces {

    public static ImageOfPieces imagesInstance=new ImageOfPieces();

   private  BufferedImage board;
    private BufferedImage BishopBlack;
    private BufferedImage BishopWhite;
    private  BufferedImage KnightBlack;
    private  BufferedImage KnightWhite;
    private  BufferedImage RookBlack;
    private  BufferedImage RookWhite;
    private  BufferedImage KingBlack;
    private  BufferedImage KingWhite;
    private  BufferedImage QueenBlack;
    private  BufferedImage QueenWhite;
    private  BufferedImage PawnWhite;
    private BufferedImage PawnBlack;
    private BufferedImage OnClicked;
    private BufferedImage Checked;

public ImageOfPieces(){

    try {
        board = ImageIO.read(new FileImageInputStream(new File("Board.jpg")));
        BishopBlack = ImageIO.read(new FileImageInputStream(new File("BishopBlack.png")));
        BishopWhite = ImageIO.read(new FileImageInputStream(new File("BishopWhite.png")));
        KnightBlack = ImageIO.read(new FileImageInputStream(new File("KnightBlack.png")));
        KnightWhite = ImageIO.read(new FileImageInputStream(new File("KnightWhite.png")));
        RookBlack = ImageIO.read(new FileImageInputStream(new File("RookBlack.png")));
        RookWhite = ImageIO.read(new FileImageInputStream(new File("RookWhite.png")));
        KingBlack = ImageIO.read(new FileImageInputStream(new File("KingBlack.png")));
        KingWhite = ImageIO.read(new FileImageInputStream(new File("KingWhite.png")));
        QueenBlack = ImageIO.read(new FileImageInputStream(new File("QueenBlack.png")));
        QueenWhite = ImageIO.read(new FileImageInputStream(new File("QueenWhite.png")));
        PawnWhite = ImageIO.read(new FileImageInputStream(new File("PawnWhite.png")));
        PawnBlack = ImageIO.read(new FileImageInputStream(new File("PawnBlack.png")));
        OnClicked = ImageIO.read(new FileImageInputStream(new File("OnClicked.png")));
        //Check=ImageIO.read(new FileImageInputStream(new File("check.png")));
        Checked = ImageIO.read(new FileImageInputStream(new File("Check.png")));
    } catch (Exception e) {
        System.out.println("Couldn't find path");
    }


}

    public BufferedImage getBoard() {
        return board;
    }

    public BufferedImage getBishopBlack() {
        return BishopBlack;
    }

    public BufferedImage getBishopWhite() {
        return BishopWhite;
    }

    public BufferedImage getKnightBlack() {
        return KnightBlack;
    }

    public BufferedImage getKnightWhite() {
        return KnightWhite;
    }

    public BufferedImage getRookBlack() {
        return RookBlack;
    }

    public BufferedImage getRookWhite() {
        return RookWhite;
    }

    public BufferedImage getKingBlack() {
        return KingBlack;
    }

    public BufferedImage getKingWhite() {
        return KingWhite;
    }

    public BufferedImage getQueenBlack() {
        return QueenBlack;
    }

    public BufferedImage getQueenWhite() {
        return QueenWhite;
    }

    public BufferedImage getPawnWhite() {
        return PawnWhite;
    }

    public BufferedImage getPawnBlack() {
        return PawnBlack;
    }

    public BufferedImage getOnClicked() {
        return OnClicked;
    }

    public BufferedImage getChecked() {
        return Checked;
    }
}
