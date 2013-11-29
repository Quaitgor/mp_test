package production;

import java.util.HashMap;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlElement;

import graphics.TextureLayer;
import controller.Controller;
import observer.DeltaUpdater;
import observer.Observer;
import reader.JavaAndXML;

public class GraphicalElement extends Basic implements Observer {

    @XmlElement
    private int speed = 10;
    @XmlElement
    private String[] graphics = { "none" };
    @XmlElement
    private String faction = "none";

    double limit = 3000;
    protected HashMap<Integer, TextureLayer> layers;
    public double animationTiming = 0;
    protected Basic owner = null;

    public void init() {
	layers = new HashMap<Integer, TextureLayer>();
	// add all Texture layers
	if (graphics[0] != "none") {
	    for (int i = 0; i < graphics.length; i++) {
		addTexture(i, graphics[i]);
	    }
	}
	DeltaUpdater.registerDelta(this);
    }

    @Override
    public void destroyObject() {
	super.destroyObject();
	DeltaUpdater.unregisterDelta(this);
	layers.clear();
	layers = null;
	owner = null;
    }

    public void update(double delta) {
	animationTiming += delta;
	drawTextures();
	// improve graphics reset (overflow prevention of double
	// animationTiming?)
	if (animationTiming >= limit) {
	    animationTiming -= limit;
	    Iterator<Integer> keySetIterator = layers.keySet().iterator();
	    while (keySetIterator.hasNext()) {
		Integer key = keySetIterator.next();
		layers.get(key).resetLastDelta(limit);
	    }
	}
    }

    private void addTexture(int i, String graphics) {
	TextureLayer tex = (TextureLayer) JavaAndXML.getInstance().XMLtoJava(Controller.graphics.get(graphics), TextureLayer.class);
	tex.init(this);
	layers.put(i, tex);
    }

    private void drawTextures() {
	Iterator<Integer> keySetIterator = layers.keySet().iterator();
	while (keySetIterator.hasNext()) {
	    Integer key = keySetIterator.next();
	    TextureLayer texture = layers.get(key);
	    texture.draw(animationTiming);
	}
    }

    public String getFaction() {
	return faction;
    }

    public void registerOwner(GraphicalElement owner) {
	this.owner = owner;
	faction = ((GraphicalElement) owner).getFaction();
    }
}
