package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import util.VisualEffects;

public class Canvas extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int mouseX;
	public static int mouseY;
	public static Color background;
	
	private int lastW = getWidth();
	private int lastH = getHeight();
	
	
	public void paint(Graphics g) {
		update(g);
	}
	
	
	public void update(Graphics g) {
		g.setColor(background);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLACK);
		
		
		if (Game.inGame) {
			LevelManager.drawMap(g);
		}
		
		List<GameComponent> l = Game.gameComponentList;
		
		for (int i = 0; i < l.size(); i++) {
			g.setColor(l.get(i).getColor());
			
			
			switch (l.get(i).getObjectType()) {
			
			case "Player": {
				l.get(i).updateView(g);
			}
			
			}
		}
		
		if (Game.inGame) {
			LevelManager.drawTopMap(g);
		}
		VisualEffects.preControlUpdate(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, ScaleManager.screenOffsetX, this.getHeight());
		g.fillRect(this.getWidth() - ScaleManager.screenOffsetX, 0, this.getWidth(), this.getHeight());
		g.fillRect(0, 0, this.getHeight(), ScaleManager.screenOffsetY);
		g.fillRect(0, this.getHeight() - ScaleManager.screenOffsetY, this.getWidth(), this.getHeight());
		
		for (int i = 0; i < l.size(); i++) {
			g.setColor(l.get(i).getColor());
			
			if (!l.get(i).getObjectType().equals("Player")) {
				l.get(i).updateView(g);
			}
		}
		
		VisualEffects.updateView(g);
		
		if (lastW != getWidth() || lastH != getHeight()) {
			resized();
		}
		lastW = getWidth();
		lastH = getHeight();
	}
	
	public void resized() {
		ScaleManager.rescale();
	}

	
	
}
