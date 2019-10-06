package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {

	private List<Clip> selection;
	
	public Selection(){
		selection = new ArrayList<Clip>();
	}
	
	public void clear(){
		selection.clear();
	}
	
	public List<Clip> getContents(){
		return selection;
	}
	
	public void select(Board board,double x, double y){
		this.clear();
//		for (Clip c : board.getContents()){
		for(int i=board.getContents().size()-1; i>=0; i--){
			Clip c = board.getContents().get(i);
			if(c.isSelected(x, y)){
				selection.add(c);
				break;
			}
		}
	}
	
	public void toogleSelect(Board board , double x, double y){
//		for (Clip c : board.getContents()){
		for(int i=board.getContents().size()-1; i>=0; i--){
			Clip c = board.getContents().get(i);
			if(c.isSelected(x, y)){
				if(!selection.contains(c)){
					selection.add(c);
				}else{
					selection.remove(c);
				}
				break;
			}
		}
	}
	
	public void drawFeedback(GraphicsContext gc){
		for(Clip c : selection){
			gc.setStroke(Color.BLUE);
			gc.strokeRect(c.getLeft(), c.getTop(), c.getRight()-c.getLeft(), c.getBottom()-c.getTop());
			//c.draw(gc);
		}
	}
	
	
}
