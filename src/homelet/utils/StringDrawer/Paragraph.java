/**
 * <pre>
 * ****************************************************
 * Name: TODO
 * Date: TODO
 * Description: TODO
 * *****************************************************
 * </pre>
 *
 * @author HomeletWei
 * @date Jun 18, 2018
 */
package homelet.utils.StringDrawer;

import java.awt.*;
import java.util.ArrayList;

/**
 * TODO
 *
 * @author HomeletWei
 * @date Jun 18, 2018
 */
public class Paragraph extends ArrayList<LineInfo>{
	
	/** @Fields <b>serialVersionUID</b> */
	private static final long serialVersionUID = 1L;
	boolean drawBorder = false;
	String  unformated;
	
	/**
	 * constructor for Paragraph
	 *
	 * @param content
	 * @author HomeletWei
	 */
	public Paragraph(String content){
		this.unformated = content;
	}
	
	/**
	 * @param lineSpaceing
	 * @return the paragraph's height
	 * @author HomeletWei
	 */
	public int getParagrahHeight(int lineSpaceing){
		int height = (this.size() - 1) * lineSpaceing;
		for(LineInfo info : this){
			height += info.getLineHeight();
		}
		return height;
	}
	
	/** @return drawBorder */
	public boolean isDrawBorder(){
		return drawBorder;
	}
	
	/** @param drawBorder : drawBorder */
	public void setDrawBorder(boolean drawBorder){
		this.drawBorder = drawBorder;
	}
	
	/** @return unformated */
	public String getUnformatedText(){
		return unformated;
	}
	
	/** @param unformated : unformated */
	public void setUnformatedText(String unformated){
		this.unformated = unformated;
	}
}

class LineInfo{
	
	String    text;
	Dimension bounds;
	int       baseLine;
	
	public LineInfo(String text, FontMetrics fm, Graphics2D g){
		this.text = text;
		setLayOut(text, fm, g);
	}
	
	private void setLayOut(String text, FontMetrics fm, Graphics2D g){
		bounds = fm.getStringBounds(text, g).getBounds().getSize();
		baseLine = fm.getAscent() + fm.getLeading();
	}
	
	/** @return text */
	public String getText(){
		return text;
	}
	
	public int getLineWidth(){
		return getLineDimention().width;
	}
	
	/** @return lineDimention */
	public Dimension getLineDimention(){
		return bounds;
	}
	
	public int getLineHeight(){
		return getLineDimention().height;
	}
	
	public int getBaseLine(){
		return baseLine;
	}
}
