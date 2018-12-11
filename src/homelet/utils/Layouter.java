package homelet.utils;

import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * the class for doing layout in a more compact way
 *
 * @author HomeletWei
 * May 16, 2018
 */
public abstract class Layouter{
	
	protected Container host;
	
	/**
	 * constructor for Layouter
	 *
	 * @param host the host
	 * @author HomeletWei
	 */
	Layouter(Container host){
		this.host = host;
	}
	
	/**
	 * @return the host of this layouter
	 * @author HomeletWei
	 */
	public Container getHost(){
		return host;
	}
	
	/**
	 * @return the layout
	 * @author HomeletWei
	 */
	public abstract LayoutManager getLayout();
	
	/**
	 * the Spring layout
	 *
	 * @author HomeletWei
	 * May 16, 2018
	 */
	public static class SpringLayouter extends Layouter{
		
		private SpringLayout layout;
		
		/**
		 * constructor for Layout.Spring
		 *
		 * @param host
		 * @author HomeletWei
		 */
		public SpringLayouter(Container host){
			super(host);
			this.layout = new SpringLayout();
			host.setLayout(layout);
		}
		
		/**
		 * the spring with min and max and a preferred
		 *
		 * @param value
		 * @param max
		 * @param min
		 * @return a spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring valueOf(int value, int max, int min){
			return javax.swing.Spring.constant(min, value, max);
		}
		
		/**
		 * the sum of the two spring
		 *
		 * @param v the array of spring
		 * @return a formed spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring sum(javax.swing.Spring... v){
			javax.swing.Spring spring = v[0];
			for(int index = 1; index < v.length; index++){
				spring = sum(spring, v[index]);
			}
			return spring;
		}
		
		/**
		 * the sum of the two spring
		 *
		 * @param v1 sp1
		 * @param v2 sp2
		 * @return a formed spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring sum(javax.swing.Spring v1, javax.swing.Spring v2){
			return javax.swing.Spring.sum(v1, v2);
		}
		
		/**
		 * the subtract of the two spring
		 * <br>
		 * relevant to sum(v1, opposite(v2))
		 *
		 * @param v1 sp1
		 * @param v2 sp2
		 * @return a formed spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring subtract(javax.swing.Spring v1, javax.swing.Spring v2){
			return javax.swing.Spring.sum(v1, opposite(v2));
		}
		
		/**
		 * the oppoist value of a spring
		 *
		 * @param v spring
		 * @return a formed spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring opposite(javax.swing.Spring v){
			return javax.swing.Spring.minus(v);
		}
		
		/**
		 * return the max value of the two spring
		 *
		 * @param v1 sp1
		 * @param v2 sp2
		 * @return a formed spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring max(javax.swing.Spring v1, javax.swing.Spring v2){
			return javax.swing.Spring.max(v1, v2);
		}
		
		/**
		 * return the min value of the two spring
		 *
		 * @param v1 sp1
		 * @param v2 sp2
		 * @return a formed spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring min(javax.swing.Spring v1, javax.swing.Spring v2){
			return javax.swing.Spring.max(v1, v2).equals(v1) ? v2 : v1;
		}
		
		/**
		 * validate the constrain for the item
		 *
		 * @param item
		 * @author HomeletWei
		 */
		public void put(Springs item){
			addComp(item.comp);
			SpringLayout.Constraints cons = layout.getConstraints(item.comp);
			for(Position c : Position.values())
				mapValue(cons, c, item.map.get(c));
		}
		
		/**
		 * add a component to the host
		 *
		 * @param comp
		 * @author HomeletWei
		 */
		private void addComp(Component comp){
			if(comp != null && !comp.equals(host) && (comp.getParent() == null || !comp.getParent().equals(host)))
				host.add(comp);
		}
		
		private void mapValue(javax.swing.SpringLayout.Constraints constrain, Position cons, javax.swing.Spring value){
			if(value == null)
				return;
			switch(cons){
				case VALUE_X:
					constrain.setX(value);
					return;
				case VALUE_Y:
					constrain.setY(value);
					return;
				case VALUE_WIDTH:
					constrain.setWidth(value);
					return;
				case VALUE_HEIGHT:
					constrain.setHeight(value);
					return;
				///////////<constrain>//////////
				case CONSTRAIN_Y:
				case TOP:
				case NORTH:
					constrain.setConstraint(SpringLayout.NORTH, value);
					return;
				case CONSTRAIN_Y_HEIGHT:
				case BOTTOM:
				case SOUTH:
					constrain.setConstraint(SpringLayout.SOUTH, value);
					return;
				case CONSTRAIN_X:
				case LEFT:
				case WEST:
					constrain.setConstraint(SpringLayout.WEST, value);
					return;
				case CONSTRAIN_X_WIDTH:
				case RIGHT:
				case EAST:
					constrain.setConstraint(SpringLayout.EAST, value);
					return;
				case NORTH_CENTER:
				case SOUTH_CENTER:
				case TOP_CENTER:
				case BOTTOM_CENTER:
				case HORIZONTAL_CENTER:
					constrain.setConstraint(SpringLayout.HORIZONTAL_CENTER, value);
					return;
				case EAST_CENTER:
				case WEST_CENTER:
				case LEFT_CENTER:
				case RIGHT_CENTER:
				case VERTICAL_CENTER:
					constrain.setConstraint(SpringLayout.VERTICAL_CENTER, value);
					return;
				case BASELINE:
					constrain.setConstraint(SpringLayout.BASELINE, value);
					return;
				default:
					return;
			}
		}
		// VALUE OF
		
		/**
		 * put a spring between comp1 and comp2 reletive to the sepcific position with the gap in between
		 *
		 * @param comp1
		 * @param position1
		 * @param gap
		 * @param comp2
		 * @param position2
		 * @author HomeletWei
		 */
		public void put(Position position1, Component comp1, int gap, Position position2, Component comp2){
			this.put(position1, comp1, valueOf(gap), position2, comp2);
		}
		
		/**
		 * put a spring between comp1 and comp2 reletive to the sepcific position with the gap in between
		 *
		 * @param comp1
		 * @param position1
		 * @param gap
		 * @param comp2
		 * @param position2
		 * @author HomeletWei
		 */
		public void put(Position position1, Component comp1, javax.swing.Spring gap, Position position2, Component comp2){
			addComp(comp1);
			addComp(comp2);
			if(!position1.isConstrain() || !position2.isConstrain())
				return;
			layout.putConstraint(position1.getValue(), comp1, gap, position2.getValue(), comp2);
		}
		
		/**
		 * the value of a integer as the preferred
		 *
		 * @param value
		 * @return a spring
		 * @author HomeletWei
		 */
		public static javax.swing.Spring valueOf(int value){
			return javax.swing.Spring.constant(value);
		}
		
