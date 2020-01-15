package sprites.blocks.particles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.Game;
import main.ScaleManager;
import sprites.Player;

public class EnemyDeath extends Particle {

	private double velx;
	private double vely;
	BufferedImage img;
	int[] coords;
	int sizeX;
	int sizeY;
	double rot;
	public EnemyDeath(int x_, int y_, BufferedImage image, double vx, double vy, int[] coords, int sX, int sY) {
		super(x_ + (int)Player.worldX, y_ + (int)Player.worldY);
		velx = vx;
		vely = vy;
		img = image;
		this.coords = coords;
		sizeX = sX;
		sizeY = sY;
		rot = 0;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updateView(Graphics g) {
		Graphics2D p = (Graphics2D) g;
		AffineTransform Tx = new AffineTransform();
		Tx.rotate(rot, x - (int)Player.worldX + sizeX/2, y - (int)Player.worldY + sizeY/2);
		AffineTransform res = p.getTransform();
		p.setTransform(Tx);
		rot += 0.1;
		// TODO Auto-generated method stub
		int[] spriteSheetPosition = coords;
		//int[] spriteSheetPosition = SpriteSheetController.spriteSheetCoord(img, 0, 5, 5);
		g.drawImage(img, x - (int)Player.worldX, y - (int)Player.worldY, x + sizeX - (int)Player.worldX, y + sizeY - (int)Player.worldY, spriteSheetPosition[0], spriteSheetPosition[1], spriteSheetPosition[2], spriteSheetPosition[3], null);
		p.setTransform(res);
	}
	
	@Override
	public void updateTick() {
		// TODO Auto-generated method stub
		x += velx;
		y += vely;
		vely += 0.06;
		if (y - (int)Player.worldY > ScaleManager.getHeight()) {
			Game.gameComponentList.remove(this);
		}
	}
}
