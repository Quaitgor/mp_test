import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileReader;

import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.PlainDocument;

import org.bounce.text.LineNumberMargin;
import org.bounce.text.ScrollableEditorPanel;
import org.bounce.text.xml.XMLEditorKit;
import org.bounce.text.xml.XMLFoldingMargin;
import org.bounce.text.xml.XMLStyleConstants;

/**
 * Simple wrapper around JEditorPane to browse java text using the XMLEditorKit
 * plug-in.
 * 
 * java XmlKitTest filename
 */
public class XMLKitTest {
    private static JEditorPane editor = null;

    /**
     * Main method...
     * 
     * @param args
     */
    public static void main(String[] args) {
        String path = "";
    	if (args.length != 1) {
            System.err.println("need filename argument");
            path = "C:/Users/cn/git/mp_testKeplar/mp_test/res/xml/texts/test2.xml";
            //System.exit( 1);
        }else{
        	path = args[0];
        }

        try {
            editor = new JEditorPane();
            
            // Instantiate a XMLEditorKit
            XMLEditorKit kit = new XMLEditorKit();

            editor.setEditorKit( kit);

            File file = new File(path);
            editor.read(new FileReader(file), file);

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

            JFrame f = new JFrame( "XmlEditorKitTest: " + path);
            f.getContentPane().setLayout(new BorderLayout());
            f.getContentPane().add(scroller, BorderLayout.CENTER);

            f.setSize(600, 600);
            f.setVisible(true);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit( 1);
        }
    }
}