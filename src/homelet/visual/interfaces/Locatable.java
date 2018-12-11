package homelet.visual.interfaces;

import java.awt.*;

public interface Locatable{
	
	/**
	 * Gets the size for the partial
	 *
	 * @return the size
	 */
	Dimension getSize();
	
	/**
	 * Gets for the dimention.
	 *
	 * @param bounds the globe bounds
	 * @return the vertex
	 */
	Point getVertex(Rectangle bounds);
}
