package launcher;

import controller.Controller;
import graphics.GraphicSync;

public class launcher {

	static Controller controller;
	static GraphicSync graphics;
	
	public static void main(String[] args) {
		//controller.initDisplay();

		controller = Controller.getInstance();
		graphics = GraphicSync.getInstance();

		//controller.initExplorer();
		graphics.initGraphics();
	}
}