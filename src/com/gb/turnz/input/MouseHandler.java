package com.gb.turnz.input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.gb.turnz.base.Game;
import com.gb.turnz.graphics.Screen;

public class MouseHandler implements MouseListener, MouseMotionListener {

	private static final int BUTTON_COUNT = 3;
	private int mousePosX;
	private int mousePosY;
	private int currentPosX;
	private int currentPosY;
	private boolean[] state = null;
	private MouseState[] poll = null;
	private int pastX, pastY;

	private enum MouseState {
		RELEASED, PRESSED, ONCE
	}

	public MouseHandler() {
		Game.getInstance().addMouseListener(this);
		Game.getInstance().addMouseMotionListener(this);
		state = new boolean[BUTTON_COUNT];
		poll = new MouseState[BUTTON_COUNT];
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			poll[i] = MouseState.RELEASED;
		}

	}

	public synchronized void poll() {
		mousePosX = currentPosX;
		mousePosY = currentPosY;
		currentPosX = pastX;
		currentPosY = pastY;
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			if (state[i]) {
				if (poll[i] == MouseState.RELEASED)
					poll[i] = MouseState.ONCE;
				else
					poll[i] = MouseState.PRESSED;
			} else {
				poll[i] = MouseState.RELEASED;
			}
		}
	}

	public int x() {
		return mousePosX;
	}

	public int y() {
		return mousePosY;
	}
	
	public Rectangle getRect() {
		return new Rectangle(mousePosX, mousePosY, 1, 1);
	}
	
	public boolean hasMoved() {
		return (mousePosX != currentPosX) || (mousePosY != currentPosY);
	}

	public boolean buttonDownOnce(int button) {
		return poll[button - 1] == MouseState.ONCE;
	}

	public boolean buttonDown(int button) {
		return poll[button - 1] == MouseState.ONCE || poll[button - 1] == MouseState.PRESSED;
	}

	public synchronized void mousePressed(MouseEvent e) {
		state[e.getButton() - 1] = true;
	}

	public synchronized void mouseReleased(MouseEvent e) {
		state[e.getButton() - 1] = false;
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		pastX = currentPosX;
		pastY = currentPosY;
		currentPosX = (int) (e.getX() * (Screen.WIDTH / (float) Game.getInstance().getWidth()));
		currentPosY = (int) (e.getY() * (Screen.HEIGHT / (float) Game.getInstance().getHeight()));
	}

	public void mouseClicked(MouseEvent e) {

	}

}