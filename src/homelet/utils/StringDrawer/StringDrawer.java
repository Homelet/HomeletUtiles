/**
 * @author HomeletWei
 * @date Apr 7, 2018
 */
/*
 * Update Log:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 * *****************************************************
 */
package homelet.utils.StringDrawer;

import homelet.utils.GH;
import homelet.utils.Alignment;
import homelet.utils.Border;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
// import homelet.Utile.Constants.Border;

/**
 * design to draw text in side a frame
 *
 * @author HomeletWei
 * @date Apr 7, 2018
 */
public class StringDrawer{
	
	/** @Fields <b>defaultFont</b> The default font */
	public static Font         defaultFont           = new Font("Lucida Grande", Font.PLAIN, 13);
	/** @Fields <b>MAX_VALUE</b> the width value equals to the frame width (after insets applys) */
	public static int          FRAME_WIDTH           = Integer.MAX_VALUE;
	/** @Fields <b>MIN_VALUE</b> the width value equals to the actual text width */
	public static int          TEXT_WIDTH            = Integer.MIN_VALUE;
	private       boolean      contentRearrangement  = true;
	private       boolean      frameRearrangement    = true;
	///////////<!-- Content arrangement -->//////////
	//    /**
	//     * the contents
	//     */
	//    private ArrayList<String> content;
	private       boolean      positionRearrangement = true;
	/**
	 * the matrix for the string
	 */
	private       StringMatrix matrix;
	/**
	 * the bounds to draw the string in
	 */
	private       Rectangle    frame;
	/**
	 * the width for the text
	 */
	private       int          textWidth;
	/**
	 * the font for drawing
	 */
	private       Font         font                  = defaultFont;
	/**
	 * the line spacing between lines
	 */
	private       int          lineSpaceing          = 0;
	/**
	 * the line spacing between paragraph
	 */
	private       int          paragraphSpacing      = 5;
	/**
	 * the alignment for displaying the text bound inside the frame
	 */
	private       Alignment    align                 = Alignment.LEFT;
	/**
	 * the vertex frame of the frame
	 */
	private       Point        textFrameVertex       = new Point(0, 0);
	/**
	 * the alignment for text
	 */
	private       Alignment    textAlign             = Alignment.LEFT;
	/**
	 * the insets for the text
	 * <br>
	 * top, left, right, bottom
	 */
	private       int[]        insets                = new int[]{ 0, 0, 0, 0 };
	/**
	 * the color for the text
	 */
	private       Color        textColor             = Color.BLACK;
	/**
	 * the text behaviour for the text
	 */
	private       LinePolicy   linePolicy            = LinePolicy.BREAK_BY_WORD;
	/**
	 * setting this to true enables the border around the frame
	 * <dl>
	 * <dt>default frame color</dt>
	 * <dd>red</dd>
	 * </dl>
	 */
	private       boolean      drawFrameBorder       = false;
	/**
	 * setting this to true enables the border around the text frame
	 * <dl>
	 * <dt>default text frame color</dt>
	 * <dd>green</dd>
	 * </dl>
	 */
	private       boolean      drawTextFrameBorder   = false;
	///// Graphics //////
	private       Graphics2D   graphic;
	//<div>//
	private       int          maxWidth              = FRAME_WIDTH;
	private       int          minWidth              = TEXT_WIDTH;
	private       boolean      isValidated           = false;
	private       Point2D      objectVertex;
	
	/**
	 * constructor for StringDrawer
	 * <br>
	 * construct valuess as default values
	 * <dl>
	 * <dt>{@link #font}</dt>
	 * <dd>{@link #defaultFont}</dd>
	 * <dt>{@link #lineSpaceing}</dt>
	 * <dd>0</dd>
	 * <dt>{@link #paragraphSpacing}</dt>
	 * <dd>5</dd>
	 * <dt>{@link #align}</dt>
	 * <dd>{@link Alignment#LEFT}</dd>
	 * <dt>{@link #textFrameVertex}</dt>
	 * <dd>0, 0</dd>
	 * <dt>{@link #textAlign}</dt>
	 * <dd>{@link Alignment#LEFT}</dd>
	 * <dt>{@link #insets}</dt>
	 * <dd>0, 0, 0, 0</dd>
	 * <dt>{@link #textColor}</dt>
	 * <dd>{@link Color#BLACK}</dd>
	 * <dt>{@link #linePolicy}</dt>
	 * <dd>{@link LinePolicy#BREAK_BY_WORD}</dd>
	 * </dl>
	 *
	 * @param content
	 * @author HomeletWei
	 */
	@SuppressWarnings("javadoc")
	public StringDrawer(String... content){
		matrix = new StringMatrix(this);
		initializeContents(content);
	}
	
