package Conroller;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import Models.Unit;
import Reader.JavaAndXML;

@SuppressWarnings("unchecked")
public class Controller{
	public boolean isServer = false;
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	public static boolean showHitbox = false;
	public static HashMap<String, StringWriter> basicUnitsBP;
	public static HashMap<String, StringWriter> graphics;
	
	public Controller(){
		basicUnitsBP = new HashMap<String, StringWriter>();
		graphics = new HashMap<String, StringWriter>();
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
		
		//Tests
		Unit X = spawn(basicUnitsBP, "enemy_red", 150, 150);
		spawn(basicUnitsBP, "player", 240, 240);
		//showHitbox = true;
		
		
		try {
			jxml.JavatoXML(Unit.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Unit u2 = (Unit) jxml.XMLtoJava(basicUnitsBP.get("enemy_red"), Unit.class);
		//u.chat();
	}
	
	public static Unit spawn(HashMap<String, StringWriter> collection, String name, int x, int y){
		Unit newUnit = (Unit) jxml.XMLtoJava( (StringWriter)collection.get(name), Unit.class);
		newUnit.setPosition(x,y);
		return newUnit;
	}

}
