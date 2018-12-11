package homelet.utils;

import homelet.utils.math.Angle;
import homelet.utils.math.Vector2D;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.ImageObserver;

/**
 * The type Gh.
 */
public class GH{
	
	private GH(){}
	// ===================================== point =====================================
	
	/**
	 * create a point using x, y
	 *
	 * @param x the x
	 * @param y the y
	 * @return the point
	 */
	public static Point2D point(boolean usingFloat, double x, double y){
		if(usingFloat)
			return new Point2D.Float((float) x, (float) y);
		return new Point2D.Double(x, y);
	}
	// ===================================== dimension =====================================
	
	/**
	 * create a dimension using width, height
	 *
	 * @param width  the width
	 * @param height the height
	 * @return the point
	 */
	public static Dimension2D size(int width, int height){
		return new Dimension(width, height);
	}
	// ===================================== line =====================================
	
	/**
	 * create a line using dropX, dropY
	 *
	 * @param startX   the start x
	 * @param startY   the start y
	 * @param dropX    the drop x
	 * @param dropY    the drop y
	 * @param useFloat should store with float format
	 * @return the line
	 */
	public static Line2D line(double startX, double startY, double dropX, double dropY, boolean useFloat){
		double endX = startX + dropX;
		double endY = startY + dropY;
		return line(useFloat, startX, startY, endX, endY);
	}
	
	/**
	 * create a line using startX, startY, endX, endY
	 *
	 * @param useFloat should store with float format
	 * @param startX   the start x
	 * @param startY   the start y
	 * @param endX     the end x
	 * @param endY     the end y
	 * @return the line
	 */
	public static Line2D line(boolean useFloat, double startX, double startY, double endX, double endY){
		if(useFloat)
			return new Line2D.Float((float) startX, (float) startY, (float) endX, (float) endY);
		return new Line2D.Double(startX, startY, endX, endY);
	}
	
	/**
	 * create a line using a vector object
	 *
	 * @param usingFloat should store with float format
	 * @param vector     the vector
	 * @return the line
	 */
	public static Line2D line(boolean usingFloat, Vector2D vector){
		Point2D end = vector.getEndPoint(usingFloat);
		return line(usingFloat, vector.getAngle2D().getVertex(), end);
	}
	
	/**
	 * create a line using the start point and end point
	 *
	 * @param useFloat should store with float format
	 * @param start    the start
	 * @param end      the end
	 * @return the line
	 */
	public static Line2D line(boolean useFloat, Point2D start, Point2D end){
		if(useFloat)
			return new Line2D.Float(start, end);
		return new Line2D.Double(start, end);
	}
	// ===================================== Curve =====================================
	
	/**
	 * create a quadratic curve using one control point and start, end
	 *
	 * @param usingFloat should store with float format
	 * @param startX     the start x
	 * @param startY     the start y
	 * @param controlX   the control x
	 * @param controlY   the control y
	 * @param endX       the end x
	 * @param endY       the end y
	 * @return the quad curve
	 */
	public static QuadCurve2D qCurve(boolean usingFloat, double startX, double startY, double controlX, double controlY, double endX, double endY){
		if(usingFloat)
			return new QuadCurve2D.Float((float) startX, (float) startY, (float) controlX, (float) controlY, (float) endX, (float) endY);
		return new QuadCurve2D.Double(startX, startY, controlX, controlY, endX, endY);
	}
	
	/**
	 * create a cubic curve using two control and start, end
	 *
	 * @param usingFloat should store with float format
	 * @param startX     the start x
	 * @param startY     the start y
	 * @param controlX_1 the control x 1
	 * @param controlY_1 the control y 1
	 * @param controlX_2 the control x 2
	 * @param controlY_2 the control y 2
	 * @param endX       the end x
	 * @param endY       the end y
	 * @return the cubic curve
	 */
	public static CubicCurve2D cCurve(boolean usingFloat, double startX, double startY, double controlX_1, double controlY_1, double controlX_2, double controlY_2, double endX, double endY){
		if(usingFloat)
			return new CubicCurve2D.Float((float) startX, (float) startY, (float) controlX_1, (float) controlX_1, (float) controlX_2, (float) controlX_2, (float) endX, (float) endY);
		return new CubicCurve2D.Double(startX, startY, controlX_1, controlY_1, controlX_2, controlY_2, endX, endY);
	}
	// ===================================== Rectangle =====================================
	
