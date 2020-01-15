package util;

import java.awt.Color;
import java.awt.Graphics;

import main.Main;
import main.ScaleManager;

public class VisualEffects {

	private static int hShakeCount = 0;
	private static int originalX = ScaleManager.screenOffsetX;
	private static int vShakeCount = 0;
	private static int originalY = ScaleManager.screenOffsetY;
	
	private static int flashCount = 0;
	private static Color flashColor;
	
	private static boolean darkened = false;
	
	private static int transitionCount = 0;
	private static Color postTransitionColor;
	private static Runnable transitionedAction;
	public static void update() {
		if (hShakeCount > 0) {
			horizontalShakeScreen();
		}
		
		if (vShakeCount > 0) {
			verticalShakeScreen();
		}
	}
	
	public static void screenShake(int duration) {
		originalX = ScaleManager.screenOffsetX;
		hShakeCount = duration;
		originalY = ScaleManager.screenOffsetY;
		vShakeCount = duration;
	}
	
	public static void screenShakeX(int duration) {
		originalX = ScaleManager.screenOffsetX;
		hShakeCount = duration;
	}
	
	public static void screenShakeY(int duration) {
		originalY = ScaleManager.screenOffsetY;
		vShakeCount = duration;
	}
	
	public static void horizontalShakeScreen() {
		if (hShakeCount <= 1) {
			ScaleManager.screenOffsetX = originalX;
			return;
		} else {
			ScaleManager.screenOffsetX = originalX + (int)(Math.random() * 20) - 10;
		}
		hShakeCount --;
	}
	
	public static void verticalShakeScreen() {
		if (vShakeCount <= 1) {
			ScaleManager.screenOffsetY = originalY;
			return;
		} else {
			ScaleManager.screenOffsetY = originalY + (int)(Math.random() * 20) - 10;
		}
		vShakeCount --;
	}
	
	public static void preControlUpdate(Graphics g) {
		if (darkened) {
			g.setColor(new Color(200,200,200,200));
			g.fillRect(ScaleManager.screenOffsetX, ScaleManager.screenOffsetY, Main.c.getWidth() - 2*ScaleManager.screenOffsetX, Main.c.getHeight() - 2*ScaleManager.screenOffsetY);
		}
	}
	
	public static void updateView(Graphics g) {
		if (flashCount > 0) {
			int alpha = flashCount;
			if (flashCount > 255) {
				alpha = 255;
			}
			g.setColor(new Color(flashColor.getRed(),flashColor.getGreen(),flashColor.getBlue(),alpha));
			g.fillRect(ScaleManager.screenOffsetX, ScaleManager.screenOffsetY, Main.c.getWidth() - 2*ScaleManager.screenOffsetX, Main.c.getHeight() - 2*ScaleManager.screenOffsetY);
			flashCount--;
		}
		
		if (transitionCount > 0) {
			int alpha = 255 - transitionCount;
			if (transitionCount > 255) {
				alpha = 0;
			}
			g.setColor(new Color(postTransitionColor.getRed(),postTransitionColor.getGreen(),postTransitionColor.getBlue(),alpha));
			g.fillRect(ScaleManager.screenOffsetX, ScaleManager.screenOffsetY, Main.c.getWidth() - 2*ScaleManager.screenOffsetX, Main.c.getHeight() - 2*ScaleManager.screenOffsetY);
			transitionCount--;
			if (transitionCount == 0) {
				transitionedAction.run();
			}
		}
		
		
	}
	
	public static void darken() {
		darkened = true;
	}
	
	public static void unDarken() {
		darkened = false;
	}
	
	public static void playFlash(int duration, Color color) {
		flashCount = duration;
		flashColor = color;
	}
	
	public static void playTransition(int duration, Color postColor, Runnable action) {
		transitionCount = duration;
		postTransitionColor = postColor;
		transitionedAction = action;
	}
	
	public static void prefix1() {
		VisualEffects.screenShake(100);
		DelayTrigger.runDelayedAction(() -> VisualEffects.playFlash(255, new Color(255,255,255)), 100);
	}
	
	public static boolean inTransition() {
		return transitionCount > 0;
	}
}
