package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	private static Clipboard clipboard = new Clipboard();
	
	private List<Clip> copies;
	
	private List<ClipboardListener> observers;
	
	
	private Clipboard() {
		copies = new ArrayList<Clip>();
		observers = new ArrayList<ClipboardListener>();
	}
	
	public static Clipboard getInstance() {
		return clipboard;
	}
	
	public void clear() {
		copies.clear();
		notifier();
	}
	
	public boolean isEmpty() {
		return copies.isEmpty();
	}
	
	public void copyToClipboard(List<Clip> clips) {
		for(Clip c : clips) {
			copies.add(c.copy());
		}
		notifier();
	}
	
	public List<Clip> copyFromClipboard(){
		List<Clip> copie = new ArrayList<>();
		for(Clip c : copies) {
			copie.add(c.copy());
		}
		return copie;
		
	}
	
	public void addListener(ClipboardListener listener){
		observers.add(listener);
	}
	
	public void	removeListener(ClipboardListener listener){
		observers.remove(listener);
	}
	
	public void notifier(){
		for (ClipboardListener obs: observers){
			obs.clipboardChanged();
		}
	}
	
}
