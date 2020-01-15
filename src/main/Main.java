package main;

import javax.swing.JFrame;


public class Main {
	public static JFrame j;
	public static Canvas c;
	public static ControlListener cont;
	public static CommandPrompt com;
	public static void main(String[] args) {
		j = new JFrame("Mario Presentation");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = new Canvas();
		cont = new ControlListener();
		com = new CommandPrompt();
		j.add(c);
		j.setSize(600,500);
		j.setLocation(300, 200);
		j.setVisible(true);
		c.addMouseListener(cont);
		j.addKeyListener(cont);
		c.addMouseMotionListener(cont);
		ScaleManager.initScale();
		Game.init();
		TitleScreen.setup();
		Game.start();
	}
}
