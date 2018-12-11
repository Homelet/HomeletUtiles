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
 * @date May 1, 2018
 */
package homelet.visual.swing.JInput;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public abstract class JInput extends JComponent{
	
	private JInputAssistant assistant;
	
	JInput(){}
	
	protected void setUpAssistant(boolean editable){
		assistant = new JInputAssistant(getTextComponent());
		assistant.setModifyEnable(editable);
	}
	
	public boolean isChanged(){
		return (getContent() != null) && !getContent().isEmpty();
	}
	
	public String getContent(){
		return getTextComponent().getText();
	}
	
	public void setContent(String content){
		getTextComponent().setText(content);
	}
	
	public JInputAssistant getInputAssistant(){
		return assistant;
	}
	
	public abstract JTextComponent getTextComponent();
}
