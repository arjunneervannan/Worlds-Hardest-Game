import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Canvas extends JComponent{
	private final int WIDTH;
	private final int HEIGHT;
	private int score;
	private Player player;
	private Enemy enemy;
	private ArrayList<Boundary> boundariesLevel1;
	private ArrayList<Boundary> boundariesLevel2;
	private ArrayList<Enemy> enemiesLevel1;
	private ArrayList<Enemy> enemiesLevel2;
	private boolean gameOver;
	private boolean gameFinished;
	private boolean nextLevel;
	private boolean restart;
	private int level;
	private int numFails;
	Boundary finishedLevel1;
	
	private boolean doneLoading = false;
	
	public Canvas(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		player = new Player(125, 237, 30, 30);
		boundariesLevel1 = new ArrayList<Boundary>();
		boundariesLevel2 = new ArrayList<Boundary>();
		enemiesLevel1 = new ArrayList<Enemy>();
		enemiesLevel1.add(new Enemy(20, 20, 245, 160, 245, 545, 160, 160));
		enemiesLevel1.add(new Enemy(20, 20, 545, 200, 245, 545, 200, 200));
		enemiesLevel1.add(new Enemy(20, 20, 245, 240, 245, 545, 240, 240));
		enemiesLevel1.add(new Enemy(20, 20, 545, 280, 245, 545, 280, 280));
		enemiesLevel1.add(new Enemy(20, 20, 245, 320, 245, 545, 320, 320));
		
		boundariesLevel1.add(new Boundary(5, 285, 80, 110)); // left starting area boundary
		boundariesLevel1.add(new Boundary(125, 5, 80, 110)); // top starting area boundary
		boundariesLevel1.add(new Boundary(5, 245, 205, 110)); // right starting area boundary
		boundariesLevel1.add(new Boundary(210, 5, 80, 395)); // bottom starting area boundary
		boundariesLevel1.add(new Boundary(40, 5, 205, 355)); // bridge 1 top boundary
		boundariesLevel1.add(new Boundary(5, 45, 285, 355)); // bridge 1 right boundary
		boundariesLevel1.add(new Boundary(5, 205, 240, 150)); // main area left boundary
		boundariesLevel1.add(new Boundary(285, 5, 240, 150)); // main area top boundary
		boundariesLevel1.add(new Boundary(285, 5, 285, 355)); // main area bottom boundary
		boundariesLevel1.add(new Boundary(5, 205, 565, 150)); // main area right boundary
		boundariesLevel1.add(new Boundary(5, 45, 520, 110)); // bridge 2 left boundary
		boundariesLevel1.add(new Boundary(40, 5, 565, 150)); // bridge 2 bottom boundary
		boundariesLevel1.add(new Boundary(210, 5, 520, 110)); // ending area top boundary
		boundariesLevel1.add(new Boundary(5, 285, 725, 110)); // ending area right boundary
		boundariesLevel1.add(new Boundary(5, 245, 600, 150)); // ending area area left boundary
		boundariesLevel1.add(new Boundary(125, 5, 600, 390)); // ending area bottom boundary
		
		boundariesLevel2.add(new Boundary(45, 45, 45, 45));
		
		finishedLevel1 = new Boundary(120, 282, 605, 115);
//		finishedLevel1 = new Boundary(120, 282, 110, 115);
		numFails = 0;
		
		gameOver = false;
		gameFinished = false;
		restart = false;
		nextLevel = false;
		level = 1;
	}
	
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		ArrayList<Boundary> boundaries = new ArrayList<Boundary>();
		enemies = enemiesLevel1;
		boundaries = boundariesLevel1;
		boolean drawSquare = false;
//		switch (level) {
//			case 1:
//				enemies = enemiesLevel1;
//				boundaries = boundariesLevel1;
//				break;
//			case 2:
//				enemies = enemiesLevel2;
//				boundaries = boundariesLevel2;
//				break;
//		}

		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				if ((i+j)%2==0) {
					g.setColor(Color.WHITE.brighter());
				} else {
					Color c = new Color(224, 218, 254);
					g.setColor(c);
				}
				if (j < 9 && j > 3 && i > 5 && i < 14) {
					drawSquare = true;
				} else if (j == 9 && i == 6) {
					drawSquare = true;
				} else if (j == 9 && i == 5){
					drawSquare = true;
				} else if (i == 14 && j == 3) {
					drawSquare = true;
				} else if (i == 13 && j == 3){
					drawSquare = true;
				} else {
					drawSquare = false;
				}
				
				if (drawSquare) {
					g.fillRect(40*i + 5, 40*j - 5,40,40);
				}
			}
		}
		g.setColor(new Color(158, 242, 155));
		g.fillRect(85, 115, 120, 280);
		g.fillRect(605, 115, 120, 280);
		
		g.setColor(Color.RED);
		player.render(g);
		
		g.setColor(Color.BLUE);

		for (Enemy enemy : enemies) {
			enemy.render(g);
		}
		g.setColor(Color.BLACK);
		for (Boundary boundary : boundaries) {
			boundary.render(g);
//			}
		}
		g.fillRect(0, 0, 800, 50);
		Font f = new Font("Arial", Font.PLAIN, 28);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString("LEVEL: 1", 20, 39);
		g.drawString("FAILS: " + numFails, 650, 39);
		
		if (gameFinished) {
			g.setColor(Color.WHITE);
//			g.fillRect(300, 275, 200, 50);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.BLACK);
			g.drawString("Congrats, you beat level 1", 225, 250);
		}
		
		if (restart) {
			gameOver = false;
			gameFinished = false;
			restart = false;
			player.setX(125);
			player.setY(237);
		}
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	private boolean begin = false;
	
	private int ticks = 0;
	public void update(ArrayList<Integer> keysPressed) {
		if (!gameOver || begin) {
//			player.move(keysPressed);
			
			for (Enemy enemy : enemiesLevel1) {
				enemy.move(ticks);
			}
			repaint();

			int[] coordinates = player.move(keysPressed);
			int[] futureCoordinates = new int[2];
			futureCoordinates[0] = coordinates[2];
			futureCoordinates[1] = coordinates[3];
			int[] priorCoordinates = new int[2];
			priorCoordinates[0] = coordinates[0];
			priorCoordinates[1] = coordinates[1];
			
			for (Boundary boundary : boundariesLevel1) {
				if (player.checkCollision(boundary, futureCoordinates)) {
					player.revert(priorCoordinates);
//					player.checkCollision(boundary);
//					System.out.println("boundary detected");
				}
			}
			
			if (player.checkDone(finishedLevel1, futureCoordinates)) {
				gameFinished = true;
			}
			
			for (Enemy enemy : enemiesLevel1) {
				if (player.checkCollision(enemy) && !begin) {
					System.out.println("game over bro u lost");
					gameOver = true;
					begin = true;
					ticks++;
					return;
				}
			}
			
			
			if (begin) {
				begin = false;
			}
			
		}
		
		if (gameOver) {
			numFails++;
			System.out.println(numFails);
			restart = true;
		}

		ticks++;
	}
	
}