import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameLoop {

    private static final long REFRESH_INTERVAL_MS = 17;
    private final Object redrawLock = new Object();
    private Component component;
    private volatile boolean keepGoing = true;
    private BufferedImage imageBuffer;
    
    private Rectangle boundBox;
    
    private static GameLoop gameLoop;
    private static Game game;
    private Graphics gr;
    
    private long ticks = 0;
    
    private Point mouseLocation= new Point(0,0);
    ArrayList<Integer> keysPressed = new ArrayList<Integer>();	
    ArrayList<Integer> mouseCoordinates = new ArrayList<Integer>();
    Canvas canvas = new Canvas(WIDTH, HEIGHT);

    public Point getMouseLocation() {
    	return mouseLocation;
    }
    
    public void start(Component component) {
        this.component = component;
        imageBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        boundBox = new Rectangle(0,0,WIDTH,HEIGHT);
        
        component.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				mouseLocation = new Point(arg0.getX(),arg0.getY());
			}
        });
        
        component.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				mouseCoordinates.set(0, e.getX());
				mouseCoordinates.set(1, e.getY());
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}
        	
        });
        
        component.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (!keysPressed.contains(e.getKeyCode()))
					keysPressed.add(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keysPressed.remove(new Integer(e.getKeyCode()));
			}
        });
        
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runGameLoop();
            }
        });
        thread.start();
    }

    public void stop() {
        keepGoing = false;
    }
    
    private long durationMs = 0;
    
    public Rectangle getBoundBox() {
    	return boundBox;
    }
    
    private void runGameLoop() {
        while (keepGoing) {
            durationMs = redraw();
            try {
                Thread.sleep(Math.max(0, REFRESH_INTERVAL_MS - durationMs));
            } catch (InterruptedException e) {
            }
        }
    }

    private long redraw() {
        long t = System.currentTimeMillis();


        updateModel();
        drawModelToImageBuffer();
        component.repaint();
        waitForPaint();
        
    	ticks++;
        return System.currentTimeMillis() - t;
    }

    private void updateModel() {
//    	if (canvas.getGameOver()) {
//    		canvas.g
//    	} else {
//    		canvas.update(keysPressed);
//    	}
    	
    	canvas.update(keysPressed);
    }
    
    public long getTicks() {
    	return ticks;
    }
    
    public GameLoop getGame() {
    	return gameLoop;
    }

    private void drawModelToImageBuffer() {
        drawModel(imageBuffer.getGraphics());
    }

    private static Color background = new Color(170, 165, 255);
    
    private void drawModel(Graphics g) {
        g.setColor(background);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        // GRAPICS METHOD
        canvas.paintComponent(g);
    }

    private void waitForPaint() {
        try {
            synchronized (redrawLock) {
                redrawLock.wait();
            }
        } catch (InterruptedException e) {
        }
    }

    private void resume() {
        synchronized (redrawLock) {
            redrawLock.notify();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(imageBuffer, 0, 0, component);

        resume();
    }

    public static class Panel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private final GameLoop game;

        public Panel(GameLoop game) {
            this.game = game;
        }

        @Override
        protected void paintComponent(Graphics g) {
            game.paint(g);
        }
    }

    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    
    private static final String TITLE = "The World's Hardest Game";
    
    private static JFrame frame;
    
    public static void main(String[] args) {
        gameLoop = new GameLoop();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Panel component = new Panel(gameLoop);
                
                frame = new JFrame();

                frame.setTitle(TITLE);
                frame.setSize(WIDTH, HEIGHT);

                frame.setLayout(new BorderLayout());
                frame.getContentPane().add(component, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setResizable(false);
                component.setFocusable(true);
                component.grabFocus();

                game = new Game();
                gameLoop.start(component);
            }
        });
    }

	public void exit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

}
