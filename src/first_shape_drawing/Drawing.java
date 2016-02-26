package first_shape_drawing;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

enum DrawType {scribble, oval,  rectangle, polygon, line};

class DrawingProperties
{
    DrawType drawType;
    boolean filled;
    Color color;
    DrawingProperties(DrawType drawType, Color color, boolean filled)
    {
        this.drawType = drawType;
        this.color = color;
        this.filled = filled;
    }
    public String toString()
    {
        return drawType + " color=" + color +" filled="+ filled;
    }
}

public class Drawing {
    DrawingProperties drawingProperties = new DrawingProperties(DrawType.rectangle, Color.blue, false);
    ArrayList<Shape> shapeArr = new ArrayList<Shape>();
    Shape inProgress = null;
    
    public String toString()
    {
        return drawingProperties.toString();
    }
    public void draw(Graphics g)
    {
        for (int i=0; i < shapeArr.size(); i++)
        {
            Shape s = shapeArr.get(i);
            s.draw(g);
        }
        if (inProgress != null)
            inProgress.draw(g);
    }
    public void setColor(Color color)
    {
        drawingProperties.color = color;
    }

    public void setFilled(boolean filled)
    {
        drawingProperties.filled = filled;
    }
    public void setDrawType(DrawType drawType)
    {
        drawingProperties.drawType = drawType;
    }

    public void mousePressed(Point p)
    {
        switch(drawingProperties.drawType)
        {
        case rectangle:
            inProgress = new Rectangle(drawingProperties.color, drawingProperties.filled);
            break;
        case oval:
            inProgress = new Oval(drawingProperties.color, drawingProperties.filled);
            break;
        case line:
            inProgress = new Line(drawingProperties.color);
            break;
        case scribble:
            inProgress = new Scribble(drawingProperties.color);
            break;
        }
        inProgress.firstPoint(p);
    }

    public void mouseDragged(Point p)
    {
        switch(drawingProperties.drawType)
        {
        case rectangle:
        case oval:
        case scribble:
        case line:
            inProgress.subsequentPoint(p);
            break;
        }
    }
    public void mouseReleased(Point p)
    {
        inProgress.subsequentPoint(p);
        shapeArr.add(inProgress);
        inProgress = null;
    }

}

abstract class Shape
{
    Color color;
    Shape ( Color c)
    {
        color =c;
    }
    abstract void firstPoint(Point p);
    abstract void draw(Graphics g);
    abstract void subsequentPoint(Point p);
}
class Rectangle extends Shape
{
    boolean filled=false;
    Point start;
    Point lastPoint;
    Rectangle(Color c, boolean filled)
    {
        super(c);
        lastPoint = start;
        this.filled = filled;
    }
    
    void firstPoint(Point p) {
        start =p;
        lastPoint =p;
    }
    
    void draw(Graphics g) {
        g.setColor(color);
        int x = Math.min(start.x, lastPoint.x);
        int y = Math.min(start.y, lastPoint.y);
        int w = Math.abs(start.x - lastPoint.x);
        int h = Math.abs(start.y - lastPoint.y);
        if (filled)
            g.fillRect(x, y, w, h);
        else
            g.drawRect(x, y, w, h);
        
    }
    
    void subsequentPoint(Point p) {
        lastPoint =p;
        
    }
}

class Oval extends Shape {
	
	boolean filled = false;
	Point start, lastPoint;
	
	Oval(Color c, boolean filled){
		super(c);
		lastPoint = start;
		this.filled = filled;
	}
	
	void firstPoint(Point p){
		start = p;
		lastPoint = p;
	}
	
	void draw(Graphics g){
		g.setColor(color);
		int x = Math.min(start.x, lastPoint.x);
		int y = Math.min(start.y, lastPoint.y);
		int w = Math.abs(start.x - lastPoint.x);
		int h = Math.abs(start.y - lastPoint.y);
		if(filled) g.fillOval(x, y, w, h);
		else g.drawOval(x, y, w, h);
		
	}
	
	void subsequentPoint(Point p){
		lastPoint = p;
	}
}

class Line extends Shape{
	Point start;
	Point lastPoint;
	
	Line(Color c){
		super(c);
		lastPoint = start;
		
	}
	
	void firstPoint(Point p){
		start = p;
		lastPoint = p;
		
	}
	
	void draw(Graphics g){
		g.setColor(color);

		g.drawLine(start.x, start.y, lastPoint.x, lastPoint.y);
	}
	
	void subsequentPoint(Point p){
		lastPoint = p;
	}
}

class Scribble extends Shape
{
    ArrayList<Point> points= new ArrayList<Point>();
    Scribble(Color c)
    {
        super(c);
    }
    
    void firstPoint(Point p) {
        points.add(p);
        
    }
    
    void draw(Graphics g) {
        g.setColor(color);
        for (int i=1; i < points.size(); i++)
        {            
            Point first = points.get(i-1);
            Point next = points.get(i);
            g.drawLine(first.x, first.y, next.x, next.y);
        }
        
    }
    
    void subsequentPoint(Point p) {
        points.add(p);
        
    }
}
