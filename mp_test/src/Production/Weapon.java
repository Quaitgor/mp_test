package Production;

import java.io.StringWriter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Conroller.Controller;
import Reader.JavaAndXML;

@XmlRootElement
public class Weapon extends GraphicalElement{
	
	@XmlElement
	protected String test = "default";
	@XmlElement
	protected String[] projectiles = {"none"};
	@XmlElement
	private int[] offset = {0,0};

	private static JavaAndXML jxml = JavaAndXML.getInstance();
	
	public void init(){
		super.init();
		if(projectiles[0] != "none"){
			for(int i=0;i<projectiles.length;i++){
				//create projectiles here
			}
		}
	}
	
	public void update(double delta){
		super.update(delta);
		this.x = owner.x;
		this.y = owner.y;
	}

	public double[] getPosition(){
		return new double[] {owner.x+offset[0],owner.y+offset[1]};
	}
	
	
	public void fire(){
		
		System.out.println("firing");
		//Projectile p = (Projectile) Controller.spawn(Controller.projectiles, "lvl0", 150, 150, Projectile.class);

		//Projectile newUnit = (Projectile) jxml.XMLtoJava( (StringWriter)Controller.projectiles.get("lvl0"), Projectile.class);
		
		
	}
}
