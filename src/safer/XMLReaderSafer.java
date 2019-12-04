package safer;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

/**
 * Diese Klasse ließt ein XML-File aus und gibt die Daten in einem String formatiert zurück.
 * @author Florian Safer
 * 2019-12-03
 */
public class XMLReaderSafer {
    /**
     * Diese Methode bekommt das nötige eingelesene Document über die untenstehende Methode, sucht sich den gewünschten Lehrer
     * sowie seine Fächer aus dem XML Tree und formatiert diesen in einem String, welcher dann zurückgegeben wird.
     * @param file Der Speicherort des XML Files
     * @param teacherid Welcher Lehrer gewünscht ist
     * @return Der Lehrer sowie seine Fächer als String formatiert
     */
    public static String parseXML(String file, String teacherid) {
        Document docXML = getDocument(file);
        XPathFactory xpf = XPathFactory.newInstance();
        XPath p = xpf.newXPath();
        Element teacher = null;
        NodeList n = null;
        try{
            teacher = (Element) p.compile("/tgm/lehrer[@id = '"+teacherid+"']").evaluate(docXML, XPathConstants.NODE);
            n = (NodeList) p.compile("/tgm/lehrer[@id = '"+teacherid+"']//gegenstand").evaluate(docXML, XPathConstants.NODESET);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < n.getLength();i++){
            Element tmp =(Element) n.item(i);
            b.append(tmp.getTextContent());
            b.append(", ");
        }
        return teacher.getElementsByTagName("vorname").item(0).getTextContent() +" "+ teacher.getElementsByTagName("nachname").item(0).getTextContent() +": "+ b.toString();
    }

    /**
     * Diese Methode baut aus dem XML File einen XML Tree, welcher über xPath einfach ausgelesen werden kann
     * @param url Welches File die Methode einlesen soll
     * @return Den eingelesenen XML-Tree
     */
    private static Document getDocument(String url){
        Document d = null;
        try{
             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
             factory.setIgnoringComments(true);
             factory.setIgnoringElementContentWhitespace(true);

             DocumentBuilder builder = factory.newDocumentBuilder();
             d = builder.parse(new File(url));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return d;
    }
}