		/**
		 * return the instance of a Spring
		 *
		 * @param comp
		 * @return a springs
		 * @author HomeletWei
		 * @see Springs
		 */
		public Springs instanceOf(Component comp){
			return new Springs(comp);
		}
		
		/**
		 * get the spring Position of the Edge of Constant
		 *
		 * @param comp
		 * @param position
		 * @return the Spring
		 * @author HomeletWei
		 */
		public javax.swing.Spring get(Component comp, Position position){
			switch(position){
				case VALUE_X:
					return getConstrain(comp).getX();
				case VALUE_Y:
					return getConstrain(comp).getY();
				case VALUE_WIDTH:
					return getConstrain(comp).getWidth();
				case VALUE_HEIGHT:
					return getConstrain(comp).getHeight();
				///////////<constrain>//////////
				case CONSTRAIN_Y:
				case TOP:
				case NORTH:
					return getConstrain(comp).getConstraint(SpringLayout.NORTH);
				case CONSTRAIN_Y_HEIGHT:
				case BOTTOM:
				case SOUTH:
					return getConstrain(comp).getConstraint(SpringLayout.SOUTH);
				case CONSTRAIN_X:
				case LEFT:
				case WEST:
					return getConstrain(comp).getConstraint(SpringLayout.WEST);
				case CONSTRAIN_X_WIDTH:
				case RIGHT:
				case EAST:
					return getConstrain(comp).getConstraint(SpringLayout.EAST);
				case NORTH_CENTER:
				case SOUTH_CENTER:
				case TOP_CENTER:
				case BOTTOM_CENTER:
				case HORIZONTAL_CENTER:
					return getConstrain(comp).getConstraint(SpringLayout.HORIZONTAL_CENTER);
				case EAST_CENTER:
				case WEST_CENTER:
				case LEFT_CENTER:
				case RIGHT_CENTER:
				case VERTICAL_CENTER:
					return getConstrain(comp).getConstraint(SpringLayout.VERTICAL_CENTER);
				case BASELINE:
					return getConstrain(comp).getConstraint(SpringLayout.BASELINE);
				default:
					return null;
			}
		}
		
		/**
		 * get the constrain for the comp
		 *
		 * @param comp
		 * @return the comp's constrain
		 * @author HomeletWei
		 */
		public javax.swing.SpringLayout.Constraints getConstrain(Component comp){
			return layout.getConstraints(comp);
		}
		
		@Override
		public SpringLayout getLayout(){
			return layout;
		}
		
		/**
		 * the position constant for constraining the component
		 *
		 * @author HomeletWei
		 * May 27, 2018
		 */
		public enum Position{
			/** <b>X</b> set the x position */
			VALUE_X,
			/** <b>Y</b> set the y position */
			VALUE_Y,
			/** <b>WIDTH</b> set the width */
			VALUE_WIDTH,
			/** <b>HEIGHT</b> set the height */
			VALUE_HEIGHT,
			///////////<constrain>//////////
			/**
			 * <b>CONSTRAIN_Y</b> constain the y value side
			 *
			 * @see #NORTH
			 * @see #TOP
			 */
			CONSTRAIN_Y(SpringLayout.NORTH),
			/**
			 * <b>TOP</b> constain the top value side
			 *
			 * @see #CONSTRAIN_X
			 * @see #NORTH
			 */
			TOP(SpringLayout.NORTH),
			/**
			 * <b>NORTH</b> constain the north value side
			 *
			 * @see #CONSTRAIN_Y
			 * @see #TOP
			 */
			NORTH(SpringLayout.NORTH),
			////////////////<div>////////////////
			/**
			 * <b>CONSTRAIN_Y_HEIGHT</b> constain the y + height value side
			 *
			 * @see #SOUTH
			 * @see #BOTTOM
			 */
			CONSTRAIN_Y_HEIGHT(SpringLayout.SOUTH),
			/**
			 * <b>BOTTOM</b> constain the bottom value side
			 *
			 * @see #SOUTH
			 * @see #CONSTRAIN_Y_HEIGHT
			 */
			BOTTOM(SpringLayout.SOUTH),
			/**
			 * <b>SOUTH</b> constain the south value side
			 *
			 * @see #CONSTRAIN_Y_HEIGHT
			 * @see #BOTTOM
			 */
			SOUTH(SpringLayout.SOUTH),
			////////////////<div>////////////////
			/**
			 * <b>CONSTRAIN_X_WIDTH</b> constain the x value side
			 *
			 * @see #EAST
			 * @see #RIGHT
			 */
			CONSTRAIN_X_WIDTH(SpringLayout.EAST),
			/**
			 * <b>RIGHT</b> constain the right value side
			 *
			 * @see #EAST
			 * @see #CONSTRAIN_X_WIDTH
			 */
			RIGHT(SpringLayout.EAST),
			/**
			 * <b>EAST</b> constain the east value side
			 *
			 * @see #CONSTRAIN_X_WIDTH
			 * @see #RIGHT
			 */
			EAST(SpringLayout.EAST),
			////////////////<div>////////////////
			/**
			 * <b>CONSTRAIN_X</b> constain the x value side
			 *
			 * @see #WEST
			 * @see #LEFT
			 */
			CONSTRAIN_X(SpringLayout.WEST),
			/**
			 * <b>LEFT</b> constain the left value side
			 *
			 * @see #WEST
			 * @see #CONSTRAIN_X
			 */
			LEFT(SpringLayout.WEST),
			/**
			 * <b>WEST</b> constain the west value side
			 *
			 * @see #CONSTRAIN_X
			 * @see #LEFT
			 */
			WEST(SpringLayout.WEST),
			////////////////<div>////////////////
			/**
			 * Note : this is equivalent to HORIZONTAL_CENTER
			 *
			 * <b>TOP_CENTER</b> constain the top center value side
			 *
			 * @see #HORIZONTAL_CENTER
			 */
			TOP_CENTER(SpringLayout.HORIZONTAL_CENTER),
			/**
			 * Note : this is equivalent to HORIZONTAL_CENTER
			 *
			 * <b>BOTTOM_CENTER</b> constain the bottom center value side
			 *
			 * @see #HORIZONTAL_CENTER
			 */
			BOTTOM_CENTER(SpringLayout.HORIZONTAL_CENTER),
			/**
			 * Note : this is equivalent to HORIZONTAL_CENTER
			 *
			 * <b>NORTH_CENTER</b> constain the north center value side
			 *
			 * @see #HORIZONTAL_CENTER
			 */
			NORTH_CENTER(SpringLayout.HORIZONTAL_CENTER),
			/**
			 * Note : this is equivalent to HORIZONTAL_CENTER
			 *
			 * <b>SOUTH_CENTER</b> constain the south center value side
			 *
			 * @see #HORIZONTAL_CENTER
			 */
			SOUTH_CENTER(SpringLayout.HORIZONTAL_CENTER),
			/**
			 * <b>HORIZONTAL_CENTER</b> constain the Horizontal Center value side
			 */
			HORIZONTAL_CENTER(SpringLayout.HORIZONTAL_CENTER),
			////////////////<div>////////////////
			/**
			 * Note : this is equivalent to VERTICAL_CENTER
			 *
			 * <b>EAST_CENTER</b> constain the east center value side
			 *
			 * @see #VERTICAL_CENTER
			 */
			EAST_CENTER(SpringLayout.VERTICAL_CENTER),
			/**
			 * Note : this is equivalent to VERTICAL_CENTER
			 *
			 * <b>WEST_CENTER</b> constain the west center value side
			 *
			 * @see #VERTICAL_CENTER
			 */
			WEST_CENTER(SpringLayout.VERTICAL_CENTER),
			/**
			 * Note : this is equivalent to VERTICAL_CENTER
			 *
			 * <b>LEFT_CENTER</b> constain the left center value side
			 *
			 * @see #VERTICAL_CENTER
			 */
			LEFT_CENTER(SpringLayout.VERTICAL_CENTER),
			/**
			 * Note : this is equivalent to VERTICAL_CENTER
			 *
			 * <b>RIGHT_CENTER</b> constain the right center value side
			 *
			 * @see #VERTICAL_CENTER
			 */
			RIGHT_CENTER(SpringLayout.VERTICAL_CENTER),
			/**
			 * <b>VERTICAL_CENTER</b> constain the vertical center value side
			 */
			VERTICAL_CENTER(SpringLayout.VERTICAL_CENTER),
			/**
			 * <b>VERTICAL_CENTER</b> constain the vertical center value side
			 */
			////////////////<div>////////////////
			BASELINE(SpringLayout.BASELINE);
			private String value;
			
