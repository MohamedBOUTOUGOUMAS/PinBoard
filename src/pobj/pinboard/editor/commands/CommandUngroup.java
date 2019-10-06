package pobj.pinboard.editor.commands;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command {

	
	private EditorInterface editor;
	private ClipGroup group;
	
	
	public CommandUngroup(EditorInterface editor, ClipGroup group) {
		this.editor = editor;
		this.group = group;
	}

	@Override
	public void execute() {
		if(editor.getBoard().getContents().contains(group)){
			editor.getBoard().removeClip(group);
			for(Clip c : group.getClips()){
				editor.getBoard().addClip(c);
			}
		}
	}

	@Override
	//*********************************************************
	public void undo() {
		int cp = 0;
		for(Clip c : editor.getBoard().getContents()){
			if(group.getClips().contains(c)){
				cp++;
			}
		}
		if(cp == group.getClips().size()){
			editor.getBoard().getContents().removeAll(group.getClips());
		}
		editor.getBoard().addClip(group);
	}

}
