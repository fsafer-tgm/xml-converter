import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class parseXMLFsafer {
    public static String parseXML(String file, String teacherid) throws XPathExpressionException {
        Document docXML = getDocument(file);
        XPath p = (XPath) XPathFactory.newInstance();
        Element teacher = (Element) p.compile("/tgm/lehrer[@id = '"+teacherid+"']").evaluate(docXML, XPathConstants.NODE);
        StringBuilder b = new StringBuilder();
        NodeList n = (NodeList) p.compile("/tgm/lehrer[@id = '\"+teacherid+\"']//gegenstand").evaluate(docXML, XPathConstants.NODESET);
        for(int i = 0; i < n.getLength();i++){
            Element tmp =(Element) n.item(i);
            b.append(tmp.getTextContent());
            b.append(", ");
        }
        return teacher.getElementsByTagName("vorname").item(0).getTextContent() + teacher.getElementsByTagName("nachname").item(0).getTextContent() + b.toString();
    }

    public static Document getDocument(String url){
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
