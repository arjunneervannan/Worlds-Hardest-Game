import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy {
	private int width;
	private int height;
	private int imageX;
	private int imageY;
	private int startX;
	private int endX;
	private int startY;
	private int endY;
	private int directionX;
	private int directionY;
	
	public Enemy(int widthIn, int heightIn, int imageXIn, int imageYIn, int startXIn, int endXIn, int startYIn, int endYIn) {
		width = widthIn;
		height = heightIn;
		imageX = imageXIn;
		imageY = imageYIn;
		directionX = 0;
		directionY = 0;
		startX = startXIn;
		endX = endXIn;
		startY = startYIn;
		endY = endYIn;
	}
	
	public void move(int ticks) {
//		imageX = (int)(400*(1-Math.abs((ticks/50.)%2-1)));
//		if (imageX > 500) {
//			imageX = imageX - 
//		}
//		imageX = 1-Math.abs(ticks%200)
		if (imageX >= startX && imageX <= startX) {
			directionX = 5;
		} else if (imageX >= endX && imageX <= endX) {
			directionX = -5;
		} else if (imageX == startX - 1 && imageX == endX + 1) {
			directionX = 0;
		}
//		} else {
//			direction 
//		}
//		
		if (imageY > startY && imageY < startY) {
			directionY = 5;
		} else if (imageY > endY && imageY < endY) {
			directionY = -5;
		} else if (imageY == startY && imageY == endY){
			directionY = 0;
		}		
		
		imageX+=directionX;
		imageY+=directionY;
	}
	
	public void render (Graphics2D g) {
		g.fillOval(imageX, imageY, width, height);
		g.setColor(Color.blue.darker());
		g.setStroke(new BasicStroke(5));
		g.drawOval(imageX, imageY, width, height);
		g.setColor(Color.blue);
	}
	
	public int getX() {
		return imageX;
	}
	
	public int getY() {
		return imageY;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public double getRadius() {
		return (width/2.0);
	}
}
