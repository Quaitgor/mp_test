package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import production.Basic;
import production.Unit;
import reader.JavaAndXML;
import writer.TextBlock;
import writer.TextWriter;

public class Controller {
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	private static Controller controller = null;
	// public boolean isServer = false;
	public static boolean showHitbox = false;
	public static HashMap<String, StringWriter> basicUnitsBP;
	public static HashMap<String, StringWriter> graphics;
	public static HashMap<String, StringWriter> weapons;
	public static HashMap<String, StringWriter> projectiles;
	public static HashMap<String, StringWriter> texts;
	public static HashMap<String, StringWriter> fonts;
	public static boolean isJarFile = false;
	public static String resPath = "res/";
	public CodeSource srcJar = null;
	public String jarPath = "";

	private Controller() {
		basicUnitsBP = new HashMap<String, StringWriter>();
		graphics = new HashMap<String, StringWriter>();
		weapons = new HashMap<String, StringWriter>();
		projectiles = new HashMap<String, StringWriter>();
		texts = new HashMap<String, StringWriter>();
		fonts = new HashMap<String, StringWriter>();
		CodeSource src = getClass().getProtectionDomain().getCodeSource();
		if (src.getLocation().toString().endsWith(".jar")) {
			isJarFile = true;
			srcJar = src;
			jarPath = srcJar.getLocation().toString();
			jarPath = jarPath.substring(5, jarPath.length());
		}
		initBlueprints();
	}

	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	private boolean contains(String haystack, String needle) {
		haystack = haystack == null ? "" : haystack;
		needle = needle == null ? "" : needle;
		return haystack.toLowerCase().contains(needle.toLowerCase());
	}

	private void readFolders(String path, HashMap<String, StringWriter> map) {
		path = "xml/" + path;
		if (new File(path).exists()) {
			System.out.println("exist: " + path);
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
				StringWriter stringWriter = null;
				try {
					stringWriter = jxml.readXML(new FileInputStream(file
							.getAbsolutePath()));
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
		} else {
			System.out.println("no exist: " + path);
		}
	}

	private void readJar(String path, HashMap<String, StringWriter> map) {
		JarInputStream jar = null;
		JarEntry jarEntry = null;
		try {
			jar = new JarInputStream(new FileInputStream(jarPath.substring(5,
					jarPath.length())));
			jarEntry = jar.getNextJarEntry();
			while (jarEntry != null) {
				if (!jarEntry.isDirectory()) {
					String str = jarEntry.getName();
					if (contains(str, path)) {
						InputStream test = this.getClass().getResourceAsStream(
								"/" + str);
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

	private void addBlueprints(String path, HashMap<String, StringWriter> map) {
		// if Jar File
		if (isJarFile) {
			readJar(path, map);
		}
		readFolders(path, map);
	}

	private void initBlueprints() {
		addBlueprints("blueprints/basic", basicUnitsBP);
		addBlueprints("blueprints/graphics", graphics);
		addBlueprints("blueprints/weapons", weapons);
		addBlueprints("blueprints/projectiles", projectiles);
		addBlueprints("blueprints/font", fonts);
		addBlueprints("text", texts);
	}

	public void start() {
		TextWriter.getInstance();
		Unit x = (Unit) spawn(basicUnitsBP, "enemy_red", 150, 150, Unit.class);
		Unit y = (Unit) spawn(basicUnitsBP, "player", 240, 240, Unit.class);
		x.setPosition(400, 100);
		y.setPosition(400, 300);
		// Tests
		jxml.XMLtoJava(Controller.texts.get("test1"), TextBlock.class);
		jxml.XMLtoJava(Controller.texts.get("test2"), TextBlock.class);

		/*
		 * TextBlock testTest2 = new TextBlock("test2", "" +
		 * "Lets test this a second text block thats slower\\w8.\\w8.\\w8. \\w8\\w8ok it works"
		 * , 500); testTest2.setPos(500, 500); testTest2.setState(true);
		 * 
		 * 
		 * InputControl controller = InputControl.getInstance(); /*
		 * t.setPosition(400, 200);
		 * t.writeText("ONLY CAPITAL LETTERS FOR NOW|LETS ROCK");
		 * 
		 * showHitbox = true;
		 * 
		 * // Unit u2 = (Unit) jxml.XMLtoJava(basicUnitsBP.get("enemy_red"), //
		 * Unit.class); // u.chat();
		 */
	}

	private static Basic spawn(HashMap<String, StringWriter> collection,
			String name, double x, double y, Class<?> contextClass) {
		Basic newUnit = (Basic) jxml.XMLtoJava(
				(StringWriter) collection.get(name), contextClass);
		newUnit.setPosition(x, y);
		return newUnit;
	}

}
