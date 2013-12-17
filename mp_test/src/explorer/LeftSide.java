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
public class LeftSide extends JPanel {

	private DynamicTree xmlListPane;
	private DynamicTree unitListPane;
	private HashMap<DefaultMutableTreeNode, StringWriter> xmlList;
	private TreeMap<String, TreeMap<String, StringWriter>> library;
	private XMLParser xmlParser;

	public LeftSide() {
		super(new BorderLayout());
		xmlParser = new XMLParser();
		xmlList = new HashMap<DefaultMutableTreeNode, StringWriter>();
		library = Controller.getInstance().getLibrary();
		JTabbedPane tab = new JTabbedPane();
		// first Tab
		xmlListPane = new DynamicTree(xmlList);
		populateXMLTree(xmlListPane);
		// second Tab
		unitListPane = new DynamicTree(xmlList);
		populateUnitTree(unitListPane);

		tab.add("XML-List", xmlListPane);
		tab.add("Unit-List", unitListPane);
		add(tab, BorderLayout.CENTER);
	}

	private void populateUnitTree(DynamicTree treePanel) {
		try {
			DefaultMutableTreeNode folderI = treePanel.addObject(null, "Internal");
			DefaultMutableTreeNode folderE = treePanel.addObject(null, "External");
			addUnitData(folderI, treePanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private void populateXMLTree(DynamicTree treePanel) {
		DefaultMutableTreeNode folderI = treePanel.addObject(null, "Internal");
		DefaultMutableTreeNode folderE = treePanel.addObject(null, "External");
		
		for (Entry<String, TreeMap<String, StringWriter>> subLibrary : library.entrySet()) {
			DefaultMutableTreeNode folder = null;
			// choose either internal or external folder
			if (subLibrary.getKey().substring(0, 1).equals("i")) {
				folder = folderI;
			} else {
				folder = folderE;
			}
			// Subfolder
			if(subLibrary.getValue().size() > 0){
				// if not empty create parent node
				DefaultMutableTreeNode parent = treePanel.addObject(folder, subLibrary.getKey().subSequence(1, subLibrary.getKey().length()), true);
				// create childnodes inside parent
				for (Entry<String, ?> xmlFile : subLibrary.getValue().entrySet()) {
					DefaultMutableTreeNode newNode = treePanel.addObject(parent, xmlFile.getKey());
					// put childnode into xmlList HashMap, for Rightside quick-access
					xmlList.put(newNode, library.get(subLibrary.getKey()).get(xmlFile.getKey()));
				}
			}
		}
	}
}