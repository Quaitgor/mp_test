package production;

import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import conroller.Controller;

import read.JavaAndXML;

@XmlRootElement
public class Weapon extends GraphicalElement{
	
	@XmlElement
	protected String test = "default";
	@XmlElement
	protected String[] projectiles = {"none"};
	@XmlElement
	private int[] offset = {0,0};
	@XmlElement
	private double cooldown = 1000;
	
	private double cooldownLeft = 0;

	private static JavaAndXML jxml = JavaAndXML.getInstance();
	
	public void init(){
		super.init();
	}
	
	public void update(double delta){
		super.update(delta);
		if(cooldownLeft > 0){
			cooldownLeft -= delta;
		}
		this.x = owner.x;
		this.y = owner.y;
	}

	public double[] getPosition(){
		return new double[] {owner.x+offset[0],owner.y+offset[1]};
	}
	
	
	public void fire(){
		if (cooldownLeft <= 0){
			cooldownLeft = cooldown;
			layers.get(0).playAnimation(animationTiming);
			spawnProjectiles();
		}else{
			System.out.println("on CD!");			
		}
	}
	
	private void spawnProjectiles(){
		if(projectiles[0] != "none"){
			for(int i=0;i<projectiles.length;i++){
				Projectile p = (Projectile) Controller.spawn(Controller.projectiles, projectiles[i], 150+i*10, 150, Projectile.class);
			}
		}
	}
}
