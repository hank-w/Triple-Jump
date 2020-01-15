package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Game;
import main.GameButton;
import main.ScaleManager;

public class InstantInfo extends UiMessage {
	GameButton[] choiceList;
	
	String message;
	
	ArrayList<String> outMessage = new ArrayList<String>();
	
	int breakPoint = 0;
	boolean updateComplete;
	Font font;
	double scale;
	public InstantInfo(String message, GameButton[] choices) {
		updateComplete = false;
		breakPoint = 0;
		choiceList = choices;
		this.message = message;
		font = new Font("Courier New", Font.BOLD, 15);
		typeInfo();
	}

	@Override
	public String getObjectType() {
		// TODO Auto-generated method stub
		return "MessageBox";
	}

	@Override
	public void updateView(Graphics g) {
		if (scale != ScaleManager.getMin()) {
			
			scale = ScaleManager.getMin();
		}
		font = new Font("Courier New", Font.BOLD, (int) (15 * scale));
		int xOffset = ScaleManager.screenOffsetX;
		int yOffset = ScaleManager.screenOffsetY;
		
		g.setColor(new Color(50,50,50,200));
		g.drawRect((int)(50 * scale) + xOffset, (int)(300 * scale) + yOffset, (int)(500 * scale), (int)(200 * scale));
		g.fillRect((int)(50 * scale) + xOffset, (int)(300 * scale) + yOffset, (int)(500 * scale), (int)(200 * scale));
		g.setColor(Color.WHITE);
		for (int i = 0; i < outMessage.size(); i++) {
			g.setFont(font);
			g.drawString(outMessage.get(i), (int)(60 * scale) + xOffset, (int)((320 + i * 20) * scale) + yOffset);
		}
		
		
	}

	@Override
	public void updateTick() {
		// TODO Auto-generated method stub
		if (!updateComplete) {
			for (int i = 0; i < choiceList.length; i++) {
				Game.gameComponentList.add(choiceList[i]);
			}
			updateComplete = true;
		}
		Game.paused = true;
		
	}

	private void typeInfo() {
		for (int i = 0; i < message.length(); i++) {
			if ((message.substring(i, i+1).equals(" ") && i - breakPoint > 50)) {
				String currentMessage = message.substring(breakPoint,i);
				breakPoint = i;
				
				outMessage.add(currentMessage);
			}
			
			
		}
		String line = message.substring(breakPoint).trim();
		outMessage.add(line);
	}
	
	
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	public void closeMessage() {
		Game.paused = false;
		for (int i = 0; i < choiceList.length; i++) {
			Game.gameComponentList.remove(choiceList[i]);
		}
		Game.gameComponentList.remove(this);
	}
}
