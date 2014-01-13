package explorer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Inspector extends JPanel {


	// private Controller controller;
	// private XMLParser xmlParser;

	public Inspector() {

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
		add(okButton);
		add(cancelButton);

	}

}