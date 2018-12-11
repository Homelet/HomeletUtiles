/**
 * @author HomeletWei
 * @date Apr 25, 2018
 */
/*
 * Update Log:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 * *****************************************************
 */
package homelet.utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * TODO
 *
 * @author HomeletWei
 * @date Apr 25, 2018
 */
public enum Border{
	/**
	 * Rectangular border style.
	 */
	RECTANGULAR,
	/**
	 * Dashed border style.
	 */
	DASHED,
	/**
	 * Dotted border style.
	 */
	DOTTED;
	
	public void draw(Graphics2D g, Rectangle2D bounds, Color c, int boarderThickness, int secondaryValue){
		Border.drawBorder(g, this, bounds, c, boarderThickness, secondaryValue);
	}
	
	/**
	 * the algorithm is as follow
	 *
	 * <pre>
	 * +---------------------+
	 * |                     |
	 * +---------------------+
	 * +---+             +---+
	 * |   |             |   |
	 * |   |             |   |
	 * |   |             |   |
	 * |   |             |   |
	 * +---+             +---+
	 * +---------------------+
	 * |                     |
	 * +---------------------+
	 * </pre>
	 *
	 * @param g                the g
	 * @param boarderThickness the bound thinkness
	 * @param style            the style
	 * @param c                the c
	 * @param secondaryValue   the secondaryValue
	 */
	public static void drawBorder(Graphics2D g, Border style, Rectangle2D bounds, Color c, int boarderThickness, int secondaryValue){
		switch(style){
			case RECTANGULAR:
				drawBorder_Rectangular(g, bounds, boarderThickness, c);
				break;
			case DASHED:
				drawBorder_Dashed_Dotted(g, bounds, boarderThickness, c, secondaryValue);
				break;
			case DOTTED:
				drawBorder_Dashed_Dotted(g, bounds, boarderThickness, c, boarderThickness);
				break;
			default:
				break;
		}
	}
	
	private static void drawBorder_Rectangular(Graphics2D g, Rectangle2D bounds, int boarderThickness, Color c){
		g.setColor(c);
		g.fill(GH.rectangle(false, bounds.getX(), bounds.getY(), bounds.getWidth(), boarderThickness));
		g.fill(GH.rectangle(false, bounds.getX(), bounds.getY() + boarderThickness, boarderThickness, bounds.getHeight() - 2 * boarderThickness));
		g.fill(GH.rectangle(false, bounds.getX() + bounds.getWidth() - boarderThickness, bounds.getY() + boarderThickness, boarderThickness, bounds.getHeight() - 2 * boarderThickness));
		g.fill(GH.rectangle(false, bounds.getX(), bounds.getY() + bounds.getHeight() - boarderThickness, bounds.getWidth(), boarderThickness));
	}
	
	private static void drawBorder_Dashed_Dotted(Graphics2D g, Rectangle2D bounds, int boundThickness, Color c, int dashedInterval){
		g.setColor(c);
		drawWithDashedInterval_Horizontal(g, bounds, boundThickness, dashedInterval, false);
		drawWidthDashedInterval_Vertical(g, bounds, boundThickness, dashedInterval, false);
	}
	
	private static void drawWithDashedInterval_Horizontal(Graphics2D g, Rectangle2D bounds, int boarderThickness, int dashedInterval, boolean was){
		for(double horizontal = bounds.getX(); horizontal + dashedInterval < bounds.getX() + bounds.getWidth(); horizontal += dashedInterval){
			if(!was){
				was = true;
				// top
				g.fill(GH.rectangle(false, horizontal, bounds.getY(), dashedInterval, boarderThickness));
				// bottom
				g.fill(GH.rectangle(false, horizontal, bounds.getY() + bounds.getHeight() - boarderThickness, dashedInterval, boarderThickness));
			}else{
				was = false;
			}
		}
	}
	
	private static void drawWidthDashedInterval_Vertical(Graphics2D g, Rectangle2D bounds, int boarderThickness, int dashedInterval, boolean was){
		for(double vertical = bounds.getY(); vertical + dashedInterval < bounds.getY() + bounds.getHeight(); vertical += dashedInterval){
			if(!was){
				was = true;
				// left
				g.fill(GH.rectangle(false, bounds.getX(), vertical, boarderThickness, dashedInterval));
				// right
				g.fill(GH.rectangle(false, bounds.getX() + bounds.getWidth() - boarderThickness, vertical, boarderThickness, dashedInterval));
			}else{
				was = false;
			}
		}
	}
}
