package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import production.*;

public class JavaAndXML {

	private static JavaAndXML jxml = null;

	private JavaAndXML() {
	}

	public static JavaAndXML getInstance() {
		if (jxml == null) {
			jxml = new JavaAndXML();
		}
		return jxml;
	}

	public File JavaToXML(Object obj, Class<?> objClass, String fullpath) {
		File createdFile = null;
		try {
			JAXBContext context = JAXBContext.newInstance(objClass);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			createdFile = new File(fullpath + ".xml");
			m.marshal(obj, createdFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return createdFile;
	}

	public Object XMLtoJava(StringWriter sw, Class<?> objClass) {
		JAXBContext jc;
		Object obj = null;
		try {
			jc = JAXBContext.newInstance(objClass);
			Unmarshaller u = jc.createUnmarshaller();
			obj = u.unmarshal(new StringReader(sw.toString()));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		((DataInit) obj).init();
		return obj;
	}

	public StringWriter readXML(InputStream inputStream) {
		StringWriter writer = new StringWriter();
		char[] buffer = new char[10240];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
			writer.close();
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
}