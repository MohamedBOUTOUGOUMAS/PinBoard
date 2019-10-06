package pobj.pinboard.editor;

import java.util.Stack;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
	private Stack<Command> undo = new Stack<Command>();
	private Stack<Command> redo = new Stack<Command>();
	
	public boolean isUndoEmpty(){
		return undo.empty();
	}
	
	public boolean isRedoEmpty(){
		return redo.empty();
	}
	
	public void addCommand(Command cmd){
		undo.push(cmd);
		redo.clear();
	}
	
	public void	undo(){
		if (undo.empty()){// pour eviter une exception meme si on grise l'option undo
			return;
		}
		Command cmd = undo.pop();
		cmd.undo();
		redo.push(cmd);
	}
	
	public void	redo(){
		if (redo.empty()){// pour eviter une exception meme si on grise l'option redo
			return;
		}
		Command cmd = redo.pop();
		cmd.execute();
		undo.push(cmd);
	}
	
}
