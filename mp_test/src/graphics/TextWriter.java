package graphics;

import java.util.HashMap;
import java.util.Iterator;

import observer.DeltaUpdater;
import observer.Observer;
import production.Basic;



public class TextWriter extends Basic implements Observer{
	TextureLayer letters;
	private static TextWriter TW = new TextWriter();
	public static HashMap<String, TextBlock> textBlocks;
	
	private TextWriter(){
		letters = new TextureLayer("alphabet", this);
		textBlocks = new HashMap<String, TextBlock>();
		DeltaUpdater.register(this);
	}
	
	public static TextWriter getInstance(){
		return TW;
	}

	public void addText(TextBlock newText){
		textBlocks.put(newText.getName(), newText);
	}
	
	
	public void writeText(double delta){
		Iterator<String> keySetIterator = textBlocks.keySet().iterator();
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
			if(textBlocks.get(key).getState()){
				writeIn(textBlocks.get(key), delta);
			}
		}
	}

	private void writeIn(TextBlock tB, double delta){
		String text = tB.getText(delta);
		if(text != null){
			x = tB.getX();
			y = tB.getY();
			for(int i = 0; i < text.length(); i ++){
				if (!text.substring(i, i+1).equals("\\")){
					char a = text.charAt(i);
					int[] texCords = getLetterCords(a);
					letters.changeSprite(texCords);
					letters.draw();
					if(texCords[1] == 4 || texCords[1] == 5){
						x += 16;
					}else{
						x += 12;
					}
				}else{
					//Commands for TextWriter
					//new Line
					String command = text.substring(i, i+3);
					if(command.equals("\\lb")){
						x = tB.getX();
						y += 22;
						i += 2;
					}
				}
			}
		}
	}

	@Override
	public void update(double delta) {
		writeText(delta);
	}
	
	//use font.png, rescale to 768xY
	
	
	public int[] getLetterCords(char a){
		switch(a){
			case 'A': return new int[]{1,4};
			case 'B': return new int[]{2,4};
			case 'C': return new int[]{3,4};
			case 'D': return new int[]{4,4};
			case 'E': return new int[]{5,4};
			case 'F': return new int[]{6,4};
			case 'G': return new int[]{7,4};
			case 'H': return new int[]{8,4};
			case 'I': return new int[]{9,4};
			case 'J': return new int[]{10,4};
			case 'K': return new int[]{11,4};
			case 'L': return new int[]{12,4};
			case 'M': return new int[]{13,4};
			case 'N': return new int[]{14,4};
			case 'O': return new int[]{15,4};
			case 'P': return new int[]{0,5};
			case 'Q': return new int[]{1,5};
			case 'R': return new int[]{2,5};
			case 'S': return new int[]{3,5};
			case 'T': return new int[]{4,5};
			case 'U': return new int[]{5,5};
			case 'V': return new int[]{6,5};
			case 'W': return new int[]{7,5};
			case 'X': return new int[]{8,5};
			case 'Y': return new int[]{9,5};
			case 'Z': return new int[]{10,5};

			case 'a': return new int[]{1,6};
			case 'b': return new int[]{2,6};
			case 'c': return new int[]{3,6};
			case 'd': return new int[]{4,6};
			case 'e': return new int[]{5,6};
			case 'f': return new int[]{6,6};
			case 'g': return new int[]{7,6};
			case 'h': return new int[]{8,6};
			case 'i': return new int[]{9,6};
			case 'j': return new int[]{10,6};
			case 'k': return new int[]{11,6};
			case 'l': return new int[]{12,6};
			case 'm': return new int[]{13,6};
			case 'n': return new int[]{14,6};
			case 'o': return new int[]{15,6};
			case 'p': return new int[]{0,7};
			case 'q': return new int[]{1,7};
			case 'r': return new int[]{2,7};
			case 's': return new int[]{3,7};
			case 't': return new int[]{4,7};
			case 'u': return new int[]{5,7};
			case 'v': return new int[]{6,7};
			case 'w': return new int[]{7,7};
			case 'x': return new int[]{8,7};
			case 'y': return new int[]{9,7};
			case 'z': return new int[]{10,7};
			
			case '0': return new int[]{0,0};
			case '1': return new int[]{0,0};
			case '2': return new int[]{0,0};
			case '3': return new int[]{0,0};
			case '4': return new int[]{0,0};
			case '5': return new int[]{0,0};
			case '6': return new int[]{0,0};
			case '7': return new int[]{0,0};
			case '8': return new int[]{0,0};
			case '9': return new int[]{0,0};
			case '`': return new int[]{0,0};
			case '~': return new int[]{0,0};
			case '!': return new int[]{0,0};
			case '@': return new int[]{0,0};
			case '#': return new int[]{0,0};
			case '$': return new int[]{0,0};
			case '%': return new int[]{0,0};
			case '^': return new int[]{0,0};
			case '&': return new int[]{0,0};
			case '*': return new int[]{10,2};
			case '(': return new int[]{0,0};
			case ')': return new int[]{0,0};
			case '-': return new int[]{0,0};
			case '_': return new int[]{0,0};
			case '+': return new int[]{0,0};
			case '=': return new int[]{0,0};
			
			case '.': return new int[]{14,2};
			case ',': return new int[]{12,2};
			case '<': return new int[]{0,0};
			case '>': return new int[]{0,0};
			case '/': return new int[]{0,0};
			case '\\': return new int[]{0,0};
			case '?': return new int[]{0,0};
			case ':': return new int[]{0,0};
			case ';': return new int[]{0,0};
			case '"': return new int[]{0,0};
			case '\'': return new int[]{0,0};
			case '[': return new int[]{0,0};
			case ']': return new int[]{0,0};
			case '{': return new int[]{0,0};
			case '}': return new int[]{0,0};
			case '|': return new int[]{0,0};
		}
		return new int[]{-1,-1};
	}
}
