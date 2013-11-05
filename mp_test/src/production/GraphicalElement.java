package production;

import graphics.TextureLayer;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;

import observer.DeltaUpdater;
import observer.Observer;


public class GraphicalElement extends Basic implements Observer{

	@XmlElement
	private int speed = 10;
	@XmlElement
	private String[] graphics = {"none"};
	@XmlElement
	private String faction = "none";
	
	double limit = 3000;
	protected HashMap<Integer, TextureLayer> layers;
	public double animationTiming = 0;
	protected Basic owner = null;

	public void init(){
		layers = new HashMap<Integer, TextureLayer>();
		//add all Texture layers
		if(graphics[0] != "none"){
				for(int i=0;i<graphics.length;i++){
				addTexture(i,graphics[i]);
			}
		}
		DeltaUpdater.register(this);
	}
	
	public void update(double delta) {
		animationTiming += delta;
		drawTextures();
		//improve graphics reset (overflow prevention of double animationTiming?)
		if(animationTiming >= limit){
			animationTiming -= limit;
			Iterator<Integer> keySetIterator = layers.keySet().iterator();
			while(keySetIterator.hasNext()){
				Integer key = keySetIterator.next();
				layers.get(key).resetLastDelta(limit);
			}
		}
	}
	
	
	private void addTexture(int i, String graphics){
		TextureLayer tex = new TextureLayer(graphics, this);
		layers.put(i, tex);
	}
	
	private void drawTextures(){
		Iterator<Integer> keySetIterator = layers.keySet().iterator();
		while(keySetIterator.hasNext()){
			Integer key = keySetIterator.next();
			TextureLayer texture = layers.get(key);
			texture.draw(animationTiming);
		}
	}

	public String getFaction(){
		return faction;
	}

	public void registerOwner(GraphicalElement owner) {
		this.owner = owner;
		faction = ((GraphicalElement) owner).getFaction();
	}
}