			/**
			 * constructor for Layout.SpringLayouter.Constant
			 *
			 * @author HomeletWei
			 */
			Position(String value){
				this.value = value;
			}
			
			/**
			 * constructor for Layout.SpringLayouter.Constant
			 * <br>
			 * for non constarins value
			 *
			 * @author HomeletWei
			 */
			Position(){
			}
			
			/**
			 * @param other the other
			 * @return whether the positon is equals the other position
			 * @author HomeletWei
			 */
			public boolean equals(Position other){
				if(this.isConstrain() && other.isConstrain()){
					return this.getValue().contentEquals(other.getValue());
				}
				return super.equals(other);
			}
			
			/**
			 * test constain wheather is valid or not
			 *
			 * @return boolean
			 * @author HomeletWei
			 */
			public boolean isConstrain(){
				return value != null;
			}
			
			/** @return value */
			public String getValue(){
				return value;
			}
		}
		
		/**
		 * the springs reletive to the component
		 *
		 * @author HomeletWei
		 * May 16, 2018
		 */
		public static class Springs{
			
			Component                 comp;
			HashMap<Position, Spring> map;
			
			/**
			 * constructor for Layout.Spring.SpringItem
			 *
			 * @param comp the comp
			 * @author HomeletWei
			 */
			private Springs(Component comp){
				map = new HashMap<>();
				this.comp = comp;
			}
			
			/**
			 * @param key
			 * @param value
			 * @return this springItem
			 * @author HomeletWei
			 */
			public Springs put(Position key, javax.swing.Spring value){
				map.put(key, value);
				return this;
			}
			
			/**
			 * @param key
			 * @param value
			 * @return this springItem
			 * @author HomeletWei
			 */
			public Springs put(Position key, int value){
				map.put(key, Spring.constant(value));
				return this;
			}
		}
	}
	
	/**
	 * the group layout
	 *
	 * @author HomeletWei
	 * May 16, 2018
	 */
	public static class GroupLayouter extends Layouter{
		
		private GroupLayout                 layout;
		private GroupLayout.SequentialGroup horizontalGroup_y;
		private GroupLayout.SequentialGroup verticalGroup_x;
		////groups////
		private List<ParallelGroup>         horizontal_y;
		private List<ParallelGroup>         vertical_x;
		////Alignment////
		private VerticalAlignment           defaultVerticalAlignment;
		private HorizontalAlignment         defaultHorizontalAlignment;
		
		/**
		 * constructor for Layouter.GroupLayouter<br>
		 * construct the default vertical and horizontal alignment as baseline and gapBTComps, and gapBTContainer as true
		 *
		 * @param host
		 * @author HomeletWei
		 */
		public GroupLayouter(Container host){
			this(host, true, true);
		}
		
		/**
		 * constructor for Layouter.GroupLayouter<br>
		 * construct the default vertical and horizontal alignment as baseline
		 *
		 * @param host
		 * @param gapBetweenComp
		 * @param gapBetweenContainer
		 * @author HomeletWei
		 */
		public GroupLayouter(Container host, boolean gapBetweenComp, boolean gapBetweenContainer){
			this(host, gapBetweenComp, gapBetweenContainer, VerticalAlignment.BASELINE, HorizontalAlignment.CENTER);
		}
		
		/**
		 * constructor for Layouter.GroupLayouter
		 *
		 * @param host                the host of this layout
		 * @param gapBetweenComp      ture if should create gap between components, false otherwise
		 * @param gapBetweenContainer should true if should gap between components and container, false otherwise
		 * @param verticalAlignment   the default vertical Alignment
		 * @param horizontalAlignment the default horizontal Alignment
		 * @author HomeletWei
		 */
		public GroupLayouter(Container host, boolean gapBetweenComp, boolean gapBetweenContainer, VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment){
			super(host);
			this.layout = new GroupLayout(host);
			this.layout.setAutoCreateGaps(gapBetweenComp);
			this.layout.setAutoCreateContainerGaps(gapBetweenContainer);
			host.setLayout(layout);
			horizontal_y = new ArrayList<>();
			vertical_x = new ArrayList<>();
			horizontalGroup_y = layout.createSequentialGroup();
			verticalGroup_x = layout.createSequentialGroup();
			layout.setHorizontalGroup(horizontalGroup_y);
			layout.setVerticalGroup(verticalGroup_x);
			layout.setHonorsVisibility(true);
			defaultVerticalAlignment = verticalAlignment;
			defaultHorizontalAlignment = horizontalAlignment;
		}
		
		/**
		 * add the group layout in a compact way
		 *
		 * @param host
		 * @param componentMatrix
		 * @param verticalAlignment
		 * @param horizontalAlignment
		 * @param GapBTComponents
		 * @param GapBTContainer
		 * @author HomeletWei
		 */
		public static void addGroupLayOut(Container host, Component[][] componentMatrix, VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment, boolean GapBTComponents, boolean GapBTContainer){
			GroupLayouter layout = new GroupLayouter(host, GapBTComponents, GapBTContainer);
			layout.add(0, 0, componentMatrix, verticalAlignment, horizontalAlignment);
		}
		
