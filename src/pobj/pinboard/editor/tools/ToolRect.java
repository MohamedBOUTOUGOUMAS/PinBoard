package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolRect implements Tool {
	private double init_x;
	private double init_y;
	private ClipRect rect = new ClipRect(0, 0, 0, 0, Color.BLACK);
	
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
		rect = new ClipRect(init_x, init_y, init_x, init_y, c);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		double min_x = Math.min(init_x, e.getX());
		double max_x = init_x + e.getX() - min_x;
		double min_y = Math.min(init_y, e.getY());
		double max_y = init_y + e.getY() - min_y;
		rect.setGeometry(min_x, min_y, max_x, max_y);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		//i.getBoard().addClip(rect);
		CommandAdd cmd = new CommandAdd(i, rect);
		cmd.execute();
		i.getUndoStack().addCommand(cmd);
		i.undoRedoChanged();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		double width = (rect.getRight() - rect.getLeft());
		double height = (rect.getBottom() - rect.getTop());
		i.getBoard().draw(gc);
		gc.setFill(i.getBoard().getColor());
		gc.strokeRect(rect.getLeft(), rect.getTop(),width,height);
	}

	@Override
	public String getName(EditorInterface editor) {
		return this.getClass().getSimpleName();
	}

}
