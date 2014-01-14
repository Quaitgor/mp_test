package explorer;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class ExplorerWindow {

	public static ResourceBundle config = ResourceBundle.getBundle("config");
	private Component rightSide;
	public ExplorerWindow() {
		int width = Integer.parseInt(config.getString("explorer.sizeX"));
		int height = Integer.parseInt(config.getString("explorer.sizeY"));
		JFrame frame = new JFrame("Explorer");
		frame.setSize(width, height);
		Rectangle bounds = frame.getGraphicsConfiguration().getBounds();
		frame.setLocation(0, (int) ((bounds.height / 2) - (frame.getHeight() / 2)));


		Component leftSide = new LeftTree();
		rightSide = new Inspector();
		((Inspector) rightSide).getCanvas();
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSide, rightSide);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200);
		frame.setContentPane(splitPane);
		
		// Display the window.
		frame.setVisible(true);
	}
	
	public Canvas getCanvas(){
		return ((Inspector)rightSide).getCanvas();
	}
}
