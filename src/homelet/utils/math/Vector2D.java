package homelet.utils.math;

import java.awt.geom.Point2D;
import java.util.Objects;

public class Vector2D{
	
	private double  magnitude;
	private Angle2D angle;
	
	public Vector2D(double magnitude, Angle2D angle){
		this.magnitude = magnitude;
		this.angle = angle;
	}
	
	public Point2D getEndPoint(boolean useFloat){
		double endX = getAngle2D().getVertex().getX() + Math.cos(this.getAngle2D().getAngle().getRadians()) * this.getMagnitude();
		double endY = getAngle2D().getVertex().getY() + Math.sin(this.getAngle2D().getAngle().getRadians()) * this.getMagnitude();
		if(useFloat)
			return new Point2D.Float((float) endX, (float) endY);
		return new Point2D.Double(endX, endY);
	}
	
	public Angle2D getAngle2D(){
		return angle;
	}
	
	public double getMagnitude(){
		return magnitude;
	}
	
	public void setMagnitude(double magnitude){
		this.magnitude = magnitude;
	}
	
	public void setAngle2D(Angle2D angle){
		this.angle = angle;
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Vector2D vector2D = (Vector2D) o;
		return Double.compare(vector2D.magnitude, magnitude) == 0 &&
				Objects.equals(angle, vector2D.angle);
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(magnitude, angle);
	}
}
