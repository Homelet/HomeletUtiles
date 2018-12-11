package homelet.utils.Animate;

import java.awt.*;

public class ColorAnimator extends Animator<ColorAnimator>{
	
	final private ValueAnimator red;
	final private ValueAnimator green;
	final private ValueAnimator blue;
	final private ValueAnimator alpha;
	private       Color         from;
	private       Color         current;
	private       Color         to;
	
	public ColorAnimator(){
		red = new ValueAnimator();
		green = new ValueAnimator();
		blue = new ValueAnimator();
		alpha = new ValueAnimator();
	}
	
	public ColorAnimator from(int rgb){
		return from(new Color(rgb));
	}
	
	public ColorAnimator from(int red, int green, int blue){
		return from(new Color(red, green, blue));
	}
	
	public ColorAnimator from(int red, int green, int blue, int alpha){
		return from(new Color(red, green, blue, alpha));
	}
	
	public ColorAnimator from(Color from){
		if(!processing){
			this.from = from;
			red.from(from.getRed());
			green.from(from.getGreen());
			blue.from(from.getBlue());
			alpha.from(from.getAlpha());
			setCurrent(from.getRed(), from.getGreen(), from.getBlue(), from.getAlpha());
		}
		return this;
	}
	
	public ColorAnimator to(int rgb){
		return to(new Color(rgb));
	}
	
	public ColorAnimator to(int red, int green, int blue){
		return to(new Color(red, green, blue));
	}
	
	public ColorAnimator to(int red, int green, int blue, int alpha){
		return to(new Color(red, green, blue, alpha));
	}
	
	public ColorAnimator to(Color to){
		if(!processing){
			this.to = to;
			red.to(to.getRed());
			green.to(to.getGreen());
			blue.to(to.getBlue());
			alpha.to(to.getAlpha());
		}
		return this;
	}
	
	public Color from(){
		return from;
	}
	
	public Color to(){
		return to;
	}
	
	public Color current(){
		return current;
	}
	
	@Override
	public Accelerator accelerator(){
		return red.accelerator();
	}
	
	@Override
	public ColorAnimator accelerator(Accelerator accelerator){
		red.accelerator(accelerator);
		green.accelerator(accelerator);
		blue.accelerator(accelerator);
		alpha.accelerator(accelerator);
		return this;
	}
	
	@Override
	public ColorAnimator animate(long delay){
		if(!processing){
			processing = true;
			red.animate(delay);
			green.animate(delay);
			blue.animate(delay);
			alpha.animate(delay);
		}
		return this;
	}
	
	@Override
	public ColorAnimator terminate(){
		if(processing){
			red.terminate();
			green.terminate();
			blue.terminate();
			alpha.terminate();
			processing = false;
			from(to);
			onAnimateFinnish();
		}
		return this;
	}
	
	@Override
	public ColorAnimator stop(){
		if(processing){
			processing = false;
			red.stop();
			green.stop();
			blue.stop();
			alpha.stop();
		}
		return this;
	}
	
	@Override
	public void tick(){
		if(processing){
			red.tick();
			green.tick();
			blue.tick();
			alpha.tick();
			setCurrent(red.current(), green.current(), blue.current(), alpha.current());
			if(!red.processing && !green.processing && !blue.processing && !alpha.processing){
				processing = false;
				from(current);
				onAnimateFinnish();
			}
		}
	}
	
	private void setCurrent(double red, double green, double blue, double alpha){
		current = new Color((int) red, (int) green, (int) blue, (int) alpha);
	}
}
