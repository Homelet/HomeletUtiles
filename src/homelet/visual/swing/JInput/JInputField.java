/**
 * @author HomeletWei
 * @date Apr 9, 2018
 */
/*
 * Update Log:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 * *****************************************************
 */
package homelet.visual.swing.JInput;

import homelet.utils.StringDrawer.StringDrawer;
import homelet.utils.StringDrawer.StringDrawerException;
import homelet.utils.Layouter;
import homelet.utils.Layouter.GridBagLayouter;
import homelet.utils.Layouter.GridBagLayouter.GridConstrain.Anchor;
import homelet.utils.Layouter.GridBagLayouter.GridConstrain.Fill;
import homelet.utils.Alignment;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class JInputField extends JInput{
	
	private JTextField   textField;
	private StringDrawer drawer;
	
	public JInputField(String placeHolder, boolean editable){
		super();
		drawer = new StringDrawer(placeHolder);
		drawer.setColor(Color.GRAY);
		drawer.setAlign(Alignment.LEFT);
		drawer.setTextAlign(Alignment.LEFT);
		drawer.setInsets(1, 1, 0, 0);
		this.textField = new JTextField(){
			@Override
			public void paint(Graphics g){
				super.paint(g);
				if(getContent().isEmpty()){
					try{
						drawer.updateGraphics((Graphics2D) g);
						drawer.setFrame(getBounds());
						drawer.validate();
						drawer.draw();
					}catch(StringDrawerException e){
						e.printStackTrace();
					}
				}
			}
		};
		this.textField.setEditable(editable);
		setUpAssistant(editable);
		Layouter.GridBagLayouter layouter = new GridBagLayouter(this);
		layouter.put(layouter.instanceOf(textField, 0, 0).setAnchor(Anchor.CENTER).setFill(Fill.BOTH).setWeight(100, 100));
	}
	
	public StringDrawer getDrawer(){
		return drawer;
	}
	
	@Override
	public JTextComponent getTextComponent(){
		return textField;
	}
}
