package homelet.visual;

import homelet.utils.ToolBox;
import homelet.visual.interfaces.ActionsListener;
import homelet.visual.interfaces.LocatableRender;
import homelet.visual.interfaces.RenderParent;
import homelet.visual.interfaces.Renderable;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * an implementation of {@link Renderable}, which has a triple Renderable buffer,<br>
 * <ul>
 * <li>pre render</li>
 * <li>render</li>
 * <li>post render</li>
 * </ul>
 * <p>
 * the pre render are rendered before render, the post render at the last<br>
 * </p>
 */
public class RenderManager implements Renderable{
	
	public static final int              PRE_RENDER_LIST  = -1;
	public static final int              RENDER_LIST      = 0;
	public static final int              POST_RENDER_LIST = 1;
	// render list
	private final       List<Renderable> preRenderList;
	private final       List<Renderable> renderList;
	private final       List<Renderable> postRenderList;
	// parent
	private             JCanvas          parent;
	
	public RenderManager(){
		this.preRenderList = Collections.synchronizedList(new ArrayList<>());
		this.renderList = Collections.synchronizedList(new ArrayList<>());
		this.postRenderList = Collections.synchronizedList(new ArrayList<>());
	}
	
	public JCanvas getParent(){
		return parent;
	}
	
	void setParent(JCanvas parent){
		this.parent = parent;
	}
	
	public void addTarget(int renderListSpecific, Object... objects){
		for(Object o : objects){
			if(o instanceof Renderable){
				switch(renderListSpecific){
					case PRE_RENDER_LIST:
						this.addPreTargets((Renderable) o);
						break;
					case RENDER_LIST:
						this.addTargets((Renderable) o);
						break;
					case POST_RENDER_LIST:
						this.addPostTargets((Renderable) o);
						break;
					default:
						break;
				}
			}
		}
	}
	
	public void addPreTargets(Renderable... r){
		synchronized(preRenderList){
			add(preRenderList, r);
		}
	}
	
	public void addTargets(Renderable... r){
		synchronized(renderList){
			add(renderList, r);
		}
	}
	
	public void addPostTargets(Renderable... r){
		synchronized(postRenderList){
			add(postRenderList, r);
		}
	}
	
	public void addTargets(int renderListSpecific, int position, Object... objects){
		for(Object o : objects){
			if(o instanceof Renderable){
				switch(renderListSpecific){
					case PRE_RENDER_LIST:
						this.addPreTargets(position, (Renderable) o);
						break;
					case RENDER_LIST:
						this.addTargets(position, (Renderable) o);
						break;
					case POST_RENDER_LIST:
						this.addPostTargets(position, (Renderable) o);
						break;
					default:
						break;
				}
			}
		}
	}
	
	public void addPreTargets(int position, Renderable... r){
		synchronized(preRenderList){
			add(preRenderList, position, r);
		}
	}
	
	public void addTargets(int position, Renderable... r){
		synchronized(renderList){
			add(renderList, position, r);
		}
	}
	
	public void addPostTargets(int position, Renderable... r){
		synchronized(postRenderList){
			add(postRenderList, position, r);
		}
	}
	
	private void addCheck(Renderable... r){
		for(Renderable renderable : r){
			preRenderList.remove(renderable);
			renderList.remove(renderable);
			postRenderList.remove(renderable);
			if(renderable instanceof KeyListener)
				getParent().addKeyListener((KeyListener) renderable);
			if(renderable instanceof MouseListener)
				getParent().addMouseListener((MouseListener) renderable);
			if(renderable instanceof MouseMotionListener)
				getParent().addMouseMotionListener((MouseMotionListener) renderable);
			if(renderable instanceof MouseWheelListener)
				getParent().addMouseWheelListener((MouseWheelListener) renderable);
		}
	}
	
	private void add(List<Renderable> list, int position, Renderable... r){
		addCheck(r);
		list.addAll(position, Arrays.asList(r));
	}
	
	private void add(List<Renderable> list, Renderable... r){
		addCheck(r);
		Collections.addAll(list, r);
	}
	
