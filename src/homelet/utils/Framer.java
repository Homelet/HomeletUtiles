package homelet.utils;

import java.awt.image.BufferedImage;

public class Framer{
	
	private FramedImage   image;
	private BufferedImage current;
	private int           speed;
	private long          lastTime, timer;
	
	public Framer(FramedImage image, int speed){
		this.speed = speed;
		this.image = image;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	// to get all the animation frame for the player
	public void tick(){
		timer += System.currentTimeMillis() - lastTime;
		if(timer > speed){
			current = image.next();
			timer = 0;
		}
		lastTime = System.currentTimeMillis();
	}
	
	public int speed(){
		return speed;
	}
	
	public void speed(int speed){
		this.speed = speed;
	}
	
	public BufferedImage current(){
		return current;
	}
}
