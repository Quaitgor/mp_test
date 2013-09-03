package Graphics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import Reader.XMLimport;

@XmlRootElement
public class TextureData extends XMLimport {

	@XmlElement
	protected String graphics = "default";
	@XmlElement
	protected int height = 100;
	@XmlElement
	protected int width = 100;
	@XmlElement
	
	protected UnitTexture owner;

	
	public void registerOwner(UnitTexture owner) {
		this.owner = owner;
	}
}
