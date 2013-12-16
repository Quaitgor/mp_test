package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;
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
	
	public static TreeMap<String, TreeMap<String, StringWriter>> library;
	
	
	
	
	
	
	public static TreeMap<String, StringWriter> basicUnitsBP;
	public static TreeMap<String, StringWriter> graphics;
	public static TreeMap<String, StringWriter> weapons;
	public static TreeMap<String, StringWriter> projectiles;
	public static TreeMap<String, StringWriter> texts;
	public static TreeMap<String, StringWriter> fonts;
	
	
	
	public static boolean isJarFile = false;
	public CodeSource srcJar = null;
	public String jarPath = "";
	public static ResourceBundle config = ResourceBundle.getBundle("config");

	private Controller() {
		
		library = new TreeMap<String, TreeMap<String, StringWriter>>();
		TreeMap<String, StringWriter> iUnits = new TreeMap<String, StringWriter>();
		library.put("iUnits", iUnits);
		TreeMap<String, StringWriter> eUnits = new TreeMap<String, StringWriter>();
		library.put("eUnits", eUnits);
		TreeMap<String, StringWriter> iGraphics = new TreeMap<String, StringWriter>();
		library.put("iGraphics", iGraphics);
		TreeMap<String, StringWriter> eGraphics = new TreeMap<String, StringWriter>();
		library.put("eGraphics", eGraphics);
		TreeMap<String, StringWriter> iWeapons = new TreeMap<String, StringWriter>();
		library.put("iWeapons", iWeapons);
		TreeMap<String, StringWriter> eWeapons = new TreeMap<String, StringWriter>();
		library.put("eWeapons", eWeapons);
		TreeMap<String, StringWriter> iprojectiles = new TreeMap<String, StringWriter>();
		library.put("iProjectiles", iprojectiles);
		TreeMap<String, StringWriter> eprojectiles = new TreeMap<String, StringWriter>();
		library.put("eProjectiles", eprojectiles);
		TreeMap<String, StringWriter> itexts = new TreeMap<String, StringWriter>();
		library.put("iTexts", itexts);
		TreeMap<String, StringWriter> etexts = new TreeMap<String, StringWriter>();
		library.put("eTexts", etexts);
		TreeMap<String, StringWriter> ifonts = new TreeMap<String, StringWriter>();
		library.put("iFonts", ifonts);
		TreeMap<String, StringWriter> efonts = new TreeMap<String, StringWriter>();
		library.put("eFonts", efonts);
		
		
		basicUnitsBP = new TreeMap<String, StringWriter>();
		graphics = new TreeMap<String, StringWriter>();
		weapons = new TreeMap<String, StringWriter>();
		projectiles = new TreeMap<String, StringWriter>();
		texts = new TreeMap<String, StringWriter>();
		fonts = new TreeMap<String, StringWriter>();
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

	public TreeMap<String, TreeMap<String, StringWriter>> getLibrary() {
		return library;
	}

	private boolean contains(String haystack, String needle) {
		haystack = haystack == null ? "" : haystack;
		needle = needle == null ? "" : needle;
		return haystack.toLowerCase().contains(needle.toLowerCase());
	}

	private void readFolders(String path, TreeMap<String, StringWriter> map) {
		if (new File(path).exists()) {
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
		}
	}

	private void readJar(String path, TreeMap<String, StringWriter> map) {
		JarInputStream jar = null;
		JarEntry jarEntry = null;
		try {
			jar = new JarInputStream(new FileInputStream(jarPath.substring(5, jarPath.length())));
			jarEntry = jar.getNextJarEntry();
			while (jarEntry != null) {
				if (!jarEntry.isDirectory()) {
					String str = jarEntry.getName();
					if (contains(str, path)) {
						InputStream test = this.getClass().getResourceAsStream("/" + str);
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

	private void addBlueprints(String path) {
		int pos = path.lastIndexOf("/");
		String libraryName = path.substring(pos+1, path.length());
		libraryName = libraryName.substring(0, 1).toUpperCase() + libraryName.substring(1);
		pos = path.indexOf("/");
		String modPath = "mod/" + path.substring(pos+1, path.length());

		if (isJarFile) {
			readJar(path, library.get("i"+libraryName));
			readFolders(modPath, library.get("e"+libraryName));
		}else{
			readFolders(path, library.get("i"+libraryName));
			readFolders(modPath, library.get("e"+libraryName));
		}
	}
	
	/*
	private void addBlueprints(String path, TreeMap<String, StringWriter> map) {
		// if Jar File
		if (isJarFile) {
			readJar(path, map);
		}
		readFolders(path, map);
	}
	*/
	
	private void initBlueprints() {
		

		addBlueprints(config.getString("source.b.units"));
		addBlueprints(config.getString("source.b.graphics"));
		addBlueprints(config.getString("source.b.weapons"));
		addBlueprints(config.getString("source.b.projectiles"));
		addBlueprints(config.getString("source.b.fonts"));
		addBlueprints(config.getString("source.b.texts"));
		
		/*
		addBlueprints(config.getString("source.blueprints") + "/basic", basicUnitsBP);
		addBlueprints(config.getString("source.blueprints") + "/graphics", graphics);
		addBlueprints(config.getString("source.blueprints") + "/weapons", weapons);
		addBlueprints(config.getString("source.blueprints") + "/projectiles", projectiles);
		addBlueprints(config.getString("source.blueprints") + "/fonts", fonts);
		addBlueprints(config.getString("source.texts"), texts);
		*/
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
		
		jxml.JavaToXML(y, x.getClass(), "D:/temp/test2");
        //editor.write(new FileWriter("D:/temp/test.xml"));

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

	private static Basic spawn(TreeMap<String, StringWriter> collection, String name, double x, double y, Class<?> contextClass) {
		Basic newUnit = (Basic) jxml.XMLtoJava((StringWriter) collection.get(name), contextClass);
		newUnit.setPosition(x, y);
		return newUnit;
	}

}
