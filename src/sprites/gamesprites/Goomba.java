package sprites.gamesprites;

import java.awt.Graphics;
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

public class Goomba extends GameSprite implements Hitable {

	protected double vX;
	protected double vY;
	boolean dead;
	boolean active;
	BufferedImage img = ResourcePath.getImage(t);
	
	public Goomba(String type, int x, int y) {
		super(type, x, y);
		vX = -0.5;
		vY = 0;
		animationSetup(3,1);
		frameDelay = 80;
		dead = false;
		active = true;
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		super.update();
		if (dead) {
			return;
		}
		boolean grounded = false;
		int collisionGapX = 5;
		for (int i = 0; i < LevelManager.map.size(); i++) {
			for (int j = 0; j < LevelManager.map.get(i).size(); j++) {
				int testX = LevelManager.map.get(i).get(j).getWorldX();
				int testY = LevelManager.map.get(i).get(j).getWorldY();
				if (LevelManager.map.get(i).get(j).canCollide) { //Collision Detection
					 if (x > testX - size - collisionGapX && x < testX - size && y + size > testY && y < testY + Tile.size) {
						//Collision Right
						if (vX > 0) {
							vX = -0.5;
						}
					}
					
					 if (x < testX + Tile.size + collisionGapX && x > testX + Tile.size && y + size > testY && y < testY + Tile.size) {
						//Collision Left
						if (vX < 0) {
							vX = 0.5;
						}
					}
					
					 if (y > testY - size - collisionGapX && y < testY - 20 && x + size > testX && x < testX + Tile.size) {
						//Grounded
						grounded = true;
						y = testY - size;
						if (!this.interactingTiles.contains(LevelManager.map.get(i).get(j))) {
							this.interactingTiles.add(LevelManager.map.get(i).get(j));
						}
						
					} else {
						if (this.interactingTiles.contains(LevelManager.map.get(i).get(j))) {
							this.interactingTiles.remove(LevelManager.map.get(i).get(j));
						}
					}
				}
			}
		}
		if (!Game.currentPlayer.deathAnimationPlaying && screenX + size > Game.currentPlayer.getX() && screenX < Game.currentPlayer.getX() + Game.currentPlayer.width && screenY + size > Game.currentPlayer.getY() && screenY < Game.currentPlayer.getY() + Game.currentPlayer.length) {
			if (Player.buffType == 6 && Game.currentPlayer.mode == 10 && Game.currentPlayer.velX != 0) {
				hitSide(Game.currentPlayer.direction == 1);
			} else
			if (Game.currentPlayer.getY() + Game.currentPlayer.length <= screenY + 10) {
				if (Game.currentPlayer.mode == 9) {
					hitUp();
				} else {
					Player.velY = -2;
					Player.onGround = true;
					Game.gameComponentList.add(new DelayTrigger(() -> Player.onGround = false, 20));
					dead = true;
					Game.gameComponentList.add(new DelayTrigger(() -> LevelManager.spriteList.remove(this),200));
				}
				
			} else {
				Player.die2();
				//Game.currentPlayer.y = ScaleManager.getHeight();
			}
		}
		if (active) {
			if (grounded) {
				vY = 0;
			} else {
				if (vY < 2) {
					vY += 0.05;
				}
				y += vY;
				if (y > LevelManager.mapHeight * Tile.rawsize) {
					LevelManager.spriteList.remove(this);
				}
			}
			x += vX;
		}
	}
	
	public void draw(Graphics g) {
		screenX = (int) (x + moveX);
		screenY = (int) (y + moveY);
		int dX = displacementX;
		int dY = displacementY;
		int scaleX = 40;
		int scaleY = 40;
		if (dead) {
			dX = displacementX;
			dY = displacementY + 15;
			int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), 2, 3, 1);
			int displayX = screenX + ScaleManager.screenOffsetX;
			int displayY = screenY + ScaleManager.screenOffsetY;
			if (displayX+size+dX + scaleX/2 > ScaleManager.screenOffsetX && displayX + dX - scaleX/2 < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size+dY + scaleY/2 > ScaleManager.screenOffsetY && displayY+dY - scaleY/2 < Main.c.getHeight() - ScaleManager.screenOffsetY) {
				g.drawImage(ResourcePath.getImage(t), displayX + dX - scaleX/2, displayY+dY - scaleY/2, displayX+size+dX + scaleX/2, displayY+size+dY + scaleY/2,coord[0],coord[1],coord[2],coord[3], null);
				active = true;
			} else {
				active = false;
			}
		} else {
			int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), frame, this.sheetrow, this.sheetcol);
			//int[] basecoord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), 0, this.sheetrow, this.sheetcol);
			int displayX = screenX + ScaleManager.screenOffsetX;
			int displayY = screenY + ScaleManager.screenOffsetY;
			if (displayX+size+dX + scaleX/2 > ScaleManager.screenOffsetX && displayX + dX - scaleX/2 < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size+dY + scaleY/2 > ScaleManager.screenOffsetY && displayY+dY - scaleY/2 < Main.c.getHeight() - ScaleManager.screenOffsetY) {
				//g.drawImage(ResourcePath.getImage(t), displayX + dX - scaleX/2, displayY+dY - scaleY/2, displayX+size+dX + scaleX/2, displayY+size+dY + scaleY/2,basecoord[0],basecoord[1],basecoord[2],basecoord[3], null);
				g.drawImage(ResourcePath.getImage(t), displayX + dX - scaleX/2, displayY+dY - scaleY/2, displayX+size+dX + scaleX/2, displayY+size+dY + scaleY/2,coord[0],coord[1],coord[2],coord[3], null);
				active = true;
			} else {
				active = false;
			}
			if (this.frame >= 2) {
				frame = 0;
			}
		}
	}
	
	public void hitUp() {
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), 0, this.sheetrow, this.sheetcol);
		Game.gameComponentList.add(new EnemyDeath((int)displayX,(int)displayY,ResourcePath.getImage("g"),-vX * 2,-0.5, coord,size * 2,size * 2));
		//Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY - 15,img,1d,-0.2d));
		LevelManager.spriteList.remove(this);
	}
	
	public void hitSide(boolean direction) {
		if (dead) {
			return;
		}
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), 0, this.sheetrow, this.sheetcol);
		if (direction == false) {
			Game.gameComponentList.add(new EnemyDeath((int)displayX,(int)displayY,ResourcePath.getImage("g"),-0.5 * 2,-0.5, coord,size * 2,size * 2));
		} else {
			Game.gameComponentList.add(new EnemyDeath((int)displayX,(int)displayY,ResourcePath.getImage("g"),0.5 * 2,-0.5, coord,size * 2,size * 2));
		}
		//Game.gameComponentList.add(new BreakParticle(this.screenX + 15,this.screenY - 15,img,1d,-0.2d));
		LevelManager.spriteList.remove(this);
	}
	
	public boolean inflictor() {
		return false;
	}
}
