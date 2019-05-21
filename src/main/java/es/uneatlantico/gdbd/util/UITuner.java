package es.uneatlantico.gdbd.util;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 * Various utilities for user interfaces
 * @author Alberto Gutiérrez Arroyo
 *
 */
public class UITuner {
	
	/**
	 * Auxiliary function to fix the broken icons on Windows 10 for JOptionPane. Oracle shall fix it on Java 1.9, already confirmed
	 */
	public static void fixBrokenIcons() {
		try
		{
		    String[][] icons =
		    {
		    	{"OptionPane.warningIcon",     "65581"},
		        {"OptionPane.questionIcon",    "65583"},
		        {"OptionPane.errorIcon",       "65585"},
		        {"OptionPane.informationIcon", "65587"}
		    };
		    //obtain a method for creating proper icons
		    Method getIconBits = Class.forName("sun.awt.shell.Win32ShellFolder2").getDeclaredMethod("getIconBits", new Class[]{long.class, int.class});
		    getIconBits.setAccessible(true);
		    //calculate scaling factor
		    double dpiScalingFactor = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
		    int icon32Size = (dpiScalingFactor == 1)?(32):((dpiScalingFactor == 1.25)?(40):((dpiScalingFactor == 1.5)?(45):((int) (32 * dpiScalingFactor))));
		    Object[] arguments = {null, icon32Size};
		    for (String[] s:icons)
		    {
		        if (UIManager.get(s[0]) instanceof ImageIcon)
		        {
		            arguments[0] = Long.valueOf(s[1]);
		            //this method is static, so the first argument can be null
		            int[] iconBits = (int[]) getIconBits.invoke(null, arguments);
		            if (iconBits != null)
		            {
		                //create an image from the obtained array
		                BufferedImage img = new BufferedImage(icon32Size, icon32Size, BufferedImage.TYPE_INT_ARGB);
		                img.setRGB(0, 0, icon32Size, icon32Size, iconBits, 0, icon32Size);
		                ImageIcon newIcon = new ImageIcon(img);
		                //override previous icon with the new one
		                UIManager.put(s[0], newIcon);
		            }
		        }
		    }
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	}
}
