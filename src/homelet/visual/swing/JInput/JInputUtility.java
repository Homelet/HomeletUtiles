package homelet.visual.swing.JInput;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.util.Scanner;

public class JInputUtility{
	
	public static void copyAll(JTextComponent parent){
		selectAll(parent);
		copy(parent);
	}
	
	public static void selectAll(JTextComponent parent){
		parent.selectAll();
	}
	
	public static void copy(JTextComponent parent){
		parent.copy();
	}
	
	public static void replaceAll(JTextComponent parent){
		selectAll(parent);
		pasta(parent);
	}
	
	public static void pasta(JTextComponent parent){
		parent.paste();
	}
	
	public static void cutAll(JTextComponent parent){
		selectAll(parent);
		cut(parent);
	}
	
	public static void cut(JTextComponent parent){
		parent.cut();
	}
	
	public static void clearAll(JTextComponent parent){
		try{
			parent.getDocument().remove(0, parent.getDocument().getLength());
		}catch(BadLocationException e){
			e.printStackTrace();
		}
	}
	
	public static int getLetterCount(JTextComponent parent){
		return removeWhiteSpace(parent.getText()).length();
	}
	
	private static String removeWhiteSpace(String source){
		StringBuilder builder = new StringBuilder();
		Scanner       sc      = new Scanner(source);
		while(sc.hasNext()){
			builder.append(sc.next());
		}
		sc.close();
		return builder.toString();
	}
	
	public static int getWordCount(JTextComponent parent){
		return parent.getText().length() == 0 ? 0 : parent.getText().trim().split("\\s+").length;
	}
	
	public static int getCharacterCount(JTextComponent parent){
		return parent.getText().length();
	}
}
