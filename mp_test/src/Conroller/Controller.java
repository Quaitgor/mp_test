package Conroller;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Models.Unit;
import Reader.XMLReader;

public class Controller implements Runnable{
	public boolean isServer = false;
	private XMLReader reader;
	private Vector<Unit> basicUnitsBP;
	
	public Controller(){
		reader = new XMLReader();
		
		File folder = new File("xml/blueprints/basic");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	try {
					reader.readXML(file.getAbsolutePath());
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	//System.out.println(file.getName());
		    }
		}


	}

	public void testing(){
		System.out.println("testestestestest");
	}

	public void run() {
		System.out.println("Init Controller");
		/*
		while(true){
			try {
				testing();
				
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}
}
