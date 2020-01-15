package sprites.blocks;

import java.awt.Color;
import java.awt.Graphics;

import main.ScaleManager;
import sprites.Tile;

public class Barrier extends Tile {

	public Barrier(String type, int x, int y) {
		super(type, x, y);
		canCollide = true;
	}
	
	
	public void draw(Graphics g) {
		screenX = (int) (x*size + moveX);
		screenY = (int) (y*size + moveY);
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		g.setColor(Color.BLACK);
		g.drawRect(displayX, displayY, size, size);
		g.fillRect(displayX, displayY, size, size);
	}
	
	
//	public void interact() {
//		Message.throwInfo("This is a wall", new String[] {"close"}, new Runnable[] {()-> Game.dummy()});
//	}
}
