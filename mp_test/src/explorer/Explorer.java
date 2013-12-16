package explorer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.lwjgl.Sys;

import controller.Controller;

@SuppressWarnings("serial")
public class Explorer extends JPanel {

	private DynamicTree treePanel;
	DefaultMutableTreeNode folderI;
	DefaultMutableTreeNode folderE; 

	public Explorer() {
		super(new BorderLayout());
		// Create the components.
		treePanel = new DynamicTree();
		folderI = treePanel.addObject(null, "Internal");
		folderE = treePanel.addObject(null, "External");
		populateTree(treePanel);
		// Lay everything out.
		treePanel.setPreferredSize(new Dimension(300, 150));
		
		add(treePanel, BorderLayout.CENTER);
	}

	public void populateTree(DynamicTree treePanel) {
		Controller controller = Controller.getInstance();
		TreeMap<String, TreeMap<String, StringWriter>> library = controller.getLibrary();

		Iterator<String> keySetIterator = library.keySet().iterator();
		while (keySetIterator.hasNext()) {
			DefaultMutableTreeNode folder = null;
			String key = keySetIterator.next();
			if(key.substring(0, 1).equals("i")){
				folder = folderI;
			}else{
				folder = folderE;
			}
			//Subfolder
			Iterator<String> xml = library.get(key).keySet().iterator();
			if(xml.hasNext()){
				DefaultMutableTreeNode parent = treePanel.addObject(folder, key.subSequence(1, key.length()), true);
				while (xml.hasNext()) {
					String xmlKey = xml.next();
					treePanel.addObject(parent, xmlKey);
				}
			}
		}
		

	}
}