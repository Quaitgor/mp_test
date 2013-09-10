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
	protected int[] hitboxSize = {0,0};
	@XmlElement
	protected int[] hitboxOffset = {0,0};
	@XmlElement
	protected int nrOfSpritesX = 1;
	@XmlElement
	protected int nrOfSpritesY = 1;
	@XmlElement
	protected int anim[][]={{0,0,0}};
	@XmlElement
	protected boolean animDepend = false;
	
}
