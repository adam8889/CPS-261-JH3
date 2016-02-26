package first_shape_drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import javax.swing.JFrame;

public class DrawingProgram extends JFrame implements Runnable{

    Drawing drawing = new Drawing();
    Image offScreenImage = null;
    Dimension screenDimension = null;

    // INNER Class
    class MyMouseHandler extends MouseAdapter
    {        
        public void mousePressed(MouseEvent e)
        {
            drawing.mousePressed(e.getPoint());
            repaint();
        }
        public void mouseReleased(MouseEvent e)
        {
            drawing.mouseReleased(e.getPoint());
            repaint();
        }
        public void mouseDragged(MouseEvent e)
        {
            drawing.mouseDragged(e.getPoint());
            repaint();
        }
    }
    
    public void run(){
    	while(true){
    		try{
    			Thread.sleep(20);
    			repaint();
    		}
    		catch(InterruptedException e){
    			
    		}
    	}
    }

    DrawingProgram()
    {
        super("My Drawing Program");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyMouseHandler mmh = new MyMouseHandler();
        addMouseListener(mmh);
        addMouseMotionListener(mmh);
        
        Thread thread = new Thread(this);
        thread.start();

        setVisible(true);
    }
    public void paint(Graphics screen)
    {
        Dimension dimen = getSize();
        if(offScreenImage == null || !dimen.equals(screenDimension)){
        	screenDimension = dimen;
        	offScreenImage = createImage(dimen.width, dimen.height);
        }
        Graphics g = offScreenImage.getGraphics();
        
        FontMetrics fm = getFontMetrics(g.getFont());        
        Insets insets = getInsets();
        String str = drawing.toString();
        int top = insets.top;
        int left = insets.left;
		int fontHeight = fm.getHeight();
		int maxAscent = fm.getMaxAscent();
		int strWidth = fm.stringWidth(str);
        g.setColor(Color.white);
        g.fillRect(0, 0, dimen.width, dimen.height);
        g.setColor(Color.yellow);
        g.fillRect(left, top, dimen.width - insets.right, fontHeight);
        g.setColor(Color.black);
        g.drawString(str, dimen.width/2 - strWidth / 2, top + maxAscent);

        drawing.draw(g);
        
        screen.drawImage(offScreenImage, 0, 0, this);
    }

    public static void main(String[] args) {

        DrawingProgram dp = new DrawingProgram();
        Scanner keyboard = new Scanner(System.in);

        boolean continueFlag=true;
        while(continueFlag)
        {
            System.out.println("Cmds: r,o,l,s,p,a,q,?,f,d,b,m,g");
            String str = keyboard.next().toLowerCase();
            if (str.length() == 0) continue;

            switch(str.charAt(0))
            {
            case 'r':
                dp.drawing.setDrawType(DrawType.rectangle);
                break;
            case 'o':
                dp.drawing.setDrawType(DrawType.oval);
                break;
            case 'l':
                dp.drawing.setDrawType(DrawType.line);
                break;
            case 's':
                dp.drawing.setDrawType(DrawType.scribble);
                break;
            case 'p':
            case 'a':
                dp.drawing.setDrawType(DrawType.polygon);
                break;
            case 'q':
                continueFlag = false;
                break;
            case 'f':
                dp.drawing.setFilled(true);
                break;
            case 'd':
                dp.drawing.setFilled(false);
                break;
            case 'b':
                dp.drawing.setColor(Color.blue);
                break;
            case 'm':
                dp.drawing.setColor(Color.magenta);
                break;
            case 'g':
                dp.drawing.setColor(Color.green);
                break;
            default: // '?' comes here
                System.out.println("r - drawType= Rectangle");
                System.out.println("o - drawType= Oval");
                System.out.println("l - drawType= Line");
                System.out.println("s - drawType= Scribble");
                System.out.println("p - drawType= Polygon");
                System.out.println("a - another Polygon");
                System.out.println("q - quit");
                System.out.println("f - filled objects");
                System.out.println("d - draw objects (not filled)");
                System.out.println("b - Use Blue Color");
                System.out.println("m - Use magenta Color");
                System.out.println("g - Use Green Color");
                break;
            }
        }
        System.out.println("Exitting the Drawing Program");
        dp.dispose();
        keyboard.close();

    }

}
