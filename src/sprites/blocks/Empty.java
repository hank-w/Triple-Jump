package sprites.blocks;

import java.awt.Graphics;

import main.Game;
import main.LevelManager;
import main.resources.images.ResourcePath;
import sprites.Tile;
import sprites.blocks.particles.BreakParticle;

public class Empty extends Tile {

	public Empty(String type, int x, int y) {
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
	
	public boolean destroy(int direction) {
		LevelManager.map.get(y).set(x, new Tile(" ", x, y));
		Game.gameComponentList.add(new BreakParticle(this.screenX - 5,this.screenY,ResourcePath.getImage(t),-0.5d + direction,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX - 5,this.screenY - 15,ResourcePath.getImage(t),-0.5d + direction,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY,ResourcePath.getImage(t),1d + direction,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY - 15,ResourcePath.getImage(t),1d + direction,-0.2d));
		return true;
	}
}
