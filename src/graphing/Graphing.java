package graphing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;

public class Graphing extends JFrame {
	
	ArrayList<Data> dataArr = new ArrayList<Data>();
	
	private class Data{
		String name;
		int value;
		
		private Data(String name, int value){
			this.name = name;
			this.value = value;
		}
	}

	public Graphing(String filename){
		setTitle(readFile(filename)); //should take in first line from txt file?
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Dimension dimen = getSize();
		Insets insets = getInsets();
		int top = insets.top;
		int left = insets.left;
		int right = insets.right;
		
		Font font = g.getFont();
		FontMetrics fm = getFontMetrics(font);
		int fontHeight = fm.getHeight();
		int maxAscent = fm.getMaxAscent();
		
		int strMaxWidth = left + getMaxTextWidth(dataArr, fm);
		int xBarStart = strMaxWidth + 1;
		
		int barMaxValue = getMaxBarWidth(dataArr);
		double scale = (dimen.width - xBarStart - right) / (double) barMaxValue;
		
		int yStart = top;
		int barHeight = fontHeight;
		
		for(int i = 0; i < dataArr.size(); i++){
			String name = dataArr.get(i).name;
			int strWidth = fm.stringWidth(name);
			int value = dataArr.get(i).value;
			int scaledValue = (int)(value * scale);
			g.drawString(name, strMaxWidth - strWidth, yStart + maxAscent);
			g.fillRect(xBarStart, yStart, scaledValue, barHeight);
			
			yStart += fontHeight + 10;
		}
		
		g.drawLine(strMaxWidth, top, strMaxWidth, dimen.height);
		
	}
	
	private String readFile(String filename){
		Scanner inputStream = null;
		String title = "";
		
		try{
			inputStream = new Scanner(new FileInputStream(filename));
			title = inputStream.nextLine();
			generateData(inputStream);
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		finally{
			if(inputStream != null) inputStream.close();
		}
		
		return title;
	}
	
	private void generateData(Scanner scan){
		String name;
		int value;
		StringTokenizer st;
		
		while(scan.hasNextLine()){
			st = new StringTokenizer(scan.nextLine(), ";");
			name = st.nextToken().trim();
			value = Integer.parseInt(st.nextToken().trim());
			dataArr.add(new Data(name, value));
		}
	}
	
	private int getMaxTextWidth(ArrayList<Data> datArr, FontMetrics fm){
		int maxWidth = 0, width = 0;
		
		for(int i = 0; i < datArr.size(); i++){
			width = fm.stringWidth(datArr.get(i).name);
			if(width > maxWidth) maxWidth = width;
		}
		return maxWidth;
	}
	
	private int getMaxBarWidth(ArrayList<Data> datArr){
		int maxVal = 0, val;
		
		for(int i = 0; i < datArr.size(); i++){
			val = datArr.get(i).value;
			if(val > maxVal) maxVal = val;
		}
		
		return maxVal;
	}
	
	public static void main(String args[]){
		Graphing g = new Graphing(args[0]);
	}
}
