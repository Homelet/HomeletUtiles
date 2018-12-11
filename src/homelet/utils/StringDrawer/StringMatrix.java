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
import java.util.Iterator;

/**
 * TODO
 *
 * @author HomeletWei
 * @date Jun 18, 2018
 */
public class StringMatrix extends ArrayList<Paragraph>{
	
	/** @Fields <b>serialVersionUID</b> */
	private static final long serialVersionUID = 1L;
	StringDrawer parent;
	FontMetrics  fm;
	
	/**
	 * constructor for StringMatrix
	 *
	 * @param parent
	 * @author HomeletWei
	 */
	public StringMatrix(StringDrawer parent){
		this.parent = parent;
	}
	
	/**
	 * @author HomeletWei
	 */
	public void refresh(){
		this.fm = parent.getGraphic().getFontMetrics(parent.getFont());
		for(Iterator<Paragraph> iterator = this.iterator(); iterator.hasNext(); )
			iterator.next().clear();
	}
	
	/**
	 * @return the total di
	 * @author HomeletWei
	 */
	public Dimension getTotalDI(){
		return new Dimension(parent.getTextWidth(), getTotalHeight());
	}
	
	/**
	 * @return the total height
	 * @author HomeletWei
	 */
	public int getTotalHeight(){
		int height = (this.size() - 1) * parent.getParagraphSpacing();
		for(Paragraph par : this){
			height += par.getParagrahHeight(parent.getLineSpaceing());
		}
		return height;
	}
	
	/**
	 * @param text
	 * @author HomeletWei
	 */
	public void newParagraph(String text){
		this.add(new Paragraph(text));
	}
	
	/**
	 * @param paragraph
	 * @param text
	 * @author HomeletWei
	 */
	public void set(int paragraph, String text){
		this.get(paragraph).setUnformatedText(text);
	}
	
	/**
	 * @param paragraphIndex
	 * @param text
	 * @author HomeletWei
	 */
	public void add(int paragraphIndex, String text){
		this.get(paragraphIndex).add(new LineInfo(text, fm, parent.getGraphic()));
	}
	
	/**
	 * @param str
	 * @return is passed
	 * @author HomeletWei
	 */
	public boolean testString(String str){
		return fm.stringWidth(str) > parent.getTextWidth();
	}
	
	public String[] getContents(){
		String[] buffer = new String[this.size()];
		for(int index = 0; index < buffer.length; index++){
			buffer[index] = this.get(index).getUnformatedText();
		}
		return buffer;
	}
}
