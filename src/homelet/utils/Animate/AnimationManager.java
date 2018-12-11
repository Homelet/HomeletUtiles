package homelet.utils.Animate;

public class AnimationManager{
	
	private class AnimatorWrap{
		
		AnimationTrigger trigger;
		Animator         animator;
	}
	
	public enum AnimationTrigger{
		WITH_PREVIOUS,
		AFTER_PREVIOUS,
		IMMEDIATELY;
	}
}
