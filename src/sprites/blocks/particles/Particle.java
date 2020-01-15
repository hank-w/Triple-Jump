package sprites.blocks.particles;

import java.awt.Color;
import java.awt.Graphics;

import main.GameComponent;

public class Particle extends GameComponent {

	int x;
	int y;
	
	public Particle(int x_, int y_) {
		x = x_;
		y = y_;
	}
	
	@Override
	public String getObjectType() {
		// TODO Auto-generated method stub
		return "Particle";
	}

	@Override
	public void updateView(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}

}
