package writer;

import graphics.TextureLayer;

import java.util.HashMap;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;
import controller.Controller;

import observer.DeltaUpdater;
import observer.Observer;
import production.Basic;
import reader.JavaAndXML;



public class TextWriter extends Basic implements Observer{
	TextureLayer letters;
	private static TextWriter TW = null;
	public static HashMap<Integer, TextBlock> textBlocks;
	public static HashMap<String, TrueTypeFont> fonts;
	private SpriteFont spritefont;

	private TextWriter(){
		spritefont = new SpriteFont();
		letters = new TextureLayer("alphabet", this);
		textBlocks = new HashMap<Integer, TextBlock>();
		fonts = new HashMap<String, TrueTypeFont>();
		DeltaUpdater.registerDelta(this);
	}
	
	public static TextWriter getInstance(){
		if(TW == null){
			TW = new TextWriter();
			Iterator<String> keySetIterator = Controller.fonts.keySet().iterator();
			while(keySetIterator.hasNext()){
				String key = keySetIterator.next();
				JavaAndXML.getInstance().XMLtoJava(Controller.fonts.get(key), FontData.class);
			}
		}
		return TW;
	}

	public void addText(TextBlock newText){
		textBlocks.put(textBlocks.size(), newText);
	}

	public void addFont(String name, TrueTypeFont newFont){
		fonts.put(name, newFont);
	}
	
	public void writeText(double delta){
		Iterator<Integer> keySetIterator = textBlocks.keySet().iterator();
		while(keySetIterator.hasNext()){
			int key = keySetIterator.next();
			if(textBlocks.get(key).getState()){
				if(textBlocks.get(key).getWriteForm()){
					writeInSprite(textBlocks.get(key), delta);
				}else{
					writeInTFF(textBlocks.get(key), delta);
				}
			}
		}
	}

	private void writeInTFF(TextBlock tB, double delta){
		HashMap<Integer, String> texts = tB.getText(delta);
		if(texts != null){
			x = tB.getX();
			y = tB.getY();
			for(int i=0; i<texts.size(); i++){
				String line = texts.get(i);
				double dx = texts.size()-i;
				TextureImpl.bindNone();
				fonts.get(tB.getFont()).drawString((float)x, (float)(y-(dx*fonts.get(tB.getFont()).getHeight())), line, Color.white);
			}
		}
	}
	
	private void writeInSprite(TextBlock tB, double delta){
		HashMap<Integer, String> texts = tB.getText(delta);
		String text = texts.get(0);
		/*
		System.out.println(texts.toString());
		*/
		//TODO here change textwriter lines depending on texts HashMap Multilines!
		if(text != null){
			x = tB.getX();
			y = tB.getY();
			for(int i = 0; i < text.length(); i ++){
				if (!text.substring(i, i+1).equals("\\")){
					char a = text.charAt(i);
					int[] texCords = spritefont.getLetterCords(a);
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
	
	@Override
	public void destroyObject(){
		super.destroyObject();
		fonts.clear();
		textBlocks.clear();
		fonts = null;
		textBlocks = null;
		DeltaUpdater.unregisterDelta(this);
	}
}
