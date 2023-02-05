/**

	This code was written by
	Dr. Davide Ceresa, PhD
	IRCCS Ospedale Policlinico San Martino, 16132 Genova, Italy
	email: davide.ceresa91@gmail.com
	and
	Prof. Paolo Malatesta, PhD 
	IRCCS Ospedale Policlinico San Martino, 16132 Genova, Italy
	Department of Experimental Medicine (DIMES), University of Genova, 16132 Genova, Italy
	email: paolo.malatesta@unige.it
	The code is intended for academic purposes only
	
*/

import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.io.OpenDialog;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.*;
import ij.text.TextWindow;
import ij.io.Opener;
import java.awt.*;
import java.io.*;
import java.net.*;
import ij.text.*;
import ij.io.FileInfo;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/*

	This plugin inserts points from a timelapse analysis file obtained with MiTrack inside the original stack,
	coloring them in yellow.	
	Actually traced points are represented with a yellow disk.
	Prospectively traced points are dotted circles and dead cells are crossed circles.
	
*/

public class Put_MiTrackPoints implements PlugInFilter {
	
    ImagePlus img;
	
    public int setup(String arg, ImagePlus img) {
        this.img = img;
        return DOES_ALL;
	}
	
    public void run(ImageProcessor ip) {
		
		
        String loadtext = getContents();
		
        String[] lines = loadtext.split("\r\n");
		
		
        String[] words;
        int x = 0;
        int y = 0;
		int z=0;
		int real=0;
		int dead=0;
		
        ImageProcessor ipr = img.getProcessor();
        ImageStack istack = img.getStack();
        int slice = istack.getSize();
		
		SetLookupTable();
		
        ipr.setColor(new Color(255,255,0));
        ipr.setLineWidth(14);
		IJ.run("Line Width...", "line=5");
		
		
        for (int i = 1; i < lines.length; i++) {
			
            words = lines[i].split(",");
            x = Integer.parseInt(words[1]);
            y = Integer.parseInt(words[2]);
			z =Integer.parseInt(words[3]);
			real=Integer.parseInt(words[5]);
			dead=Integer.parseInt(words[6]);
			img.setSlice(z);
			if (dead==1) {
				ipr.drawOval(x - 5, y - 5, 10, 10);
				ipr.drawLine(x - 10, y - 10, x+10, y+20);
				ipr.drawLine(x - 10, y+20, x+10, y - 10);
				} else if(real!=0){
				ipr.fillOval(x - 10, y - 10, 20, 20);
				} else {
				ipr.drawOval(x - 1, y - 1, 2, 2);
				ipr.drawOval(x - 10, y - 10, 20, 20);
				
			}
			
			
            
			
			
		}
		img.setSlice(1);
        img.show();
		
		
		
		
		
	}
	
    static public String getContents() {
        //...checks on aFile are elided
        StringBuilder contents = new StringBuilder();
		
        String filename = getfilename();
        if (!filename.equals("")) {
            try {
                File aFile = new File(filename);
                //use buffering, reading one line at a time
                //FileReader always assumes default encoding is OK!
                BufferedReader input = new BufferedReader(new FileReader(aFile));
                try {
                    String line = null; //not declared within while loop
					/*
						* readLine is a bit quirky :
						* it returns the content of a line MINUS the newline.
						* it returns null only for the END of the stream.
						* it returns an empty String if two newlines appear in a row.
					*/
                    while ((line = input.readLine()) != null) {
                        contents.append(line);
                        contents.append(System.getProperty("line.separator"));
					}
					} finally {
                    input.close();
				}
				} catch (IOException ex) {
                ex.printStackTrace();
			}
			
            return contents.toString();
			} else {
            return "";
		}
	}
	
    public static String getfilename() {
		
        OpenDialog od = new OpenDialog("File di traccia", null);
        String directory = od.getDirectory();
        String name = od.getFileName();
		
        return directory + name;
		
	}
	
	void SetLookupTable(){
		ImagePlus implut = IJ.getImage();
		String lutName = "Lookup_Tables/MiTrack.lut";	
		IndexColorModel cm = null;
		
		try {
			InputStream is = getClass().getResourceAsStream(lutName);
			cm = LutLoader.open(is);
			} catch(IOException e) {
			String msg = e.getMessage();
			if(msg==null || msg.equals(""))
			msg = ""+e;
			IJ.error("Lookup Tables", msg);
			return;
		}
		ImageProcessor ip= implut.getChannelProcessor();
		if(implut.isComposite())
		((CompositeImage)implut).setChannelColorModel(cm);
		else
		ip.setColorModel(cm);
		implut.updateAndRepaintWindow();
	}
}

