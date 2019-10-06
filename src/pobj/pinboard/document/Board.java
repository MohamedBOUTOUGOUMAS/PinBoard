package pobj.pinboard.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board implements Clip, Serializable{
	
	private double left;
	private double right;
	private double top;
	private double bottom;
	private OurColor color = new OurColor(0, 0, 0);
	private List<Clip> contents;

	public Board(){
		contents = new ArrayList<Clip>();
	}
	
	public List<Clip> getContents(){
		return contents;
	}
	
	public void addClip(Clip clip){
		//if  (left<=clip.getLeft() && clip.getRight()<=right && top<=clip.getTop() && clip.getBottom()<=bottom){
			contents.add(clip);
		//}
	}
	
	public void	addClip(List<Clip> clip){
		for (Clip c : clip){
			this.addClip(c);
		}
	}
	
	public void	removeClip(Clip clip){
		contents.remove(clip);
	}
	
	public void	removeClip(List<Clip> clip){
		contents.removeAll(clip);
	}
	
	@Override
	public void	draw(GraphicsContext gc){
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		gc.setStroke(Color.BLACK);
		gc.strokeRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		for(Clip clip : contents){
			clip.draw(gc);
		}
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
		return bottom;
	}

	@Override
	public double getRight() {
		return right;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	@Override
	public void move(double x, double y) {
		left += x;
		right += x;
		top += y;
		bottom += y;
	}

	@Override
	public boolean isSelected(double x, double y) {
		if ( left <= x && x<=right && top<=y && y<=bottom ){
			return true;
		}
		return false;
	}

	@Override
	public void setColor(Color c) {
		color = OurColor.colorToOurColor(c);
	}

	@Override
	public Color getColor() {
		return OurColor.ourColorToColor(color);
	}

	@Override
	public Clip copy() {
		Board boardCopy = new Board();
		//boardCopy.addClip(contents); *******************************
		for (Clip clip : contents) {
			boardCopy.addClip(clip.copy());
		}
		boardCopy.setGeometry(left, top, right, bottom);
		boardCopy.setColor(OurColor.ourColorToColor(color));
		return boardCopy;
	}

}
