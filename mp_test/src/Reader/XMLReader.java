package Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
 
public class XMLReader {


	public void readXML(String filePath) throws SAXException, IOException, ParserConfigurationException{
		File file = new File(filePath);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(file);
		NodeList nodes = doc.getElementsByTagName("unit");

		/*
	    NodeList nodeList = nodes.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	            doSomething(currentNode);
	        }
	    }
	    */
		
	}
	/*
public static void main(String[] args) {
	
 
 
  try {
 
 
  for (int i = 0; i < nodes.getLength(); i++) {
 
     Element element = (Element) nodes.item(i);
 
     NodeList name = element.getElementsByTagName("name");
     Element line = (Element) name.item(0);
 
     System.out.println("Student name: " + line.getFirstChild().getTextContent());
 
     System.out.println("Id: " + line.getAttribute("id"));
 
     NodeList age = element.getElementsByTagName("age");
     line = (Element) age.item(0);
     System.out.println("Age: " + line.getFirstChild().getTextContent());
 
     NodeList hobby = element.getElementsByTagName("hobby");
     for(int j=0;j<hobby.getLength();j++)
     {
        line = (Element) hobby.item(j);
        System.out.println("Hobby: " + line.getFirstChild().getTextContent());
     }
 
     System.out.println();
  }
  }
  catch (Exception e) {
  e.printStackTrace();
  }
  }
  */
}