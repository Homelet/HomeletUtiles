package homelet.visual;

import homelet.visual.interfaces.ActionsListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class ActionsManager implements ActionsListener{
	
	private boolean hovering;
	
	@Override
	public final void keyTyped(KeyEvent e){
		ActionsListener.super.keyTyped(e);
	}
	
	@Override
	public final void keyPressed(KeyEvent e){
		ActionsListener.super.keyPressed(e);
	}
	
	@Override
	public final void keyReleased(KeyEvent e){
		ActionsListener.super.keyReleased(e);
	}
	
	@Override
	public final void mouseClicked(MouseEvent e){
		ActionsListener.super.mouseClicked(e);
	}
	
	@Override
	public final void mousePressed(MouseEvent e){
		ActionsListener.super.mousePressed(e);
	}
	
	@Override
	public final void mouseReleased(MouseEvent e){
		ActionsListener.super.mouseReleased(e);
	}
	
	@Override
	public final void mouseEntered(MouseEvent e){
		ActionsListener.super.mouseEntered(e);
	}
	
	@Override
	public final void mouseExited(MouseEvent e){
		ActionsListener.super.mouseExited(e);
	}
	
	@Override
	public final void mouseDragged(MouseEvent e){
		ActionsListener.super.mouseDragged(e);
	}
	
	@Override
	public final void mouseMoved(MouseEvent e){
		ActionsListener.super.mouseMoved(e);
	}
	
	@Override
	public final void mouseWheelMoved(MouseWheelEvent e){
		ActionsListener.super.mouseWheelMoved(e);
	}
	
	@Override
	public void onKeyType(KeyEvent e){}
	
	@Override
	public void onKeyPress(KeyEvent e){}
	
	@Override
	public void onKeyRelease(KeyEvent e){}
	
	@Override
	public void onMouseClick(MouseEvent e){}
	
	@Override
	public void onMousePress(MouseEvent e){}
	
	@Override
	public void onMouseRelease(MouseEvent e){}
	
	@Override
	public void onMouseEnter(Point p){}
	
	@Override
	public void onMouseExit(Point p){}
	
	@Override
	public void onMouseDrag(MouseEvent e){}
	
	@Override
	public void onMouseMove(MouseEvent e){}
	
	@Override
	public void onMouseWheelMove(MouseWheelEvent e){}
	
	@Override
	public final void setHovering(boolean hovering){
		this.hovering = hovering;
	}
	
	@Override
	public final boolean isHovering(){
		return hovering;
	}
}
