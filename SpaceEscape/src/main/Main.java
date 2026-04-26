package main;
import javax.swing.JFrame;
public class Main {
	public static void main(String[] args) {
		JFrame windows = new JFrame();
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setResizable(false);
		windows.setTitle("Space Escape");
		GamePanel gp = new GamePanel();
		windows.add(gp);
		windows.pack();
		windows.setVisible(true);
		windows.setLocationRelativeTo(null);
		gp.gameThread();
	}
}