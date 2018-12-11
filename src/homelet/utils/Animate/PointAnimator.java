package homelet.utils.Animate;

import homelet.utils.GH;

import java.awt.*;
import java.awt.geom.Point2D;

public class PointAnimator extends Animator<PointAnimator>{
	
	final private ValueAnimator x;
	final private ValueAnimator y;
	private       Point2D       from;
	private       Point2D       current;
	private       Point2D       to;
	
	public PointAnimator(){
		x = new ValueAnimator();
		y = new ValueAnimator();
	}
	
	public PointAnimator from(double x, double y){
		return from(GH.point(false, x, y));
	}
	
	public PointAnimator from(Point2D from){
		if(!processing){
			this.from = from;
			this.current = (Point2D) from.clone();
			x.from(from.getX());
			y.from(from.getY());
		}
		return this;
	}
	
	public PointAnimator to(double x, double y){
		return to(GH.point(false, x, y));
	}
	
	public PointAnimator to(Point2D to){
		if(!processing){
			this.to = to;
			x.to(to.getX());
			y.to(to.getY());
		}
		return this;
	}
	
	public Point2D from(){
		return from;
	}
	
	public Point2D to(){
		return to;
	}
	
	public Point2D current2D(){
		return current;
	}
	
	public Point current(){
		return new Point((int) current2D().getX(), (int) current2D().getY());
	}
	
	@Override
	public PointAnimator accelerator(Accelerator accelerator){
		x.accelerator(accelerator);
		y.accelerator(accelerator);
		return this;
	}
	
	@Override
	public Accelerator accelerator(){
		return x.accelerator();
	}
	
	@Override
	public PointAnimator animate(long delay){
		if(!processing){
			processing = true;
			x.animate(delay);
			y.animate(delay);
		}
		return this;
	}
	
	@Override
	public PointAnimator terminate(){
		if(processing){
			x.terminate();
			y.terminate();
			processing = false;
			from(to);
			onAnimateFinnish();
		}
		return this;
	}
	
	@Override
	public PointAnimator stop(){
		if(processing){
			processing = false;
			x.stop();
			y.stop();
		}
		return this;
	}
	
	@Override
	public void tick(){
		if(processing){
			x.tick();
			y.tick();
			current.setLocation(x.current(), y.current());
			if(!x.processing && !y.processing){
				processing = false;
				from(current);
				onAnimateFinnish();
			}
		}
	}
}
