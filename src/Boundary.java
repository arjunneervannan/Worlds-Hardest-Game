import java.awt.Graphics2D;

public class Boundary {
	private int height;
	private int width;
	private int xPos;
	private int yPos;
	
	public Boundary (int widthIn, int heightIn, int xPosIn, int yPosIn) {
		height = heightIn;
		width = widthIn;
		xPos = xPosIn;
		yPos = yPosIn;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void render (Graphics2D g) {
		g.fillRect(xPos, yPos, width, height);
	}
}
