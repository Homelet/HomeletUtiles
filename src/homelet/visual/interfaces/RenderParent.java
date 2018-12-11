package homelet.visual.interfaces;

import java.util.List;

public interface RenderParent{
	
	<T extends RenderChildren & Renderable> List<T> getChildren();
	
	/**
	 * indicate whether current render is rendered before it's sub render
	 *
	 * @return the boolean
	 */
	default boolean isRenderedBefore(){
		return true;
	}
}
