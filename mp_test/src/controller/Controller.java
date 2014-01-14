package controller;

import explorer.ExplorerWindow;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.CodeSource;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.swing.SwingUtilities;

import observer.DeltaUpdater;
import production.Unit;
import reader.JavaAndXML;
import writer.TextBlock;

public class Controller {
	private static JavaAndXML jxml = JavaAndXML.getInstance();
	private static Controller controller = null;
	// public boolean isServer = false;
	public static boolean showHitbox = false;
	public static TreeMap<String, TreeMap<String, StringWriter>> library;
	public static boolean isJarFile = false;
	public static DeltaUpdater deltaUpdater;

	public ExplorerWindow expWin;
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

	public void initExplorer() {
		expWin = new ExplorerWindow();
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
		String libraryName = path.substring(pos + 1, path.length());
		libraryName = libraryName.substring(0, 1).toUpperCase() + libraryName.substring(1);
		pos = path.indexOf("/");
		String modPath = "mod/" + path.substring(pos + 1, path.length());

		if (isJarFile) {
			readJar(path, library.get("i" + libraryName));
		} else {
			readFolders(path, library.get("i" + libraryName));
		}
		readFolders(modPath, library.get("e" + libraryName));
	}

	private void initBlueprints() {
		addBlueprints(config.getString("source.b.units"));
		addBlueprints(config.getString("source.b.graphics"));
		addBlueprints(config.getString("source.b.weapons"));
		addBlueprints(config.getString("source.b.projectiles"));
		addBlueprints(config.getString("source.b.fonts"));
		addBlueprints(config.getString("source.b.texts"));
	}

	public void start() {
		// Menu etc here?

		// Test units etc follow
		Unit x = (Unit) spawn("Units", "enemy_red", Unit.class);
		Unit y = (Unit) spawn("Units", "player", Unit.class);
		if (x != null) {
			x.setPosition(400, 100);
		}
		if (y != null) {
			y.setPosition(400, 300);
		}

		jxml.XMLtoJava(getXML("Texts", "test1"), TextBlock.class);
		jxml.XMLtoJava(getXML("Texts", "test2"), TextBlock.class);
	}

	public StringWriter getXML(String sublibrary, String object) {
		StringWriter returnWriter = null;
		// check if library includes e or i, else check both librarys, external
		// has priority (mod overwrites interal data)
		if (Character.isLowerCase(sublibrary.charAt(0)) && (sublibrary.charAt(0) == 'e' || sublibrary.charAt(0) == 'i')) {
			returnWriter = library.get(sublibrary).get(object);
		} else {
			StringWriter internal = null;
			StringWriter external = null;
			internal = checkLibrary("i" + sublibrary, object);
			external = checkLibrary("e" + sublibrary, object);
			if (external != null) {
				returnWriter = external;
			} else {
				returnWriter = internal;
			}
		}
		return returnWriter;
	}

	public TreeMap<String, StringWriter> getAllXML(String sublibrary) {
		TreeMap<String, StringWriter> returnTree = new TreeMap<String, StringWriter>();

		if (Character.isLowerCase(sublibrary.charAt(0)) && (sublibrary.charAt(0) == 'e' || sublibrary.charAt(0) == 'i')) {

			Iterator<String> keySetIterator = library.get(sublibrary).keySet().iterator();
			while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				returnTree.put(key, library.get(sublibrary).get(key));
			}
		} else {
			Iterator<String> keySetIterator = library.get("i" + sublibrary).keySet().iterator();
			while (keySetIterator.hasNext()) {
				String key = keySetIterator.next();
				returnTree.put(key, library.get("i" + sublibrary).get(key));
			}

			Iterator<String> keySetIterator2 = library.get("e" + sublibrary).keySet().iterator();
			while (keySetIterator2.hasNext()) {
				String key = keySetIterator2.next();
				returnTree.put(key, library.get("e" + sublibrary).get(key));
			}
		}
		return returnTree;
	}

	public StringWriter checkLibrary(String sublibrary, String object) {
		StringWriter returnXML = null;
		TreeMap<String, StringWriter> subLibraryTreeMap = library.get(sublibrary);
		if (subLibraryTreeMap != null) {
			returnXML = subLibraryTreeMap.get(object);
		}
		return returnXML;
	}

	private Object spawn(String library, String object, Class<?> contextClass) {
		Object newUnit = null;
		StringWriter xmlOfUnit = getXML(library, object);
		if (xmlOfUnit != null) {
			newUnit = jxml.XMLtoJava(xmlOfUnit, contextClass);
		}
		return newUnit;
	}
}
