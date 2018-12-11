package homelet.utils;

import java.awt.image.BufferedImage;

public class FramedImage{
	
	private final BufferedImage[] frame;
	private       PlayBack        playBack;
	private       int             cursor     = 0;
	private       boolean         forwarding = true;
	
	public FramedImage(BufferedImage[] frame){
		this(frame, PlayBack.IN_ORDER);
	}
	
	public FramedImage(BufferedImage[] frame, PlayBack playBack){
		this.playBack = playBack;
		this.frame = frame;
	}
	
	public BufferedImage imageAt(int index){
		if(index < 0 || index >= frame.length)
			return null;
		return frame[index];
	}
	
	public BufferedImage next(){
		return frame[nextCursor()];
	}
	
	public BufferedImage jumpTo(int index){
		return frame[setCursor(cursor)];
	}
	
	private int nextCursor(){
		if(forwarding)
			cursor = cursor + 1;
		else
			cursor = cursor - 1;
		checkBounding();
		return cursor;
	}
	
	private int setCursor(int cursor){
		this.cursor = Math.abs(cursor % frame.length);
		return this.cursor;
	}
	
	private void checkBounding(){
		if(cursor < 0 || cursor >= frame.length){
			switch(playBack){
				default:
				case IN_ORDER:
					cursor = 0;
					forwarding = true;
					break;
				case REVERSED:
					cursor = frame.length - 1;
					forwarding = false;
					break;
				case BOUNCING:
					if(forwarding){
						forwarding = false;
						cursor = frame.length - 1;
					}else{
						forwarding = true;
						cursor = 0;
					}
					break;
			}
		}
	}
	
	public void setPlayBack(PlayBack playBack){
		this.playBack = playBack;
	}
	
	public int countFrame(){
		return frame.length;
	}
	
	public enum PlayBack{
		IN_ORDER,
		REVERSED,
		BOUNCING;
	}
}
