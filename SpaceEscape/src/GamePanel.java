import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;
import java.io.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	final int block = 16;
	final int scale = 3;
	final int tileSize = block * scale;
	final int maxScreenCol = 24;
	final int maxScreenRow = 18;
	final int WIDTH = maxScreenCol * tileSize;
	final int HEIGHT = maxScreenRow * tileSize;
	boolean rightPress, leftPress, upPress, downPress;
	String direction = "";
	BufferedImage image, up, left, right, down;
	
	int x = 100;
	int y = 100;
	int speed = 4;
	Thread game;
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPress = true;
			direction = "up";
		}
		if(code == KeyEvent.VK_A) {
			leftPress = true;
			direction = "left";
		}
		if(code == KeyEvent.VK_S) {
			downPress = true;
			direction = "down";
		}
		if(code == KeyEvent.VK_D) {
			rightPress = true;
			direction = "right";
		}
	}
	public void gameThread() {
		game = new Thread(this);
		game.start();
	}
	public void getPlayerImage() {
		try {
		up = ImageIO.read(getClass().getResourceAsStream("/player/player_right.png"));
		right = ImageIO.read(getClass().getResourceAsStream("/player/player_right.png"));
		down = ImageIO.read(getClass().getResourceAsStream("/player/player_left.png"));
		left = ImageIO.read(getClass().getResourceAsStream("/player/player_left.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		switch(direction) {
		case "up":
			image = up;
		break;
		case "right":
			image = right;
		break;
		case "left":
			image = left;
		break;
		case "down":
			image = down;
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPress = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPress = false;
		}
		if(code == KeyEvent.VK_S) {
			downPress = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPress = false;
		}
	}
	public void update() {
		if(upPress == true) {
			y -= speed;
		}
		if(leftPress == true) {
			x -= speed;
		}
		if(downPress == true) {
			y += speed;
		}
		if(rightPress == true) {
			x += speed;
		}
		getPlayerImage();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, x, y, tileSize, tileSize, null);
		g2.dispose();
	}

	@Override
	public void run() {
		while(game != null) {
			update();
			repaint();
			try {
				game.sleep(16);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		JFrame windows = new JFrame();
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setResizable(false);
		windows.setLocationRelativeTo(null);
		windows.setTitle("Space Escape");
		GamePanel gp = new GamePanel();
		windows.add(gp);
		windows.pack();
		windows.setVisible(true);
		gp.gameThread();
	}
}
