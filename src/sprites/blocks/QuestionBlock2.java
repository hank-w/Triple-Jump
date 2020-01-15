package sprites.blocks;

import java.awt.Graphics;

import main.Game;
import main.LevelManager;
import main.resources.images.ResourcePath;
import sprites.Player;
import sprites.Tile;
import sprites.blocks.particles.BreakParticle;
import sprites.gamesprites.GrayFlower;
import sprites.gamesprites.RedFlower;
import sprites.gamesprites.RedMushroom;

public class QuestionBlock2 extends Tile {

	boolean isHit = false;
	int count = 10;
	public QuestionBlock2(String type, int x, int y) {
		super(type, x, y);
		canCollide = true;
		isHit = false;
	}
	
	
	public void draw(Graphics g) {
		screenX = (int) (x*size + moveX);
		screenY = (int) (y*size + moveY);
		if (!isHit) {
			drawTexture(g, displacementX, displacementY);
		} else {
			drawTexture(g, displacementX, displacementY - 15);
		}
	}
	
	public void drawFloating(Graphics g) {
		
	}
	
	public void collideUp() {
		if (!isHit) {
			isHit = true;
			for (int i = 0; i < LevelManager.spriteList.size(); i++) {
				if (LevelManager.spriteList.get(i).getInteractingTiles().contains(this)) {
					LevelManager.spriteList.get(i).hitUp();
				}
			}
		}
	}
	
	public void update() {
		super.update();
		if (isHit) {
			if (count > 0) {
				count--;
				if(count <= 0) {
					LevelManager.map.get(y).set(x, new Empty("E", x, y));
					isHit = false;
					if (Player.buffType == 0) {
						LevelManager.spriteList.add(new RedMushroom("m",x*size,(y-1)*size));
					} else {
						LevelManager.spriteList.add(new GrayFlower("t",x*size,(y-1)*size));
					}
				}
			}
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