		/**
		 * add the component matrix at the sepecific xStart, yStart<br>
		 * to layout the component as a matrix group
		 * the component Matix shall be as follow:
		 * <br>
		 *
		 * <pre>
		 * <code>
		 * +------------+
		 * | p1    p2
		 * | p3 p4 p5
		 * |    p6 p7
		 * |       p8 p9
		 * |
		 * +
		 * </code>
		 * </pre>
		 *
		 * <pre>
		 * <code>
		 * { { p1, null, p2 },
		 *   { p3, p4, p5 },
		 *   { null, p6, p7 },
		 *   { null, null, p8, p9 } }
		 * </code>
		 * </pre>
		 * <p>
		 * or
		 *
		 * <pre>
		 * <code>
		 * { { p1, null, p2, null },
		 *   { p3, p4, p5, null },
		 *   { null, p6, p7, null },
		 *   { null, null, p8, p9 } }
		 * </code>
		 * </pre>
		 *
		 * @param xStart
		 * @param yStart
		 * @param componentMatrix
		 * @param verticalAlignment
		 * @param horizontalAlignment
		 * @return whether this action successed or not
		 * @author HomeletWei
		 */
		public boolean add(int xStart, int yStart, Component[][] componentMatrix, VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment){
			for(int x = xStart; x < componentMatrix.length; x++){            // the x component show be all on the vertical group
				for(int y = yStart; y < componentMatrix[x].length; y++){            // the y component show be all on the horizontal group
					if(!add(x, y, componentMatrix[x][y], verticalAlignment, horizontalAlignment)){
						return false;
					}
				}
			}
			return true;
		}
		
		/**
		 * add the component at the position which sepcific
		 * <br>
		 * please note that, x / y can only be one up, or there will be no effect and return false
		 *
		 * @param x
		 * @param y
		 * @param component
		 * @param horizontalAlignment
		 * @param verticalAlignment
		 * @return whether this action successed or not
		 * @author HomeletWei
		 */
		public boolean add(int x, int y, Component component, VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment){
			addComp(component);
			GroupLayout.ParallelGroup xGroup = checkX(x, verticalAlignment);
			GroupLayout.ParallelGroup yGroup = checkY(y);
			if(xGroup == null || yGroup == null){
				System.err.println("invalide position");
				return false;
			}
			xGroup.addComponent(component, horizontalAlignment.alignment);
			yGroup.addComponent(component, horizontalAlignment.alignment);
			return true;
		}
		
		/**
		 * add a component to the host
		 *
		 * @param comp
		 * @author HomeletWei
		 */
		private void addComp(Component comp){
			if(comp != null && !comp.equals(host) && (comp.getParent() == null || !comp.getParent().equals(host)))
				host.add(comp);
		}
		
		private javax.swing.GroupLayout.ParallelGroup checkX(int x, VerticalAlignment alignment){
			if(vertical_x.size() <= x){
				if(x + 1 <= vertical_x.size())
					return null;
				GroupLayout.ParallelGroup group = layout.createParallelGroup(alignment.alignment);
				vertical_x.add(x, group);
				verticalGroup_x.addGroup(group);
			}
			return vertical_x.get(x);
		}
		
		private javax.swing.GroupLayout.ParallelGroup checkY(int y){
			if(horizontal_y.size() <= y){
				if(y + 1 <= horizontal_y.size())
					return null;
				GroupLayout.ParallelGroup group = layout.createParallelGroup();
				horizontal_y.add(y, group);
				horizontalGroup_y.addGroup(group);
			}
			return horizontal_y.get(y);
		}
		
		/** @return defaultVerticalAlignment */
		public VerticalAlignment getVerticalAlignment(){
			return defaultVerticalAlignment;
		}
		
		/** @param alignment : alignment */
		public void setVerticalAlignment(VerticalAlignment alignment){
			this.defaultVerticalAlignment = alignment;
		}
		
		/** @return defaultHorizontalAlignment */
		public HorizontalAlignment getHorizontalAlignment(){
			return defaultHorizontalAlignment;
		}
		
		/** @param defaultHorizontalAlignment : defaultHorizontalAlignment */
		public void setHorizontalAlignment(HorizontalAlignment defaultHorizontalAlignment){
			this.defaultHorizontalAlignment = defaultHorizontalAlignment;
		}
		
		/**
		 * @param bool
		 * @author HomeletWei
		 */
		public void setGapBetweenComponents(boolean bool){
			this.layout.setAutoCreateGaps(bool);
		}
		
		/**
		 * @param bool
		 * @author HomeletWei
		 */
		public void setGapBetweenContainer(boolean bool){
			this.layout.setAutoCreateContainerGaps(bool);
		}
		
		/**
		 * @param bool
		 * @author HomeletWei
		 */
		public void setHonorsVisable(boolean bool){
			this.layout.setHonorsVisibility(bool);
		}
		
		/**
		 * @return the component on the vertical(x)
		 * @author HomeletWei
		 */
		public int getVerticalComponentCount(){
			return vertical_x.size();
		}
		
		/**
		 * @return the component on the horizontal(y)
		 * @author HomeletWei
		 */
		public int getHorizontalComponentCount(){
			return horizontal_y.size();
		}
		
		/**
		 * @param xStart
		 * @param yStart
		 * @param componentMatrix
		 * @return whether this action successed or not
		 * @author HomeletWei
		 * @see #add(int, int, Component[][], VerticalAlignment, HorizontalAlignment)
		 */
		public boolean add(int xStart, int yStart, Component[][] componentMatrix){
			return this.add(xStart, yStart, componentMatrix, defaultVerticalAlignment, defaultHorizontalAlignment);
		}
		
		/**
		 * @param x
		 * @param y
		 * @param component
		 * @return whether this action successed or not
		 * @author HomeletWei
		 * @see #add(int, int, Component, VerticalAlignment, HorizontalAlignment)
		 */
		public boolean add(int x, int y, Component component){
			return this.add(x, y, component, defaultVerticalAlignment, defaultHorizontalAlignment);
		}
		
		/**
		 * @param x
		 * @param component
		 * @return whether this action successed or not
		 * @author HomeletWei
		 * @see #add(int, Component, VerticalAlignment, HorizontalAlignment)
		 */
		public boolean addX(int x, Component component){
			return this.add(x, component, defaultVerticalAlignment, defaultHorizontalAlignment);
		}
		
		/**
		 * add the component along the vertical axis which sepcific
		 * <br>
		 * please note that, x can only be one up, or there will be no effect and return false
		 *
		 * @param x
		 * @param component
		 * @param verticalAlignment
		 * @param horizontalAlignment
		 * @return whether this action successed or not
		 * @author HomeletWei
		 */
		public boolean add(int x, Component component, VerticalAlignment verticalAlignment, HorizontalAlignment horizontalAlignment){
			addComp(component);
			GroupLayout.ParallelGroup xGroup = checkX(x, verticalAlignment);
			if(xGroup == null){
				System.err.println("invalide position");
				return false;
			}
			xGroup.addComponent(component, horizontalAlignment.alignment);
			return true;
		}
		
