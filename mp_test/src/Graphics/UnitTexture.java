package Graphics;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;

import Models.Unit;
import Reader.JavaAndXML;

public class UnitTexture {
	private JavaAndXML jxml = JavaAndXML.getInstance();
	private HashMap<Integer, DrawingTexture> layers;
	private Unit owner;
	
	
	public UnitTexture(Unit unit, String graphics) {
		layers = new HashMap<Integer, DrawingTexture>();
		this.owner = unit;
		// build loop to grab and create all textures in this string
		addTexture(0,graphics);
	}
	private void addTexture(int i, String graphics){
		File texXML = new File(graphics);
		System.out.println(texXML.toString());
		StringWriter stringWriter = jxml.readXML(texXML.getAbsolutePath());
		TextureData tex = (TextureData) jxml.XMLtoJava(stringWriter, TextureData.class);
		tex.registerOwner(this);
		DrawingTexture dTex = new DrawingTexture(tex);
		layers.put(i, dTex);
	}
	public void draw() {
		Iterator<Integer> keySetIterator = layers.keySet().iterator();
		while(keySetIterator.hasNext()){
			Integer key = keySetIterator.next();
			DrawingTexture texture = layers.get(key);
			texture.draw();
		}
	}
	
	public double[] getPosition(){
		return owner.getPosition();
	}


}
