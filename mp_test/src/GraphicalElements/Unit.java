package GraphicalElements;
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


@XmlRootElement
public class Unit extends GraphicElement implements Observer {

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
	
	protected double x = -100;
	protected double y = -100;
	private HashMap<Integer, DrawingTexture> layers;
	private JavaAndXML jxml = JavaAndXML.getInstance();
	public double animationTiming = 0;
	
	
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
		animationTiming += delta;
		drawTextures();
		if(animationTiming >= 60000){
			animationTiming = 0;
			Iterator<Integer> keySetIterator = layers.keySet().iterator();
			while(keySetIterator.hasNext()){
				Integer key = keySetIterator.next();
				DrawingTexture texture = layers.get(key);
				texture.resetLastDelta();
			}
		}
	}
	
	private void drawTextures(){
		Iterator<Integer> keySetIterator = layers.keySet().iterator();
		while(keySetIterator.hasNext()){
			Integer key = keySetIterator.next();
			DrawingTexture texture = layers.get(key);
			texture.draw(animationTiming);
		}
	}
	

}