		/**
		 * @param y
		 * @param component
		 * @return whether this action successed or not
		 * @author HomeletWei
		 * @see #add(int, Component, HorizontalAlignment)
		 */
		public boolean addY(int y, Component component){
			return this.add(y, component, defaultHorizontalAlignment);
		}
		
		/**
		 * add the component along the horizontal axis which sepcific
		 * <br>
		 * please note that, y can only be one up, or there will be no effect and return false
		 *
		 * @param y
		 * @param component
		 * @param horizontalAlignment
		 * @return whether this action successed or not
		 * @author HomeletWei
		 */
		public boolean add(int y, Component component, HorizontalAlignment horizontalAlignment){
			addComp(component);
			GroupLayout.ParallelGroup yGroup = checkY(y);
			if(yGroup == null){
				System.err.println("invalide position");
				return false;
			}
			yGroup.addComponent(component, horizontalAlignment.alignment);
			return true;
		}
		
		/**
		 * add a soild gap between the group which sepcific
		 * <br>
		 * the gap is between y and y+1
		 *
		 * @param y
		 * @param Gap
		 * @author HomeletWei
		 */
		public void addHorizontalGap(int y, int Gap){
			checkY(y).addGap(Gap);
		}
		
		/**
		 * add a soild gap between the group which sepcific
		 * <br>
		 * the gap is between x and x+1
		 *
		 * @param x
		 * @param Gap
		 * @author HomeletWei
		 */
		public void addVerticalGap(int x, int Gap){
			addVerticalGap(x, defaultVerticalAlignment, Gap);
		}
		
		/**
		 * add a soild gap between the group which sepcific
		 * <br>
		 * the gap is between x and x+1
		 *
		 * @param x
		 * @param alignment
		 * @param Gap
		 * @author HomeletWei
		 */
		public void addVerticalGap(int x, VerticalAlignment alignment, int Gap){
			checkX(x, alignment).addGap(Gap);
		}
		
		/**
		 * add a flexible gap between the group which sepcific
		 * <br>
		 * the gap is between y and y+1
		 *
		 * @param y
		 * @param pref
		 * @param min
		 * @param max
		 * @author HomeletWei
		 */
		public void addHorizontalGap(int y, int pref, int min, int max){
			checkY(y).addGap(min, pref, max);
		}
		
		/**
		 * add a flexible gap between the group which sepcific
		 * <br>
		 * the gap is between x and x+1
		 *
		 * @param x
		 * @param pref
		 * @param min
		 * @param max
		 * @author HomeletWei
		 */
		public void addVerticalGap(int x, int pref, int min, int max){
			addVerticalGap(x, defaultVerticalAlignment, pref, min, max);
		}
		
		/**
		 * add a flexible gap between the group which sepcific
		 * <br>
		 * the gap is between x and x+1
		 *
		 * @param x
		 * @param alignment
		 * @param pref
		 * @param min
		 * @param max
		 * @author HomeletWei
		 */
		public void addVerticalGap(int x, VerticalAlignment alignment, int pref, int min, int max){
			checkX(x, alignment).addGap(min, pref, max);
		}
		
		/**
		 * the horizontal alignment
		 *
		 * @author HomeletWei
		 * May 28, 2018
		 */
		public enum HorizontalAlignment{
			/** <b>LEADING</b> */
			LEADING(javax.swing.GroupLayout.Alignment.LEADING),
			/** <b>TRALING</b> */
			TRALING(javax.swing.GroupLayout.Alignment.TRAILING),
			/** <b>CENTER</b> */
			CENTER(javax.swing.GroupLayout.Alignment.CENTER);
			javax.swing.GroupLayout.Alignment alignment;
			
			private HorizontalAlignment(javax.swing.GroupLayout.Alignment alignment){
				this.alignment = alignment;
			}
		}
		
		/**
		 * the vertical alignment
		 *
		 * @author HomeletWei
		 * May 28, 2018
		 */
		public enum VerticalAlignment{
			/** <b>LEADING</b> */
			LEADING(javax.swing.GroupLayout.Alignment.LEADING),
			/** <b>TRALING</b> */
			TRALING(javax.swing.GroupLayout.Alignment.TRAILING),
			/** <b>BASELINE</b> */
			BASELINE(javax.swing.GroupLayout.Alignment.BASELINE),
			/** <b>CENTER</b> */
			CENTER(javax.swing.GroupLayout.Alignment.CENTER);
			javax.swing.GroupLayout.Alignment alignment;
			
			private VerticalAlignment(javax.swing.GroupLayout.Alignment alignment){
				this.alignment = alignment;
			}
		}
		
		@Override
		public GroupLayout getLayout(){
			return layout;
		}
	}
	
	/**
	 * the layouter for GridBagLayout
	 *
	 * @author HomeletWei
	 * May 27, 2018
	 */
	public static class GridBagLayouter extends Layouter{
		
		private GridBagLayout layout;
		
		/**
		 * constructor for Layouter.GridBagLayouter
		 *
		 * @param host
		 * @author HomeletWei
		 */
		public GridBagLayouter(Container host){
			super(host);
			layout = new GridBagLayout();
			host.setLayout(layout);
		}
		
		/**
		 * put the component into the layout as well as the host with the constrains
		 *
		 * @param constrains
		 * @author HomeletWei
		 */
		public void put(GridConstrain constrains){
			host.add(constrains.comp, constrains);
		}
		
		/**
		 * <p>
		 * Use <code style="color:red;">REMAINDER</code> to specify that the component's
		 * display area will be the last cell / row in the column / row.
		 * </p>
		 * <p>
		 * Use <code style="color:red;">RELATIVE</code> to specify that the component's
		 * display area will be next to the last one in its column / row.
		 * </p>
		 *
		 * @param comp
		 * @param x
		 * @param y
		 * @return instance of Grid Constrain
		 * @author HomeletWei
		 */
		public GridConstrain instanceOf(Component comp, int x, int y){
			return new GridConstrain(comp, x, y);
		}
		
		/**
		 * <p>
		 * Use <code style="color:red;">REMAINDER</code> to specify that the component's
		 * display area will be the last cell / row in the column / row.
		 * </p>
		 * <p>
		 * Use <code style="color:red;">RELATIVE</code> to specify that the component's
		 * display area will be next to the last one in its column / row.
		 * </p>
		 *
		 * @param comp
		 * @param x
		 * @param y
		 * @param collSpan
		 * @param rowSpan
		 * @return instance of Grid Constrain
		 * @author HomeletWei
		 */
		public GridConstrain instanceOf(Component comp, int x, int y, int collSpan, int rowSpan){
			return new GridConstrain(comp, x, y, collSpan, rowSpan);
		}
		
