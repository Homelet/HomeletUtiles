package homelet.utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Design for giving accurate position
 * <br>
 * <ul>
 * <li>top left</li>
 * <ul>
 * <li>the context will be display at the top left corner of the canvas</li>
 * </ul>
 * <li>top center</li>
 * <ul>
 * <li>the context will be display at the top center of the canvas</li>
 * </ul>
 * <li>top right</li>
 * <ul>
 * <li>the context will be display at the top right corner of the canvas</li>
 * </ul>
 * <li>center left</li>
 * <ul>
 * <li>the context will be display at the top center left of the canvas</li>
 * </ul>
 * <li>center</li>
 * <ul>
 * <li>the context will be display at the center of the canvas</li>
 * </ul>
 * <li>center right</li>
 * <ul>
 * <li>the context will be display at the center right of the canvas</li>
 * </ul>
 * <li>bottom left</li>
 * <ul>
 * <li>the context will be display at the bottom left of the canvas</li>
 * </ul>
 * <li>bottom center</li>
 * <ul>
 * <li>the context will be display at the bottom center of the canvas</li>
 * </ul>
 * <li>bottom right</li>
 * <ul>
 * <li>the context will be display at the bottom right of the canvas</li>
 * </ul>
 * <li>keep x on left</li>
 * <ul>
 * <li>the context will be display at the given y position with x = 0 of the canvas</li>
 * </ul>
 * <li>keep x on center</li>
 * <ul>
 * <li>the context will be display at the given y position with x = half the of (width - imageWidth) of the canvas</li>
 * </ul>
 * <li>keep x on right</li>
 * <ul>
 * <li>the context will be display at the given y position with x = width - imageWidth of the canvas</li>
 * </ul>
 * <li>keep y on top</li>
 * <ul>
 * <li>the context will be display at the given x position with y = 0 of the canvas</li>
 * </ul>
 * <li>keep y on center</li>
 * <ul>
 * <li>the context will be display at the given x position with y = half the of (width - imageWidth) of the canvas</li>
 * </ul>
 * <li>keep y on bottom</li>
 * <ul>
 * <li>the context will be display at the given x position with y = 0 of the canvas</li>
 * </ul>
 * <li>customize</li>
 * <ul>
 * <li>the context will be display at the customize position of the canvas</li>
 * </ul>
 * </ul>
 *
 * @author HomeletWei  Apr 25, 2018
 */
