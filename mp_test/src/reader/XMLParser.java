package reader;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

	public TreeMap<Integer, String> getTargetfromFile(InputStream file, String target) {
		TreeMap<Integer, String> returnHash = new TreeMap<Integer, String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			Element docEle = doc.getDocumentElement();
			NodeList targetList = docEle.getElementsByTagName(target);
			if (targetList != null && targetList.getLength() > 0) {
				for (int i = 0; i < targetList.getLength(); i++) {

					Node node = targetList.item(i);
					returnHash.put(returnHash.size(), node.getChildNodes().item(0).getNodeValue());
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnHash;
	}
}