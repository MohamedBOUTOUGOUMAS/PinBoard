package pobj.pinboard.document;

import java.io.File;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage implements Clip, Serializable {
	private double left;
	private double top;
	private double right;
	private double bottom;
	private File filename;
	private transient Image image;
	
	public ClipImage(double left, double top, File filename){
		this.left = left;
		this.top = top;
		this.filename = filename;
		this.image = new Image("file://" + filename.getAbsolutePath());
		
	}

	public void setImage(Image image){
		this.image = image;
	}
	
	public File getFileName(){
		return this.filename;
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(image, getLeft(), getTop());
		
	}

	@Override
	public double getTop() {
		return top;
	}

	@Override
	public double getLeft() {
		return left;
	}

	@Override
	public double getBottom() {
		return top + image.getHeight();
	}

	@Override
	public double getRight() {
		return left + image.getWidth();
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.right = left + image.getWidth();
		this.top = top;
		this.bottom = top + image.getHeight();
	}

	@Override
	public void move(double x, double y) {
		left += x;
		top += y;
	}

	@Override
	public boolean isSelected(double x, double y) {
		if (getLeft()<=x && x<=getRight() && getTop()<=y && y<=getBottom() ){
			return true;
		}
		return false;
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clip copy() {
		return new ClipImage(left, top, filename);
	}
	

}
