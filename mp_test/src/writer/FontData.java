package writer;

import java.awt.Font;
import java.io.InputStream;
import java.security.CodeSource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

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
	private String size = "40f";

	public String getName(){
		return name;
	}
	public void init(){
		TrueTypeFont font = null;
		String path = file;
		CodeSource src = getClass().getProtectionDomain().getCodeSource();
		if (src.getLocation().toString().endsWith(".jar")) {
			path = file.substring(4, file.length());
		}
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream(path);
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(Float.valueOf(size+"f")); // set font size
			font = new TrueTypeFont(awtFont2, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(font != null){
			TextWriter.getInstance().addFont(name, font);
		}
	}
	
}