package homelet.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Pictures{
	
	private static HashMap<Object, Pictures> lib = new HashMap<>();
	
	public static Pictures put(Object key, String path, String comment){
		return lib.put(key, new Pictures(path, comment));
	}
	
	public static Pictures put(Object key, BufferedImage image, String comment){
		return lib.put(key, new Pictures(image, comment));
	}
	
	public static Pictures put(Object key, String path){
		return put(key, path, path);
	}
	
	public static Pictures put(Object key, BufferedImage image){
		return put(key, image, null);
	}
	
	public static Pictures get(Object key){
		return lib.get(key);
	}
	
	public static HashMap<Object, Pictures> getLib(){
		return lib;
	}
	
	private String        comment;
	private BufferedImage image = null;
	
	private Pictures(String path, String comment){
		try{
			this.image = ImageIO.read(Pictures.class.getResource(path));
		}catch(IOException e){
			e.printStackTrace();
		}
		this.comment = comment;
	}
	
	private Pictures(BufferedImage image, String comment){
		this.image = image;
		this.comment = comment;
	}
	
	public BufferedImage image(){
		return image;
	}
	
	public Icon icon(){
		return new ImageIcon(image(), comment);
	}
	
	public TrayIcon trayIcon(){
		return new TrayIcon(image(), comment);
	}
}
