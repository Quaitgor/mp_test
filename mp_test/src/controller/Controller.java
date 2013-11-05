package controller;

import graphics.TextBlock;
import graphics.TextWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import production.Basic;
import production.Unit;
import reader.JavaAndXML;
import testing.InputControl;


public class Controller {
	public boolean isServer = false;
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	public static boolean showHitbox = false;
	public static HashMap<String, StringWriter> basicUnitsBP;
	public static HashMap<String, StringWriter> graphics;
	public static HashMap<String, StringWriter> weapons;
	public static HashMap<String, StringWriter> projectiles;

	public Controller() {
		basicUnitsBP = new HashMap<String, StringWriter>();
		graphics = new HashMap<String, StringWriter>();
		weapons = new HashMap<String, StringWriter>();
		projectiles = new HashMap<String, StringWriter>();
		initBlueprints();
	}

	public boolean contains(String haystack, String needle) {
		haystack = haystack == null ? "" : haystack;
		needle = needle == null ? "" : needle;
		return haystack.toLowerCase().contains(needle.toLowerCase());
	}

	private void readFolders(String path, HashMap<String, StringWriter> map){
		path = "xml/"+path;
		if (new File(path).exists()) {
			System.out.println("exist: "+path);
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				StringWriter stringWriter = null;
				try {
					stringWriter = jxml.readXML(new FileInputStream(file.getAbsolutePath()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				String mapKey = file.getName();
				int pos = mapKey.lastIndexOf(".");
				if (pos > 0) {
					mapKey = mapKey.substring(0, pos);
				}
				map.put(mapKey, stringWriter);
			}
		}else{
			System.out.println("no exist: "+path);
		}
	}
	
	private void addBlueprints(String path, HashMap<String, StringWriter> map) {
		CodeSource src = getClass().getProtectionDomain().getCodeSource();
		//if Jar File
		if (src.getLocation().toString().endsWith(".jar")) {
			JarInputStream jar = null;
			JarEntry jarEntry = null;
			try {
				String jarPath = src.getLocation().toString();
				jar = new JarInputStream(new FileInputStream(jarPath.substring(5, jarPath.length())));
				jarEntry = jar.getNextJarEntry();
				while (jarEntry != null) {
					if (!jarEntry.isDirectory()){
						String str = jarEntry.getName();
						if (contains(str, path)) {
							InputStream test = this.getClass().getResourceAsStream("/"+str);  
							StringWriter stringWriter = jxml.readXML(test);
							String mapKey = new File(str).getName();
							int pos = mapKey.lastIndexOf(".");
							if (pos > 0) {
								mapKey = mapKey.substring(0, pos);
							}
							map.put(mapKey, stringWriter);
						}
					}
					jarEntry = jar.getNextJarEntry();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		readFolders(path, map);
	}

	private void initBlueprints() {
		addBlueprints("blueprints/basic", basicUnitsBP);
		addBlueprints("blueprints/graphics", graphics);
		addBlueprints("blueprints/weapons", weapons);
		addBlueprints("blueprints/projectiles", projectiles);

		// Tests
		
		Unit x = (Unit) spawn(basicUnitsBP, "enemy_red", 150, 150,
		Unit.class); Unit y = (Unit) spawn(basicUnitsBP, "player", 240, 240,
		Unit.class); x.setPosition(400, 100); y.setPosition(400, 300);
		
		TextWriter t = TextWriter.getInstance();
		
		TextBlock testTest = new TextBlock("test", "" +
		"I HAVE TO WAIT \\w8WAIT \\w8\\w8WAIT, Lets test a NEW LINE\\lb" +
		"next LINE starts here");
		testTest.setWaitTime(800);
		testTest.setPos(300, 100);
		testTest.setState(true);
		
		TextBlock testTest2 = new TextBlock("test2", "" +
		"Lets test this a second text block thats slower\\w8.\\w8.\\w8. \\w8\\w8ok it works", 500);
		testTest2.setPos(500, 500);
		testTest2.setState(true);
		
		
		InputControl controller = InputControl.getInstance();
		/*
		t.setPosition(400, 200);
		t.writeText("ONLY CAPITAL LETTERS FOR NOW|LETS ROCK");
		
		// showHitbox = true;

		// Unit u2 = (Unit) jxml.XMLtoJava(basicUnitsBP.get("enemy_red"),
		// Unit.class);
		// u.chat();
		*/
	}

	public static Basic spawn(HashMap<String, StringWriter> collection, String name, double x, double y, Class<?> contextClass) {
		Basic newUnit = (Basic) jxml.XMLtoJava((StringWriter) collection.get(name), contextClass);
		newUnit.setPosition(x, y);
		return newUnit;
	}

}
