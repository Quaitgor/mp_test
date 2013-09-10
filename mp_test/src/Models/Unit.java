package Models;
import java.io.File;
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
	private String[] graphics = {"none"};
	
	public int rotation = 0;
	private double x = -100;
	private double y = -100;
	private double delta = 0;
	private HashMap<Integer, DrawingTexture> layers;
	private JavaAndXML jxml = JavaAndXML.getInstance();
	
	
	public void init(){
		layers = new HashMap<Integer, DrawingTexture>();
		for(int i=0;i<graphics.length;i++){
			addTexture(i,graphics[i]);
		}
		DeltaUpdater.register(this);
	}

	private void addTexture(int i, String graphics){
		TextureData tex = (TextureData) jxml.XMLtoJava(Controller.graphics.get(graphics), TextureData.class);
		DrawingTexture dTex = new DrawingTexture(tex, this);
		layers.put(i, dTex);
	}
	
	public void update(double delta) {
		this.delta = delta;
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
	
	public double getDelta(){
		return delta;
	}
	
	public double[] getPosition(){
		return new double[] {x,y};
	}

	public void setPosition(int nx, int ny){
		this.x = nx;
		this.y = ny;
	}
}
