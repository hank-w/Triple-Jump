package sprites.gamesprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import main.Game;
import main.LevelManager;
import main.Main;
import main.ScaleManager;
import main.SpriteSheetController;
import main.resources.images.ResourcePath;
import sprites.GameSprite;
import sprites.Player;
import sprites.Tile;
import sprites.blocks.particles.EnemyDeath;
import util.DelayTrigger;

public class Fireball extends GameSprite {

	protected double vX;
	protected double vY;
	boolean active;
	BufferedImage img = ResourcePath.getImage(t);
	double angle;
	int localsize;
	public Fireball(String type, int x, int y, int direction) {
		super(type, x + Game.currentPlayer.width/2 - direction * Game.currentPlayer.width/2, y + 5);
		vY = 0;
		frame = 2;
		frameDelay = 10;
		active = true;
		displacementY = -5;
		displacementX = -5;
		size = 20;
		localsize = 10;
		angle = 0;
		vX = -direction * 2;
//		this.x-= moveX;
//		this.y-= moveY;
		vY = 1;
//		this.x *=  Tile.size;
//		this.y *=  Tile.size;
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		super.update();
			x += vX;
			for (int i = 0; i < LevelManager.spriteList.size(); i++) {
				if (LevelManager.spriteList.get(i) instanceof Hitable) {
					GameSprite target = (GameSprite)LevelManager.spriteList.get(i);
					if (screenX + localsize > target.screenX && screenX < target.screenX + target.size && screenY + localsize > target.screenY && screenY < target.screenY + target.size) {
						if (vX > 0) {
							((Hitable)target).hitSide(true);
						} else {
							((Hitable)target).hitSide(false);
						}
						destroy();
					}
				}
			}
		
		int collisionGapX = 5;
		int collisionGapY = 5;
		for (int i = 0; i < LevelManager.map.size(); i++) {
			for (int j = 0; j < LevelManager.map.get(i).size(); j++) {
				int testX = LevelManager.map.get(i).get(j).getWorldX();
				int testY = LevelManager.map.get(i).get(j).getWorldY();
				if (LevelManager.map.get(i).get(j).canCollide) { //Collision Detection
//					if (x > testX - size - collisionGapX && x < testX - size && y + size > testY && y < testY + Tile.size) {
//						//Collision Right
//						destroy();
//					}
//					
//					 if (x < testX + Tile.size + collisionGapX && x > testX + Tile.size && y + size > testY && y < testY + Tile.size) {
//						//Collision Left
//						 destroy();
//					}
//					
//					 if (y > testY - size - collisionGapX && y < testY - 20 && x + size > testX && x < testX + Tile.size) {
//						//Grounded
//						 vY = -vY;
//						
//					}
					 if (x > testX - localsize - collisionGapX && x < testX - localsize && y + localsize > testY && y < testY + Tile.size) {
						//Collision Right
						destroy();
					}
					
					 if (x < testX + Tile.size + collisionGapX && x > testX + Tile.size && y + localsize > testY && y < testY + Tile.size) {
						//Collision Left
						
						destroy();
					}
					
					 if (y > testY - localsize - collisionGapY && y < testY + collisionGapY && x + localsize + collisionGapY > testX && x - collisionGapY < testX + Tile.size) {
						//Grounded
						vY = -Math.abs(vY);
//						
						
					}
				}
			}
		}
		
		
		if (vY < 2) {
			vY += 0.05;
		}
		y += vY;
		if (y > LevelManager.mapHeight * Tile.rawsize) {
			LevelManager.spriteList.remove(this);
		}
	}
	
	public void destroy() {
		LevelManager.spriteList.remove(this);
	}
	
	public void draw(Graphics g) {
		screenX = (int) (x + moveX);
		screenY = (int) (y + moveY);
		Graphics2D p = (Graphics2D) g;
		
		int dX = displacementX;
		int dY = displacementY;
		//g.drawRect((int)screenX,(int) screenY, localsize, localsize);
		AffineTransform orig = p.getTransform();
		AffineTransform Tx = new AffineTransform();
		angle += 0.1;
		if (angle > Math.PI * 2) {
			angle = 0;
		}
		Tx.rotate(angle,screenX + size/2d,screenY + size/2d);
		  
		p.setTransform(Tx);
		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), frame, sheetrow, sheetcol);
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		if (displayX+size+dX > ScaleManager.screenOffsetX && displayX + dX < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size+dY > ScaleManager.screenOffsetY && displayY+dY < Main.c.getHeight() - ScaleManager.screenOffsetY) {
			p.drawImage(ResourcePath.getImage(t), displayX + dX, displayY+dY, displayX+size+dX, displayY+size+dY,coord[0],coord[1],coord[2],coord[3], null);
		}
		p.setTransform(orig);
//		dX = displacementX;
//		dY = displacementY + 15;
//		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), frame, this.sheetrow, this.sheetcol);
//		int displayX = screenX + ScaleManager.screenOffsetX;
//		int displayY = screenY + ScaleManager.screenOffsetY;
//		if (displayX+size+dX + scaleX/2 > ScaleManager.screenOffsetX && displayX + dX - scaleX/2 < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size+dY + scaleY/2 > ScaleManager.screenOffsetY && displayY+dY - scaleY/2 < Main.c.getHeight() - ScaleManager.screenOffsetY) {
//			if (vX < 0) {
//			//
//			p.drawImage(ResourcePath.getImage(t), displayX + dX - scaleX/2, displayY+dY - scaleY/2, displayX+size+dX + scaleX/2, displayY+size+dY + scaleY/2,coord[0],coord[1],coord[2],coord[3], null);
//			} else {
//				
//				p.drawImage(ResourcePath.getImage(t), displayX+size+dX + scaleX/2, displayY+dY - scaleY/2, displayX + dX - scaleX/2, displayY+size+dY + scaleY/2,coord[0],coord[1],coord[2],coord[3], null);
//			}
//			active = true;
//		} else {
//			active = false;
//		}
//		if (this.frame >= 5) {
//			frame = 2;
//		}
	}
	
	public void hitUp() {
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), 3, this.sheetrow, this.sheetcol);
		Game.gameComponentList.add(new EnemyDeath((int)displayX,(int)displayY,ResourcePath.getImage("k"),-vX * 2,-0.5, coord,size * 2,size * 2));
		LevelManager.spriteList.remove(this);
	}

}
