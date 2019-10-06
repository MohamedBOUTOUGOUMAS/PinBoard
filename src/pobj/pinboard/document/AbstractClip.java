package pobj.pinboard.document;

import java.io.Serializable;

import javafx.scene.paint.Color;

public abstract class AbstractClip implements Serializable {

	private double left;
	private double right;
	private double top;
	private double bottom;
	private OurColor color;
	
	
	public AbstractClip(double left, double top, double right, double bottom, Color color) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.color = OurColor.colorToOurColor(color);
	}
	
	public double getTop() {
		return top;
	}

	
	public double getLeft() {
		return left;
	}

	
	public double getBottom() {
		return bottom;
	}

	
	public double getRight() {
		return right;
	}

	
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	
	public void move(double x, double y) {
		left += x;
		right += x;
		top += y;
		bottom += y;
	}

	
	public boolean isSelected(double x, double y) {
		if ( left <= x && x<=right && top<=y && y<=bottom){
			return true;
		}
		return false;
	}

	
	public void setColor(Color c) {
		color = OurColor.colorToOurColor(c);
	}

	
	public Color getColor() {
		return OurColor.ourColorToColor(color);
	}
	
	

}
