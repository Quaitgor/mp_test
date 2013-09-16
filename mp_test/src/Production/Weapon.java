package Production;

import Conroller.Controller;
import Reader.JavaAndXML;

public class Weapon {
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	
	public Weapon(String weapondata, Unit unit) {
		getWeaponD(weapondata);
	}
	
	private void getWeaponD(String weapondata){
		WeaponData wepD = (WeaponData) jxml.XMLtoJava(Controller.weapons.get(weapondata), WeaponData.class);
		//grab all Data
		System.out.println(wepD.test);
	}
}
