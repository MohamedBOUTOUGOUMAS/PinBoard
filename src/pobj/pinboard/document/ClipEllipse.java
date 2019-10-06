package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip implements Clip {

	public ClipEllipse(double left, double top, double right, double bottom,Color color) {
		super(left, top, right, bottom, color);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		double width = this.getRight() - this.getLeft();
		double height = this.getBottom() - this.getTop();
		Color c  = getColor();
		ctx.setFill(c);
		ctx.fillOval(getLeft(), getTop(), width, height);
		//ctx.setStroke(c);
		ctx.strokeOval(getLeft(), getTop(),width,height);
		
	}

	@Override
	public Clip copy() {
		ClipEllipse o = new ClipEllipse(getLeft(), getTop(), getRight(), getBottom(), getColor());
		return o;
	}

	public boolean isSelected(double x, double y) {
		double cx = (getLeft() + getRight())/2;
		double cy = (getTop() + getBottom())/2;
		double rx = (getLeft() - getRight())/2;
		double ry = (getTop() - getBottom())/2;
		
		double X = ((x - cx)/rx) * ((x - cx)/rx);
		double Y = ((y-cy)/ry) * ((y-cy)/ry);
		
		if((X + Y) <= 1){
			return true;
		}
		return false;
	}
	
}
