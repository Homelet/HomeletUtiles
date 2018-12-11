package homelet.utils.math;

import java.util.Objects;

public class Angle implements Comparable<Angle>, Cloneable{
	
	public static final Angle DEGREE_0   = degree(0);
	public static final Angle DEGREE_30  = degree(30);
	public static final Angle DEGREE_60  = degree(60);
	public static final Angle DEGREE_45  = degree(45);
	public static final Angle DEGREE_90  = degree(90);
	public static final Angle DEGREE_120 = degree(120);
	public static final Angle DEGREE_150 = degree(150);
	public static final Angle DEGREE_180 = degree(180);
	public static final Angle DEGREE_360 = degree(360);
	
	public static Angle degree(double value){
		return new Angle(value, AngleUnit.DEGREES);
	}
	
	public static Angle radians(double value){
		return new Angle(value, AngleUnit.RADIANS);
	}
	
	private final AngleUnit type;
	private final double    value;
	
	public Angle(Angle angle){
		this(angle.value, angle.type);
	}
	
	public Angle(double value){
		this(value, AngleUnit.RADIANS);
	}
	
	public Angle(double value, AngleUnit type){
		this.value = value;
		this.type = type;
	}
	
	public double getDegrees(){
		switch(type){
			case DEGREES:
				return value;
			case RADIANS:
				return Math.toDegrees(value);
			default:
				return 0.d;
		}
	}
	
	public double getRadians(){
		switch(type){
			case DEGREES:
				return Math.toRadians(value);
			case RADIANS:
				return value;
			default:
				return 0.d;
		}
	}
	
	public AngleUnit getUnit(){
		return type;
	}
	
	@Override
	public int compareTo(Angle o){
		return Double.compare(this.getRadians() % (2 * Math.PI), o.getRadians() % (2 * Math.PI));
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(!(o instanceof Angle))
			return false;
		Angle angle = (Angle) o;
		return this.compareTo(angle) == 0;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(type, value);
	}
	
	/**
	 * @author HomeletWei
	 * @date Jun 11, 2018
	 */
	public enum AngleUnit{
		DEGREES,
		RADIANS;
	}
}