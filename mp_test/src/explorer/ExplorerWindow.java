package explorer;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class ExplorerWindow {

	public static ResourceBundle config = ResourceBundle.getBundle("config");

	public ExplorerWindow() {
		int width = Integer.parseInt(config.getString("explorer.sizeX"));
		int height = Integer.parseInt(config.getString("explorer.sizeY"));
		JFrame frame = new JFrame("Explorer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		Rectangle bounds = frame.getGraphicsConfiguration().getBounds();
		frame.setLocation(0, (int) ((bounds.height / 2) - (frame.getHeight() / 2)));

		// Create and set up the content pane.

		Component leftSide = new LeftSide();
		Component rightSide = new RightSide();

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSide, rightSide);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(250);
		frame.setContentPane(splitPane);
		
		// Display the window.
		frame.setVisible(true);
	}
}
