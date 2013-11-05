package production;

import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import reader.JavaAndXML;
import testing.InputControl;
import testing.InputInterface;

import controller.Controller;


@XmlRootElement
public class Weapon extends GraphicalElement implements InputInterface{
	
	@XmlElement
	protected String test = "default";
	@XmlElement
	protected String[] projectiles = {"none"};
	@XmlElement
	private int[] offset = {0,0};
	@XmlElement
	private int[] pOffset = {0,0};
	@XmlElement
	private double cooldown = 100;
	@XmlElement
	private String key = "p";
	@XmlElement
	private boolean keyDownPossible = false;
	@XmlElement
	private boolean fireOnRelease = false;
	@XmlElement
	private boolean animOnRelease = false;
	@XmlElement
	private double maxEnergy = 0;
	
	private boolean keyDown = false;
	private double cooldownLeft = 0;
	private double energyCollected = 0;
	
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	
	public void init(){
		super.init();
		register();
	}
	
	public void update(double delta){
		super.update(delta);
		if(cooldownLeft > 0){
			cooldownLeft -= delta;
		}
		if(keyDown){
			if(energyCollected < maxEnergy){
				energyCollected += delta;
			}else{
				energyCollected = maxEnergy;
			}
			firePressed();
		}
		
		this.x = owner.x + offset[0];
		this.y = owner.y + offset[1];
	}

	@Override
	public double[] getPosition(){
		return new double[] {owner.x+offset[0],owner.y+offset[1]};
	}
	

	private void spawnProjectiles(){
		if(projectiles[0] != "none"){
			double pOffsetX = pOffset[0];
			double pOffsetY = pOffset[1];
			for(int i=0;i<projectiles.length;i++){
				if(pOffset.length > i*2){
					pOffsetX = pOffset[i*2];
					pOffsetY = pOffset[i*2+1];
				}
				Projectile p = (Projectile) Controller.spawn(Controller.projectiles, projectiles[i], x+pOffsetX, y+pOffsetY, Projectile.class);
			}
		}
	}
	
	
	public void fireWeapon(double energy){
		spawnProjectiles();
	}
	
	
	public void firePressed(){
		if (cooldownLeft <= 0){
			if(!fireOnRelease){
				layers.get(0).playAnimation();
				cooldownLeft = cooldown;
				fireWeapon(energyCollected);
			}else{
				if(layers.size() > 1){
					layers.get(1).turnOnAnimation();
				}
			}
		}else{
			//System.out.println("on CD!");			
		}
	}
	
	public void fireReleased(){
		if(fireOnRelease){
			fireWeapon(energyCollected);
		}
		if(animOnRelease){
			layers.get(0).playAnimation();
		}
		if(layers.size() > 1){
			layers.get(1).stopAnimation();
		}
		System.out.println("fired at: "+energyCollected);
		energyCollected = 0;
	}
	

	@Override
	public void keyDown(String key) {
		firePressed();
		if(keyDownPossible){
			keyDown = true;
		}
	}
	
	@Override
	public void keyUp(String key) {
		keyDown = false;
		fireReleased();
	}
	@Override
	public void keyAction(String key) {
		firePressed();
		fireReleased();
	}


	@Override
	public void register() {
		System.out.println("registering");
		InputControl.getInstance().registerKey(key, this);
	}

	@Override
	public void unregister() {
		InputControl.getInstance().unregisterKey(key);
		
	}
}
