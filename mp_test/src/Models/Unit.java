package Models;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Conroller.Controller;
import Graphics.DrawingTexture;
import Graphics.TextureData;
import Observer.DeltaUpdater;
import Observer.Observer;
import Reader.JavaAndXML;
import Reader.XMLimport;


@XmlRootElement
public class Unit extends XMLimport implements Observer {

	@XmlElement
	private String name = "default";
	@XmlElement
	private String mind = "none";
	@XmlElement
	private int hp = 1;
	@XmlElement
	private int damage = 10;
	@XmlElement
	private String Weapon = "none";
	@XmlElement
	private int speed = 10;
	@XmlElement
	private String graphics = "none";
	@XmlElement
	public double size = 1;
	
	public int rotation = 0;
	private double x = -100;
	private double y = -100;
	private HashMap<Integer, DrawingTexture> layers;
	private JavaAndXML jxml = JavaAndXML.getInstance();
	
	
	public void init(){
		layers = new HashMap<Integer, DrawingTexture>();
		// build loop to grab and create all textures in this string
		addTexture(0,graphics);
		//
		DeltaUpdater.register(this);
	}

	private void addTexture(int i, String graphics){
		
		TextureData tex = (TextureData) jxml.XMLtoJava(Controller.graphics.get(graphics), TextureData.class);
		tex.registerOwner(this);
		DrawingTexture dTex = new DrawingTexture(tex, this);
		layers.put(i, dTex);
	}
	
	public void update(double delta) {
		drawTextures();
	}
	
	private void drawTextures(){
		Iterator<Integer> keySetIterator = layers.keySet().iterator();
		while(keySetIterator.hasNext()){
			Integer key = keySetIterator.next();
			DrawingTexture texture = layers.get(key);
			texture.draw();
		}
	}
	
	public double[] getPosition(){
		return new double[] {x,y};
	}

	public void setPosition(int nx, int ny){
		this.x = nx;
		this.y = ny;
	}
}
