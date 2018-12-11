package homelet.utils;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolBox{
	
	private ToolBox(){}
	
	/**
	 * Determine the Position of the value which between two peaks
	 * <br>
	 * if know the two Comparable's large, use {@link ToolBox#betweenPeaks(Comparable, Comparable, Comparable)}
	 *
	 * @param <E>     the type parameter
	 * @param value   value that needs to be compared
	 * @param peakOne one value
	 * @param peakTwo two value
	 * @return <ul> <li>-2 value is smaller than minimum</li> <li>-1 value is equals than minimum</li> <li>0 value in range</li> <li>1 value is equals than max</li> <li>2 value is bigger than max</li> </ul>
	 * @author HomeletWei
	 */
	public static <E extends Comparable<E>> int between(E value, E peakOne, E peakTwo){
		return betweenPeaks(value, peakOne.compareTo(peakTwo) >= 0 ? peakOne : peakTwo, peakTwo.compareTo(peakOne) <= 0 ? peakTwo : peakOne);
	}
	
	/**
	 * Determine the Position of the value which between two peaks
	 *
	 * @param <E>     the type parameter
	 * @param value   value that needs to be compared
	 * @param maxPeak max peak
	 * @param minPeak minimum peak
	 * @return <ul> <li>-2 value is smaller than minimum peak</li> <li>-1 value is equals than minimum peak</li> <li>0 value in range</li> <li>1 value is equals than max peak</li> <li>2 value is bigger than max peak</li> </ul>
	 * @author HomeletWei
	 */
	public static <E extends Comparable<E>> int betweenPeaks(E value, E maxPeak, E minPeak){
		if(value.compareTo(minPeak) < 0){
			return -2;
		}else if(value.compareTo(minPeak) == 0){
			return -1;
		}else{
			if(value.compareTo(maxPeak) > 0){
				return 2;
			}else if(value.compareTo(maxPeak) == 0){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	/**
	 * Pad string.
	 *
	 * @param i             the
	 * @param desiredLength the desired length
	 * @param padder        the padder
	 * @return the string
	 */
	public static String padString(String i, int desiredLength, char padder, boolean fromStart){
		if(i.length() >= desiredLength)
			return i;
		StringBuilder builder = new StringBuilder();
		for(int adder = 0; adder < desiredLength - i.length(); adder++){
			builder.append(padder);
		}
		return (fromStart ? builder.append(i) : builder.insert(0, i)).toString();
	}
	
	/**
	 * Pad string.
	 *
	 * @param i             the
	 * @param desiredLength the desired length
	 * @param padder        the padder
	 * @return the string
	 */
	public static String padString(String i, int desiredLength, char padder){
		return padString(i, desiredLength, padder, true);
	}
	
	/**
	 * Index of int.
	 *
	 * @param strings the strings
	 * @param str     the str
	 * @return the int
	 */
	public static int indexOf(String[] strings, String str){
		if(strings == null || strings.length == 0 || str == null){
			return -1;
		}
		for(int index = 0; index < strings.length; index++){
			if(strings[index].contentEquals(str)){
				return index;
			}
		}
		return -1;
	}
	
	/**
	 * Get mouse location point.
	 *
	 * @param component the component
	 * @return the point
	 */
	public static Point getMouseLocation(Component component){
		Point p = MouseInfo.getPointerInfo().getLocation();
		if(component == null)
			return p;
		Point frameP = component.getLocationOnScreen();
		p.translate(-frameP.x, -frameP.y);
		return p;
	}
	
	/**
	 * set the component's preferred size
	 * <br>
	 * <br>
	 * <b>equivalent to setSize(c, new Dimension(width, height));</b>
	 *
	 * @param c      the c
	 * @param width  the width
	 * @param height the height
	 * @author HomeletWei
	 * @see ToolBox#setPreferredSize(Component, Dimension) Utility#setPreferredSize(Component, Dimension)Utility#setPreferredSize(Component, Dimension)
	 */
	public static void setPreferredSize(Component c, int width, int height){
		setPreferredSize(c, new Dimension(width, height));
	}
	
	/**
	 * set the component's preferred Size
	 *
	 * @param c the c
	 * @param d the d
	 * @author HomeletWei
	 */
	public static void setPreferredSize(Component c, Dimension d){
		c.setPreferredSize(d);
		c.setMaximumSize(d);
		c.setMinimumSize(d);
	}
	
	/**
	 * get the orientation of the DI
	 *
	 * @param di the di
	 * @return the orientation
	 * @author HomeletWei
	 */
	public static Orientation getOrientation(Dimension di){
		if(di.width > di.height){
			return Orientation.HORIZONTAL;
		}else if(di.width < di.height){
			return Orientation.VERTICAL;
		}else{
			return Orientation.EQUILATERAL;
		}
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, Object... objects){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < objects.length; index++){
			builder.append(objects[index]);
			if(index + 1 < objects.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, int[] ints){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < ints.length; index++){
			builder.append(ints[index]);
			if(index + 1 < ints.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, short[] shorts){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < shorts.length; index++){
			builder.append(shorts[index]);
			if(index + 1 < shorts.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, long[] longs){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < longs.length; index++){
			builder.append(longs[index]);
			if(index + 1 < longs.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, float[] floats){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < floats.length; index++){
			builder.append(floats[index]);
			if(index + 1 < floats.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, double[] doubles){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < doubles.length; index++){
			builder.append(doubles[index]);
			if(index + 1 < doubles.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, char[] chars){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < chars.length; index++){
			builder.append(chars[index]);
			if(index + 1 < chars.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	public static StringBuilder createString(String prepend, String separator, String endLine, boolean[] objects){
		StringBuilder builder = new StringBuilder();
		if(prepend != null)
			builder.append(prepend);
		for(int index = 0; index < objects.length; index++){
			builder.append(objects[index]);
			if(index + 1 < objects.length && separator != null)
				builder.append(separator);
		}
		if(endLine != null)
			builder.append(endLine);
		return builder;
	}
	
	/**
	 * print an array into a System.out, with Addition in the front, with toString as String converter
	 *
	 * @param <E>      the type parameter
	 * @param addition the addtion String added in the front
	 * @param array    the array of object
	 * @author HomeletWei
	 */
	public static <E> void printOutArray(String addition, E[] array){
		printOutArray(addition, array, Object::toString);
	}
	
	/**
	 * print a centain array into a System.out sepcific, with Addition in the front
	 *
	 * @param <E>       the type parameter
	 * @param addition  the addtion String added in the front
	 * @param array     the array of object
	 * @param converter the converter of the E
	 * @author HomeletWei
	 */
	public static <E> void printOutArray(String addition, E[] array, ToString<E> converter){
		printOutArray(System.out, addition, array, false, converter);
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param <E>         the type parameter
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @param array       the array of object
	 * @param ignoreNull  should null be ignored
	 * @param converter   the converter of the E
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static <E> void printOutArray(PrintStream printStream, String addition, E[] array, boolean ignoreNull, ToString<E> converter){
		if(array == null || array.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(array.length - 1);
		for(E e : array){
			if(e == null && ignoreNull){
				return;
			}
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + ((e == null) ? "NULL" : converter.string(e)));
		}
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static void printOutArray(PrintStream printStream, String addition, short[] shorts){
		if(shorts == null || shorts.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(shorts.length - 1);
		for(short e : shorts){
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + e);
		}
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static void printOutArray(PrintStream printStream, String addition, int[] ints){
		if(ints == null || ints.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(ints.length - 1);
		for(int e : ints){
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + e);
		}
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static void printOutArray(PrintStream printStream, String addition, long[] longs){
		if(longs == null || longs.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(longs.length - 1);
		for(long e : longs){
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + e);
		}
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static void printOutArray(PrintStream printStream, String addition, float[] floats){
		if(floats == null || floats.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(floats.length - 1);
		for(float e : floats){
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + e);
		}
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static void printOutArray(PrintStream printStream, String addition, double[] doubles){
		if(doubles == null || doubles.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(doubles.length - 1);
		for(double e : doubles){
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + e);
		}
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static void printOutArray(PrintStream printStream, String addition, boolean[] booleans){
		if(booleans == null || booleans.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(booleans.length - 1);
		for(boolean e : booleans){
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + e);
		}
	}
	
	/**
	 * print a certain array into a PrintStream specific, with Addition in the front
	 *
	 * @param printStream the print Steam that specific
	 * @param addition    the addition String added in the front
	 * @author HomeletWei
	 * @see java.io.PrintStream
	 */
	public static void printOutArray(PrintStream printStream, String addition, char[] chars){
		if(chars == null || chars.length == 0){
			return;
		}
		int index     = 0;
		int maxLength = lengthOf(chars.length - 1);
		for(char e : chars){
			printStream.println(padString(String.valueOf(index++), maxLength, ' ') + "|" + addition + e);
		}
	}
	
	/**
	 * Length of int.
	 *
	 * @param integer the integer
	 * @return the int
	 */
	public static int lengthOf(int integer){
		return String.valueOf(integer).length();
	}
	
	public static double random(double min, double max){
		if(max < min)
			throw new IllegalArgumentException("Max is smaller than min");
		return Math.random() * (max - min) + min;
	}
	
	/**
	 * TODO
	 *
	 * @return string
	 * @author HomeletWei
	 */
	public static String getDateInfo(){
		return getDateInfo("yyyy/MM/dd hh:mm:ss");
	}
	
	/**
	 * <pre>
	 * G 	Era 			designator Text 		AD
	 * y 	Year 			Year 				1996; 96
	 * Y 	Week 			year 				Year 2009; 09
	 * M 	Month in year 		(context sensitive) Month 	July; Jul; 07
	 * L 	Month in year 		(standalone form) Month 	July; Jul; 07
	 * w 	Week in year 		Number 				27
	 * W 	Week in month 		Number 				2
	 * D 	Day in year 		Number 				189
	 * d 	Day in month 		Number 				10
	 * F 	Day of week in month 	Number 				2
	 * E 	Day name in week 	Text 				Tuesday; Tue
	 * u 	Day number of week 	(1 = Monday, ..., 7 = Sunday) 	1
	 * a 	Am/pm marker 		Text 				PM
	 * H 	Hour in day (0-23) 	Number				0
	 * k 	Hour in day (1-24) 	Number 				24
	 * K 	Hour in am/pm (0-11) 	Number 				0
	 * h 	Hour in am/pm (1-12) 	Number 				12
	 * m 	Minute in hour 		Number 				30
	 * s 	Second in minute 	Number 				55
	 * S 	Millisecond 		Number 				978
	 * z 	Time zone General time zone Pacific Standard Time; PST; GMT-08:00
	 * Z 	Time zone RFC 822 	time zone 			-0800
	 * X 	Time zone ISO 8601 	time zone 			-08; -0800; -08:00
	 * </pre>
	 *
	 * @param format the format
	 * @return string
	 * @author HomeletWei
	 */
	public static String getDateInfo(String format){
		Calendar         currentDate = Calendar.getInstance();
		SimpleDateFormat formatter   = new SimpleDateFormat(format);
		return formatter.format(currentDate.getTime());
	}
	
	public static String readTextFile(File file){
		if(!file.exists() || !file.canRead()){
			return null;
		}
		StringBuilder builder = new StringBuilder();
		try(FileReader reader = new FileReader(file)){
			int buffer;
			while(true){
				buffer = reader.read();
				if(buffer != -1){
					builder.append((char) buffer);
				}else{
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	public static String readTextFile(String file){
		return readTextFile(new File(file));
	}
	
	public static String readTextResource(URL resource){
		return readTextFile(resource.getFile());
	}
	
	public static String findAll(String str, String regex, int groupIndex){
		Matcher matcher = Pattern.compile(regex).matcher(str);
		if(matcher.find())
			return matcher.group(groupIndex);
		else
			return null;
	}
	
	/**
	 * The enum Orientation.
	 */
	public enum Orientation{
		/**
		 * Horizontal orientation.
		 */
		HORIZONTAL,
		/**
		 * Vertical orientation.
		 */
		VERTICAL,
		/**
		 * Equilateral orientation.
		 */
		EQUILATERAL;
	}
	
	/**
	 * The interface String convert.
	 *
	 * @param <E> the type parameter
	 */
	public interface ToString<E>{
		
		String string(E e);
	}
}