public enum Alignment{
	//////////////////////////////////VV Full Position Alignment VV/////////////////////////////////////////
	/** <b>TOP_LEFT</b> the context will be display at the top left corner of the canvas */
	TOP_LEFT,
	/**
	 * The North west.
	 *
	 * @see Alignment#TOP_LEFT Alignment#TOP_LEFT
	 */
	NORTH_WEST(TOP_LEFT),
	/** <b>TOP</b> the context will be display at the top center of the canvas */
	TOP,
	/**
	 * The North.
	 *
	 * @see Alignment#TOP Alignment#TOP
	 */
	NORTH(TOP),
	/** <b>TOP_RIGHT</b> the context will be display at the top right corner of the canvas */
	TOP_RIGHT,
	/**
	 * The North east.
	 *
	 * @see Alignment#TOP_RIGHT Alignment#TOP_RIGHT
	 */
	NORTH_EAST(TOP_RIGHT),
	/** <b>LEFT</b> the context will be display at the top center left of the canvas */
	LEFT,
	/**
	 * The West.
	 *
	 * @see Alignment#LEFT Alignment#LEFT
	 */
	WEST(LEFT),
	/** <b>CENTER</b> the context will be display at the center of the canvas */
	CENTER,
	/**
	 * The Origin.
	 *
	 * @see Alignment#CENTER Alignment#CENTER
	 */
	ORIGIN(CENTER),
	/** <b>RIGHT</b> the context will be display at the center right of the canvas */
	RIGHT,
	/**
	 * The East.
	 *
	 * @see Alignment#RIGHT Alignment#RIGHT
	 */
	EAST(RIGHT),
	/** <b>BOTTOM_LEFT</b> the context will be display at the bottom left of the canvas */
	BOTTOM_LEFT,
	/**
	 * The South west.
	 *
	 * @see Alignment#BOTTOM_LEFT Alignment#BOTTOM_LEFT
	 */
	SOUTH_WEST(BOTTOM_LEFT),
	/** <b>BOTTOM</b> the context will be display at the bottom center of the canvas */
	BOTTOM,
	/**
	 * The South.
	 *
	 * @see Alignment#BOTTOM Alignment#BOTTOM
	 */
	SOUTH(BOTTOM),
	/** <b>BOTTOM_RIGHT</b> the context will be display at the bottom center of the canvas */
	BOTTOM_RIGHT,
	/**
	 * The South east.
	 *
	 * @see Alignment#BOTTOM_RIGHT Alignment#BOTTOM_RIGHT
	 */
	SOUTH_EAST(BOTTOM_RIGHT),
	//////////////////////////////////VV Single Line Alignment VV/////////////////////////////////////////
	/** <b>KEEP_X_ON_LEFT</b> the context will be display at the given y position with x = 0 of the canvas */
	KEEP_X_ON_LEFT,
	/**
	 * The Keep x on west.
	 *
	 * @see Alignment#KEEP_X_ON_LEFT Alignment#KEEP_X_ON_LEFT
	 */
	KEEP_X_ON_WEST(KEEP_X_ON_LEFT),
	/** <b>KEEP_X_ON_CENTER</b> the context will be display at the given y position with x = half the of (width - imageWidth) of the canvas */
	KEEP_X_ON_CENTER,
	/**
	 * The Keep x on origin.
	 *
	 * @see Alignment#KEEP_X_ON_CENTER Alignment#KEEP_X_ON_CENTER
	 */
	KEEP_X_ON_ORIGIN(KEEP_X_ON_CENTER),
	/** <b>KEEP_X_ON_RIGHT</b> the context will be display at the given y position with x = width - imageWidth of the canvas */
	KEEP_X_ON_RIGHT,
	/**
	 * The Keep x on east.
	 *
	 * @see Alignment#KEEP_X_ON_RIGHT Alignment#KEEP_X_ON_RIGHT
	 */
	KEEP_X_ON_EAST(KEEP_X_ON_RIGHT),
	/** <b>KEEP_Y_ON_TOP</b> the context will be display at the given x position with y = 0 of the canvas */
	KEEP_Y_ON_TOP,
	/**
	 * The Keep y on north.
	 *
	 * @see Alignment#KEEP_Y_ON_TOP Alignment#KEEP_Y_ON_TOP
	 */
	KEEP_Y_ON_NORTH(KEEP_Y_ON_TOP),
	/** <b>KEEP_Y_ON_CENTER</b> the context will be display at the given x position with y = half the of (width - imageWidth) of the canvas */
	KEEP_Y_ON_CENTER,
	/**
	 * The Keep y on origin.
	 *
	 * @see Alignment#KEEP_Y_ON_CENTER Alignment#KEEP_Y_ON_CENTER
	 */
	KEEP_Y_ON_ORIGIN(KEEP_Y_ON_CENTER),
	/** <b>KEEP_Y_ON_BOTTOM</b> the context will be display at the given x position with y = 0 of the canvas */
	KEEP_Y_ON_BOTTOM,
	/**
	 * The Keep y on south.
	 *
	 * @see Alignment#KEEP_Y_ON_BOTTOM Alignment#KEEP_Y_ON_BOTTOM
	 */
	KEEP_Y_ON_SOUTH(KEEP_Y_ON_BOTTOM),
	/////////////////////////////////////VV Free Alignment VV///////////////////////////////////////////
	/** <b>CUSTOMIZE</b> the context will be display at the customize position of the canvas */
	CUSTOMIZE;
	/**
	 * The Equivalent.
	 */
	Alignment equivalent;
	
	Alignment(){
		this.equivalent = this;
	}
	
	Alignment(Alignment equivalent){
		this.equivalent = equivalent;
	}
	
