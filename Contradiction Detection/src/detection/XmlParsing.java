package detection;

import detection.DRS.Pred;
import java.text.ParseException;
import java.util.ArrayList;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlParsing {

//    private ArrayList<Token> sentence;
//    private ArrayList<DRS> drss;
//
//
//    public ArrayList<DRS> getDrss() {
//        return drss;
//    }
//
//    public ArrayList<Token> getSentence() {
//        return sentence;
//    }
    public XmlParsing() {
//        this.sentence = new ArrayList<Token>();
//        this.drss = new ArrayList<DRS>();
    }

    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc),
                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    public ArrayList<Token> generateTokens(String filename) throws ParseException {

        File xmlFile = new File(filename);
        Document doc = null;
        ArrayList<Token> tokens = new ArrayList<Token>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
//            printDocument(doc, System.out);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("tagtoken");
            for (int i = 0; i < list.getLength(); i++) {
                Node tagNode = list.item(i);
                Token tok = new Token();
                if (tagNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) tagNode;
                    tok.setId(element.getAttribute("xml:id"));
                    tok.setLemma(element.getElementsByTagName("tag").item(2).getTextContent());
                    tok.setToken(element.getElementsByTagName("tag").item(1).getTextContent());
                    tok.setPos(element.getElementsByTagName("tag").item(2).getTextContent());
                    tok.setNamex(element.getElementsByTagName("tag").item(3).getTextContent());
                }
                tokens.add(tok);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return tokens;
        }
    }

    public ArrayList<DRS> generateDRS(String filename) throws ParseException {
        File xmlFile = new File(filename);
        Document doc = null;
        ArrayList<DRS> drss = new ArrayList<DRS>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
//            printDocument(doc, System.out);
            doc.getDocumentElement().normalize();
            NodeList drsList = doc.getElementsByTagName("drs");
            for (int i = 0; i < drsList.getLength(); i++) {
                Node drsNode = drsList.item(i);
                DRS drs = new DRS();
                if (drsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) drsNode;
                    drs.setType(element.getAttribute("type"));
                    drs.setLabel(element.getAttribute("label"));
                    String domain = element.getElementsByTagName("dr").item(0).getAttributes().getNamedItem("name").getTextContent();
                    String ax = element.getElementsByTagName("named").item(0).getAttributes().getNamedItem("symbol").getTextContent();
                    int preds = element.getElementsByTagName("pred").getLength();
                    if (preds > 0) {
                        for (int j = 0; j < preds; j++) {
                            DRS.Pred instance = drs.new Pred();
                            String cond = element.getElementsByTagName("pred").item(j).getAttributes().getNamedItem("symbol").getTextContent();
                            instance.setSymbol(cond);
                            instance.setArg(element.getElementsByTagName("pred").item(j).getAttributes().getNamedItem("arg").getTextContent());
                            instance.setType(element.getElementsByTagName("pred").item(j).getAttributes().getNamedItem("type").getTextContent());
                            instance.setSense(element.getElementsByTagName("pred").item(j).getAttributes().getNamedItem("sense").getTextContent());
                        }
                    }

                    int rels = element.getElementsByTagName("rel").getLength();
//                    System.out.println(rels);
                    if (rels > 0) {
                        for (int j = 0; j < preds; j++) {
                            DRS.Rel relation = drs.new Rel();
//                            String cond = element.getElementsByTagName("rel").item(j).getAttributes().getNamedItem("arg1").getTextContent();
//                            instance.setArg1(element.getElementsByTagName("rel").item(j).getAttributes().getNamedItem("arg1").getTextContent());
//                            instance.setArg2(element.getElementsByTagName("rel").item(j).getAttributes().getNamedItem("arg2").getTextContent());
//                            instance.setSymbol(element.getElementsByTagName("rel").item(j).getAttributes().getNamedItem("symbol").getTextContent());
//                            instance.setSense(element.getElementsByTagName("rel").item(j).getAttributes().getNamedItem("sense").getTextContent());
//                            System.out.println(instance.getSymbol());
                        }
                    }
                }
                drss.add(drs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return drss;
        }
    }
}
