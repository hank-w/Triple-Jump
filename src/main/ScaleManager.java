package main;

public class ScaleManager {
	private static double xConstant = Main.c.getWidth();
	private static double yConstant = Main.c.getHeight();
	
	public static double scaleX = Main.c.getWidth() / xConstant;
	public static double scaleY = Main.c.getHeight() / yConstant;
	
	public static int screenOffsetX = 0;
	public static int screenOffsetY = 0;
	
	public static void initScale() {
		xConstant = Main.c.getWidth();
		yConstant = Main.c.getHeight();
	}
	
	public static void rescale() {
		scaleX = ((double)Main.c.getWidth() / xConstant);
		scaleY = ((double)Main.c.getHeight() / yConstant);
		
		if (Main.c.getWidth() > Main.c.getHeight()) {
			screenOffsetX = (int) ((Main.c.getWidth() - getWidth())/2);
			//screenOffsetY = (int) ((Main.c.getHeight() - yConstant) / 2);
		} else {
			//screenOffsetX = (int) ((Main.c.getWidth() - xConstant) / 2);
			screenOffsetY = (int) ((Main.c.getHeight() - getHeight())/2);
		}
	}
	
	public static double getMin() {
		return Math.min(scaleX, scaleY);
	}
	
	public static double getWidth() {
		return getMin() * xConstant;
	}
	
	public static double getHeight() {
		return getMin() * yConstant;
	}
	
	public static double getxConstant() {
		return xConstant;
	}
	
	public static double getyConstant() {
		return yConstant;
	}
}
