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
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * TODO
 *
 * @author HomeletWei
 * @date Apr 25, 2018
 */
public enum ImageResize{
	/** <b>SCALE_TO_FIT</b> resize by keeping the shortest side and resize the other by ratio to fit the desired DI, this may result in some blank spacing */
	SCALE_TO_FIT,
	/** <b>SCALE_TO_FILL</b> resize by keeping the longest side and resize the other by ratio to fit the desired DI, this may result in cut off some of the part */
	SCALE_TO_FILL,
	/** <b>STRETCH_TO_FILL</b> resize by stretching both size of the image to be the desired Dimension */
	STRETCH_TO_FILL,
	/** <b>DO_NOT_RESIZE</b> do not do any resize */
	DO_NOT_RESIZE;
	
	/**
	 * get the image
	 *
	 * @param image       the image source
	 * @param containerDI the dimension required
	 * @return a bufferedImage
	 * @author HomeletWei
	 */
	public Image getResizedImage(Image image, Dimension containerDI){
		return ImageResize.getResizedImage(this, image, containerDI);
	}
	
	/**
	 * get the image
	 *
	 * @param image       the image source
	 * @param containerDI the dimension required
	 * @return a bufferedImage
	 * @author HomeletWei
	 */
	public static Image getResizedImage(ImageResize mode, Image image, Dimension containerDI){
		switch(mode){
			case SCALE_TO_FIT:
				return scaleToFit(image, containerDI);
			case SCALE_TO_FILL:
				return scaleToFill(image, containerDI);
			case STRETCH_TO_FILL:
				return stretchToFill(image, containerDI);
			case DO_NOT_RESIZE://$FALL-THROUGH$
			default:
				return image;
		}
	}
	
	private static BufferedImage scaleToFit(Image image, Dimension containerDI){
		// because scaling to fit result in the long side to be kept,
		// but the short side needs to be resize
		int width  = image.getWidth(null);
		int height = image.getHeight(null);
		if(width > height){
			float scaleFactor = Integer.valueOf(containerDI.width).floatValue() / width;
			width = containerDI.width;
			height = (int) (height * scaleFactor);
		}else{
			float scaleFactor = Integer.valueOf(containerDI.height).floatValue() / height;
			height = containerDI.height;
			width = (int) (width * scaleFactor);
		}
		Dimension     finalDI    = new Dimension(width, height);
		BufferedImage finalImage = new BufferedImage(containerDI.width, containerDI.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g          = finalImage.createGraphics();
		Point2D       point      = Alignment.CENTER.getVertex(false, new Rectangle(0, 0, containerDI.width, containerDI.height), new Rectangle(finalDI));
		GH.draw(g, image.getScaledInstance(finalDI.width, finalDI.height, Image.SCALE_SMOOTH), point, null);
		g.dispose();
		finalImage.flush();
		return finalImage;
	}
	
	private static BufferedImage scaleToFill(Image image, Dimension containerDI){
		// because scaling to fill result in the short side to be kept,
		// but the long side needs to be resized
		int width  = image.getWidth(null);
		int height = image.getHeight(null);
		if(width < height){
			float scaleFactor = Integer.valueOf(containerDI.width).floatValue() / width;
			width = containerDI.width;
			height = (int) (height * scaleFactor);
		}else{
			float scaleFactor = Integer.valueOf(containerDI.height).floatValue() / height;
			height = containerDI.height;
			width = (int) (width * scaleFactor);
		}
		Dimension     finalDI    = new Dimension(width, height);
		BufferedImage finalImage = new BufferedImage(containerDI.width, containerDI.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g          = finalImage.createGraphics();
		Point2D       point      = Alignment.CENTER.getVertex(false, new Rectangle(new Point(0, 0), containerDI), new Rectangle(finalDI));
		GH.draw(g, image.getScaledInstance(finalDI.width, finalDI.height, Image.SCALE_SMOOTH), point, null);
		g.dispose();
		finalImage.flush();
		return finalImage;
	}
	
	private static BufferedImage stretchToFill(Image image, Dimension containerDI){
		BufferedImage finalImage = new BufferedImage(containerDI.width, containerDI.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D    g          = finalImage.createGraphics();
		GH.draw(g, image.getScaledInstance(containerDI.width, containerDI.height, Image.SCALE_SMOOTH), new Point(0, 0), null);
		g.dispose();
		finalImage.flush();
		return finalImage;
	}
}
