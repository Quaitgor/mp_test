package Graphics;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import Models.Unit;
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
	protected Unit owner;
	@XmlElement
	protected int layer = -75;
	@XmlElement
	protected String hitbox = null;
	
	protected int hitboxX = 50;
	protected int hitboxY = 50;
	protected int hitboxOffX = 10;
	protected int hitboxOffY = 10;
	
	public void registerOwner(Unit unit) {
		this.owner = unit;
	}
}
