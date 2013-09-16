package Production;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WeaponData extends DataCollector{

	@XmlElement
	protected String test = "default";
	
}
