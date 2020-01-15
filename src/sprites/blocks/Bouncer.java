package sprites.blocks;

import java.awt.Color;
import java.awt.Graphics;

import main.ScaleManager;
import sprites.Player;
import sprites.Tile;

public class Bouncer extends Tile {

	public Bouncer(String type, int x, int y) {
		super(type, x, y);
		canCollide = true;
	}
	
	
	public void draw(Graphics g) {
		screenX = (int) (x*size + moveX);
		screenY = (int) (y*size + moveY);
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		g.setColor(Color.ORANGE);
		g.drawRect(displayX, displayY, size, size);
		g.setColor(Color.GRAY);
		g.fillRect(displayX, displayY, size, size);
		g.setColor(Color.CYAN);
		g.drawPolygon(new int[] {displayX,displayX + size,displayX + size/2}, new int[] {displayY + size,displayY + size,displayY}, 3);
	}
	
	
	public boolean interact() {
		canCollide = false;
		Player.velY = -5.0f;
		Player.onGround = false;
		return true;
	}
}
