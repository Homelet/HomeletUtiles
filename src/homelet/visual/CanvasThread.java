package homelet.visual;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CanvasThread implements Runnable{
	
	private RenderManager       renderManager;
	private JCanvas             parent;
	// util
	private int                 FPS;
	private String              name;
	private boolean             clearScreenBeforeRender = true;
	private boolean             printNoticeInConsole    = false;
	private Map<Object, Object> renderingHintsMapping;
	//<!--MultiThreading-->
	private boolean             running                 = false;
	private int                 fps                     = 30;
	// render mode
	private RenderMode          renderMode              = RenderMode.RENDER_BY_FPS;
	
	CanvasThread(JCanvas parent, String name) throws Exception{
		this(parent, name, new RenderManager());
	}
	
	CanvasThread(JCanvas parent, String name, RenderManager renderManager) throws Exception{
		this.name = name;
		this.parent = parent;
		setRenderManager(renderManager);
		this.renderingHintsMapping = new HashMap<>();
	}
	
	public RenderManager getRenderManager(){
		return renderManager;
	}
	
	public void setRenderManager(RenderManager renderManager){
		if(renderManager == null)
			return;
		if(renderManager.getParent() != null && renderManager.getParent() != parent){
			throw new IllegalArgumentException("Can't add RenderManager because RenderManager implements a different JCanvas");
		}else{
			this.renderManager = renderManager;
			this.renderManager.setParent(parent);
		}
	}
	
	public boolean isClearingScreenBeforeRender(){
		return clearScreenBeforeRender;
	}
	
	public void setClearScreenBeforeRender(boolean clearScreenBeforeRender){
		this.clearScreenBeforeRender = clearScreenBeforeRender;
	}
	
	public boolean isPrintingNoticeInConsole(){
		return printNoticeInConsole;
	}
	
	public void setPrintNoticeInConsole(boolean printNoticeInConsole){
		this.printNoticeInConsole = printNoticeInConsole;
	}
	
	public RenderMode getRenderMode(){
		return renderMode;
	}
	
	public void setRenderMode(RenderMode renderMode){
		if(running){
			System.err.println("RenderMode will not proceed because CanvasThread is currently rendering");
		}
		this.renderMode = renderMode;
	}
	
	public Map<Object, Object> getRenderingHintsMapping(){
		return renderingHintsMapping;
	}
	
	public void setRenderingHintsMapping(Map<Object, Object> renderingHintsMap){
		this.renderingHintsMapping = renderingHintsMap;
	}
	
	/**
	 * create a snapshot of the current thread
	 *
	 * @return a bufferedImage
	 * @author HomeletWei
	 */
	public BufferedImage createSnapshot(){
		Dimension     finalDi = parent.getSize();
		BufferedImage bg      = new BufferedImage(finalDi.width, finalDi.height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D    g       = bg.createGraphics();
		/////////////////////-draw Start-/////////////////////
		g.clearRect(0, 0, finalDi.width, finalDi.height);
		renderManager.render(g);
		/////////////////////-draw End-//////////////////////
		g.dispose();
		bg.flush();
		return bg;
	}
	
	//<!--MultiThreading-->
	@Override
	public void run(){
		switch(getRenderMode()){
			case RENDER_BY_FPS:{
				int    FPSBuffer   = 0;
				double timePerTick = 1000000000.0 / fps;
				double delta       = 0;
				long   now;
				long   lastTime;
				long   timer       = 0;
				lastTime = System.nanoTime();
				parent.createBufferStrategy(3);
				BufferStrategy bs = parent.getBufferStrategy();
				while(running){
					now = System.nanoTime();
					delta += (now - lastTime) / timePerTick;
					timer += now - lastTime;
					lastTime = now;
					if(fps <= 0 || delta >= 1){
						FPSBuffer++;
						delta = 0;
						render(bs);
					}
					if(timer >= 1000000000){
						updateFPS(FPSBuffer);
						if(isPrintingNoticeInConsole())
							System.out.println(name + " : FPS | " + this.checkFPS());
						FPSBuffer = 0;
						timer = 0;
					}
				}
				break;
			}
			case RENDER_WHEN_CALLED:{
				parent.createBufferStrategy(3);
				BufferStrategy bs               = parent.getBufferStrategy();
				int            FPSBuffer        = 0;
				long           lastTimeCalled   = 0;
				long           currentTimeStop  = 0;
				long           lastTimeFPSCheck = 0;
				while(running){
					if(System.nanoTime() - lastTimeCalled >= currentTimeStop && renderNow){
						currentTimeStop = getTimeStop();
						render(bs);
						FPSBuffer++;
						renderNow = false;
						lastTimeCalled = System.nanoTime();
					}
					if(System.nanoTime() - lastTimeFPSCheck >= 1000000000){
						updateFPS(FPSBuffer);
						if(isPrintingNoticeInConsole())
							System.out.println(name + " : FPS | " + this.checkFPS());
						FPSBuffer = 0;
						lastTimeFPSCheck = System.nanoTime();
					}
				}
				break;
			}
		}
	}
	
	private long    timeStop  = 0;
	private boolean renderNow = false;
	
	public void renderNow(){
		renderNow(0);
	}
	
	public synchronized void renderNow(long timeStop_in_nano){
		if(renderNow) // last call hasn't finished
			return;
		if(getRenderMode() == RenderMode.RENDER_BY_FPS && running){
			System.err.println("Can't process Render because CanvasTread is doing it as another render mode");
			return;
		}
		this.timeStop = timeStop_in_nano;
		this.renderNow = true;
	}
	
	private long getTimeStop(){
		long buffer = timeStop;
		this.timeStop = 0;
		return buffer;
	}
	
	private void render(BufferStrategy bs){
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHints(renderingHintsMapping);
		Dimension dimension = parent.getSize();
		if(isClearingScreenBeforeRender())
			g.clearRect(0, 0, dimension.width, dimension.height);
		/////////////////////-draw Start-/////////////////////
		renderManager.render((Graphics2D) g.create(0, 0, dimension.width, dimension.height));
		/////////////////////-draw End-//////////////////////
		g.dispose();
		bs.show();
	}
	
	private void updateFPS(int fps){
		this.FPS = fps;
	}
	
	public int checkFPS(){
		return FPS;
	}
	
	public void setFPS(int newFPS){
		if(running){
			System.err.println("Setting FPS failed because thread is currently running");
			return;
		}
		this.fps = newFPS;
	}
	
	private Thread renderThread;
	
	/**
	 * start the rendering ASAP
	 *
	 * @author HomeletWei
	 */
	public synchronized void startRendering(){
		if(renderManager == null){
			System.err.println("Start Rendering failed because No RenderManager has been set yet");
			return;
		}
		if(running)
			return;
		running = true;
		renderThread = new Thread(null, this, name);
		renderThread.setPriority(Thread.NORM_PRIORITY);
		renderThread.start();
		if(isPrintingNoticeInConsole())
			System.out.println(name.concat(" Started"));
	}
	
	/**
	 * stop the rendering ASAP
	 *
	 * @author HomeletWei
	 */
	public synchronized void stopRendering(){
		if(!running)
			return;
		running = false;
		try{
			renderThread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		if(isPrintingNoticeInConsole())
			System.out.println(name.concat(" Stopped"));
	}
	
	public enum RenderMode{
		RENDER_BY_FPS,
		RENDER_WHEN_CALLED;
	}
}
