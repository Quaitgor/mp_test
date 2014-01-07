package writer;

import java.awt.Font;
import java.io.InputStream;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import controller.Controller;
import production.DataInit;

@XmlRootElement
public class FontData extends DataInit {

	@XmlElement
	private String file;
	@XmlElement
	private boolean antiAlias = false;
	@XmlElement
	private String name = "default";
	@XmlElement
	private String size = "80f";

	/**
	 * this object will be deleted by the trashcollector after its done
	 * after its creating the init is called, where it creates a font, sets a few parameters
	 * then adds that font to the Textwriters Font collection and idles after that.
	 */
	
	public void init() {
		TrueTypeFont font = null;
		String path = file;
		if (Controller.isJarFile) {
			path = file.substring(Controller.config.getString("source").length(), file.length());
		}
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream(path);
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(Float.valueOf(size));
			font = new TrueTypeFont(awtFont2, antiAlias);
			if (font != null) {
				TextWriter.getInstance().addFont(name, font);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
