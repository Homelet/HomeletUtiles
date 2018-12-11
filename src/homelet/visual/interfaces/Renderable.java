package homelet.visual.interfaces;

import java.awt.*;

/**
 * The interface homelet.Visual.Renderable.
 */
public interface Renderable{
	
	/**
	 * responsible for updating the info, this method is called before {@link Renderable#render(Graphics2D)}
	 */
	void tick();
	
	/**
	 * responsible for drawing object on the graphics object<br>
	 *
	 * @param g the g
	 */
	void render(Graphics2D g);
	
	default boolean isTicking(){
		return true;
	}
	
	default boolean isRendering(){
		return true;
	}
}
