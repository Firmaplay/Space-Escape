package main;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class GamePanel extends JPanel implements Runnable{
	final int originalTileSize = 16;
	final int scale = 3;
	final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int WIDHT = maxScreenCol * tileSize;
	final int HEIGHT = maxScreenRow * tileSize;
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	int FPS = 60;
	KeyHandler KeyH = new KeyHandler();
	Thread game;
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDHT, HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);
	}
	public void gameThread() {
		game = new Thread(this);
		game.start();
	}
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		while(game != null) {
			update();
			repaint();
			try {
				double remainTime = nextDrawTime - System.nanoTime();
				remainTime = remainTime/1000000;
				if(remainTime < 0) {
					remainTime = 0;
				}
				game.sleep((long) remainTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void update() {
		if(KeyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		if(KeyH.downPressed == true) {
			playerY += playerSpeed;
		}
		if(KeyH.rightPressed == true) {
			playerX += playerSpeed;
		}
		if(KeyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.blue);
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		g2.dispose();
	}
}