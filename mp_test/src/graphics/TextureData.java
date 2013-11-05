package graphics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import production.*;

@XmlRootElement
public class TextureData extends DataCollector {

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
	protected boolean repeat = true;
	@XmlElement
	protected boolean playOnSpawn = true;
	
}
