package homelet.utils.Animate;

import homelet.utils.GH;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class SizeAnimator extends Animator<SizeAnimator>{
	
	final private ValueAnimator width;
	final private ValueAnimator height;
	private       Dimension2D   from;
	private       Dimension2D   current;
	private       Dimension2D   to;
	
	public SizeAnimator(){
		width = new ValueAnimator();
		height = new ValueAnimator();
	}
	
	public SizeAnimator from(double x, double y){
		return from(GH.size((int) x, (int) y));
	}
	
	public SizeAnimator from(Dimension2D from){
		if(!processing){
			this.from = from;
			this.current = (Dimension2D) from.clone();
			width.from(from.getWidth());
			height.from(from.getHeight());
		}
		return this;
	}
	
	public SizeAnimator to(double x, double y){
		return to(GH.size((int) x, (int) y));
	}
	
	public SizeAnimator to(Dimension2D to){
		if(!processing){
			this.to = to;
			width.to(to.getWidth());
			height.to(to.getHeight());
		}
		return this;
	}
	
	public Dimension2D from(){
		return from;
	}
	
	public Dimension2D to(){
		return to;
	}
	
	public Dimension2D current2D(){
		return current;
	}
	
	public Dimension current(){
		return (Dimension) current2D();
	}
	
	@Override
	public SizeAnimator accelerator(Accelerator accelerator){
		width.accelerator(accelerator);
		height.accelerator(accelerator);
		return this;
	}
	
	@Override
	public Accelerator accelerator(){
		return width.accelerator();
	}
	
	@Override
	public SizeAnimator animate(long delay){
		if(!processing){
			processing = true;
			width.animate(delay);
			height.animate(delay);
		}
		return this;
	}
	
	@Override
	public SizeAnimator terminate(){
		if(processing){
			width.terminate();
			height.terminate();
			processing = false;
			from(to);
			onAnimateFinnish();
		}
		return this;
	}
	
	@Override
	public SizeAnimator stop(){
		if(processing){
			processing = false;
			width.stop();
			height.stop();
		}
		return this;
	}
	
	@Override
	public void tick(){
		if(processing){
			width.tick();
			height.tick();
			current.setSize(width.current(), height.current());
			if(!width.processing && !height.processing){
				processing = false;
				from(current);
				onAnimateFinnish();
			}
		}
	}
}
