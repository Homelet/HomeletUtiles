package homelet.visual;

import java.awt.*;

public class JCanvas extends Canvas{
	
	private static final long         serialVersionUID = 1L;
	// util
	private              CanvasThread canvasThread;
	
	public JCanvas(String threadName){
		this.setFocusable(true);
		this.setIgnoreRepaint(true);
		createCanvasThread(threadName);
	}
	
	private void createCanvasThread(String name){
		CanvasThread thread = null;
		try{
			thread = new CanvasThread(this, name);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.canvasThread = thread;
	}
	
	public CanvasThread getCanvasThread(){
		return canvasThread;
	}
	
	public void startRendering(){
		canvasThread.startRendering();
	}
	
	public void stopRendering(){
		canvasThread.stopRendering();
	}
}
