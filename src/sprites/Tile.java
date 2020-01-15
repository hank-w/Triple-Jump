package sprites;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import main.LevelManager;
import main.Main;
import main.ScaleManager;
import main.SpriteSheetController;
import main.resources.images.ResourcePath;

public class Tile {
	public static final int rawsize = 40;
	public static int size;
	protected String t;
	protected int x;
	protected int y;
	protected double moveX;
	protected double moveY;
	
	//Tile Local Position On Screen
	protected int screenX;
	protected int screenY;
	
	//Tile Collision Switch
	public boolean canCollide;
	
	//Animation Variables
	int sheetrow = 1;
	int sheetcol = 1;
	int frame;
	protected int displacementX = 0;
	protected int displacementY = 0;
	//Count
	int count = 0;
	
	double scale;
	
	public Tile(String type, int x, int y) {
		this.x = x;
		this.y = y;
		this.t = type;
		size = (int) (rawsize * ScaleManager.getMin());
		scale = ScaleManager.getMin();
		displacementX = 0;
		displacementY = 0;
		canCollide = false;
		
		animationSetup(1,1);
	}
	
	protected void animationSetup(int r, int c) {
		frame = 0;
		sheetrow = r;
		sheetcol = c;
	}
	
	public void update() {
		if (scale != ScaleManager.getMin()) {
			Player.worldLockX = false;
			Player.worldLockY = false;
		}
		count++;
		if (count == 20) {
			count = 0;
			if (frame < sheetrow * sheetcol - 1) {
				frame++;
			} else {
				frame = 0;
			}
		}
		
		if (-Player.resizedworldX <= 0 && -Player.resizedworldX > -LevelManager.mapWidth * size + ScaleManager.getWidth()) {
			if (!Player.worldLockX) {
				moveX = -Player.resizedworldX;
			}
		} else {
			if (-Player.resizedworldX > 0) {
				moveX = 0;
			} else {
				moveX = -LevelManager.mapWidth * size + ScaleManager.getWidth();
			}
			//Player.worldLockX = true;
		}
		if (-Player.resizedworldY <= 0 && -Player.resizedworldY > -LevelManager.mapHeight * size + ScaleManager.getHeight()) {
			if (!Player.worldLockY) {
				moveY = -Player.resizedworldY;
			}
		} else {
			if (-Player.resizedworldY > 0) {
				moveY = 0;
			} else {
				moveY = -LevelManager.mapHeight * size + ScaleManager.getHeight();
			}
			//Player.worldLockY = true;
		}
		
		if (scale != ScaleManager.getMin()) {
			size = (int) (rawsize * ScaleManager.getMin());
			scale = ScaleManager.getMin();
//			Player.worldLockX = true;
//			Player.worldLockY = true;
		}
	}
	
	public void draw(Graphics g) {
		screenX = (int) (x*size + moveX);
		screenY = (int) (y*size + moveY);
		
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		if (displayX+size > ScaleManager.screenOffsetX && displayX < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size > ScaleManager.screenOffsetY && displayY < Main.c.getHeight() - ScaleManager.screenOffsetY) {
			switch (t) {
			case "#": {
				g.setColor(Color.BLACK);
				g.drawRect(displayX, displayY, size, size);
				g.fillRect(displayX, displayY, size, size);
				break;
			}
			
			
			case "D": {
				g.setColor(Color.GREEN);
				g.drawRect(displayX, displayY, size, size);
				g.fillRect(displayX, displayY, size, size);
				break;
			}
			
			case " ": {
				
				break;
			}
			
			}
		}
	}
	
	public void drawFloating(Graphics g) {
		screenX = (int) (x*size + moveX);
		screenY = (int) (y*size + moveY);
		
		switch (t) {
		}
		
	}
	
	protected void drawTexture(Graphics g, int dX, int dY) {
		//g.drawImage(ResourcePath.getImage(t), screenX, screenY - 10, size, size, null);
		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), frame, sheetrow, sheetcol);
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		if (displayX+size+dX > ScaleManager.screenOffsetX && displayX + dX < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size+dY > ScaleManager.screenOffsetY && displayY+dY < Main.c.getHeight() - ScaleManager.screenOffsetY) {
			g.drawImage(ResourcePath.getImage(t), displayX + dX, displayY+dY, displayX+size+dX, displayY+size+dY,coord[0],coord[1],coord[2],coord[3], null);
		}
	}
	
	protected void drawTexture(Graphics g, int dX, int dY, int scaleX, int scaleY) {
		//g.drawImage(ResourcePath.getImage(t), screenX, screenY - 10, size, size, null);
		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), frame, sheetrow, sheetcol);
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		if (displayX+size+dX + scaleX/2 > ScaleManager.screenOffsetX && displayX + dX - scaleX/2 < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size+dY + scaleY/2 > ScaleManager.screenOffsetY && displayY+dY - scaleY/2 < Main.c.getHeight() - ScaleManager.screenOffsetY) {
			g.drawImage(ResourcePath.getImage(t), displayX + dX - scaleX/2, displayY+dY - scaleY/2, displayX+size+dX + scaleX/2, displayY+size+dY + scaleY/2,coord[0],coord[1],coord[2],coord[3], null);
		}
	}
	
	protected void drawTexture(Graphics g, int dX, int dY, int scaleX, int scaleY,String target) {
		//g.drawImage(ResourcePath.getImage(t), screenX, screenY - 10, size, size, null);
		int[] coord = SpriteSheetController.spriteSheetCoord(ResourcePath.getImage(t), frame, sheetrow, sheetcol);
		int displayX = screenX + ScaleManager.screenOffsetX;
		int displayY = screenY + ScaleManager.screenOffsetY;
		if (displayX+size+dX + scaleX/2 > ScaleManager.screenOffsetX && displayX + dX - scaleX/2 < Main.c.getWidth() - ScaleManager.screenOffsetX && displayY+size+dY + scaleY/2 > ScaleManager.screenOffsetY && displayY+dY - scaleY/2 < Main.c.getHeight() - ScaleManager.screenOffsetY) {
			g.drawImage(ResourcePath.getImage(target), displayX + dX - scaleX/2, displayY+dY - scaleY/2, displayX+size+dX + scaleX/2, displayY+size+dY + scaleY/2,coord[0],coord[1],coord[2],coord[3], null);
		}
	}
	
	public int getX() {
		return screenX;
	}
	
	public int getY() {
		return screenY;
	}
	
	public int getWorldX() {
		return x * size;
	}
	
	public int getWorldY() {
		return y * size;
	}
	
	public String getID() {
		return t;
	}
	
	public boolean interact() {
		return false;
	}
	
	public void collideUp() {
		
	}

	public boolean bounce(int direction) {
		// TODO Auto-generated method stub
		collideUp();
		return false;
	}

	public boolean destroy(int direction) {
		// TODO Auto-generated method stub
		return false;
	}
}
