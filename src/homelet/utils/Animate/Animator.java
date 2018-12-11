package homelet.utils.Animate;

import java.util.LinkedList;

/**
 * The Animator for animating the value, object, or index, by default the animator has two mode:
 * <ul>
 * <li>Animate by Time</li>
 * <li>Animate by Speed</li>
 * </ul>
 * <p>
 * When calling Animator to animate, it offers an {@link AnimateEvent} in to the Animator queue, each Animator has their
 * own event queue. If willing to manage the event queue, add the Animator to a {@link AnimationManager} to manage them, they
 * are not visible to user
 * </p>
 * <p>
 * When animate by time(T), the animator will calculate the drop, and then apply the {@link Accelerator}. Once it's time
 * for the Animator to animate, it will activate it's own animate logic, the animation will last at least T.
 * </p>
 * <p>
 * When animate by speed, the accelerator becomes useless, thus it won't be applied, the animator will finish once it
 * reaches the target.
 * </p>
 */
@SuppressWarnings("unchecked")
public abstract class Animator<T extends Animator, E extends AnimateEvent>{
	
	protected final LinkedList<E>             queue;
	private         AnimateFinnishListener<T> finnishListener = null;
	protected       boolean                   pausing;
	
	public Animator(){
		queue = new LinkedList<>();
	}
	
	/**
	 * @return the animation finnish listener
	 */
	public AnimateFinnishListener finnishListener(){
		return finnishListener;
	}
	
	/**
	 * to set the finnish listener<br>
	 * <p>
	 * this can be used to create a animation loop, setting this to be null to indicate the animation finnish
	 * </p>
	 *
	 * @param finnishListener the finnish listener to be called on each animation finished
	 */
	public T onFinnish(AnimateFinnishListener<T> finnishListener){
		this.finnishListener = finnishListener;
		return (T) this;
	}
	
	/**
	 * to call on animation finnish event manually, this is used to trigger some looped event
	 */
	public T onFinnish(){
		onAnimateFinnish();
		return (T) this;
	}
	
	protected void onAnimateFinnish(){
		if(finnishListener == null)
			return;
		finnishListener.onFinnish((T) this);
	}
	
	/**
	 * to check if this Animator is processing
	 *
	 * @return the accelerator
	 */
	public abstract boolean processing();
	
	/**
	 * get the current used accelerator;
	 *
	 * @return the accelerator
	 */
	public abstract Accelerator accelerator();
	
	/**
	 * set the accelerator;
	 */
	public abstract T accelerator(Accelerator accelerator);
	
	/**
	 * append the animation event to the end of the Animation Event Queue
	 *
	 * @param time_in_mili the time to specific in millisecond
	 */
	public abstract T animate(long time_in_mili);
	
	/**
	 * append the animation event to the end of the Animation Event Queue
	 *
	 * @param speed the speed to specific
	 */
	public abstract T animate(double speed);
	
	/**
	 * to pause the animation, the animation can be restore by calling {@link #resume()} on this Animator
	 */
	public T pause(){
		pausing = true;
		return (T) this;
	}
	
	/**
	 * to resume the animation paused by calling {@link #pause()} on this Animator
	 */
	public T resume(){
		pausing = false;
		return (T) this;
	}
	
	/**
	 * terminate the current AnimateEvent and proceed to the next animation event,
	 * if the current event is the last event, will call AnimateFinnish Event
	 */
	public abstract T toNext();
	
	/**
	 * to stop the animation and clear the animation queue,
	 * the operations including:
	 * <ul>
	 * <li>the animate finnish event will not be fired</li>
	 * <li>no element will be reposition</li>
	 * </ul>
	 */
	public abstract T stop();
	
	/**
	 * to terminate the animation and clear the animation queue, the animation will be terminate normally, and reposition
	 * to the last AnimationEvent,
	 * the operations including:
	 * <ul>
	 * <li>the animation finnish event will be fired</li>
	 * <li>some element will reposition</li>
	 * </ul>
	 */
	public abstract T terminate();
	
	/**
	 * this is a <span style="color: red; font-weight: bold;">MUST call</span> operation to make the animator functions, to call this in each tick
	 */
	public abstract void tick();
}
