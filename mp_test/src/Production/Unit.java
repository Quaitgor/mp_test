package Production;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Conroller.Controller;
import Observer.Observer;
import Reader.JavaAndXML;


@XmlRootElement
public class Unit extends GraphicalElement implements Observer {

	@XmlElement
	private String name = "default";
	@XmlElement
	private String mind = "none";
	@XmlElement
	private int hp = 1;
	@XmlElement
	private int damage = 10;
	@XmlElement
	private String[] weaponlist = {"none"};
	
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	private HashMap<Integer, Weapon> weapons;
	
	public void init(){
		super.init();
		weapons = new HashMap<Integer, Weapon>();
		if(weaponlist[0] != "none"){
			for(int i=0;i<weaponlist.length;i++){
				addWeapon(i,weaponlist[i]);
			}
		}
	}

	private void addWeapon(int i, String weapon){
		System.out.println(i);
		Weapon createdWeapon = (Weapon) jxml.XMLtoJava(Controller.weapons.get(weapon), Weapon.class);
		createdWeapon.registerOwner(this);
		weapons.put(i, createdWeapon);
	}
	
	//Weapon fire Testing!?!
	public void update(double delta){
		super.update(delta);
		if(super.animationTiming <= 100){
			fireWeapon(0);
		}
	}
	
	public void fireWeapon(int nr){
		if(nr < weapons.size()){
			if(weapons.get(nr) != null){
				weapons.get(nr).fire();
			}else{
				System.out.println("No weapon equiped in that slot");
			}
		}else{
			System.out.println("I dont have that many weapons");
		}
	}
}