	/**
	 * create a rectangle using vertex and size
	 *
	 * @param usingFloat should store with float format
	 * @param vertex     the vertex
	 * @param size       the size
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(boolean usingFloat, Point2D vertex, Dimension2D size){
		return rectangle(usingFloat, vertex.getX(), vertex.getY(), size.getWidth(), size.getHeight());
	}
	
	/**
	 * create a rectangle using x, y, width, height
	 *
	 * @param usingFloat should store with float format
	 * @param x          the x
	 * @param y          the y
	 * @param width      the width
	 * @param height     the height
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(boolean usingFloat, double x, double y, double width, double height){
		if(usingFloat)
			return new Rectangle2D.Float((float) x, (float) y, (float) width, (float) height);
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	/**
	 * create a rectangle using vertex and endpoint
	 *
	 * @param usingFloat should store with float format
	 * @param vertex     the vertex
	 * @param endPoint   the end point
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(boolean usingFloat, Point2D vertex, Point2D endPoint){
		return rectangle(vertex.getX(), vertex.getY(), endPoint.getX(), endPoint.getY(), usingFloat);
	}
	
	/**
	 * create a rectangle using startX, startY, endX, endY
	 *
	 * @param startX     the start x
	 * @param startY     the start y
	 * @param endX       the end x
	 * @param endY       the end y
	 * @param usingFloat should store with float format
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(double startX, double startY, double endX, double endY, boolean usingFloat){
		double width  = endX - startX;
		double height = endY - startY;
		return rectangle(usingFloat, startX, startY, width, height);
	}
	
	/**
	 * create a rectangle from a origin point and the padding from top, bottom, left, right
	 *
	 * @param usingFloat should store with float format
	 * @param originX    the origin x
	 * @param originY    the origin y
	 * @param top        the top
	 * @param bottom     the bottom
	 * @param left       the left
	 * @param right      the right
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(boolean usingFloat, double originX, double originY, double top, double bottom, double left, double right){
		double x      = originX - left;
		double y      = originY - top;
		double width  = left + right;
		double height = top + bottom;
		return rectangle(usingFloat, x, y, width, height);
	}
	
	/**
	 * create a rectangle from another rectangle and the padding from top, bottom, left, right
	 *
	 * @param usingFloat      should store with float format
	 * @param parentRectangle the parent rectangle
	 * @param top             the top
	 * @param bottom          the bottom
	 * @param left            the left
	 * @param right           the right
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(boolean usingFloat, Rectangle2D parentRectangle, double top, double bottom, double left, double right){
		double x      = parentRectangle.getX() - left;
		double y      = parentRectangle.getY() - top;
		double width  = parentRectangle.getWidth() + left + right;
		double height = parentRectangle.getHeight() + top + bottom;
		return rectangle(usingFloat, x, y, width, height);
	}
	
	/**
	 * create a rectangle from a line
	 *
	 * @param usingFloat should store with float format
	 * @param line       the line
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(boolean usingFloat, Line2D line){
		return rectangle(line.getX1(), line.getY1(), line.getX2(), line.getY2(), usingFloat);
	}
	
	/**
	 * create a rectangle from size, with x, y equals to 0
	 *
	 * @param usingFloat  should store with float format
	 * @param dimension2D the size
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(boolean usingFloat, Dimension2D dimension2D){
		return rectangle(usingFloat, 0, 0, dimension2D.getWidth(), dimension2D.getHeight());
	}
	
	/**
	 * create a rectangle from 2 value of x-axis, and 2 value from y-axis
	 *
	 * @param x_1        value from x-axis
	 * @param x_2        value from x-axis
	 * @param usingFloat should store with float format
	 * @param y_1        value from y-axis
	 * @param y_2        value from y-axis
	 * @return the rectangle
	 */
	public static Rectangle2D rectangle(double x_1, double x_2, boolean usingFloat, double y_1, double y_2){
		double x  = Double.min(x_1, x_2);
		double y  = Double.min(y_1, y_2);
		double x2 = Double.max(x_1, x_2);
		double y2 = Double.max(y_1, y_2);
		return rectangle(x, y, x2, y2, usingFloat);
	}
	// ===================================== Round Rectangle =====================================
	