		/**
		 * the grid bag constrain
		 *
		 * @author HomeletWei
		 * May 28, 2018
		 */
		public static class GridConstrain extends GridBagConstraints{
			
			/** <b>serialVersionUID</b> */
			private static final long      serialVersionUID = 1L;
			private              Component comp;
			
			/**
			 * constructor for Layouter.GridBagLayouter.gridConstrain
			 * <br>
			 * <p>
			 * Use <code style="color:red;">REMAINDER</code> to specify that the component's
			 * display area will be the last cell / row in the column / row.
			 * </p>
			 * <p>
			 * Use <code style="color:red;">RELATIVE</code> to specify that the component's
			 * display area will be next to the last one in its column / row.
			 * </p>
			 *
			 * @param x
			 * @param y
			 * @param collSpan
			 * @param rowSpan
			 * @author HomeletWei
			 */
			private GridConstrain(Component comp, int x, int y, int collSpan, int rowSpan){
				this(comp, x, y);
				this.gridwidth = collSpan;
				this.gridheight = rowSpan;
			}
			
			/**
			 * constructor for Layouter.GridBagLayouter.gridConstrain
			 * <br>
			 * <p>
			 * Use <code style="color:red;">REMAINDER</code> to specify that the component's
			 * display area will be the last cell / row in the column / row.
			 * </p>
			 * <p>
			 * Use <code style="color:red;">RELATIVE</code> to specify that the component's
			 * display area will be next to the last one in its column / row.
			 * </p>
			 *
			 * @param x
			 * @param y
			 * @author HomeletWei
			 */
			private GridConstrain(Component comp, int x, int y){
				super();
				this.gridx = x;
				this.gridy = y;
				this.comp = comp;
			}
			
			/**
			 * set the ancher (component alignment) of the component
			 *
			 * @param anchor
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setAnchor(Anchor anchor){
				return setAnchor(anchor.value);
			}
			
			/**
			 * set the ancher (component alignment) of the component
			 *
			 * @param anchor
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setAnchor(int anchor){
				this.anchor = anchor;
				return this;
			}
			
			/**
			 * set the weight of x and y
			 *
			 * @param weightX horizontal Space portion
			 * @param weightY vertical Space portion
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setWeight(double weightX, double weightY){
				this.weightx = weightX;
				this.weighty = weightY;
				return this;
			}
			
			/**
			 * set the fill value (both, vertical, horizontal, none)
			 *
			 * @param fill
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setFill(Fill fill){
				return setFill(fill.value);
			}
			
			/**
			 * set the fill value (both, vertical, horizontal, none)
			 *
			 * @param fill
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setFill(int fill){
				this.fill = fill;
				return this;
			}
			
			/**
			 * set the inner pad
			 *
			 * @param innerPadX
			 * @param innerPadY
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setInnerPad(int innerPadX, int innerPadY){
				this.ipadx = innerPadX;
				this.ipady = innerPadY;
				return this;
			}
			
			/**
			 * set the inset for the same value for the four
			 *
			 * @param value
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setInsets(int value){
				return setInsets(value, value, value, value);
			}
			
			/**
			 * set the insets
			 *
			 * @param top
			 * @param bottom
			 * @param left
			 * @param right
			 * @return this
			 * @author HomeletWei
			 */
			public GridConstrain setInsets(int top, int bottom, int left, int right){
				this.insets = new Insets(top, left, bottom, right);
				return this;
			}
			
