import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Player {
	private int imageX;
	private int imageY;
	private int width;
	private int height;
	
	public Player(int imageXIn, int imageYIn, int widthIn, int heightIn) {
		imageX = imageXIn;
		imageY = imageYIn;
		width = widthIn;
		height = heightIn;
	}
	
	public void setX(int x) {
		imageX = x;
	}
	
	public void setY(int y) {
		imageY = y;
	}
	
	public int getX() {
		return imageX;
	}
	
	public int getY() {
		return imageY;
	}
	
	public int[] move(ArrayList<Integer> keysPressed) {
		int[] coordinates = new int[4];
		coordinates[0] = imageX;
		coordinates[1] = imageY;
		if (keysPressed.size() == 1) {
			if (keysPressed.get(0) == 37 || keysPressed.get(0)==39) {
				imageX+=3*(keysPressed.get(0) - 38);
			}
			
			if (keysPressed.get(0) == 38 || keysPressed.get(0) == 40) {
				imageY+=3*(keysPressed.get(0) - 39);
			}
		}
		
		if (keysPressed.size() == 2) {
			if(keysPressed.contains(37)) {
				imageX-=3;
			}
			if (keysPressed.contains(38)) {
				imageY-=3;
			}
			if (keysPressed.contains(39)) {
				imageX+=3;
			}
			if (keysPressed.contains(40)) {
				imageY+=3;
			}
		}
		coordinates[2] = imageX;
		coordinates[3] = imageY;
		return coordinates;
	}
	
	public void revert(int[] coordinates) {
		imageX = coordinates[0];
		imageY = coordinates[1];
	}
	
	public void render(Graphics2D g) {
		g.fillRect(imageX, imageY, width, height);
		g.setColor(Color.red.darker());
		g.setStroke(new BasicStroke(5));
		g.drawRect(imageX, imageY, width, height);
	}
	
//	public double euclidianDistance(int X, int Y, int circleX, int circleY, int radius) {
//		
//	}
	
//	public boolean checkCollision(Enemy enemy, int[] futureCoordinates) {
//	
	public boolean checkDone(Boundary boundary, int[] futureCoordinates) {
		int imageXFuture = futureCoordinates[0];
		int imageYFuture = futureCoordinates[1];
		if (imageXFuture < boundary.getX() + boundary.getWidth() &&
				   imageXFuture + width > boundary.getX() &&
				   imageYFuture < boundary.getY() + boundary.getHeight() &&
				   imageYFuture + height > boundary.getY()) {
				   return true;
				}
		return false;
	}
	
	public boolean checkCollision (Enemy enemy) {
//		int imageXFuture = futureCoordinates[0];
//		int imageYFuture = futureCoordinates[1];
		int radius = (int)(enemy.getRadius());
		int centerX = enemy.getX() + radius;
		int centerY = enemy.getY() + radius;
		int DeltaX =  centerX - Math.max(imageX, Math.min(centerX, imageX + width));
		int DeltaY = centerY - Math.max(imageY, Math.min(centerY, imageY + height));
		return (DeltaX * DeltaX + DeltaY * DeltaY) < (radius * radius);
	}
	
	public boolean checkCollision(Boundary boundary, int[] futureCoordinates) {
		int imageXFuture = futureCoordinates[0];
		int imageYFuture = futureCoordinates[1];
		if (imageXFuture < boundary.getX() + boundary.getWidth() &&
				   imageXFuture + width > boundary.getX() &&
				   imageYFuture < boundary.getY() + boundary.getHeight() &&
				   imageYFuture + height > boundary.getY()) {
//			System.out.println("left collision: " + );
//				   System.out.println("1: " + (imageX < boundary.getX() + boundary.getWidth()));
//				   System.out.println("2: " + (imageX + width > boundary.getX()));
//				   System.out.println("3: " + (imageY < boundary.getY() + boundary.getHeight()));
//				   System.out.println("4: " + (imageY + height > boundary.getY()));
				   return true;
				}
		return false;
	}
}
