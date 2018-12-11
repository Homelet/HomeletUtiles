package homelet.visual.interfaces;

import java.awt.*;
import java.awt.event.*;

public interface ActionsListener extends KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	@Override
	default void keyTyped(KeyEvent e){
		onKeyType(e);
	}
	
	@Override
	default void keyPressed(KeyEvent e){
		onKeyPress(e);
	}
	
	@Override
	default void keyReleased(KeyEvent e){
		onKeyRelease(e);
	}
	
	@Override
	default void mouseClicked(MouseEvent e){
		if(isHovering())
			onMouseClick(e);
	}
	
	@Override
	default void mousePressed(MouseEvent e){
		if(isHovering())
			onMousePress(e);
	}
	
	@Override
	default void mouseReleased(MouseEvent e){
		if(isHovering())
			onMouseRelease(e);
	}
	
	@Override
	default void mouseEntered(MouseEvent e){}
	
	@Override
	default void mouseExited(MouseEvent e){}
	
	@Override
	default void mouseDragged(MouseEvent e){
		if(isHovering())
			onMouseDrag(e);
	}
	
	@Override
	default void mouseMoved(MouseEvent e){
		if(isHovering())
			onMouseMove(e);
	}
	
	@Override
	default void mouseWheelMoved(MouseWheelEvent e){
		if(isHovering())
			onMouseWheelMove(e);
	}
	
	void onKeyType(KeyEvent e);
	
	void onKeyPress(KeyEvent e);
	
	void onKeyRelease(KeyEvent e);
	
	void onMouseClick(MouseEvent e);
	
	void onMousePress(MouseEvent e);
	
	void onMouseRelease(MouseEvent e);
	
	void onMouseEnter(Point p);
	
	void onMouseExit(Point p);
	
	void onMouseDrag(MouseEvent e);
	
	void onMouseMove(MouseEvent e);
	
	void onMouseWheelMove(MouseWheelEvent e);
	
	void setHovering(boolean hovering);
	
	boolean isHovering();
}
