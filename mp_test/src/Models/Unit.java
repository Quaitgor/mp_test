package Models;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Graphics.UnitTexture;
import Observer.DeltaUpdater;
import Observer.Observer;
import Reader.XMLimport;


@XmlRootElement
public class Unit extends XMLimport implements Observer {

	private UnitTexture ut = null;
	
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
	private double x = 10;
	@XmlElement
	private double y = 10;
	@XmlElement
	private int speed = 10;
	@XmlElement
	private String graphics = "none";
	@XmlElement
	private int size = 1;
	
	public void chat(){
		System.out.println(name);
	}
	
	public void init(){
		ut = new UnitTexture(this, graphics);
		DeltaUpdater.register(this);
	}

	@Override
	public void update(double delta) {
		if(ut!=null){
			ut.draw();
		}
	}
	
	public double[] getPosition(){
		return new double[] {x,y};
	}
}
