package graphics;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.ResourceLoader;

import controller.Controller;

import observer.DeltaUpdater;
import observer.Observer;
import production.Basic;
import read.JavaAndXML;



public class TextWriter extends Basic implements Observer{
	TextureLayer letters;
	private static TextWriter TW = new TextWriter();
	public static HashMap<Integer, TextBlock> textBlocks;
	public static HashMap<String, TrueTypeFont> fonts;
	
	private boolean antiAlias = false;
	private SpriteFont spritefont;
	private static boolean setup = false;

	private TextWriter(){
		spritefont = new SpriteFont();
		letters = new TextureLayer("alphabet", this);
		textBlocks = new HashMap<Integer, TextBlock>();
		fonts = new HashMap<String, TrueTypeFont>();
		
		DeltaUpdater.register(this);

		/*
		// load a default java font
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, antiAlias);
		// load font from file
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("res/font/computer_pixel-7.ttf");
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(40f); // set font size
			font2 = new TrueTypeFont(awtFont2, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public static TextWriter getInstance(){
		if(!setup){
			setup = true;

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
		String text = tB.getText(delta);
		if(text != null){
			x = tB.getX();
			y = tB.getY();
			int limit = tB.getLimit();
			String rest = "";
			double nrLines  = Math.floor(text.length()/limit);
			if(text.substring((int)nrLines*limit, text.length()).length() <= 0){
				nrLines--;
			}
			TextureImpl.bindNone();
			for(int i=0; i<nrLines; i++){
				String line = text.substring(i*limit, (i+1)*limit);
				double dx = nrLines-i;
				fonts.get(tB.getFont()).drawString((float)x, (float)(y-(dx*fonts.get(tB.getFont()).getHeight())), line, Color.white);
			}
			rest = text.substring((int)(nrLines)*limit, text.length());
			fonts.get(tB.getFont()).drawString((float)x, (float)y, rest, Color.white);
		}
	}
	
	private void writeInSprite(TextBlock tB, double delta){
		String text = tB.getText(delta);
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
}
