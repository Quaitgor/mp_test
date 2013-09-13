package Graphics;

import Conroller.Controller;
import GraphicalElements.GraphicElement;
import GraphicalElements.Unit;
import Observer.DeltaUpdater;
import Observer.Observer;
import Reader.JavaAndXML;


public class TextWriter extends GraphicElement implements Observer{
	DrawingTexture letters;
	private JavaAndXML jxml = JavaAndXML.getInstance();
	private String text;
	private int startX = 0;
	private int startY = 0;
	
	public TextWriter(){
		TextureData tex = (TextureData) jxml.XMLtoJava(Controller.graphics.get("alphabet"), TextureData.class);
		letters = new DrawingTexture(tex, this);
		DeltaUpdater.register(this);
	}
	
	public void writeText(String text){
		this.text = text;
		startX = x;
		startY = y;
	}
	
	
	
	//todo:
	// system /lb, /w8 etc for commands in text
	// text animated write
	
	public void writeText(){
		x = startX;
		y = startY;
		for(int i = 0; i < text.length(); i ++){
			char a = text.charAt(i);
			int[] texCords = getLetterCords(a);
			letters.changeSprite(texCords);
			
			if(text.charAt(i) == '|'){
				y += 20;
				x = startX;
			}else{
				letters.draw();
				x += 16;
			}
		}		
	}


	@Override
	public void update(double delta) {
		writeText();
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
		
		case 'a': return new int[]{2,0};
		case 'b': return new int[]{2,1};
		case 'c': return new int[]{2,2};
		case 'd': return new int[]{2,3};
		case 'e': return new int[]{2,4};
		case 'f': return new int[]{2,5};
		case 'g': return new int[]{2,6};
		case 'h': return new int[]{2,7};
		case 'i': return new int[]{2,8};
		case 'j': return new int[]{2,9};
		case 'k': return new int[]{2,10};
		case 'l': return new int[]{2,11};
		case 'm': return new int[]{2,12};
		case 'n': return new int[]{2,13};
		case 'o': return new int[]{2,14};
		case 'p': return new int[]{2,15};
		case 'q': return new int[]{3,0};
		case 'r': return new int[]{3,1};
		case 's': return new int[]{3,2};
		case 't': return new int[]{3,3};
		case 'u': return new int[]{3,4};
		case 'v': return new int[]{3,5};
		case 'w': return new int[]{3,6};
		case 'x': return new int[]{3,7};
		case 'y': return new int[]{3,8};
		case 'z': return new int[]{3,9};
		
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
