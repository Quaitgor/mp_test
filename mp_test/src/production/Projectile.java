package production;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Projectile extends GraphicalElement {
	
	public void init(){
		super.init();
		System.out.println("firing");
	}
	public void update(double delta){
		super.update(delta);
		this.x += 5;
	}
}
