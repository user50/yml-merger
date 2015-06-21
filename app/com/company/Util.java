package com.company;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.*;

public class Util {

    private static final String DTD_DECLARATION = "<!DOCTYPE yml_catalog SYSTEM \"shops.dtd\">\n";

    public static void marshal(Object t, OutputStream  outputStream, String encoding) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", DTD_DECLARATION);

        marshaller.marshal( t, outputStream );
    }

    public static void marshal(Object t, String  fileName, String encoding) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", DTD_DECLARATION);

        marshaller.marshal( t, new FileOutputStream(fileName) );
    }

    public static <T> T unmarshal(InputStream inputStream, Class<T> tClas, String encoding) throws JAXBException, SAXException, ParserConfigurationException, FileNotFoundException, UnsupportedEncodingException {

        JAXBContext jc = JAXBContext.newInstance(tClas);

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        spf.setFeature("http://xml.org/sax/features/validation", false);

        Reader reader = new InputStreamReader(inputStream, encoding);

        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
        InputSource inputSource = new InputSource(reader);

        SAXSource source = new SAXSource(xmlReader, inputSource);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (T) unmarshaller.unmarshal(source);
    }

    public static <T> T unmarshal(String fileName, Class<T> tClas, String encoding ) throws FileNotFoundException, UnsupportedEncodingException, SAXException, ParserConfigurationException, JAXBException {
        return unmarshal(new FileInputStream(fileName), tClas, encoding);
    }




}
