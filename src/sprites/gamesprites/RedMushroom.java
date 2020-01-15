package sprites.gamesprites;

import java.awt.Graphics;

import main.Game;
import main.LevelManager;
import sprites.GameSprite;
import sprites.Player;
import sprites.Tile;

public class RedMushroom extends GameSprite {

	protected double vX;
	protected double vY;
	public RedMushroom(String type, int x, int y) {
		super(type, x, y);
		vX = 0.5;
		vY = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		super.update();
		
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
					}
				}
			}
			
			if (screenX + size > Game.currentPlayer.getX() && screenX < Game.currentPlayer.getX() + Game.currentPlayer.width && screenY + size > Game.currentPlayer.getY() && screenY < Game.currentPlayer.getY() + Game.currentPlayer.width) {
				Player.buffType = 1;
				Game.currentPlayer.rawLength = (int) (Player.defaultLength * 1.5);
				if (Player.buffType == 0)
					Game.currentPlayer.y -= 1;
				LevelManager.spriteList.remove(this);
			}
		}
		if (grounded) {
			vY = 0;
		} else {
			if (vY < 2) {
				vY += 0.05;
			}
			y += vY;
		}
		x += vX;
	}
	
	public void draw(Graphics g) {
		screenX = (int) (x + moveX);
		screenY = (int) (y + moveY);
		drawTexture(g, displacementX, displacementY);
	}

}
