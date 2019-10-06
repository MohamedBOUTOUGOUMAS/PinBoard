package pobj.pinboard.document;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class OurColor implements Serializable {
	
	private double red;
	private double green;
	private double blue;
	
	
	public OurColor(double r, double g, double b){
		red =r;
		green = g;
		blue = b;
	}
	
	public double getRed() {
		return red;
	}
	public void setRed(double red) {
		this.red = red;
	}
	public double getGreen() {
		return green;
	}
	public void setGreen(double green) {
		this.green = green;
	}
	public double getBlue() {
		return blue;
	}
	public void setBlue(double blue) {
		this.blue = blue;
	}
	
	public static OurColor colorToOurColor(Color color){
		return new OurColor(color.getRed(), color.getGreen(), color.getBlue());
	}
	public static Color ourColorToColor(OurColor c){
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), 1.0);
				
	}

}
