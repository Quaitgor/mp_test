package Production;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Graphics.TextureLayer;
import Observer.DeltaUpdater;
import Observer.Observer;


@XmlRootElement
public class Unit extends Basic implements Observer {

	@XmlElement
	private String name = "default";
	@XmlElement
	private String mind = "none";
	@XmlElement
	private int hp = 1;
	@XmlElement
	private int damage = 10;
	@XmlElement
	private int speed = 10;
	@XmlElement
	private String[] graphics = {"none"};
	@XmlElement
	private String[] weaponlist = {"none"};
	
	protected double x = -100;
	protected double y = -100;
	private HashMap<Integer, TextureLayer> layers;
	public double animationTiming = 0;
	private HashMap<Integer, Weapon> weapons;
	
	
	public void init(){
		layers = new HashMap<Integer, TextureLayer>();
		weapons = new HashMap<Integer, Weapon>();
		//add all Texture layers
		if(graphics[0] != "none"){
				for(int i=0;i<graphics.length;i++){
				addTexture(i,graphics[i]);
			}
		}
		//add all weapons
		if(weaponlist[0] != "none"){
		for(int i=0;i<weaponlist.length;i++){
				addWeapon(i,weaponlist[i]);
			}
		}
		DeltaUpdater.register(this);
	}

	private void addWeapon(int i, String weapon){
		Weapon finishedWep = new Weapon(weapon, this);
		weapons.put(i, finishedWep);
	}

	private void addTexture(int i, String graphics){
		TextureLayer tex = new TextureLayer(graphics, this);
		layers.put(i, tex);
	}
	
	public void update(double delta) {
		animationTiming += delta;
		drawTextures();
		if(animationTiming >= 60000){
			animationTiming = 0;
			Iterator<Integer> keySetIterator = layers.keySet().iterator();
			while(keySetIterator.hasNext()){
				Integer key = keySetIterator.next();
				TextureLayer texture = layers.get(key);
				texture.resetLastDelta();
			}
		}
	}
	
	private void drawTextures(){
		Iterator<Integer> keySetIterator = layers.keySet().iterator();
		while(keySetIterator.hasNext()){
			Integer key = keySetIterator.next();
			TextureLayer texture = layers.get(key);
			texture.draw(animationTiming);
		}
	}
	

}
