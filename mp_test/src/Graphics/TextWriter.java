package Graphics;

import Conroller.Controller;
import Models.GrahpicElement;
import Models.Unit;
import Observer.DeltaUpdater;
import Observer.Observer;
import Reader.JavaAndXML;


public class TextWriter extends GrahpicElement implements Observer{
	DrawingTexture letters;
	private JavaAndXML jxml = JavaAndXML.getInstance();
	private double x;
	private double y;
	private String text;
	
	public TextWriter(){
		TextureData tex = (TextureData) jxml.XMLtoJava(Controller.graphics.get("alphabet"), TextureData.class);
		letters = new DrawingTexture(tex, this);
		DeltaUpdater.register(this);
	}
	
	public void writeText(String text){
		this.text = text;
	}
	
	public void writeText(){

		char a = text.charAt(0);
		int[] texCords = getLetterCords(a);
		letters.changeSprite(texCords);
		letters.draw();
		//for(int i = 0; i < text.length(); i ++){
		/*
			x += 20;
			if(texCords[0] >= 0 && texCords[1] >= 0){
			}
			nextchar = 'a';
			//lastchar = 'a';
			
			//if(i-1 > 0 ) lastchar = text.charAt(i-1);
			if(i+1 < text.length()) nextchar = text.charAt(i+1);
			
			if(a == '\n')
			{
				carriage++;
				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				glOrtho(0, orthowidth, orthoheight, 0, 1, -1);
				glTranslated(0,(iCharHeight+1) *carriage,0);//TODO no idea why this works
				continue;
			}
			yoffset=getyspacing(a);
			xoffset=getxspacing(a);
			if(xoffset <= 0) xoffset=getxspacing(nextchar);
			*/
		//}		
	}


	@Override
	public void update(double delta) {
		writeText();
	}
	
	public int[] getLetterCords(char a){
		switch(a){
		case 'A': return new int[]{0,0};
		case 'B': return new int[]{0,1};
		case 'C': return new int[]{0,2};
		case 'D': return new int[]{0,3};
		case 'E': return new int[]{0,4};
		case 'F': return new int[]{0,5};
		case 'G': return new int[]{0,6};
		case 'H': return new int[]{0,7};
		case 'I': return new int[]{0,8};
		case 'J': return new int[]{0,9};
		case 'K': return new int[]{0,10};
		case 'L': return new int[]{0,11};
		case 'M': return new int[]{0,12};
		case 'N': return new int[]{0,13};
		case 'O': return new int[]{0,14};
		case 'P': return new int[]{0,15};
		case 'Q': return new int[]{1,0};
		case 'R': return new int[]{1,1};
		case 'S': return new int[]{1,2};
		case 'T': return new int[]{1,3};
		case 'U': return new int[]{1,4};
		case 'V': return new int[]{1,5};
		case 'W': return new int[]{1,6};
		case 'X': return new int[]{1,7};
		case 'Y': return new int[]{1,8};
		case 'Z': return new int[]{1,9};
		
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
