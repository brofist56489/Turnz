package com.gb.turnz.graphics;

public class Light {
	private int brightness;
	private int radius;
	private int x, y;
	
	private float ratio;
	
	public Light(int x, int y, int rad, int bright) {
		this.x = x;
		this.y = y;
		this.radius = rad;
		this.brightness = bright;
		ratio = (float)brightness / (rad * rad);
	}
	
	public void apply(int[] lighting) {
		
		int r2 = radius * radius;
		int d, xp, yp, i;
		
		int x = this.x - Screen.getXoff();
		int y = this.y - Screen.getYoff();
		
		for(int yy=-radius; yy < radius; yy++) {
			yp = yy + y;
			if(yp < 0 || yp >= Screen.HEIGHT) continue;
			for(int xx=-radius; xx < radius; xx++) {
				xp = xx + x;
				if(xp < 0 || xp >= Screen.WIDTH) continue;
				
				d = xx * xx + yy * yy;
				if(d >= r2) {
					continue;
				}
				
				d = r2 - d;
				
				i = xp + yp * Screen.WIDTH;
				lighting[i] += d * ratio;
				if(lighting[i] > 255) lighting[i] = 255;
			}
		}
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
