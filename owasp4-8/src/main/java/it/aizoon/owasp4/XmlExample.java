package it.aizoon.owasp4;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlExample {

  public void parseXml(String file)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    String FEATURE = null;
//
//    // This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all
//    // XML entity attacks are prevented
//    // Xerces 2 only - http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
//    FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
//    dbf.setFeature(FEATURE, true);
//
//    // If you can't completely disable DTDs, then at least do the following:
//    // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
//    // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
//    // JDK7+ - http://xml.org/sax/features/external-general-entities
//    //This feature has to be used together with the following one, otherwise it will not protect you from XXE for sure
//    FEATURE = "http://xml.org/sax/features/external-general-entities";
//    dbf.setFeature(FEATURE, false);
//
//    // Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
//    // Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
//    // JDK7+ - http://xml.org/sax/features/external-parameter-entities
//    //This feature has to be used together with the previous one, otherwise it will not protect you from XXE for sure
//    FEATURE = "http://xml.org/sax/features/external-parameter-entities";
//    dbf.setFeature(FEATURE, false);
//
//    // Disable external DTDs as well
//    FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
//    dbf.setFeature(FEATURE, false);
//
//    // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
//    dbf.setXIncludeAware(false);
//    dbf.setExpandEntityReferences(false);
//
//    // And, per Timothy Morgan: "If for some reason support for inline DOCTYPEs are a requirement, then
//    // ensure the entity settings are disabled (as shown above) and beware that SSRF attacks
//    // (http://cwe.mitre.org/data/definitions/918.html) and denial
//    // of service attacks (such as billion laughs or decompression bombs via "jar:") are a risk."
//
//    // remaining parser logic

  // Load XML file or stream using a XXE agnostic configured parser...
    DocumentBuilder safebuilder = dbf.newDocumentBuilder();

    Document document = safebuilder.parse(new File(file));
    NodeList nodeList = document.getElementsByTagName("*");
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      System.out.println(node);
    }


    //print string
    StringWriter sw = new StringWriter();
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

    transformer.transform(new DOMSource(document), new StreamResult(sw));
    System.out.println(sw.toString());
  }

  public static void main(String[] args)
      throws ParserConfigurationException, IOException, SAXException, TransformerException {
    XmlExample serializator = new XmlExample();
    serializator.parseXml("C:\\tmp\\evil.xml");
  }

}
