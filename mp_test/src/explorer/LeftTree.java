package explorer;

import java.awt.BorderLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;

import reader.XMLParser;
import controller.Controller;

@SuppressWarnings("serial")
public class LeftTree extends JPanel {

	private HashMap<DefaultMutableTreeNode, StringWriter> xmlList;
	private Controller controller;
	private XMLParser xmlParser;

	public LeftTree() {
		super(new BorderLayout());
		controller = Controller.getInstance();
		JTabbedPane tab = new JTabbedPane();
		
		// Unit Tab
		DynamicTree unitListPane = new DynamicTree();
		populateTree(unitListPane, "Units");
		tab.add("Unit", unitListPane);
		// Weapons Tab
		DynamicTree weaponListPane = new DynamicTree();
		populateTree(weaponListPane, "Weapons");
		tab.add("Weapons", weaponListPane);
		// Projectiles Tab
		DynamicTree projectilesListPane = new DynamicTree();
		populateTree(projectilesListPane, "Projectiles");
		tab.add("Projectiles", projectilesListPane);
		// Projectiles Tab
		DynamicTree graphicsListPane = new DynamicTree();
		populateTree(graphicsListPane, "Graphics");
		tab.add("Graphics", graphicsListPane);
		// Texts Tab
		DynamicTree textsListPane = new DynamicTree();
		populateTree(textsListPane, "Texts");
		tab.add("Texts", textsListPane);

		add(tab, BorderLayout.CENTER);
	}

	private void populateTree(DynamicTree treePanel, String library) {
		DefaultMutableTreeNode Units = treePanel.addObject(null, library);
		DefaultMutableTreeNode unitsI = treePanel.addObject(Units, "Internal", true);
		DefaultMutableTreeNode unitsE = treePanel.addObject(Units, "External", true);
		
		Iterator<String> keySetIterator = Controller.library.get("e"+library).keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			DefaultMutableTreeNode newNode = treePanel.addObject(unitsE, key, true);
		}
		
		Iterator<String> keySetIterator2 = Controller.library.get("i"+library).keySet().iterator();
		while (keySetIterator2.hasNext()) {
			String key = keySetIterator2.next();
			DefaultMutableTreeNode newNode = treePanel.addObject(unitsI, key, true);
		}
	}
}