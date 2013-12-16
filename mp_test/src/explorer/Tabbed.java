package explorer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.PlainDocument;

import org.bounce.text.LineNumberMargin;
import org.bounce.text.ScrollableEditorPanel;
import org.bounce.text.xml.XMLEditorKit;
import org.bounce.text.xml.XMLFoldingMargin;
import org.bounce.text.xml.XMLStyleConstants;

import controller.Controller;

public class Tabbed extends JPanel {
    static JEditorPane editor = null;
	private JPanel xmlDisplay = null;
    
	public Tabbed() {
		super(new BorderLayout());
		JTabbedPane tab = new JTabbedPane();
		xmlDisplay = new JPanel();
		add(tab, BorderLayout.CENTER);
        xmlDisplay.setLayout(new BorderLayout());

        loadXMLWindow(null, null);
        
		JPanel editor = new JPanel();
		tab.add("XML", xmlDisplay);
		tab.add("Editor", editor);
	}
	
	public void loadXMLWindow(ByteArrayInputStream input, String desc){
        try {
            editor = new JEditorPane();
            
            // Instantiate a XMLEditorKit
            XMLEditorKit kit = new XMLEditorKit();

            editor.setEditorKit(kit);
            if(input != null){
                editor.read(input, desc);
            }
            // Set the font style.
            editor.setFont(new Font("Courier", Font.PLAIN, 12));

            // Set the tab size
            editor.getDocument().putProperty(PlainDocument.tabSizeAttribute, 
                                              new Integer(4));

            // Enable auto indentation.
            kit.setAutoIndentation(true);

            // Enable tag completion.
            kit.setTagCompletion(true);
            
            // Enable error highlighting.
            editor.getDocument().putProperty(XMLEditorKit.ERROR_HIGHLIGHTING_ATTRIBUTE, new Boolean(true));

            // Set a style
            kit.setStyle(XMLStyleConstants.ATTRIBUTE_NAME, new Color(255, 0, 0), 
                          Font.BOLD);
            
            // Put the editor in a panel that will force it to resize, when a different 
            // view is choosen.
            ScrollableEditorPanel editorPanel = new ScrollableEditorPanel(editor);

            JScrollPane scroller = new JScrollPane( editorPanel);

            // Add the number margin and folding margin as a Row Header View
            JPanel rowHeader = new JPanel(new BorderLayout());
            rowHeader.add(new XMLFoldingMargin(editor), BorderLayout.EAST);
            rowHeader.add(new LineNumberMargin(editor), BorderLayout.WEST);
            scroller.setRowHeaderView(rowHeader);

            xmlDisplay.setLayout(new BorderLayout());
            xmlDisplay.add(scroller, BorderLayout.CENTER);
            //editor.write(new FileWriter("D:/temp/test.xml"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
	}
}
