package sprites.blocks;

import java.awt.Graphics;

import main.Game;
import main.LevelManager;
import main.resources.images.ResourcePath;
import sprites.Tile;
import sprites.blocks.particles.BreakParticle;

public class NormalBrick extends Tile {

	int bounce = 0;
	int bounceDir = 0;
	public NormalBrick(String type, int x, int y) {
		super(type, x, y);
		canCollide = true;
	}
	
	
	public void draw(Graphics g) {
		screenX = (int) (x*size + moveX);
		screenY = (int) (y*size + moveY);
		if (bounce > 0) {
			if (bounceDir == 0) {
				drawTexture(g, displacementX, displacementY - ((bounce - 5) + 25)/10);
			} else if (bounceDir == 1) {
				drawTexture(g, displacementX, displacementY + ((bounce - 5) + 25)/10);
			}
		} else {
			drawTexture(g, displacementX, displacementY);
		}
	}
	
	public void drawFloating(Graphics g) {
		
	}
	
	public void collideUp() {
		LevelManager.map.get(y).set(x, new Tile(" ", x, y));
		Game.gameComponentList.add(new BreakParticle(this.screenX - 5,this.screenY,ResourcePath.getImage(t),-0.5d,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX - 5,this.screenY - 15,ResourcePath.getImage(t),-0.5d,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY,ResourcePath.getImage(t),1d,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY - 15,ResourcePath.getImage(t),1d,-0.2d));
		for (int i = 0; i < LevelManager.spriteList.size(); i++) {
			if (LevelManager.spriteList.get(i).getInteractingTiles().contains(this)) {
				LevelManager.spriteList.get(i).hitUp();
			}
		}
	}
	
	public void update() {
		super.update();
		if (bounce > 0) {
			bounce--;
		}
	}
	
	public boolean bounce(int direction) {
		if (Game.currentPlayer.buffType > 0) {
			collideUp();
			return true;
		} else {
			bounce = 25;
			bounceDir = direction;
			for (int i = 0; i < LevelManager.spriteList.size(); i++) {
				if (LevelManager.spriteList.get(i).getInteractingTiles().contains(this)) {
					LevelManager.spriteList.get(i).hitUp();
				}
			}
			return false;
		}
		
		
	}
	public boolean destroy(int direction) {
		LevelManager.map.get(y).set(x, new Tile(" ", x, y));
		Game.gameComponentList.add(new BreakParticle(this.screenX - 5,this.screenY,ResourcePath.getImage(t),-0.5d + direction,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX - 5,this.screenY - 15,ResourcePath.getImage(t),-0.5d + direction,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY,ResourcePath.getImage(t),1d + direction,-0.2d));
		Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY - 15,ResourcePath.getImage(t),1d + direction,-0.2d));
		return true;
	}
	
}
