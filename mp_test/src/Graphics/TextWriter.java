package Graphics;

import java.util.HashMap;
import java.util.Iterator;

import Observer.DeltaUpdater;
import Observer.Observer;
import Production.Basic;


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
					x += 16;
				}else{
					//Commands for TextWriter
					//new Line
					String command = text.substring(i, i+3);
					if(command.equals("\\lb")){
						x = tB.getX();
						y += 20;
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
	
	public int[] getLetterCords(char a){
		switch(a){
			case 'A': return new int[]{0,0};
			case 'B': return new int[]{1,0};
			case 'C': return new int[]{2,0};
			case 'D': return new int[]{3,0};
			case 'E': return new int[]{4,0};
			case 'F': return new int[]{5,0};
			case 'G': return new int[]{6,0};
			case 'H': return new int[]{7,0};
			case 'I': return new int[]{8,0};
			case 'J': return new int[]{9,0};
			case 'K': return new int[]{10,0};
			case 'L': return new int[]{11,0};
			case 'M': return new int[]{12,0};
			case 'N': return new int[]{13,0};
			case 'O': return new int[]{14,0};
			case 'P': return new int[]{15,0};
			case 'Q': return new int[]{16,0};
			case 'R': return new int[]{17,0};
			case 'S': return new int[]{18,0};
			case 'T': return new int[]{19,0};
			case 'U': return new int[]{20,0};
			case 'V': return new int[]{21,0};
			case 'W': return new int[]{22,0};
			case 'X': return new int[]{23,0};
			case 'Y': return new int[]{24,0};
			case 'Z': return new int[]{25,0};

			case 'a': return new int[]{0,1};
			case 'b': return new int[]{1,1};
			case 'c': return new int[]{2,1};
			case 'd': return new int[]{3,1};
			case 'e': return new int[]{4,1};
			case 'f': return new int[]{5,1};
			case 'g': return new int[]{6,1};
			case 'h': return new int[]{7,1};
			case 'i': return new int[]{8,1};
			case 'j': return new int[]{9,1};
			case 'k': return new int[]{10,1};
			case 'l': return new int[]{11,1};
			case 'm': return new int[]{12,1};
			case 'n': return new int[]{13,1};
			case 'o': return new int[]{14,1};
			case 'p': return new int[]{15,1};
			case 'q': return new int[]{16,1};
			case 'r': return new int[]{17,1};
			case 's': return new int[]{18,1};
			case 't': return new int[]{19,1};
			case 'u': return new int[]{20,1};
			case 'v': return new int[]{21,1};
			case 'w': return new int[]{22,1};
			case 'x': return new int[]{23,1};
			case 'y': return new int[]{24,1};
			case 'z': return new int[]{25,1};
			
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
			case '*': return new int[]{0,0};
			case '(': return new int[]{0,0};
			case ')': return new int[]{0,0};
			case '-': return new int[]{0,0};
			case '_': return new int[]{0,0};
			case '+': return new int[]{0,0};
			case '=': return new int[]{0,0};
			
			case '.': return new int[]{0,0};
			case ',': return new int[]{0,0};
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
