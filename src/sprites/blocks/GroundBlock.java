package sprites.blocks;

import java.awt.Graphics;

import sprites.Tile;

public class GroundBlock extends Tile {

	public GroundBlock(String type, int x, int y) {
		super(type, x, y);
		canCollide = true;
	}
	
	
	public void draw(Graphics g) {
		screenX = (int) (x*size + moveX);
		screenY = (int) (y*size + moveY);
		drawTexture(g, displacementX, displacementY);
	}
	
	public void drawFloating(Graphics g) {
//		screenX = (int) (x*size + moveX);
//		screenY = (int) (y*size + moveY);
//		drawTexture(g, 0 + displacementX, -10 + displacementY);
	}
	
//	public void interact() {
//		Message.throwInfo("This is a wall", new String[] {"close"}, new Runnable[] {()-> Game.dummy()});
//	}
}
