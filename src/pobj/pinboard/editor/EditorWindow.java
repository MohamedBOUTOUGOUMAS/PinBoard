package pobj.pinboard.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditorWindow extends javafx.application.Application implements EditorInterface, ClipboardListener {

	private Stage stage;
	private Board board;
	private Tool tool = new ToolRect();
	private Selection selection = new Selection();
	
	private MenuItem Paste;
	private Color couleurCourante = new Color(0,0,0,0);
	
	private CommandStack undoStack = new CommandStack();
	
	private MenuItem Undo;
	private MenuItem Redo;
	
	private static int numeroFile = 0;
	
	
	
	
	public CommandStack getUndoStack(){
		return undoStack;
	}
	
	public Color getCouleurCourante() {
		return couleurCourante;
	}

	public Stage getStage() {
		return stage;
	}
	
	public EditorWindow(Stage stage) throws Exception {
		this.stage = stage;
		this.board = new Board();
		this.stage.setTitle("Titre_au_choix");
		VBox vbox = new VBox();
		MenuItem New = new MenuItem("New");
		MenuItem Close = new MenuItem("Close");
		MenuItem Open = new MenuItem("Open");
		MenuItem Save = new MenuItem("Save");
		Menu m1 = new Menu("File");
		m1.getItems().addAll(New, Open, Save, Close);
		Menu m2 = new Menu("Edit");
		Menu m3 = new Menu("Tools");
		MenuBar menubar = new MenuBar(m1,m2,m3);
		Button b1 = new Button("Box");
		Button b2 = new Button("Ellipse");
		Button b3 = new Button("Img");
		Button b4 = new Button("Select");
		ToolBar toolbar = new ToolBar(b1,b2,b3,b4);
		Button c1 = new Button("Rouge", new Rectangle(10, 20, Color.RED));
		Button c2 = new Button("Bleu", new Rectangle(10, 20, Color.BLUE));
		Button c3 = new Button("vert", new Rectangle(10, 20, Color.GREEN));
		Button c4 = new Button("jaune", new Rectangle(10, 20, Color.YELLOW));
		Button c5 = new Button("gris", new Rectangle(10, 20, Color.GRAY));
		Button c6 = new Button("noire", new Rectangle(10, 20, Color.BLACK));
		ToolBar toolbar2 = new ToolBar(c1, c2, c3, c4, c5, c6);
		
		Separator separator = new Separator();
		Label label = new Label(tool.getName(getMe()));
		Canvas canvas = new Canvas(600, 735);
		board.draw(canvas.getGraphicsContext2D());
		vbox.getChildren().addAll(menubar,toolbar, toolbar2, canvas,separator,label);
		this.stage.setScene(new javafx.scene.Scene(vbox));
		start(this.stage);
		
		MenuItem Copy = new MenuItem("Copy");
		Paste = new MenuItem("Paste");
		clipboardChanged();
		MenuItem Delete = new MenuItem("Delete");
		m2.getItems().addAll(Copy, Paste, Delete);
		
		MenuItem Clear = new MenuItem("Clear");
		MenuItem Group = new MenuItem("Group");
		MenuItem Ungroup= new MenuItem("Ungroup");
		m2.getItems().addAll(Group, Ungroup);
		
		Undo = new MenuItem("Undo");
		Redo = new MenuItem("Redo");
		m2.getItems().addAll(Undo, Redo);
		
		m2.getItems().addAll(Clear);
		
		undoRedoChanged();
		
		
		//Gestion evenements
		
		
		Save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				try {
					FileOutputStream fos;
					fos = new FileOutputStream("Dessin_"+new Date().getTime()/*Math.random()*/+".txt");
				    ObjectOutputStream oos = new ObjectOutputStream(fos);
				    oos.writeObject(board);
				    
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			});
		
		Open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choisir un fichier");
				File selectedFile = fileChooser.showOpenDialog(getStage());
				
				FileInputStream fis;
				try {
					fis = new FileInputStream(selectedFile);
					ObjectInputStream ois = new ObjectInputStream(fis);
				    board = (Board) ois.readObject();
				    for (Clip clip : board.getContents()){
				    	if (clip instanceof ClipImage){
				    		Image image = new Image("file://" + ((ClipImage) clip).getFileName());
				    		((ClipImage) clip).setImage(image);
				    	}
				    }
				    board.draw(canvas.getGraphicsContext2D());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
				}
			});
		
		
		Undo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				undoStack.undo();
				board.draw(canvas.getGraphicsContext2D());
				undoRedoChanged();
				}
			});
		
		Redo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				undoStack.redo();
				board.draw(canvas.getGraphicsContext2D());
				undoRedoChanged();
				}
			});
		
		Group.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				/*
				board.removeClip(selection.getContents());
				ClipGroup groupe = new ClipGroup();
				groupe.addClip(selection.getContents());
				board.addClip(groupe);
				*/
				CommandGroup cmd = new CommandGroup(getMe(), selection.getContents());
				cmd.execute();
				undoStack.addCommand(cmd);
				undoRedoChanged();
				board.draw(canvas.getGraphicsContext2D());
				}
			}); 
		
		Ungroup.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				/*
				for (Clip clip : selection.getContents()) {
					if (clip instanceof ClipGroup) {
						ClipGroup cg = (ClipGroup)clip;
						board.removeClip(cg);
						board.addClip(cg.getClips());
					}
				}
				*/
				for (Clip c : selection.getContents()) {
					if (c instanceof ClipGroup) {
						CommandUngroup cmd = new CommandUngroup(getMe(),(ClipGroup)c);
						cmd.execute();
						undoStack.addCommand(cmd);
						undoRedoChanged();
					}	
				}
				board.draw(canvas.getGraphicsContext2D());
				}
			}); 
		
		Clear.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				Clipboard.getInstance().clear();
				}
			}); 
		
		c1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Rectangle r = (Rectangle)c1.getGraphic();
					couleurCourante = (Color)r.getFill();
			}
		}); 
		c2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Rectangle r = (Rectangle)c2.getGraphic();
					couleurCourante = (Color)r.getFill();
			}
		}); 
		c3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Rectangle r = (Rectangle)c3.getGraphic();
					couleurCourante = (Color)r.getFill();
			}
		}); 
		c4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Rectangle r = (Rectangle)c4.getGraphic();
					couleurCourante = (Color)r.getFill();
			}
		}); 
		c5.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Rectangle r = (Rectangle)c5.getGraphic();
					couleurCourante = (Color)r.getFill();
			}
		}); 
		c6.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Rectangle r = (Rectangle)c6.getGraphic();
					couleurCourante = (Color)r.getFill();
			}
		}); 
		
		Copy.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				Clipboard.getInstance().copyToClipboard(selection.getContents());
				}
			}); 
		
		Paste.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				CommandAdd cmd = new CommandAdd(getMe(), Clipboard.getInstance().copyFromClipboard());
				cmd.execute();
				undoStack.addCommand(cmd);
				board.draw(canvas.getGraphicsContext2D());			
				}
			}); 
		
		Delete.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				board.removeClip(selection.getContents());
				board.draw(canvas.getGraphicsContext2D());
				}
			}); 
		
		
		New.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				try {
					Clipboard.getInstance().addListener(new EditorWindow(new Stage()));
					} 
				catch (Exception e1) {
					e1.printStackTrace();
					} 
				}
			}); 
		Close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				Clipboard.getInstance().removeListener((ClipboardListener) getMe());
				getStage().close();}
			});
		b1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				tool = new ToolRect();
				label.setText(tool.getName(getMe()));
			}
		}); 
		
		b2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				tool = new ToolEllipse();
				label.setText(tool.getName(getMe()));
			}
		}); 
		
		b3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choisir une image");
				File selectedFile = fileChooser.showOpenDialog(getStage());
				tool = new ToolImage(selectedFile);
				label.setText(tool.getName(getMe()));
			}
		}); 
		
		b4.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				tool = new ToolSelection(); 
				label.setText(tool.getName(getMe()));
			}
		}); 
		
		
		
		
		
		canvas.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				tool.press(getMe(),e);
				tool.drawFeedback(getMe(), canvas.getGraphicsContext2D());
			}
		});
		
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				tool.drag(getMe(),e);
				tool.drawFeedback(getMe(), canvas.getGraphicsContext2D());
			}
		});
		
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				tool.release(getMe(),e);
				tool.drawFeedback(getMe(), canvas.getGraphicsContext2D());
			}
		});
		
		
		Clipboard.getInstance().addListener((ClipboardListener) getMe());
		
	}


	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
	}

	@Override
	public Board getBoard() {
		return this.board;
	}
	
	public EditorInterface getMe() {
		return this;
	}

	@Override
	public Selection getSelection() {
		return selection;
	}

	@Override
	public void clipboardChanged() {
		if(Clipboard.getInstance().isEmpty()){
			Paste.setDisable(true);
		}
		else {
			Paste.setDisable(false);
		}
	}

	
	public void undoRedoChanged() {
		if(undoStack.isUndoEmpty()){
			Undo.setDisable(true);
		}else{
			Undo.setDisable(false);
		}
		if(undoStack.isRedoEmpty()){
			Redo.setDisable(true);
		}else{
			Redo.setDisable(false);
		}
	}

}
