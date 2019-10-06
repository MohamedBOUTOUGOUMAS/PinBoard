package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command {

	private EditorInterface editor;
	private Clip clip;
	private double x;
	private double y;
	
	
	public CommandMove(EditorInterface editor, Clip clip, double x, double y) {
		this.editor = editor;
		this.clip = clip;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		clip.move(x, y);
	}

	@Override
	public void undo() {
		clip.move(-x, -y);
	}

}
