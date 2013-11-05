package production;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Projectile extends GraphicalElement {
	
	public void init(){
		super.init();
		System.out.println("firing");
	}
}
