package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup extends AbstractClip implements Composite {
	
	private List<Clip> groupe = new ArrayList<Clip>();

//	public ClipGroup(double left, double top, double right, double bottom, Color color) {
//		super(left, top, right, bottom, color);
//		groupe = new ArrayList<Clip>();
//	}
	
	public ClipGroup(){
		super(0,0,0,0, Color.BLACK);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		for (Clip c : groupe){
			c.draw(ctx);
		}
	}
	
	@Override
	public void move(double x, double y) {
		for(Clip clip : groupe) {
			clip.move(x, y);
		}
		setDimension();
	}

	@Override
	public Clip copy() {
		ClipGroup copies = new ClipGroup();
		for (Clip c : groupe){
			copies.addClip(c.copy());
		}
		return copies;
	}

	@Override
	public List<Clip> getClips() {
		return groupe;
	}

	@Override
	public void addClip(Clip clip) {
		groupe.add(clip);
		setDimension();
	}
	public void addClip(List<Clip> clips) {
		for(Clip clip: clips) {
			addClip(clip);
		}
		setDimension();
	}
	

	@Override
	public void removeClip(Clip clip) {
		groupe.remove(clip);
		setDimension();	
	}
	public void removeClip(List<Clip> clips) {
		for(Clip clip: clips) {
			removeClip(clip);
		}
		setDimension();
	}
	
	private void setDimension(){
		/* Cas d'erreur */
		if (groupe.isEmpty()) 
			return;
		// fin cas d'erreur
		
		double minLeft = groupe.get(0).getLeft();
		double minTop = groupe.get(0).getTop();
		double maxRight = groupe.get(0).getRight();
		double maxButtom = groupe.get(0).getBottom();
		for(Clip c : groupe) {
			minLeft = Math.min(minLeft, c.getLeft());
			minTop = Math.min(minTop, c.getTop());
			maxRight = Math.max(maxRight, c.getRight());
			maxButtom = Math.max(maxButtom, c.getBottom());
		}
		
		setGeometry(
				minLeft, 
				minTop, 
				maxRight, 
				maxButtom
				);
		
	}

		
}