	/**
	 * <span style="color:red;">Note : this method should requires the content & frame rearrangement occurs on the next validation</span>
	 *
	 * @param text the content
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean initializeContents(String... text){
		clearAllContents();
		for(String s : text)
			matrix.newParagraph(s);
		requireFrameRearrangement();
		requireContentRearrangement();
		return true;
	}
	
	/**
	 * <span style="color:red;">Note : this method should requires the content & frame rearrangement occurs on the next validation</span>
	 *
	 * @return is this required rearrangement
	 * @throws IndexOutOfBoundsException
	 * @author HomeletWei
	 */
	public boolean clearAllContents(){
		this.matrix.clear();
		requireFrameRearrangement();
		requireContentRearrangement();
		return true;
	}
	
	/**
	 * <span style="color:red;">Note : this method <b>FORCE</b> the frame rearrangement occurs on the next validation</span>
	 *
	 * @author HomeletWei
	 */
	public void requireFrameRearrangement(){
		this.frameRearrangement = true;
	}
	
	/**
	 * <span style="color:red;">Note : this method <b>FORCE</b> the content rearrangement occurs on the next validation</span>
	 *
	 * @author HomeletWei
	 */
	public void requireContentRearrangement(){
		this.contentRearrangement = true;
	}
	
	/**
	 * draw the text in the frame
	 *
	 * @param g
	 * @param content
	 * @param frame
	 * @param font
	 * @param lineSpacing
	 * @param paragraphSpacing
	 * @param align
	 * @param textFrameVertex
	 * @param textAlign
	 * @param insets
	 * @param textColor
	 * @param policy
	 * @param drawFrameBorder
	 * @param drawTextFramBorder
	 * @param drawLineBorder
	 * @return the dimention
	 * @throws StringDrawerException
	 * @author HomeletWei
	 */
	public static Dimension drawContentsWidthinFrame(Graphics2D g, String[] content, Rectangle frame, Font font, int lineSpacing, int paragraphSpacing, Alignment align, Point textFrameVertex, Alignment textAlign, int[] insets, Color textColor, LinePolicy policy, boolean drawFrameBorder, boolean drawTextFramBorder, int... drawLineBorder) throws StringDrawerException{
		StringDrawer drawer = new StringDrawer(content);
		try{
			drawer.updateGraphics(g);
			drawer.setFrame(frame);
			drawer.setFont(font);
			drawer.setLineSpaceing(lineSpacing);
			drawer.setParagraphSpacing(paragraphSpacing);
			drawer.setAlign(align);
			drawer.setTextFrameVertex(textFrameVertex);
			drawer.setTextAlign(textAlign);
			drawer.setInsets(insets);
			drawer.setColor(textColor);
			drawer.setLinePolicy(policy);
			drawer.setDrawFrameBorder(drawFrameBorder);
			drawer.setDrawTextFrameBorder(drawTextFramBorder);
			drawer.validate();
			drawer.setDrawParagraphFrameBorder(true, drawLineBorder);
		}finally{
			drawer.draw();
		}
		return null;
	}
	
	/**
	 * this is the alternative method for {@link #updateGraphics(Graphics2D)}
	 * <p>
	 * <span style="color:red;">Note : Call this Method on the first line</span>
	 * </p>
	 * Check list (please call the following method in the given order)
	 * <ol>
	 * <li>{@link #updateGraphics(Graphics2D)} <b>should be called on the first line</li>
	 * <li>{@link #setFrame(Rectangle)} should be called before <span style="color:red;">#validate()</span></li>
	 * <li>{@link #validate()} <b>should be called on the last line, after all settings</b></li>
	 * </ol>
	 * <p>
	 * the string drawer API have been optimized to do validation as minimum as possible, for example, if no change were made to the critical argument (font, text and width) the validation shall not occurs
	 * </p>
	 *
	 * @param graphic
	 * @author HomeletWei
	 */
	public void updateGraphics(Graphics graphic){
		updateGraphics((Graphics2D) graphic);
	}
	
