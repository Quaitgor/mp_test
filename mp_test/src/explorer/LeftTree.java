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
		// xmlParser = new XMLParser();
		// xmlList = new HashMap<DefaultMutableTreeNode, StringWriter>();
		JTabbedPane tab = new JTabbedPane();
		// first Tab
		// xmlListPane = new DynamicTree(xmlList);
		// populateXMLTree(xmlListPane);

		// Unit Tab
		DynamicTree unitListPane = new DynamicTree();
		populateTree(unitListPane, "Units");
		tab.add("Unit", unitListPane);

		DynamicTree weaponListPane = new DynamicTree();
		populateTree(weaponListPane, "Weapons");
		tab.add("Weapons", weaponListPane);

		DynamicTree projectilesListPane = new DynamicTree();
		populateTree(projectilesListPane, "Projectiles");
		tab.add("Projectiles", projectilesListPane);

		DynamicTree graphicsListPane = new DynamicTree();
		populateTree(graphicsListPane, "Graphics");
		tab.add("Graphics", graphicsListPane);

		DynamicTree textsListPane = new DynamicTree();
		populateTree(textsListPane, "Texts");
		tab.add("Texts", textsListPane);

		// Weapon Tab
		
		// add("XML-List", xmlListPane);
		add(tab, BorderLayout.CENTER);
	}

	private void populateTree(DynamicTree treePanel, String library) {
		DefaultMutableTreeNode Units = treePanel.addObject(null, library);
		DefaultMutableTreeNode unitsI = treePanel.addObject(Units, "Internal");
		DefaultMutableTreeNode unitsE = treePanel.addObject(Units, "External");
		
		Iterator<String> keySetIterator = Controller.library.get("e"+library).keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			System.out.println(key);
			DefaultMutableTreeNode newNode = treePanel.addObject(unitsE, key);
		}
		
		Iterator<String> keySetIterator2 = Controller.library.get("i"+library).keySet().iterator();
		while (keySetIterator2.hasNext()) {
			String key = keySetIterator2.next();
			System.out.println(key);
			DefaultMutableTreeNode newNode = treePanel.addObject(unitsI, key);
		}
		// addUnitData(folderI, treePanel);
	}

	private TreeMap<String, DefaultMutableTreeNode> createChildNotes(String newNodeName, InputStream xmlFile, String target, DefaultMutableTreeNode parent, DynamicTree tree) {
		TreeMap<Integer, String> targetList = xmlParser.getTargetfromFile(xmlFile, target);
		TreeMap<String, DefaultMutableTreeNode> returnMap = new TreeMap<String, DefaultMutableTreeNode>();
		if (targetList.size() > 0) {
			DefaultMutableTreeNode newNode = tree.addObject(parent, newNodeName);
			for (Entry<Integer, String> entry : targetList.entrySet()) {
				String weapon = entry.getValue();
				DefaultMutableTreeNode weaponNode = tree.addObject(newNode, weapon);
				returnMap.put(weapon, weaponNode);
			}
		}
		return returnMap;
	}

	// TODO improve alot

	private void addUnitData(DefaultMutableTreeNode targetNode, DynamicTree tree) throws IOException {

		TreeMap<String, StringWriter> unitsLibrary = Controller.library.get("iUnits");

		Iterator<String> keySetIterator = unitsLibrary.keySet().iterator();
		while (keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			DefaultMutableTreeNode unitNode = tree.addObject(targetNode, key);

			// get UnitGraphics
			InputStream unitXML = new ByteArrayInputStream(unitsLibrary.get(key).toString().getBytes());
			createChildNotes("Graphics", unitXML, "graphics", unitNode, tree);
			unitXML.close();

			// getWeapons
			InputStream unitXML2 = new ByteArrayInputStream(unitsLibrary.get(key).toString().getBytes());
			TreeMap<String, DefaultMutableTreeNode> weapons = createChildNotes("Weapons", unitXML2, "weaponlist", unitNode, tree);
			unitXML2.close();

			for (Entry<String, DefaultMutableTreeNode> entry : weapons.entrySet()) {
				// weaponsgraphics
				InputStream weaponsXML = new ByteArrayInputStream(Controller.library.get("iWeapons").get(entry.getKey()).toString().getBytes());
				createChildNotes("Graphics", weaponsXML, "graphics", entry.getValue(), tree);
				weaponsXML.close();
				// weaponsprojectiles
				InputStream weaponsXML2 = new ByteArrayInputStream(Controller.library.get("iWeapons").get(entry.getKey()).toString().getBytes());
				TreeMap<String, DefaultMutableTreeNode> weaponsProjectiles = createChildNotes("Projectiles", weaponsXML2, "projectiles", entry.getValue(), tree);
				weaponsXML2.close();

				for (Entry<String, DefaultMutableTreeNode> entryProjectiles : weaponsProjectiles.entrySet()) {
					// weaponsprojectiles
					InputStream projectilesXML = new ByteArrayInputStream(Controller.library.get("iProjectiles").get(entryProjectiles.getKey()).toString().getBytes());
					createChildNotes("Graphics", projectilesXML, "graphics", entryProjectiles.getValue(), tree);
					projectilesXML.close();
				}
			}
		}
	}
}