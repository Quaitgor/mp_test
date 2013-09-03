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
	private JavaAndXML jxml = JavaAndXML.getInstance();
	//private Vector<StringWriter> basicUnitsBP;

	private HashMap<String, StringWriter> basicUnitsBP;
	
	public Controller(){
		System.out.println("Init Controller");
		basicUnitsBP = new HashMap();
		initBlueprints();
		System.out.println("Controller Done");
	}
	
	private void initBlueprints(){
		File folder = new File("xml/blueprints/basic");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			StringWriter stringWriter = jxml.readXML(file.getAbsolutePath());
			System.out.println(file.getName());
			String mapKey = file.getName();
			int pos = mapKey.lastIndexOf(".");
			if (pos > 0) {
				mapKey = mapKey.substring(0, pos);
			}
			basicUnitsBP.put(mapKey, stringWriter);
		}
		//Tests
		/*
		Iterator<String> keySetIterator = basicUnitsBP.keySet().iterator();
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
			StringWriter xml = basicUnitsBP.get(key);
			Unit u = jxml.XMLtoJava(xml);
			u.chat();
		}
		*/
		Unit u = (Unit) jxml.XMLtoJava(basicUnitsBP.get("player"), Unit.class);
		//Unit u2 = (Unit) jxml.XMLtoJava(basicUnitsBP.get("enemy_red"), Unit.class);
		//u.chat();
	}

}
