package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command {

	private EditorInterface editor;
	private List<Clip> groupContent = new ArrayList<Clip>();
	
	public CommandGroup(EditorInterface editor, List<Clip> groupContent){
		this.editor = editor;
		this.groupContent.addAll(groupContent);
	}
	
	
	@Override
	public void execute() {
		ClipGroup g = new ClipGroup();
		g.addClip(groupContent);
		editor.getBoard().removeClip(groupContent);
		editor.getBoard().addClip((Clip)g);
	}

	@Override
	public void undo() {
		if(editor.getBoard().getContents().get(editor.getBoard().getContents().size()-1) instanceof ClipGroup){
			ClipGroup g = (ClipGroup) editor.getBoard().getContents().get(editor.getBoard().getContents().size()-1);
			editor.getBoard().removeClip(g);
			editor.getBoard().addClip(groupContent);
		}
	}

}
