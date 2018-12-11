package homelet.utils.Animate;

import homelet.utils.Animate.ValueAnimator.ValueAnimationEvent;

public class ValueAnimator extends Animator<ValueAnimator, ValueAnimationEvent>{
	
	private double              current     = 0.0d;
	private double              from        = 0.0d;
	private double              to          = 0.0d;
	private Accelerator         accelerator = null;
	private ValueAnimationEvent currentEvent;
	
	public ValueAnimator(){}
	
	private void current(double current){
		this.current = current;
	}
	
	public ValueAnimator from(double from){
		this.from = from;
		return this;
	}
	
	public ValueAnimator to(double to){
		this.to = to;
		return this;
	}
	
	@Override
	public ValueAnimator accelerator(Accelerator accelerator){
		this.accelerator = accelerator;
		return this;
	}
	
	public double current(){
		return current;
	}
	
	public double from(){
		return currentEvent.from;
	}
	
	public double to(){
		return currentEvent.to;
	}
	
	@Override
	public Accelerator accelerator(){
		return currentEvent.accelerator;
	}
	
	@Override
	public boolean processing(){
		return currentEvent != null;
	}
	
	@Override
	public ValueAnimator animate(double speed){
		ValueAnimationEvent event = new ValueAnimationEvent(from, to, speed);
		queue.offer(event);
		return this;
	}
	
	@Override
	public ValueAnimator animate(long time_in_mili){
		ValueAnimationEvent event = new ValueAnimationEvent(accelerator, from, to, time_in_mili);
		queue.offer(event);
		return this;
	}
	
	@Override
	public synchronized ValueAnimator toNext(){
		ValueAnimationEvent nextEvent = queue.poll();
		// if has no next event, and there is a event in progress, then stop the event
		if(nextEvent == null && currentEvent != null){
			currentEvent = null;
			onAnimateFinnish();
		}
		return this;
	}
	
	@Override
	public synchronized ValueAnimator terminate(){
		double finalPosition = queue.getLast().to;
		current(finalPosition);
		currentEvent = null;
		queue.clear();
		onAnimateFinnish();
		return this;
	}
	
	@Override
	public synchronized ValueAnimator stop(){
		currentEvent = null;
		queue.clear();
		return this;
	}
	
	@Override
	public synchronized void tick(){
		if(pausing){
			if(currentEvent != null)
				currentEvent.syncroTime();
			return;
		}
		// if have no event currently, then try to poll one from queue
		if(currentEvent == null){
			ValueAnimationEvent event = queue.poll();
			// if no event in queue, return
			if(event == null)
				return;
			else{
				currentEvent = event;
				event.syncroTime();
			}
		}
		currentEvent.updateTime();
		double thisV    = currentEvent.from + currentEvent.getIncrement();
		double thisDrop = currentEvent.to - thisV;
		double lastDrop = currentEvent.to - current;
		if(hasDifferentSign(thisDrop, lastDrop))
			current(currentEvent.to);
		else
			current(thisV);
		if(current == currentEvent.to){
			currentEvent = queue.poll();
			// means all animations in queue are finished so call onFinnish
			if(currentEvent == null)
				onAnimateFinnish();
		}
	}
	
	private boolean hasDifferentSign(double value1, double value2){
		boolean isV1Po = isPositive(value1);
		boolean isV2Po = isPositive(value2);
		return isV1Po != isV2Po;
	}
	
	private boolean isPositive(double value){
		return value >= 0;
	}
	
	class ValueAnimationEvent extends AnimateEvent{
		
		final Accelerator accelerator;
		final double      from, to;
		final double  interval;
		final boolean forwarding;
		long timePassed = 0;
		long lastTime;
		
		public ValueAnimationEvent(Accelerator accelerator, double from, double to, long time_in_mili){
			// setting the accelerator
			if(accelerator == null)
				this.accelerator = Accelerator.LINEAR;
			else
				this.accelerator = accelerator;
			// initialize the event
			this.from = from;
			this.to = to;
			double totalTimeFrame = this.accelerator.reverse(Math.abs(from - to));
			this.interval = totalTimeFrame / time_in_mili;
			this.forwarding = from <= to;
		}
		
		public ValueAnimationEvent(double from, double to, double speed){
			// if using a speed specific animation, the accelerator is useless
			this.accelerator = null;
			// initialize the event
			this.from = from;
			this.to = to;
			this.interval = speed;
			this.forwarding = from <= to;
		}
		
		void syncroTime(){
			this.lastTime = System.currentTimeMillis();
		}
		
		void updateTime(){
			this.timePassed += System.currentTimeMillis() - this.lastTime;
			this.lastTime = System.currentTimeMillis();
		}
		
		double getIncrement(){
			if(accelerator == null)
				return forwarding ? timePassed * interval : timePassed * -interval;
			else{
				double increase = accelerator.f(timePassed * interval);
				return forwarding ? increase : -increase;
			}
		}
	}
}
