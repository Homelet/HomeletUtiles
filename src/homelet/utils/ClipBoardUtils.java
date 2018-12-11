/**
 * @author HomeletWei
 * @date Apr 25, 2018
 */
/*
 * Update Log:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 * *****************************************************
 */
package homelet.utils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * TODO
 *
 * @author HomeletWei
 * @date Apr 25, 2018
 */
public class ClipBoardUtils{
	
	private static Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	public static void copyToClipBoard(ClipboardOwner owner, Transferable transferable){
		systemClipboard.setContents(transferable, owner);
	}
	
	public static void copyToClipBoard(ClipboardOwner owner, String text){
		copyToClipBoard(owner, new StringSelection(text));
	}
	
	public static Object getClipBoardItem(Object requester, DataFlavor flavor){
		try{
			return systemClipboard.getContents(requester).getTransferData(flavor);
		}catch(HeadlessException | IOException | UnsupportedFlavorException e){
			return null;
		}
	}
	
	public static String getClipBoardText(Object requester){
		Object re = getClipBoardItem(requester, new DataFlavor(String.class, "java.lang.String"));
		return re == null ? null : (String) re;
	}
}
