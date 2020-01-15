package sprites.blocks.particles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.ScaleManager;
import main.SpriteSheetController;
import sprites.Player;

public class BreakParticle extends Particle {

	double velx;
	double vely;
	BufferedImage img;
	public BreakParticle(int x_, int y_, BufferedImage image, double vx, double vy) {
		super(x_ + (int)Player.worldX, y_ + (int)Player.worldY);
		velx = vx;
		vely = vy;
		img = image;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updateView(Graphics g) {
		// TODO Auto-generated method stub
		int[] spriteSheetPosition = SpriteSheetController.spriteSheetCoord(img, 0, 5, 5);
		g.drawImage(img, x - (int)Player.worldX, y - (int)Player.worldY, x + 10 - (int)Player.worldX, y + 10 - (int)Player.worldY, spriteSheetPosition[0], spriteSheetPosition[1], spriteSheetPosition[2], spriteSheetPosition[3], null);
	}
	
	@Override
	public void updateTick() {
		// TODO Auto-generated method stub
		x += velx;
		y += vely;
		vely += 0.1;
		if (y - (int)Player.worldY > ScaleManager.getHeight()) {
			Game.gameComponentList.remove(this);
		}
	}
}
