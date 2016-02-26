package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JFrame;

public class ChessProgram extends JFrame{

    ChessProgram()
    {
        super("Chess Program");
        setSize(800, 600);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ChessPiece.readInImages(); 
        setVisible(true);//It's important to wait to make your screen visible AFTER reading 
                         // in any images needed in the paint method.  In the video, I didn't 
                         // do it this way and it seemed to work.  But it won't always work 
                         // unless the setVisible call follows reading in the appropriate images. 
    }
    
    public void paint(Graphics g)
    {      
       super.paint(g);
       Insets insets = getInsets();
       int top = insets.top;
       int bottom = insets.bottom;
       int left = insets.left;
       int right = insets.right;
       
       int height = getHeight();
       int width = getWidth();
       
       int cell_h = (height-top-bottom)/8;
       int cell_w = (width-left-right)/8;
       
       BoardDimensions boardDimensions = new BoardDimensions(left, top, cell_w, cell_h);
      
       int x,y;
       
       for (int row=0; row < 8; row++)
       {
           y = top + row*cell_h;
           for (int col=0; col < 8; col++)
           {
               x = left + col*cell_w;
               boolean greenColor = (row+col) %2 == 1;
               if (greenColor)
               {
                   g.setColor(Color.green);
               }
               else
               {
                   g.setColor(Color.white);
               }
               g.fillRect(x, y, cell_w, cell_h);
           }
       }
       
       for (int col=0; col < 8; col++)
       {
           Piece p = new Piece(PieceType.Pawn, ColorType.black, col, 1);
           p.drawInPosition(g, boardDimensions);
           
           p = new Piece(PieceType.Pawn, ColorType.white, col, 6);
           p.drawInPosition(g, boardDimensions);
       }
       
       for(int col = 0; col < 8; col++){
    	   PieceType[] kingrow = {PieceType.Rook, PieceType.Knight, PieceType.Bishop, PieceType.Queen,
    			   PieceType.King, PieceType.Bishop, PieceType.Knight, PieceType.Rook};
    	   
    	   Piece p = new Piece(kingrow[col], ColorType.black, col, 0);
    	   p.drawInPosition(g, boardDimensions);
    	   
    	   p = new Piece(kingrow[col], ColorType.white, col, 7);
    	   p.drawInPosition(g, boardDimensions);
       }         
    }

    public static void main(String[] args) {
        ChessProgram cp = new ChessProgram();

    }

}
