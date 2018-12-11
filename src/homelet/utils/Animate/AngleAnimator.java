package homelet.utils.Animate;

import homelet.utils.math.Angle;
import homelet.utils.math.Angle.AngleUnit;

public class AngleAnimator extends Animator<AngleAnimator>{
	
	private final RangedValueAnimator angle;
	private       Angle               from;
	private       Angle               to;
	private       Angle               current;
	
	public AngleAnimator(){
		angle = new RangedValueAnimator();
		angle.bounds(0, Math.nextUp(Math.PI * 2));
	}
	
	public AngleAnimator from(Angle from){
		if(!processing){
			this.from = from;
			this.current = new Angle(from);
			angle.from(from.getRadians());
		}
		return this;
	}
	
	public AngleAnimator to(Angle to){
		if(!processing){
			this.to = to;
			angle.to(to.getRadians());
		}
		return this;
	}
	
	public Angle from(){
		return from;
	}
	
	public Angle to(){
		return to;
	}
	
	public Angle current(){
		return current;
	}
	
	@Override
	public Accelerator accelerator(){
		return angle.accelerator();
	}
	
	@Override
	public AngleAnimator accelerator(Accelerator accelerator){
		angle.accelerator(accelerator);
		return this;
	}
	
	long startTime;
	
	@Override
	public AngleAnimator animate(long delay){
		if(!processing){
			processing = true;
			angle.animate(delay);
			startTime = System.currentTimeMillis();
		}
		return this;
	}
	
	@Override
	public AngleAnimator terminate(){
		if(processing){
			angle.terminate();
			processing = false;
			from(to);
			onAnimateFinnish();
		}
		return this;
	}
	
	@Override
	public AngleAnimator stop(){
		if(processing){
			angle.stop();
			processing = false;
		}
		return this;
	}
	
	@Override
	public void tick(){
		if(processing){
			angle.tick();
			setCurrent(angle.current());
			if(!angle.processing){
				processing = false;
				from(current);
				onAnimateFinnish();
			}
		}
	}
	
	private void setCurrent(double current){
		this.current = new Angle(current, AngleUnit.RADIANS);
	}
}
