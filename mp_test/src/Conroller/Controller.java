package Conroller;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import Graphics.TextWriter;
import Production.Basic;
import Production.Unit;
import Reader.JavaAndXML;

public class Controller{
	public boolean isServer = false;
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	public static boolean showHitbox = false;
	public static HashMap<String, StringWriter> basicUnitsBP;
	public static HashMap<String, StringWriter> graphics;
	public static HashMap<String, StringWriter> weapons;
	public static HashMap<String, StringWriter> projectiles;
	
	public Controller(){
		basicUnitsBP = new HashMap<String, StringWriter>();
		graphics = new HashMap<String, StringWriter>();
		weapons = new HashMap<String, StringWriter>();
		projectiles = new HashMap<String, StringWriter>();
		initBlueprints();
	}

	private void addBlueprints(String path, HashMap<String, StringWriter> map){
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			StringWriter stringWriter = jxml.readXML(file.getAbsolutePath());
			String mapKey = file.getName();
			int pos = mapKey.lastIndexOf(".");
			if (pos > 0) {
				mapKey = mapKey.substring(0, pos);
			}
			map.put(mapKey, stringWriter);
		}
	}
	
	private void initBlueprints(){
		addBlueprints("xml/blueprints/basic", basicUnitsBP);
		addBlueprints("xml/blueprints/graphics", graphics);
		addBlueprints("xml/blueprints/weapons", weapons);
		addBlueprints("xml/blueprints/projectiles", projectiles);
		
		//Tests
		Unit x = (Unit) spawn(basicUnitsBP, "enemy_red", 150, 150, Unit.class);
		Unit y = (Unit) spawn(basicUnitsBP, "player", 240, 240, Unit.class);
		x.setPosition(400, 100);
		y.setPosition(400, 300);
		/*
		TextWriter t = new TextWriter();
		t.setPosition(400, 200);
		t.writeText("ONLY CAPITAL LETTERS FOR NOW|LETS ROCK");
		*/
		//showHitbox = true;
		
		//Unit u2 = (Unit) jxml.XMLtoJava(basicUnitsBP.get("enemy_red"), Unit.class);
		//u.chat();
	}
	
	public static Basic spawn(HashMap<String, StringWriter> collection, String name, int x, int y, Class<?> contextClass){
		Basic newUnit = (Basic) jxml.XMLtoJava( (StringWriter)collection.get(name), contextClass);
		newUnit.setPosition(x,y);
		return newUnit;
	}

}