	/**
	 * Equals boolean.
	 *
	 * @param alignment the alignment
	 * @return the boolean
	 */
	public boolean equals(Alignment alignment){
		return alignment == this || alignment == this.equivalent;
	}
	
	/**
	 * Get vertex point 2 d.
	 *
	 * @param usingFloat the using float
	 * @param container  the container
	 * @param object     the object
	 * @return the point 2 d
	 */
	public Point2D getVertex(boolean usingFloat, Rectangle2D container, Rectangle2D object){
		return Alignment.getVertex(this, usingFloat, container.getX(), container.getY(), container.getWidth(), container.getHeight(), object.getX(), object.getY(), object.getWidth(), object.getHeight());
	}
	
	/**
	 * Get vertex point 2 d.
	 *
	 * @param usingFloat the using float
	 * @param container  the container
	 * @param object     the object
	 * @return the point 2 d
	 */
	public Point getVertex(boolean usingFloat, Rectangle container, Rectangle object){
		Point2D point2D = Alignment.getVertex(this, usingFloat, container.getX(), container.getY(), container.getWidth(), container.getHeight(), object.getX(), object.getY(), object.getWidth(), object.getHeight());
		return new Point((int) point2D.getX(), (int) point2D.getY());
	}
	
	/**
	 * Design for giving accurate position
	 *
	 * @param alignment        the alignment mode
	 * @param usingFloat       the using float
	 * @param container_x      the container x
	 * @param container_y      the container y
	 * @param container_width  the container width
	 * @param container_height the container height
	 * @param object_x         the object x
	 * @param object_y         the object y
	 * @param object_width     the object width
	 * @param object_height    the object height
	 * @return a point that meet the desire
	 * @author HomeletWei
	 * @see Alignment
	 */
	public static Point2D getVertex(Alignment alignment, boolean usingFloat, double container_x, double container_y, double container_width, double container_height, double object_x, double object_y, double object_width, double object_height){
		switch(alignment){
			case NORTH_WEST:
			case TOP_LEFT:
				return GH.point(usingFloat, container_x, container_y);
			case NORTH:
			case TOP:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y);
			case NORTH_EAST:
			case TOP_RIGHT:
				return GH.point(usingFloat, container_x + (container_width - object_width), container_y);
			case WEST:
			case LEFT:
				return GH.point(usingFloat, container_x, container_y + (container_height - object_height) / 2);
			case ORIGIN:
			case CENTER:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y + (container_height - object_height) / 2);
			case EAST:
			case RIGHT:
				return GH.point(usingFloat, container_x + (container_width - object_width), container_y + (container_height - object_height) / 2);
			case SOUTH_WEST:
			case BOTTOM_LEFT:
				return GH.point(usingFloat, container_x, container_y + (container_height - object_height));
			case SOUTH:
			case BOTTOM:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y + (container_height - object_height));
			case SOUTH_EAST:
			case BOTTOM_RIGHT:
				return GH.point(usingFloat, container_x + (container_width - object_width), container_y + (container_height - object_height));
			case KEEP_X_ON_WEST:
			case KEEP_X_ON_LEFT:
				return GH.point(usingFloat, container_x, container_y + object_y);
			case KEEP_X_ON_ORIGIN:
			case KEEP_X_ON_CENTER:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y + object_y);
			case KEEP_X_ON_EAST:
			case KEEP_X_ON_RIGHT:
				return GH.point(usingFloat, container_x + container_width - object_width, container_y + object_y);
			case KEEP_Y_ON_NORTH:
			case KEEP_Y_ON_TOP:
				return GH.point(usingFloat, container_x + object_x, container_y);
			case KEEP_Y_ON_ORIGIN:
			case KEEP_Y_ON_CENTER:
				return GH.point(usingFloat, container_x + object_x, container_y + (container_height - object_height) / 2);
			case KEEP_Y_ON_SOUTH:
			case KEEP_Y_ON_BOTTOM:
				return GH.point(usingFloat, container_x + object_x, container_y + container_height - object_height);
			case CUSTOMIZE:
			default:
				return GH.point(usingFloat, container_x + object_x, container_y + object_y);
		}
	}
}