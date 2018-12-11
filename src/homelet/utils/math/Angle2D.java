package homelet.utils.math;

import java.awt.geom.Point2D;
import java.util.Objects;

public class Angle2D{
	
	private final Point2D vertex;
	private final Angle   angle;
	
	public Angle2D(Point2D vertex, Angle angle){
		this.vertex = vertex;
		this.angle = angle;
	}
	
	public Point2D getVertex(){
		return vertex;
	}
	
	public Angle getAngle(){
		return angle;
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Angle2D angle2D = (Angle2D) o;
		return Objects.equals(vertex, angle2D.vertex) &&
				Objects.equals(angle, angle2D.angle);
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(vertex, angle);
	}
}