	public void clear(int renderListSpecific){
		switch(renderListSpecific){
			case PRE_RENDER_LIST:
				removeAllPre();
				break;
			case RENDER_LIST:
				removeAllRender();
				break;
			case POST_RENDER_LIST:
				removeAllPost();
				break;
		}
	}
	
	public void removeAllPre(){
		synchronized(preRenderList){
			remove(preRenderList);
		}
	}
	
	public void removeAllRender(){
		synchronized(renderList){
			remove(renderList);
		}
	}
	
	public void removeAllPost(){
		synchronized(postRenderList){
			remove(postRenderList);
		}
	}
	
	private void remove(List<Renderable> list){
		for(int index = 0; index < list.size(); index++){
			removeTargets(list.get(index));
		}
	}
	
	public void removeTargets(Renderable... r){
		for(Renderable renderable : r){
			preRenderList.remove(renderable);
			renderList.remove(renderable);
			postRenderList.remove(renderable);
			if(renderable instanceof KeyListener)
				getParent().removeKeyListener((KeyListener) renderable);
			if(renderable instanceof MouseListener)
				getParent().removeMouseListener((MouseListener) renderable);
			if(renderable instanceof MouseMotionListener)
				getParent().removeMouseMotionListener((MouseMotionListener) renderable);
			if(renderable instanceof MouseWheelListener)
				getParent().removeMouseWheelListener((MouseWheelListener) renderable);
		}
	}
	
	@Override
	public void tick(){}
	
	@Override
	public void render(Graphics2D g){
		this.mouseCursor = ToolBox.getMouseLocation(getParent());
		preRender(g);
		doRender(g);
		postRender(g);
	}
	
	private void doRender(Graphics2D g){
		synchronized(renderList){
			doRenderProcess(g, renderList);
		}
	}
	
	private void preRender(Graphics2D g){
		synchronized(preRenderList){
			doRenderProcess(g, preRenderList);
		}
	}
	
	private void postRender(Graphics2D g){
		synchronized(postRenderList){
			doRenderProcess(g, postRenderList);
		}
	}
	
	private void doRenderProcess(Graphics2D g, List<? extends Renderable> renderList){
		if(renderList.isEmpty())
			return;
		for(Renderable renderable : renderList){
			if(renderable instanceof RenderParent){
				Graphics2D   localGraphics = g;
				RenderParent parent        = (RenderParent) renderable;
				if(parent.isRenderedBefore())
					localGraphics = renderProcess(localGraphics, renderable);
				List<? extends Renderable> subRenderable = parent.getChildren();
				if(subRenderable != null)
					doRenderProcess(localGraphics, subRenderable);
				if(!parent.isRenderedBefore())
					renderProcess(localGraphics, renderable);
			}else{
				renderProcess(g, renderable);
			}
		}
	}
	
	private Graphics2D renderProcess(Graphics2D g, Renderable renderable){
		Rectangle clipBounds = g.getClipBounds();
		Dimension size       = null;
		Point     vertex     = null;
		if(renderable instanceof LocatableRender){
			LocatableRender locatableRender = ((LocatableRender) renderable);
			size = locatableRender.getSize();
			vertex = locatableRender.getVertex(clipBounds);
		}
		if(size == null)
			size = clipBounds.getSize();
		if(vertex == null)
			vertex = new Point(0, 0);
		Rectangle  bounds = clipBounds.intersection(new Rectangle(vertex, size));
		Graphics2D g2     = (Graphics2D) g.create(bounds.x, bounds.y, bounds.width, bounds.height);
		if(renderable instanceof ActionsListener)
			callAction((ActionsListener) renderable, bounds);
		if(renderable.isTicking())
			renderable.tick();
		if(renderable.isRendering())
			renderable.render(g2);
		return g2;
	}
	
	private Point mouseCursor;
	
	private void callAction(ActionsListener a, Rectangle bounds){
		if(bounds.contains(mouseCursor)){
			if(!a.isHovering()){
				a.setHovering(true);
				a.onMouseEnter(mouseCursor);
			}
		}else{
			if(a.isHovering()){
				a.setHovering(false);
				a.onMouseExit(mouseCursor);
			}
		}
	}
}