	/**
	 * Apply the graphics for current revolution
	 * <p>
	 * <span style="color:red;">Note : Call this Method on the first line</span>
	 * </p>
	 * Check list (please call the following method in the given order)
	 * <ol>
	 * <li>{@link #updateGraphics(Graphics2D)} <b>should be called on the first line,</b> especially before <span style="color:red;">#setPreferredWidth(int)</span></li>
	 * <li>{@link #setFrame(Rectangle)} should be called before <span style="color:red;">#setPreferredWidth(int)</span></li>
	 * <li>{@link #validate()} <b>should be called on the last line, after all settings</b></li>
	 * </ol>
	 * <p>
	 *
	 * @param graphic
	 * @author HomeletWei
	 */
	public void updateGraphics(Graphics2D graphic){
		this.graphic = graphic;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the content rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param linePolicy
	 * @return is this required rearrangement
	 */
	public boolean setLinePolicy(LinePolicy linePolicy){
		if(this.linePolicy != linePolicy){
			this.linePolicy = linePolicy;
			requireContentRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the content rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param font the font
	 * @return is this required rearrangement
	 */
	public boolean setFont(Font font){
		Font newFont;
		if(font == null)
			newFont = defaultFont;
		else
			newFont = font;
		if(this.font == null || this.font != newFont){
			this.font = newFont;
			requireContentRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * <span style="color:red;">Note : this method should requires the content & frame rearrangement occurs on the next validation</span>
	 *
	 * @param content
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean addContent(String content){
		this.matrix.newParagraph(content);
		requireFrameRearrangement();
		requireContentRearrangement();
		return true;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the content & frame rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param index
	 * @param content
	 * @return is this required rearrangement
	 * @throws IndexOutOfBoundsException
	 * @author HomeletWei
	 */
	public boolean setContent(int index, String content) throws IndexOutOfBoundsException{
		if(!this.matrix.get(index).getUnformatedText().contentEquals(content)){
			this.matrix.set(index, content);
			requireFrameRearrangement();
			requireContentRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * <span style="color:red;">Note : this method should requires the content & frame rearrangement occurs on the next validation</span>
	 *
	 * @param index
	 * @return is this required rearrangement
	 * @throws IndexOutOfBoundsException
	 * @author HomeletWei
	 */
	public boolean clearContent(int index) throws IndexOutOfBoundsException{
		this.matrix.remove(index);
		requireFrameRearrangement();
		requireContentRearrangement();
		return true;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the content & frame rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param content
	 * @return is this required rearrangement
	 * @throws IndexOutOfBoundsException
	 * @author HomeletWei
	 */
	public boolean clearContent(String content) throws IndexOutOfBoundsException{
		for(Paragraph p : matrix){
			if(p.getUnformatedText().contentEquals(content)){
				matrix.remove(p);
				requireFrameRearrangement();
				requireContentRearrangement();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the frame rearrangement occurs on the next validation if necessary</span>
	 * <ul>
	 * <li>set the width equals to {@link Integer#MAX_VALUE} means the width is equals to the Frame Width</li>
	 * <li>set the width equals to {@link Integer#MIN_VALUE} means the width is equals to the Text Width</li>
	 * </ul>
	 *
	 * @param min
	 * @param max
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setTextWidth(int min, int max){
		return setMinimumTextWidth(min) | setMaximumTextWidth(max);
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires frame the rearrangement occurs on the next validation if necessary</span>
	 * <ul>
	 * <li>set the width equals to {@link Integer#MAX_VALUE} means the width is equals to the Frame Width</li>
	 * <li>set the width equals to {@link Integer#MIN_VALUE} means the width is equals to the Text Width</li>
	 * </ul>
	 *
	 * @param min
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setMinimumTextWidth(int min){
		if(min != minWidth){
			this.minWidth = min;
			requireFrameRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the frame rearrangement occurs on the next validation if necessary</span>
	 * <ul>
	 * <li>set the width equals to {@link Integer#MAX_VALUE} means the width is equals to the Frame Width</li>
	 * <li>set the width equals to {@link Integer#MIN_VALUE} means the width is equals to the Text Width</li>
	 * </ul>
	 *
	 * @param max
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setMaximumTextWidth(int max){
		if(max != maxWidth){
			this.maxWidth = max;
			requireFrameRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * set the inner intersection for the same value
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param value
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setInsets(int value){
		return setInsets(value, value, value, value);
	}
	
	/**
	 * set the intersection
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param top
	 * @param left
	 * @param right
	 * @param bottom
	 * @return is this required rearrangement
	 */
	public boolean setInsets(int top, int left, int right, int bottom){
		if(insets == null)
			insets = new int[4];
		return setInsetsTop(top) | setInsetsLeft(left) | setInsetsRight(right) | setInsetsBottom(bottom);
	}
	
	/**
	 * set the top insets
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param top the value
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setInsetsTop(int top){
		if(insets == null)
			insets = new int[4];
		if(insets[0] != top){
			insets[0] = top;
			requireFrameRearrangement();
			requirePositionRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * set the left insets
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param left the value
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setInsetsLeft(int left){
		if(insets == null)
			insets = new int[4];
		if(insets[1] != left){
			insets[1] = left;
			requireFrameRearrangement();
			requirePositionRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * set the right insets
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param right the value
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setInsetsRight(int right){
		if(insets == null)
			insets = new int[4];
		if(insets[2] != right){
			insets[2] = right;
			requireFrameRearrangement();
			requirePositionRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * set the bottom insets
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param bottom the value
	 * @return is this required rearrangement
	 * @author HomeletWei
	 */
	public boolean setInsetsBottom(int bottom){
		if(insets == null)
			insets = new int[4];
		if(insets[3] != bottom){
			insets[3] = bottom;
			requireFrameRearrangement();
			requirePositionRearrangement();
			return true;
		}
		return false;
	}
	
	/**
	 * <span style="color:red;">Note : this method <b>FORCE</b> the position rearrangement occurs on the next validation</span>
	 *
	 * @author HomeletWei
	 */
	public void requirePositionRearrangement(){
		this.positionRearrangement = true;
	}
	
	/**
	 * set the intersection
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param values
	 * @return is this required rearrangement
	 */
	public boolean setInsets(int... values){
		if(values == null || values.length < 4)
			return false;
		return setInsets(values[0], values[1], values[2], values[3]);
	}
	
	/** @param color : color */
	public void setColor(Color color){
		this.textColor = color;
	}
	
	/**
	 * set the frame depends on the contents width and height, thus, this should be called after {@link #addContent(String)}...
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @return the frame
	 * @author HomeletWei
	 */
	public Rectangle setFrame(){
		setFrame(new Rectangle(0, 0, approximateStringWidth() + getInsetsLeft() + getInsetsRight(), approximateFontHeight() + getInsetsTop() + getInsetsBottom()));
		return getFrame();
	}
	
	/**
	 * set the frame to draw in
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param frame
	 * @author HomeletWei
	 */
	public void setFrame(Rectangle frame){
		if(this.frame != frame){
			this.frame = frame;
			requireFrameRearrangement();
			requirePositionRearrangement();
		}
	}
	
	/**
	 * get the approximate string width for the longest paragraph, this should be called after {@link #addContent(String)}...
	 * <p>
	 * <span style="color:red;">Note : this method only returns approximate value</span>
	 * </p>
	 *
	 * @return the string width
	 * @author HomeletWei
	 */
	public int approximateStringWidth(){
		if(getStringMatrix().size() == 0){
			return 0;
		}
		int finalWidth = approximateStringWidth(getStringMatrix().get(0).getUnformatedText());
		for(int index = 1; index < getStringMatrix().size(); index++){
			finalWidth = Math.max(finalWidth, approximateStringWidth(getStringMatrix().get(index).getUnformatedText()));
		}
		return finalWidth;
	}
	
	/** @return insetsLeft */
	public int getInsetsLeft(){
		return getInsets()[1];
	}
	
	/** @return insetsRight */
	public int getInsetsRight(){
		return getInsets()[2];
	}
	
	/**
	 * get the approximate font height
	 * <p>
	 * <span style="color:red;">Note : this method only returns approximate value</span>
	 * </p>
	 *
	 * @return the font height
	 * @author HomeletWei
	 */
	public int approximateFontHeight(){
		FontMetrics fm = graphic.getFontMetrics(font);
		return fm.getAscent() + fm.getDescent() + fm.getLeading();
	}
	
	/** @return insetsTop */
	public int getInsetsTop(){
		return getInsets()[0];
	}
	// content
	
	/** @return insetsBottom */
	public int getInsetsBottom(){
		return getInsets()[3];
	}
	
	/** @return bounds */
	public Rectangle getFrame(){
		return frame;
	}
	
	/** @return text */
	public StringMatrix getStringMatrix(){
		return matrix;
	}
	// frame
	
	private int approximateStringWidth(String content){
		if(content == null)
			return 0;
		return graphic.getFontMetrics(font).stringWidth(content);
	}
	
	/** @return insets */
	public int[] getInsets(){
		return insets;
	}
	
	/**
	 * set the frame to draw in
	 * <p>
	 * <span style="color:red;">Note : this method may requires the frame & position rearrangement occurs on the next validation if necessary</span>
	 * </p>
	 *
	 * @param di
	 * @author HomeletWei
	 */
	public void setFrame(Dimension di){
		setFrame(new Rectangle(di));
	}
	// position
	
	/**
	 * @param draw
	 * @param index
	 */
	public void setDrawParagraphFrameBorder(boolean draw, int index){
		if(index < 0){
			for(int i = 0; i < matrix.size(); i++){
				matrix.get(i).setDrawBorder(draw);
			}
			return;
		}
		if(index >= matrix.size())
			return;
		matrix.get(index).setDrawBorder(draw);
	}
	
	/**
	 * @param draw
	 * @param indexs
	 */
	public void setDrawParagraphFrameBorder(boolean draw, int... indexs){
		for(int i : indexs)
			setDrawParagraphFrameBorder(draw, i);
	}
	
	/** @return requireRearrangement */
	public boolean isReqiredContentRearrangment(){
		return contentRearrangement;
	}
	
	/** can only be called by {@link #validate()} */
	private void discardContentRearrangment(){
		this.contentRearrangement = false;
	}
	
	/** @return requireRearrangement */
	public boolean isReqiredFrameRearrangment(){
		return frameRearrangement;
	}
	
	/** can only be called by {@link #validate()} */
	private void discardFrameRearrangment(){
		this.frameRearrangement = false;
	}
	
	/** @return requireRearrangement */
	public boolean isReqiredPositionRearrangment(){
		return positionRearrangement;
	}
	
	/** can only be called by {@link #validate()} */
	private void discardPositionRearrangment(){
		this.positionRearrangement = false;
	}
	
	private void preValidateCheck() throws StringDrawerException{
		if(getFrame() == null)
			throw new StringDrawerException("Frame should be initialized before validation");
	}
	
	/**
	 * validate all Settings
	 * <p>
	 * <span style="color:red;">Note : Call this method after all settings</span>
	 * </p>
	 * <p>
	 * consider the dimention and repaint when this returns a valid(none null) dimension
	 * </p>
	 *
	 * @return the dimension
	 * @throws StringDrawerException
	 * @author HomeletWei
	 */
	public Dimension validate() throws StringDrawerException{
		preValidateCheck();
		Dimension finalDi = null;
		if(isReqiredFrameRearrangment())
			arrangeFrame();
		if(isReqiredContentRearrangment())
			finalDi = arrangeContent();
		if(isReqiredPositionRearrangment())
			arrangePosition();
		isValidated = true;
		return finalDi;
	}
	
	private Dimension arrangeContent(){
		matrix = createMatrix();
		if(matrix == null)
			return null;
		discardContentRearrangment();
		return matrix.getTotalDI();
	}
	
	private void arrangeFrame() throws StringDrawerException{
		int newWidth = computeStringWidth();
		if(newWidth != textWidth){
			textWidth = newWidth;
			requireContentRearrangement();
		}
		discardFrameRearrangment();
	}
	
	private void arrangePosition(){
		objectVertex = applyPosition();
		discardPositionRearrangment();
	}
	
	private int getCalculatedWidth(int width, int frameWidth){
		if(width == TEXT_WIDTH){
			return approximateStringWidth();
		}else if(width == FRAME_WIDTH){
			return frameWidth;
		}else{
			return width;
		}
	}
	
	private int computeStringWidth() throws StringDrawerException{
		if(maxWidth < minWidth)
			throw new StringDrawerException("maximum width is smaller than minimum width (max:" + String.valueOf(maxWidth) + ", min:" + String.valueOf(minWidth) + ")");
		int frameWidth = getCalculatedFrameWidth();
		int max        = getCalculatedWidth(maxWidth, frameWidth);
		int min        = getCalculatedWidth(minWidth, frameWidth);
		if(frameWidth > max){
			return max;
		}else if(frameWidth < min){
			return min;
		}else{
			return frameWidth;
		}
	}
	
	private int getCalculatedFrameWidth(){
		return frame.width - getInsetsLeft() - getInsetsRight();
	}
	
	private Point2D applyPosition(){
		return align.getVertex(false, applyInsets(), new Rectangle(textFrameVertex, matrix.getTotalDI()));
	}
	
	private Rectangle applyInsets(){
		return new Rectangle(frame.x + getInsetsLeft(), frame.y + getInsetsTop(), frame.width - (getInsetsLeft() + getInsetsRight()), frame.height - (getInsetsTop() + getInsetsBottom()));
	}
	
	private void preDrawCheck() throws StringDrawerException{
		if(!isValidated)
			throw new StringDrawerException("validation must occurs before draw");
	}
	
	/**
	 * <p>
	 * this method performs the draw action, thus this should be placed <span style="color:red;">at the end of the list</span>
	 * </p>
	 *
	 * @throws StringDrawerException
	 * @author HomeletWei
	 */
	public void draw() throws StringDrawerException{
		preDrawCheck();
		if(matrix == null)
			return;
		graphic.setFont(font);
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(int paragraphIndex = 0, stackedHeight = 0; paragraphIndex < matrix.size(); paragraphIndex++){
			Paragraph paragraph = matrix.get(paragraphIndex);
			if(paragraph.isDrawBorder())
				Border.RECTANGULAR.draw(graphic, GH.rectangle(false, objectVertex.getX(), objectVertex.getY() + stackedHeight, textWidth, paragraph.getParagrahHeight(lineSpaceing)), Color.GREEN, 1, 0);
			for(LineInfo stringInfo : paragraph){
				graphic.setColor(textColor);
				Point2D innerVertex = textAlign.getVertex(false, GH.rectangle(false, objectVertex.getX(), objectVertex.getY() + stackedHeight, textWidth, stringInfo.getLineHeight()), GH.rectangle(false, stringInfo.getLineDimention()));
				drawString(stringInfo.getText(), innerVertex, stringInfo.getBaseLine());
				stackedHeight += stringInfo.getLineHeight() + lineSpaceing;
			}
			stackedHeight += paragraphSpacing;
		}
		if(drawTextFrameBorder)
			Border.RECTANGULAR.draw(graphic, GH.rectangle(false, objectVertex, matrix.getTotalDI()), Color.BLUE, 1, 0);
		if(drawFrameBorder)
			Border.RECTANGULAR.draw(graphic, frame, Color.RED, 1, 0);
		isValidated = false;
	}
	
	private void drawString(String text, Point2D vertex, int baseLine){
		if(text == null)
			return;
		graphic.drawString(text, (float) vertex.getX(), (float) vertex.getY() + baseLine);
	}
	// getters
	
	/** @return font */
	public Font getFont(){
		return font;
	}
	
	/** @return textWidth */
	public int getTextWidth(){
		return textWidth;
	}
	
	/** @return maxWidth */
	public int getMaxWidth(){
		return maxWidth;
	}
	
	/** @return minWidth */
	public int getMinWidth(){
		return minWidth;
	}
	
	/** @return lineSpaceing */
	public int getLineSpaceing(){
		return lineSpaceing;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the position rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param lineSpaceing : lineSpaceing
	 */
	public void setLineSpaceing(int lineSpaceing){
		if(this.lineSpaceing != lineSpaceing){
			requirePositionRearrangement();
			this.lineSpaceing = lineSpaceing;
		}
	}
	
	/** @return paragraphSpacing */
	public int getParagraphSpacing(){
		return paragraphSpacing;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the position rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param paragraphSpacing : paragraphSpacing
	 */
	public void setParagraphSpacing(int paragraphSpacing){
		if(this.paragraphSpacing != paragraphSpacing){
			requirePositionRearrangement();
			this.paragraphSpacing = paragraphSpacing;
		}
	}
	
	/** @return align */
	public Alignment getAlign(){
		return align;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the position rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param align : align
	 */
	public void setAlign(Alignment align){
		if(this.align != align){
			requirePositionRearrangement();
			this.align = align;
		}
	}
	
	/** @return textFrameVertex */
	public Point getTextFrameVertex(){
		return textFrameVertex;
	}
	
	/**
	 * <span style="color:red;">Note : this method may requires the position rearrangement occurs on the next validation if necessary</span>
	 *
	 * @param textFrameVertex
	 * @author HomeletWei
	 */
	public void setTextFrameVertex(Point textFrameVertex){
		if(this.textFrameVertex != textFrameVertex){
			requirePositionRearrangement();
			this.textFrameVertex = textFrameVertex;
		}
	}
	
	/** @return textAlign */
	public Alignment getTextAlign(){
		return textAlign;
	}
	
	/**
	 * @param textAlign : textAligns
	 */
	public void setTextAlign(Alignment textAlign){
		this.textAlign = textAlign;
	}
	
	/** @return textColor */
	public Color getTextColor(){
		return textColor;
	}
	
	/** @return linePolicy */
	public LinePolicy getLinePolicy(){
		return linePolicy;
	}
	
	/** @return graphic */
	public Graphics2D getGraphic(){
		return graphic;
	}
	
	/**
	 * call this after {@link #validate()}
	 *
	 * @return the line count
	 * @author HomeletWei
	 */
	public int getLineCount(){
		return matrix.size();
	}
	
	/** @return drawFrameBorder */
	public boolean isDrawFrameBorder(){
		return drawFrameBorder;
	}
	
	/** @param drawFrameBorder : drawFrameBorder */
	public void setDrawFrameBorder(boolean drawFrameBorder){
		this.drawFrameBorder = drawFrameBorder;
	}
	
	/** @return drawTextFrameBorder */
	public boolean isDrawTextFrameBorder(){
		return drawTextFrameBorder;
	}
	
	/** @param drawTextFrameBorder : drawTextFrameBorder */
	public void setDrawTextFrameBorder(boolean drawTextFrameBorder){
		this.drawTextFrameBorder = drawTextFrameBorder;
	}
	
	// getters end
	// functional starts
	private StringMatrix createMatrix(){
		matrix.refresh();
		switch(getLinePolicy()){
			case BREAK_BY_WORD:
				return getStringMatrix_break_by_word();
			case BREAK_WHERE_NECESSARY:
				return getStringMatrix_break_where_necessary();
			case NEVER_BREAK:
				return getStringMatrix_never_break();
			default:
				return null;
		}
	}
	
	/**
	 * <strong>nextLine</strong>
	 * <ul>
	 * <li>length enough auto nextLine</li>
	 * <li>next line sign detected</li>
	 * <li>a string has too much letter so that will be force to split</li>
	 * </ul>
	 */
	private StringMatrix getStringMatrix_break_where_necessary(){
		int paragraphIndex = 0;
		for(Iterator<Paragraph> iterator = getStringMatrix().iterator(); iterator.hasNext(); paragraphIndex++){
			Paragraph     paragraph = iterator.next();
			StringBuilder builder   = new StringBuilder();
			char[]        chars     = paragraph.getUnformatedText().toCharArray();
			for(int index = 0; index < chars.length; index++){
				if(matrix.testString(builder.toString() + chars[index])){
					matrix.add(paragraphIndex, builder.toString());
					builder = new StringBuilder();
				}else{
					builder.append(chars[index]);
					if(index + 1 == chars.length){
						matrix.add(paragraphIndex, builder.toString());
					}
				}
			}
		}
		return matrix;
	}
	
	private StringMatrix getStringMatrix_never_break(){
		int paragraphIndex = 0;
		for(Iterator<Paragraph> iterator = getStringMatrix().iterator(); iterator.hasNext(); paragraphIndex++){
			Paragraph paragraph = iterator.next();
			matrix.add(paragraphIndex, paragraph.getUnformatedText());
		}
		return matrix;
	}
	
	/**
	 * <strong>nextLine</strong>
	 * <ul>
	 * <li>word length enough auto nextLine</li>
	 * <li>next line sign detected</li>
	 * <li>a string has too much letter so that will be force to split</li>
	 * </ul>
	 */
	private StringMatrix getStringMatrix_break_by_word(){
		ArrayList<ArrayList<String>> formatedText   = toStringArray();
		int                          paragraphIndex = 0;
		for(Iterator<ArrayList<String>> iterator = formatedText.iterator(); iterator.hasNext(); paragraphIndex++){
			ArrayList<String> arrayList = iterator.next();
			String            strBuffer = "";
			for(Iterator<String> iterator2 = arrayList.iterator(); iterator2.hasNext(); ){
				String buffer = iterator2.next();
				if(buffer.contentEquals("<NL>")){
					matrix.add(paragraphIndex, strBuffer);
					strBuffer = "";
				}else{
					if(matrix.testString(buffer)){
						String[] localStr = splitLongString(buffer, matrix);
						for(int index = 0; index < localStr.length - 1; index++){
							matrix.add(paragraphIndex, localStr[index]);
						}
						strBuffer = localStr[localStr.length - 1];
					}else{
						if(matrix.testString(strBuffer + buffer)){
							matrix.add(paragraphIndex, strBuffer);
							strBuffer = "";
						}
						strBuffer += buffer;
					}
				}
				if(!iterator2.hasNext()){
					matrix.add(paragraphIndex, strBuffer);
				}
			}
		}
		return matrix;
	}
	
	private ArrayList<ArrayList<String>> toStringArray(){
		ArrayList<ArrayList<String>> strBuffer = new ArrayList<>();
		boolean                      wasSpace;
		for(Iterator<Paragraph> iterator = getStringMatrix().iterator(); iterator.hasNext(); ){
			Paragraph         paragraph    = iterator.next();
			ArrayList<String> stringBuffer = new ArrayList<>();
			String            str          = paragraph.getUnformatedText();
			if(str == null)
				continue;
			char[] textChars = str.toCharArray();
			wasSpace = textChars.length > 0 && (textChars[0] == ' ' || textChars[0] == '\t');
			String buffer = "";
			for(int index = 0; index < textChars.length; index++){
				char c = textChars[index];
				switch(c){
					case '\n':
						if(!buffer.isEmpty())
							stringBuffer.add(buffer);
						buffer = "";
						stringBuffer.add("<NL>");
						continue;
					case '\t':
					case ' ':
						if(!wasSpace){
							stringBuffer.add(buffer);
							buffer = "";
							wasSpace = true;
						}
						buffer += c == ' ' ? ' ' : "    ";
						continue;
					default:
						if(wasSpace){
							stringBuffer.add(buffer);
							buffer = "";
							wasSpace = false;
						}
						buffer += c;
						continue;
				}
			}
			stringBuffer.add(buffer);
			strBuffer.add(stringBuffer);
		}
		return strBuffer;
	}
	
	/**
	 * @param str
	 * @param matrix
	 * @return a spited Long String
	 * @author HomeletWei
	 */
	private String[] splitLongString(String str, StringMatrix matrix){
		String            buffer         = "";
		int               lastSplitPoint = 0;
		ArrayList<String> strBuffer      = new ArrayList<>();
		char[]            chars          = str.toCharArray();
		for(int index = 0; index < chars.length; index++){
			char c = chars[index];
			if(matrix.testString(buffer + c)){
				strBuffer.add(str.substring(lastSplitPoint, index));
				lastSplitPoint = index;
				buffer = "";
			}
			buffer += c;
		}
		if(lastSplitPoint < str.length()){
			strBuffer.add(str.substring(lastSplitPoint));
		}
		return Arrays.copyOf(strBuffer.toArray(), strBuffer.size(), String[].class);
	}
	
	/**
	 * <p>
	 * the LinePolicy defines how StringDrawer API process the break point for a line
	 * </p>
	 *
	 * @author HomeletWei
	 * @date Jun 2, 2018
	 */
	public enum LinePolicy{
		/**
		 * <pre>
		 *
		 * |Licensed under the Apache License, Versio|
		 * |n 2.0 (the "License"); you may not use th|
		 * |is file except in compliance with the Lic|
		 * |ense. You may obtain a copy of the Licens|
		 * |e at                                     |
		 * </pre>
		 *
		 * @Fields <b>BREAK_WHERE_NECESSARY</b> break if there is a nextLine sign detected or the length reach the limit even a word hasn't finish<br>
		 */
		BREAK_WHERE_NECESSARY,
		/**
		 * <pre>
		 *
		 * |Licensed under the Apache License,       |
		 * |Version 2.0 (the "License"); you may not |
		 * |use this file except in compliance with  |
		 * |the License. You may obtain a copy of the|
		 * |License at                               |
		 * </pre>
		 *
		 * @Fields <b>BREAK_BY_WORD</b> break if there is a nextLine sign detected or the current length + next word's length reach the limit<br>
		 */
		BREAK_BY_WORD,
		/**
		 * @Fields <b>BREAK_BY_WORD</b> never break<br>
		 */
		NEVER_BREAK;
	}
}
