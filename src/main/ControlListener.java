package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import util.Container;

public class ControlListener implements MouseListener, MouseMotionListener, KeyListener {

	public static boolean mouseDown = false;
	
	@Override
	public void keyPressed(KeyEvent e) {
		Game.onKeyDown(e.getKeyCode());
		//Game.onKeyDown(e.getKeyChar());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Game.onKeyUp(e.getKeyCode());
		//Game.onKeyUp(e.getKeyChar());
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Canvas.mouseX = e.getX();
		Canvas.mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Canvas.mouseX = e.getX();
		Canvas.mouseY = e.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (Container.currentContainer != null) {
			Container.currentContainer.mouseClicked();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		Main.com.enterPrompt("MouseDown");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		
	}

}
