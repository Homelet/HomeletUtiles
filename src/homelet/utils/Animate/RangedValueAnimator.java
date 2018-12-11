package homelet.utils.Animate;

public class RangedValueAnimator extends Animator<RangedValueAnimator>{
	
	public static final boolean FORWARD    = true;
	public static final boolean REVERSE    = false;
	private             boolean forwarding = true;
	private             double  max        = 0, min = 0;
	private double from, current, to;
	private double      interval;
	private double      totalDistance;
	private Accelerator accelerator;
	private long        startTime;
	
	public RangedValueAnimator(){}
	
	public RangedValueAnimator bounds(double bound1, double bound2){
		if(!processing){
			this.min = Math.min(bound1, bound2);
			this.max = Math.max(bound1, bound2);
		}
		return this;
	}
	
	public RangedValueAnimator from(double from){
		if(!processing){
			this.from = from;
		}
		return this;
	}
	
	public RangedValueAnimator to(double to){
		if(!processing){
			this.to = to;
		}
		return this;
	}
	
	public RangedValueAnimator setForwarding(boolean forwarding){
		if(!processing){
			this.forwarding = forwarding;
		}
		return this;
	}
	
	@Override
	public Accelerator accelerator(){
		return accelerator;
	}
	
	public double min(){
		return min;
	}
	
	public double max(){
		return max;
	}
	
	public double from(){
		return from;
	}
	
	public double current(){
		return current;
	}
	
	public double to(){
		return to;
	}
	
	public boolean forwarding(){
		return forwarding;
	}
	
	@Override
	public RangedValueAnimator accelerator(Accelerator accelerator){
		if(accelerator == null)
			this.accelerator = Accelerator.LINEAR;
		else
			this.accelerator = accelerator;
		return this;
	}
	
	private boolean checkOutOfBounds(double value){
		return value < min || value >= max;
	}
	
	private double getTotalDistance(){
		if(forwarding){
			if(from >= to){
				return (max - from) + (to - min);
			}else{
				return to - from;
			}
		}else{
			if(from > to){
				return from - to;
			}else{
				return (from - min) + (max - to);
			}
		}
	}
	
	@Override
	public RangedValueAnimator animate(long delay){
		if(!processing){
			// first check if the argument are legal
			if(checkOutOfBounds(from) || checkOutOfBounds(to))
				throw new IllegalArgumentException("From or To out of bounds!");
			processing = true;
			this.totalDistance = getTotalDistance();
			double totalTimeFrame = accelerator.reverse(totalDistance);
			interval = totalTimeFrame / delay;
			current = from;
			startTime = System.currentTimeMillis();
		}
		return this;
	}
	
	@Override
	public synchronized RangedValueAnimator terminate(){
		if(processing){
			processing = false;
			onAnimateFinnish();
			current = to;
		}
		return this;
	}
	
	@Override
	public synchronized RangedValueAnimator stop(){
		if(processing){
			processing = false;
		}
		return this;
	}
	
	private double getDistance(long timePassed){
		double increase = accelerator.f(timePassed * interval);
		return forwarding ? increase : -increase;
	}
	
	@Override
	public void tick(){
		if(processing){
			long   timePassed = System.currentTimeMillis() - startTime;
			double thisRaise  = getDistance(timePassed);
			double thisV      = from + calculateActualValue(thisRaise);
			if(thisRaise >= totalDistance){
				current = to;
			}else
				current = thisV;
			if(current == to){
				processing = false;
				from(to);
				onAnimateFinnish();
			}
		}
	}
	
	private double calculateActualValue(double value){
		if(value >= max){
			return value % (max - min) + min;
		}else if(value < min){
			return max - (min - value) % (max - min);
		}else{
			return value;
		}
	}
}
