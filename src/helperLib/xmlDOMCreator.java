package helperLib;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class xmlDOMCreator {
    private String xmlElementName;
    private String xmlFileNameAndPath;

    public xmlDOMCreator(String xmlElementName, String xmlFileNameAndPath){
        this.xmlElementName = xmlElementName;
        this.xmlFileNameAndPath = xmlFileNameAndPath;
    }

    public String xmlElementString(){
        File fXmlFile = new File(this.xmlFileNameAndPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            System.out.println("Err 1");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Err 2");
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            System.out.println("Err 3");
            e.printStackTrace();
        }
        NodeList nodes = doc.getElementsByTagName("resources");
        System.out.println(doc.getChildNodes());
        Element element = (Element) nodes.item(0);
        NodeList nameList = element.getElementsByTagName("name");
        for (int i = 0; i < nameList.getLength(); i++){
            Element nameElement = (Element) nameList.item(i);
            if(nameElement.getAttributes().getNamedItem(this.xmlElementName).getNodeName() == xmlElementName){
                return nameElement.getAttributes().getNamedItem(this.xmlElementName).getNodeValue().toString();
            }
        }
        return "Not Found";
    }
}
