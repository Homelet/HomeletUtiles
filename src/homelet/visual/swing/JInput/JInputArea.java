/**
 * @author HomeletWei
 * @date Apr 4, 2018
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

public class JInputArea extends JInput{
	
	private JTextArea    textField;
	private StringDrawer drawer;
	
	public JInputArea(String placeHolder, boolean editable){
		super();
		drawer = new StringDrawer(placeHolder);
		drawer.setColor(Color.GRAY);
		drawer.setAlign(Alignment.TOP_LEFT);
		drawer.setTextAlign(Alignment.LEFT);
		drawer.setInsets(0, 2, 0, 0);
		this.textField = new JTextArea(){
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				if(getContent().isEmpty()){
					try{
						drawer.updateGraphics(g);
						drawer.setFrame(getBounds());
						drawer.validate();
						drawer.draw();
					}catch(StringDrawerException e){
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void setEnabled(boolean enabled){
				getInputAssistant().setModifyEnable(enabled);
				super.setEnabled(enabled);
			}
		};
		this.textField.setLineWrap(true);
		this.textField.setWrapStyleWord(true);
		this.textField.setEditable(editable);
		this.textField.setRequestFocusEnabled(true);
		JScrollPane scrollPane = new JScrollPane(textField, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setAutoscrolls(true);
		setUpAssistant(editable);
		Layouter.GridBagLayouter layouter = new GridBagLayouter(this);
		layouter.put(layouter.instanceOf(scrollPane, 0, 0).setAnchor(Anchor.CENTER).setFill(Fill.BOTH).setWeight(100, 100));
	}
	
	public StringDrawer getDrawer(){
		return drawer;
	}
	
	@Override
	public JTextComponent getTextComponent(){
		return textField;
	}
}
