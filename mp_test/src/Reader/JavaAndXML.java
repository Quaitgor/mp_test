package Reader;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Models.Unit;
 
public class JavaAndXML {
	
	private static JavaAndXML jxml = new JavaAndXML();
	
	private JavaAndXML(){}
	
	public static JavaAndXML getInstance(){
		return jxml;
	}
	
    public StringWriter JavatoXML(Class contextClass) throws Exception {
    	JAXBContext context = JAXBContext.newInstance(contextClass);
    	Marshaller m = context.createMarshaller();
    	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    	Object object = contextClass;
        StringWriter stringWriter = new StringWriter();
    	m.marshal(object, stringWriter);
    	m.marshal(object, new File("xml/blueprints/basic/1.xml"));
    	return stringWriter;
    }
    
    public Object XMLtoJava(StringWriter sw, Class objClass){
        JAXBContext jc;
        Object obj = null;
		try {
			jc = JAXBContext.newInstance(objClass);
	        Unmarshaller u = jc.createUnmarshaller();
	        obj = u.unmarshal(new StringReader(sw.toString()));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		((XMLimport)obj).init();
		return obj;
    }

	public StringWriter readXML(String filePath) {
		FileInputStream fileInputStream = null;
		StringWriter writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			fileInputStream = new FileInputStream(filePath);
			Reader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
			writer.close();
			fileInputStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return writer;
	}
}