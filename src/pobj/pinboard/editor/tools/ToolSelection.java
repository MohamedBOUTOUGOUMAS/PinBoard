package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.Selection;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolSelection implements Tool {

	private Selection selection;	
	double init_x;
	double init_y;
	
	double curr_x;
	double curr_y;
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		init_x = e.getX();
		init_y = e.getY();
		curr_x = init_x;
		curr_y = init_y;
		selection = i.getSelection();
		if(e.isShiftDown()){
			selection.toogleSelect(i.getBoard(), e.getX(), e.getY());
		}else{
			selection.select(i.getBoard(), e.getX(), e.getY());
		}
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
//		init_x = e.getX() - init_x;
//		init_y = e.getY() - init_y;
		for(Clip c : selection.getContents()){
			//c.move(e.getX() - init_x, e.getY() - init_y);
			CommandMove cmd = new CommandMove(i, c, e.getX() - curr_x, e.getY() - curr_y);
			cmd.execute();
			i.getUndoStack().addCommand(cmd);
			i.undoRedoChanged();
		}
		curr_x = e.getX();
		curr_y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {

	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return this.getClass().getSimpleName();
	}
}