	/**
	 * create a round rectangle using another rectangle and arc-width, arc-height
	 *
	 * @param usingFloat should store with float format
	 * @param rectangle  the rectangle
	 * @param arcWidth   the arc width
	 * @param arcHeight  the arc height
	 * @return the round rectangle
	 */
	public static RoundRectangle2D rRectangle(boolean usingFloat, Rectangle2D rectangle, double arcWidth, double arcHeight){
		return rRectangle(usingFloat, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), arcWidth, arcHeight);
	}
	
	/**
	 * create a round rectangle from x, y, width, height, and arcWidth, arcHeight
	 *
	 * @param usingFloat should store with float format
	 * @param x          the x
	 * @param y          the y
	 * @param width      the width
	 * @param height     the height
	 * @param arcWidth   the arc width
	 * @param arcHeight  the arc height
	 * @return the round rectangle
	 */
	public static RoundRectangle2D rRectangle(boolean usingFloat, double x, double y, double width, double height, double arcWidth, double arcHeight){
		if(usingFloat)
			return new RoundRectangle2D.Float((float) x, (float) y, (float) width, (float) height, (float) arcWidth, (float) arcHeight);
		return new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight);
	}
	// ===================================== ellipse =====================================
	
	/**
	 * create a ellipse from a rectangle
	 *
	 * @param usingFloat should store with float format
	 * @param rectangle  the rectangle
	 * @return the ellipse
	 */
	public static Ellipse2D ellipse(boolean usingFloat, Rectangle2D rectangle){
		return ellipse(usingFloat, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
	}
	
	/**
	 * create a ellipse from x, y, width, height
	 *
	 * @param usingFloat should store with float format
	 * @param x          the x
	 * @param y          the y
	 * @param width      the width
	 * @param height     the height
	 * @return the ellipse
	 */
	public static Ellipse2D ellipse(boolean usingFloat, double x, double y, double width, double height){
		if(usingFloat)
			return new Ellipse2D.Float((float) x, (float) y, (float) width, (float) height);
		return new Ellipse2D.Double(x, y, width, height);
	}
	
	/**
	 * create a ellipse from a originX, originY, and radius of the circle
	 *
	 * @param usingFloat should store with float format
	 * @param originX    the origin x
	 * @param originY    the origin y
	 * @param radius     the radius
	 * @return the ellipse
	 */
	public static Ellipse2D ellipse(boolean usingFloat, double originX, double originY, double radius){
		double x      = originX - radius;
		double y      = originY - radius;
		double width  = radius * 2;
		double height = radius * 2;
		return ellipse(usingFloat, x, y, width, height);
	}
	
	/**
	 * create a ellipse from origin and radius
	 *
	 * @param usingFloat should store with float format
	 * @param origin     the origin point
	 * @param radius     the radius
	 * @return the ellipse
	 */
	public static Ellipse2D ellipse(boolean usingFloat, Point2D origin, double radius){
		return ellipse(usingFloat, origin.getX(), origin.getY(), radius);
	}
	// ===================================== arc =====================================
	
	/**
	 * create a arc from a rectangle and start angle, end angle, and the type of the end distribute
	 *
	 * @param usingFloat should store with float format
	 * @param rectangle  the rectangle
	 * @param startAngle the start angle
	 * @param endAngle   the end angle
	 * @param type       the type
	 * @return the arc
	 */
	public static Arc2D arc(boolean usingFloat, Rectangle2D rectangle, Angle startAngle, Angle endAngle, int type){
		return arc(usingFloat, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), startAngle, endAngle, type);
	}
	
	/**
	 * create a arc from x, y, width, height, and start angle, end angle, and type of the end distribute
	 *
	 * @param usingFloat should store with float format
	 * @param x          the x
	 * @param y          the y
	 * @param width      the width
	 * @param height     the height
	 * @param startAngle the start angle
	 * @param endAngle   the end angle
	 * @param type       the type
	 * @return the arc
	 */
	public static Arc2D arc(boolean usingFloat, double x, double y, double width, double height, Angle startAngle, Angle endAngle, int type){
		if(usingFloat)
			return new Arc2D.Float((float) x, (float) y, (float) width, (float) height, (float) startAngle.getDegrees(), (float) endAngle.getDegrees(), type);
		return new Arc2D.Double(x, y, width, height, startAngle.getDegrees(), endAngle.getDegrees(), type);
	}
	// ===================================== graphics =====================================
	
	/**
	 * Draw a image with a Rectangle bound
	 *
	 * @param g        the g
	 * @param image    the image
	 * @param bounds   the bounds
	 * @param observer the observer
	 * @return the boolean
	 */
	public static void draw(Graphics2D g, Image image, Rectangle2D bounds, ImageObserver observer){
		g.drawImage(image, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), observer);
	}
	
	/**
	 * Draw a image with AffineTransform specific
	 *
	 * @param g         the g
	 * @param image     the image
	 * @param transform the transform
	 * @param observer  the observer
	 * @return the boolean
	 */
	public static void draw(Graphics2D g, Image image, AffineTransform transform, ImageObserver observer){
		g.drawImage(image, transform, observer);
	}
	
	/**
	 * draw a image from a vertex specific
	 *
	 * @param g        the g
	 * @param image    the image
	 * @param vertex   the vertex
	 * @param observer the observer
	 * @return the boolean
	 */
	public static void draw(Graphics2D g, Image image, Point2D vertex, ImageObserver observer){
		g.drawImage(image, (int) vertex.getX(), (int) vertex.getY(), observer);
	}
	
	/**
	 * draw a image from a vertex specific
	 *
	 * @param g        the g
	 * @param center   the center
	 * @param image    the image
	 * @param observer the observer
	 * @return the boolean
	 */
	public static void draw(Graphics2D g, Image image, Point2D center, Dimension2D size, ImageObserver observer){
		g.drawImage(image, (int) (center.getX() - size.getWidth() / 2), (int) (center.getY() - size.getHeight() / 2), (int) size.getWidth(), (int) size.getHeight(), observer);
	}
}