			/**
			 * the anchor for the layout
			 *
			 * @author HomeletWei
			 * May 28, 2018
			 */
			public enum Anchor{
				//////POSITION ALIGNMENT/////
				/** <b>CENTER</b> */
				CENTER(GridBagConstraints.CENTER),
				/** <b>NORTH</b> */
				NORTH(GridBagConstraints.NORTH),
				/** <b>TOP</b> */
				TOP(GridBagConstraints.NORTH),
				/** <b>NORTH_EAST</b> */
				NORTH_EAST(GridBagConstraints.NORTHEAST),
				/** <b>TOP_RIGHT</b> */
				TOP_RIGHT(GridBagConstraints.NORTHEAST),
				/** <b>EAST</b> */
				EAST(GridBagConstraints.EAST),
				/** <b>RIGHT</b> */
				RIGHT(GridBagConstraints.EAST),
				/** <b>SOUTH_EAST</b> */
				SOUTH_EAST(GridBagConstraints.SOUTHEAST),
				/** <b>BOTTOM_RIGHT</b> */
				BOTTOM_RIGHT(GridBagConstraints.SOUTHEAST),
				/** <b>SOUTH</b> */
				SOUTH(GridBagConstraints.SOUTH),
				/** <b>BOTTOM</b> */
				BOTTOM(GridBagConstraints.SOUTH),
				/** <b>SOUTH_WEST</b> */
				SOUTH_WEST(GridBagConstraints.SOUTHWEST),
				/** <b>BOTTOM_LEFT</b> */
				BOTTOM_LEFT(GridBagConstraints.SOUTHWEST),
				/** <b>WEST</b> */
				WEST(GridBagConstraints.WEST),
				/** <b>LEFT</b> */
				LEFT(GridBagConstraints.WEST),
				/** <b>NORTH_WEST</b> */
				NORTH_WEST(GridBagConstraints.NORTHWEST),
				/** <b>TOP_LEFT</b> */
				TOP_LEFT(GridBagConstraints.NORTHWEST),
				//////PAGE ALIGNMENT/////
				/** <b>PAGE_START</b> */
				PAGE_START(GridBagConstraints.PAGE_START),
				/** <b>PAGE_END</b> */
				PAGE_END(GridBagConstraints.PAGE_END),
				/** <b>LINE_START</b> */
				LINE_START(GridBagConstraints.LINE_START),
				/** <b>LINE_END</b> */
				LINE_END(GridBagConstraints.LINE_END),
				/** <b>FIRST_LINE_START</b> */
				FIRST_LINE_START(GridBagConstraints.FIRST_LINE_START),
				/** <b>FIRST_LINE_END</b> */
				FIRST_LINE_END(GridBagConstraints.FIRST_LINE_END),
				/** <b>LAST_LINE_START</b> */
				LAST_LINE_START(GridBagConstraints.LAST_LINE_START),
				/** <b>LAST_LINE_END</b> */
				LAST_LINE_END(GridBagConstraints.LAST_LINE_END),
				//////CENTER_BASELINE/////
				/** <b>X_CENTER_Y_CENTER_BASELINE</b> */
				X_CENTER_Y_CENTER_BASELINE(BASELINE),
				/** <b>HORIZ_CENTER_VERTI_CENTER_BASELINE</b> */
				HORIZ_CENTER_VERTI_CENTER_BASELINE(BASELINE),
				/** <b>X_LEFT_Y_CENTER_BASELINE</b> */
				X_LEFT_Y_CENTER_BASELINE(BASELINE_LEADING),
				/** <b>HORIZ_LEFT_VERTI_CENTER_BASELINE</b> */
				HORIZ_LEFT_VERTI_CENTER_BASELINE(BASELINE_LEADING),
				/** <b>X_RIGHT_Y_CENTER_BASELINE</b> */
				X_RIGHT_Y_CENTER_BASELINE(BASELINE_TRAILING),
				/** <b>HORIZ_RIGHT_VERTI_CENTER_BASELINE</b> */
				HORIZ_RIGHT_VERTI_CENTER_BASELINE(BASELINE_TRAILING),
				/////TOP_BASELINE//////
				/** <b>X_CENTER_Y_TOP_BASELINE</b> */
				X_CENTER_Y_TOP_BASELINE(ABOVE_BASELINE),
				/** <b>HORIZ_CENTER_VERTI_TOP_BASELINE</b> */
				HORIZ_CENTER_VERTI_TOP_BASELINE(ABOVE_BASELINE),
				/** <b>X_LEFT_Y_TOP_BASELINE</b> */
				X_LEFT_Y_TOP_BASELINE(ABOVE_BASELINE_LEADING),
				/** <b>HORIZ_LEFT_VERTI_TOP_BASELINE</b> */
				HORIZ_LEFT_VERTI_TOP_BASELINE(ABOVE_BASELINE_LEADING),
				/** <b>X_RIGHT_Y_TOP_BASELINE</b> */
				X_RIGHT_Y_TOP_BASELINE(ABOVE_BASELINE_TRAILING),
				/** <b>HORIZ_RIGHT_VERTI_TOP_BASELINE</b> */
				HORIZ_RIGHT_VERTI_TOP_BASELINE(ABOVE_BASELINE_TRAILING),
				/////BOTTOM_BASELINE////
				/** <b>X_CENTER_Y_BOTTOM_BASELINE</b> */
				X_CENTER_Y_BOTTOM_BASELINE(BELOW_BASELINE),
				/** <b>HORIZ_CENTER_VERTI_BOTTOM_BASELINE</b> */
				HORIZ_CENTER_VERTI_BOTTOM_BASELINE(BELOW_BASELINE),
				/** <b>X_LEFT_Y_BOTTOM_BASELINE</b> */
				X_LEFT_Y_BOTTOM_BASELINE(BELOW_BASELINE_LEADING),
				/** <b>HORIZ_LEFT_VERTI_BOTTOM_BASELINE</b> */
				HORIZ_LEFT_VERTI_BOTTOM_BASELINE(BELOW_BASELINE_LEADING),
				/** <b>X_RIGHT_Y_BOTTOM_BASELINE</b> */
				X_RIGHT_Y_BOTTOM_BASELINE(BELOW_BASELINE_TRAILING),
				/** <b>HORIZ_RIGHT_VERTI_BOTTOM_BASELINE</b> */
				HORIZ_RIGHT_VERTI_BOTTOM_BASELINE(BELOW_BASELINE_TRAILING);
				int value;
				
				private Anchor(int value){
					this.value = value;
				}
			}
			
			/**
			 * the choice for the fill
			 *
			 * @author HomeletWei
			 * May 28, 2018
			 */
			public enum Fill{
				/** <b>BOTH</b> */
				BOTH(GridBagConstraints.BOTH),
				/** <b>HORIZONTAL</b> */
				HORIZONTAL(GridBagConstraints.HORIZONTAL),
				/** <b>VERTICAL</b> */
				VERTICAL(GridBagConstraints.VERTICAL),
				/** <b>NONE</b> */
				NONE(GridBagConstraints.NONE);
				int value;
				
				private Fill(int value){
					this.value = value;
				}
			}
		}
		
		@Override
		public GridBagLayout getLayout(){
			return layout;
		}
	}
	
	/**
	 * the layouter for BorderLayout
	 *
	 * @author HomeletWei
	 * May 28, 2018
	 */
	public static class BorderLayouter extends Layouter{
		
		private BorderLayout layout;
		
		/**
		 * constructor for Layouter.BorderLayouter
		 * s*
		 *
		 * @param host
		 * @author HomeletWei
		 */
		public BorderLayouter(Container host){
			super(host);
			this.layout = new BorderLayout();
			this.host.setLayout(layout);
		}
		
		/**
		 * set the horizontal gap
		 *
		 * @param hGap
		 * @author HomeletWei
		 */
		public void setHorizontalGap(int hGap){
			layout.setHgap(hGap);
		}
		
		/**
		 * set the vertical gap
		 *
		 * @param vGap
		 * @author HomeletWei
		 */
		public void setVerticalGap(int vGap){
			layout.setVgap(vGap);
		}
		
		/**
		 * add the object
		 *
		 * @param comp
		 * @param position
		 * @author HomeletWei
		 */
		public void add(Component comp, EDGE position){
			add(comp, position.value);
		}
		
		/**
		 * add the object
		 *
		 * @param comp
		 * @param position
		 * @author HomeletWei
		 */
		public void add(Component comp, String position){
			host.add(comp, position);
		}
		
		/**
		 * the position for the borderlayout
		 *
		 * @author HomeletWei
		 * May 28, 2018
		 */
		public enum EDGE{
			/** <b>NORTH</b> */
			NORTH(BorderLayout.NORTH),
			/** <b>TOP</b> */
			TOP(BorderLayout.NORTH),
			/** <b>SOUTH</b> */
			SOUTH(BorderLayout.SOUTH),
			/** <b>BOTTOM</b> */
			BOTTOM(BorderLayout.SOUTH),
			/** <b>WEST</b> */
			WEST(BorderLayout.WEST),
			/** <b>LEFT</b> */
			LEFT(BorderLayout.WEST),
			/** <b>EAST</b> */
			EAST(BorderLayout.EAST),
			/** <b>RIGHT</b> */
			RIGHT(BorderLayout.EAST),
			/** <b>CENTER</b> */
			CENTER(BorderLayout.CENTER),
			/** <b>MIDDLE</b> */
			MIDDLE(BorderLayout.CENTER);
			String value;
			
			private EDGE(String value){
				this.value = value;
			}
		}
		
		@Override
		public BorderLayout getLayout(){
			return layout;
		}
	}
	
	/**
	 * the layouter for GridLayout
	 * <br>
	 * Note : please remember to call apply layout in the layouter to notify the layouter for applying the layout
	 *
	 * @author HomeletWei
	 * May 28, 2018
	 */
	public static class GridLayouter extends Layouter{
		
		private Component[] childrenList;
		private GridLayout  layout;
		
