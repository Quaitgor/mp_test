package GraphicalElements;

import java.util.HashMap;

public class TextBlock {

	private HashMap<Integer, String> texts;
	private int textSpeed = 100;
	
	public HashMap<Integer, String> getTexts(){
		return texts;
	}
	
	public TextBlock(String text){
		texts.put(texts.size()+1, text);
	}
}
