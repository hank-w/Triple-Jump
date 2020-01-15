package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import util.VisualEffects;

public class GameButton extends GameComponent{

	int x;
	int y;
	int w;
	int h;
	int rawW;
	int rawH;
	String t;
	Font font;
	boolean highLighted;
	boolean pressed;
	String command;
	
	public GameButton(String text, int posX, int posY, int width, int height, String c) {
		highLighted = false;
		x = posX;
		y = posY;
		rawW = width;
		rawH = height;
		w = (int) (rawW * ScaleManager.getMin());
		h = (int) (rawH * ScaleManager.getMin());
		t = text;
		command = c;
		pressed = false;
		int fontSize = 0;
		fontSize = (int) (h/2);
		if (fontSize * t.length() > w) {
			fontSize = (int) (w * 1.5/t.length());
		}
		font = new Font("Courier New", Font.BOLD, fontSize);
	}
	
	@Override
	public String getObjectType() {
		// TODO Auto-generated method stub
		return "Button";
	}

	@Override
	public void updateView(Graphics g) {
		// TODO Auto-generated method stub
		
		int screenX = (int)(x * ScaleManager.getMin() + ScaleManager.screenOffsetX);
		int screenY = (int)(y * ScaleManager.getMin() + ScaleManager.screenOffsetY);
		g.drawRect(screenX, screenY, w, h);
		
		if (w != (int)(rawW * ScaleManager.getMin())) {
			w = (int) (rawW * ScaleManager.getMin());
			h = (int) (rawH * ScaleManager.getMin());
			int fontSize = 0;
			fontSize = (int) (h/2);
			if (fontSize * t.length() > w) {
				fontSize = (int) (w * 1.5/t.length());
			}
			font = new Font("Courier New", Font.BOLD, fontSize);
		}
		g.setFont(font);
		// - (font.getSize() * t.length()/3) + w / 2 + 3
		// + (int)(font.getSize() / 2) + (int)(h/2.5)
		g.drawString(t, screenX + (w/2) - (int)(t.length()*font.getSize()/3.3), screenY + (h/2) + (int)(font.getSize()/3));
		g.setColor(new Color(50,50,50,50));
		g.fillRect(screenX, screenY, w, h);
	}

	public void updateTick() {
		int screenX = (int)(x * ScaleManager.getMin() + ScaleManager.screenOffsetX);
		int screenY = (int)(y * ScaleManager.getMin() + ScaleManager.screenOffsetY);
		
		if (Canvas.mouseX > screenX && Canvas.mouseX < screenX + w && Canvas.mouseY > screenY && Canvas.mouseY < screenY + h) {
			highLighted = true;
			if (ControlListener.mouseDown) {
				if (!pressed) {
					pressed = true;
					onClick();
				}
				
			}
		} else {
			highLighted = false;
		}
		
		if (!ControlListener.mouseDown) {
			pressed = false;
		}
	}
	
	public void onClick() {
		if (!VisualEffects.inTransition()) {
			Main.com.actionPerformed(new ActionEvent(this, 0, command));
		}
		//Main.com.enterPrompt(command);
	}
	
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		if (!pressed) {
		if (highLighted) {
			return Color.CYAN;
		} else {
			return Color.MAGENTA;
		}
		} else {
			return Color.GRAY;
		}
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
	public void move(int posX, int posY) {
		x = posX;
		y = posY;
		
	}

}