		/**
		 * constructor for Layouter.GridLayouter
		 *
		 * @param host
		 * @param row           horizontal
		 * @param col           vertical
		 * @param horizontalGap
		 * @param verticalGap
		 * @author HomeletWei
		 */
		public GridLayouter(Container host, int row, int col, int horizontalGap, int verticalGap){
			super(host);
			this.layout = new GridLayout(row, col, horizontalGap, verticalGap);
			host.setLayout(layout);
			this.childrenList = new Component[row * col];
		}
		
		/**
		 * set the row number
		 *
		 * @param row
		 * @author HomeletWei
		 */
		public void setRow(int row){
			layout.setRows(row);
			onSizeChange();
		}
		
		private void onSizeChange(){
			int newSize = layout.getRows() * layout.getColumns();
			if(childrenList.length != newSize)
				childrenList = Arrays.copyOf(childrenList, newSize);
		}
		
		/**
		 * set the columns number
		 *
		 * @param col
		 * @author HomeletWei
		 */
		public void setColumns(int col){
			layout.setColumns(col);
			onSizeChange();
		}
		
		/**
		 * set the horizontal gap
		 *
		 * @param hGap
		 * @author HomeletWei
		 */
		public void setHorizontalGap(int hGap){
			layout.setHgap(hGap);
		}
		
		/**
		 * set the vertical gap
		 *
		 * @param vGap
		 * @author HomeletWei
		 */
		public void setVerticalGap(int vGap){
			layout.setVgap(vGap);
		}
		
		/**
		 * add the comp to the opsition depends on the current layout coloums
		 * <br>
		 * Note : please remember to call apply layout in the layouter to notify the layouter for applying the layout
		 *
		 * @param comp
		 * @param x
		 * @param y
		 * @author HomeletWei
		 */
		public void add(Component comp, int x, int y){
			add(comp, layout.getColumns() * y + x);
		}
		
		/**
		 * add the comp to the position with the index
		 * <br>
		 * Note : please remember to call apply layout in the layouter to notify the layouter for applying the layout
		 *
		 * @param comp
		 * @param index
		 * @author HomeletWei
		 */
		public void add(Component comp, int index){
			childrenList[index] = comp;
		}
		
		/**
		 * apply this layout
		 *
		 * @author HomeletWei
		 */
		public void applyLayout(){
			host.removeAll();
			for(int index = 0; index < childrenList.length; index++){
				Component comp = childrenList[index];
				if(comp != null)
					host.add(comp);
			}
		}
		
		@Override
		public GridLayout getLayout(){
			return layout;
		}
	}
	
	/**
	 * the flowLayout layouter
	 *
	 * @author HomeletWei
	 * May 28, 2018
	 */
	public static class FlowLayouter extends Layouter{
		
		private FlowLayout layout;
		
		/**
		 * constructor for Layouter.FlowLayouter
		 *
		 * @param host
		 * @author HomeletWei
		 */
		public FlowLayouter(Container host){
			this(host, FlowAlignment.CENTER, 5, 5, false, false);
		}
		
		/**
		 * constructor for FlowLayouter
		 *
		 * @param host
		 * @param alignment
		 * @param horizontalGap
		 * @param verticalGap
		 * @param fillHorizontal for Vertical Layout exclusive
		 * @param fillVertical   for Vertical Layout exclusive
		 * @author HomeletWei
		 */
		public FlowLayouter(Container host, FlowAlignment alignment, int horizontalGap, int verticalGap, boolean fillHorizontal, boolean fillVertical){
			super(host);
			switch(alignment){
				case LEADING:
				case LEFT:
				case CENTER:
				case RIGHT:
				case TRAILING:
					this.layout = new FlowLayout(alignment.value, horizontalGap, verticalGap);
					break;
				case TOP:
				case VERTICAL_CENTER:
				case BOTTOM:
					this.layout = new VerticalFlowLayout(alignment.value, horizontalGap, verticalGap, fillHorizontal, fillVertical);
					break;
				default:
					this.layout = new FlowLayout(FlowAlignment.CENTER.value);
					break;
			}
			this.host.setLayout(layout);
		}
		
		/**
		 * set the horizontals gap
		 *
		 * @param hGap
		 * @author HomeletWei
		 */
		public void setHorizontalGap(int hGap){
			layout.setHgap(hGap);
		}
		
		/**
		 * set the vertical gap
		 *
		 * @param vGap
		 * @author HomeletWei
		 */
		public void setVerticalGap(int vGap){
			layout.setVgap(vGap);
		}
		
		/**
		 * set should be align on base line or not
		 *
		 * @param bool
		 * @author HomeletWei
		 */
		public void setAlignOnBaseLine(boolean bool){
			layout.setAlignOnBaseline(bool);
		}
		
		/**
		 * add the comp
		 *
		 * @param comp
		 * @author HomeletWei
		 */
		public void add(Component comp){
			host.add(comp);
		}
		
		/**
		 * add the comp with a sepcific index
		 *
		 * @param comp
		 * @param index
		 * @author HomeletWei
		 */
		public void add(Component comp, int index){
			host.add(comp, index);
		}
		
		/**
		 * remove the comp
		 *
		 * @param comp
		 * @author HomeletWei
		 */
		public void remove(Component comp){
			host.remove(comp);
		}
		
		/**
		 * remove the sepcific index
		 *
		 * @param index
		 * @author HomeletWei
		 */
		public void remove(int index){
			if(index == -1){
				removeAll();
				return;
			}
			host.remove(index);
		}
		
		/**
		 * remove all
		 *
		 * @author HomeletWei
		 */
		public void removeAll(){
			host.removeAll();
		}
		
		/**
		 * the flow layout alignments
		 *
		 * @author HomeletWei
		 * May 28, 2018
		 */
		public enum FlowAlignment{
			//// Flow layout ////
			/** <b>LEFT</b> */
			LEFT(FlowLayout.LEFT),
			/** <b>RIGHT</b> */
			RIGHT(FlowLayout.RIGHT),
			/** <b>CENTER</b> */
			CENTER(FlowLayout.CENTER),
			/** <b>LEADING</b> */
			LEADING(FlowLayout.LEADING),
			/** <b>TRAILING</b> */
			TRAILING(FlowLayout.TRAILING),
			//// Vertical Flow layout ////
			/** <b>TOP</b> Vertical Layout */
			TOP(VerticalFlowLayout.TOP),
			/** <b>TOP</b> Vertical Layout */
			VERTICAL_CENTER(VerticalFlowLayout.CENTER),
			/** <b>BOTTOM</b> Vertical Layout */
			BOTTOM(VerticalFlowLayout.BOTTOM);
			int value;
			
			FlowAlignment(int value){
				this.value = value;
			}
		}
		
		@Override
		public FlowLayout getLayout(){
			return layout;
		}
	}
}
