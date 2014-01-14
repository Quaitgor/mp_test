package explorer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

@SuppressWarnings("serial")
public class Inspector extends JPanel {

	// private Controller controller;
	// private XMLParser xmlParser;

	public Canvas canvas;

	public Inspector() {
		super(new BorderLayout());

		canvas = new Canvas();
		/*
		add(new JLabel("test1"));
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controller.doOk();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// controller.doCancel();
			}
		});
		add(okButton, BorderLayout.NORTH);
		add(cancelButton, BorderLayout.NORTH);
		*/

		canvas.setFocusable(true);
		canvas.setIgnoreRepaint(true);
		canvas.setSize(100, 100);
		add(canvas, BorderLayout.EAST);
		
		try {
			Display.setParent(canvas);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Canvas getCanvas() {
		return canvas;
	}
}