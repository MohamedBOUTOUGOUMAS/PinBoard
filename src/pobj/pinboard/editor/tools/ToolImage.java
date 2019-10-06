package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolImage implements Tool {
	
	private ClipImage image;
	
	public void pressButton(File file, MouseEvent e) {
	
	}

	public ToolImage(File filename) {
		image = new ClipImage(0, 0, filename);
	}
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		//init_x = e.getX();
		//init_y = e.getY();
		image.setGeometry(e.getX(), e.getY(), image.getRight(), image.getTop());
		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		
		//image.move(e.getX()- image.getLeft(), e.getY() - image.getTop());
		CommandMove cmd = new CommandMove(i,image,e.getX()- image.getLeft(), e.getY() - image.getTop());
		cmd.execute();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		//i.getBoard().addClip(image);
		CommandAdd cmd = new CommandAdd(i, image);
		cmd.execute();
		i.getUndoStack().addCommand(cmd);
		i.undoRedoChanged();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
		image.draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return this.getClass().getSimpleName();
	}

}
