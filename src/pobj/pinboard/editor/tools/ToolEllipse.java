package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolEllipse implements Tool {

	private double init_x;
	private double init_y;
	private ClipEllipse ellip = new ClipEllipse(0, 0, 0, 0, Color.BLACK);
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		init_x = e.getX();
		init_y = e.getY();
		//Color c = new Color(Math.random(), Math.random(), Math.random(), Math.random());
		Color c = i.getCouleurCourante();
		if (c==null)
			c = new Color(Math.random(), Math.random(), Math.random(), Math.random());
		else if (c.equals(new Color(0,0,0,0)))
			c = new Color(Math.random(), Math.random(), Math.random(), Math.random());
		ellip = new ClipEllipse(init_x, init_y, init_x, init_y, c);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		double min_x = Math.min(init_x, e.getX());
		double max_x = init_x + e.getX() - min_x;
		double min_y = Math.min(init_y, e.getY());
		double max_y = init_y + e.getY() - min_y;
		ellip.setGeometry(min_x, min_y, max_x, max_y);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		//i.getBoard().addClip(ellip);
		CommandAdd cmd = new CommandAdd(i, ellip);
		cmd.execute();
		i.getUndoStack().addCommand(cmd);
		i.undoRedoChanged();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		double width = (ellip.getRight() - ellip.getLeft());
		double height = (ellip.getBottom() - ellip.getTop());
		i.getBoard().draw(gc);
		gc.setFill(i.getBoard().getColor());
		gc.strokeOval(ellip.getLeft(), ellip.getTop(),width,height);
	}


	@Override
	public String getName(EditorInterface editor) {
		return this.getClass().getSimpleName();
	}

}
