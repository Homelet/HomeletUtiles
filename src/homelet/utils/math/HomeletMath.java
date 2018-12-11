package homelet.utils.math;

import java.awt.geom.Point2D;

public class HomeletMath{
	
	public static double getDistance(Point2D p1, Point2D p2){
		double dropX = p1.getX() - p2.getX();
		double dropY = p1.getY() - p2.getY();
		return Math.abs(Math.pow(Math.pow(dropX, 2) + Math.pow(dropY, 2), 0.5));
	}
	
	public static Function getLine(Point2D p1, Point2D p2){
		double slope = getSlope(p1, p2);
		double bias  = p1.getX() * slope - p1.getY();
		return x->new double[]{ slope * x + bias };
	}
	
	public static double getSlope(Point2D p1, Point2D p2){
		double changeInX = p2.getX() - p1.getX();
		double changeInY = p2.getY() - p2.getX();
		return changeInY / changeInX;
	}
	
	public static double getDistance(Point2D p, Function line){

		return 0;
	}
}
