package explorer;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;

import controller.Controller;

@SuppressWarnings("serial")
public class DynamicTree extends JPanel {

	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;
	public JTree tree;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	TreePath selected = null;
	
	public DynamicTree() {
		super(new GridLayout(1, 0));
		rootNode = new DefaultMutableTreeNode("Library");
		treeModel = new DefaultTreeModel(rootNode);
		tree = new JTree(treeModel);
		tree.addTreeSelectionListener(new MyTreeSelectionListener());
		tree.addTreeExpansionListener(new MyTreeExpansionListener());
		tree.addTreeWillExpandListener(new MyTreeWillExpandListener());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane);
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
			}
		}
		// Either there was no selection, or the root was selected.
		toolkit.beep();
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();
		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(parentNode, child, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
		return addObject(parent, child, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
		if (parent == null) {
			parent = rootNode;
		}
		// It is key to invoke this on the TreeModel, and NOT
		// DefaultMutableTreeNode
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}

	// reacts to Selections made
	class MyTreeSelectionListener implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			JTree tree = (JTree) e.getSource();
			selected = e.getPath();
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if (selectedNode.isLeaf()) {
				//System.out.println(selectedNode.getParent().toString());
				/*
				StringWriter xmlFile = xmlList.get(selectedNode);
				ByteArrayInputStream test = new ByteArrayInputStream(xmlFile.toString().getBytes());
				try {
					RightSide.editor.read(test, "Edit XML");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				*/
			}
		}
	}

	// reacts Before expand or collapses are made
	class MyTreeWillExpandListener implements TreeWillExpandListener {
		public void treeWillExpand(TreeExpansionEvent evt) throws ExpandVetoException {

			// System.out.println("will expand");
			// boolean veto = false;
			// if (veto) {
			// System.out.println("blocking expand");
			// throw new ExpandVetoException(evt);
			// }
		}

		public void treeWillCollapse(TreeExpansionEvent evt) throws ExpandVetoException {
			TreePath tree = evt.getPath();
			if (tree.isDescendant(selected)) {
				throw new ExpandVetoException(evt);
			}
		}
	}

	// reacts After expand or collapses are made
	class MyTreeExpansionListener implements TreeExpansionListener {
		public void treeExpanded(TreeExpansionEvent evt) {
			// JTree tree = (JTree) evt.getSource();
			// TreePath path = evt.getPath();
			// System.out.println("treeExpanded");
		}

		public void treeCollapsed(TreeExpansionEvent evt) {
			// JTree tree = (JTree) evt.getSource();
			// TreePath path = evt.getPath();
			// System.out.println("treeCollapsed");
		}
	}
}