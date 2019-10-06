package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect extends AbstractClip implements Clip {

	
	public ClipRect(double left, double top, double right, double bottom, Color color) {
		super(left,top,right,bottom, color);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		double width = this.getRight() - this.getLeft();
		double height = this.getBottom() - this.getTop();
		Color c  = getColor();	
		ctx.setFill(c);
		ctx.fillRect(getLeft(), getTop(), width, height);
		//ctsx.setStroke(c);
		ctx.strokeRect(getLeft(), getTop(), width, height);
	}

	@Override
	public Clip copy() {
		ClipRect o = new ClipRect(getLeft(), getTop(), getRight(), getBottom(), getColor());
		return o;
	}

}
