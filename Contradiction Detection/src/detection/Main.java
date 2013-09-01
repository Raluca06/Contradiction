package detection;

import java.text.ParseException;
import java.util.ArrayList;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import alchemy.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Main {

    private static ArrayList<Opinion> opinions;
    private SentiStrengthApp senti;
    private static final String opinionFile = "opinionExample.xml";
    private static final String drsfile = "output1.xml";
    private static final String drsfile2 = "output2.xml";
    private static Main instance = null;

    protected Main() {
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public static ArrayList<Opinion> getOpinions() {
        return opinions;
    }

    public static void setOpinions(ArrayList<Opinion> opinions) {
        Main.opinions = opinions;
    }

    public ArrayList<Opinion> parseXMLFile(String filename) throws ParseException {

        ArrayList<Opinion> ops = new ArrayList<Opinion>();
        File xmlFile = new File(filename);
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("opinion");

            for (int i = 0; i < list.getLength(); i++) {
                Node opinionNode = list.item(i);
                Opinion op = new Opinion();

                if (opinionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) opinionNode;
                    op.setText(element.getElementsByTagName("text").item(0).getTextContent());
                    op.setClaim(element.getElementsByTagName("claim").item(0).getTextContent());
                    op.setHolder(element.getElementsByTagName("holder").item(0).getTextContent());
                    op.setDependency_structure(element.getElementsByTagName("dependency_structure").item(0).getTextContent());
                    op.setNegation_word(element.getElementsByTagName("negation_word").item(0).getTextContent());
                    op.setOpinion_bearing_word(element.getElementsByTagName("opinionBearingWord").item(0).getTextContent());
//                    op.setOrganization(element.getElementsByTagName("organization").item(0).getTextContent());
//                    op.setPerson(element.getElementsByTagName("person").item(0).getTextContent());
                    op.setPhrase_structure(element.getElementsByTagName("phrase_structure").item(0).getTextContent());
//                    op.setPos_tagged(element.getElementsByTagName("pos_tagged").item(0).getTextContent());
                    op.setTarget(element.getElementsByTagName("target").item(0).getTextContent());
                    op.setReporting_verb(element.getElementsByTagName("reporting_verb").item(0).getTextContent());
//                    op.setSentiment_polarity_negative(Integer.parseInt(element.getElementsByTagName("sentiment_polarity_negative").item(0).getTextContent()));
//                    op.setSentiment_polarity_positive(Integer.parseInt(element.getElementsByTagName("sentiment_polarity_positive").item(0).getTextContent()));
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    Date date = sdf.parse(element.getElementsByTagName("time").item(0).getTextContent());
                    c.setTime(date);
                    op.setTime(c);
                }
                ops.add(op);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ops;
    }

    public void print(ArrayList<Opinion> opinions) {
        Iterator it = opinions.iterator();
        System.out.println("List length: " + opinions.size());
        while (it.hasNext()) {
            Object obj = it.next();
            Opinion op = (Opinion) obj;
            System.out.println(
                    op.getClaim() + "\n"
                    + op.getTarget() + "\n"
                    + op.getDependency_structure() + "\n"
                    + op.getHolder() + "\n"
                    + op.getNegation_word() + "\n"
                    + op.getOpinion_bearing_word() + "\n"
                    + op.getOrganization() + "\n"
                    + op.getPerson() + "\n"
                    + op.getPhrase_structure() + "\n"
                    + op.getPos_tagged() + "\n"
                    + op.getReporting_verb() + "\n"
                    + op.getPolarity_neg() + "\n"
                    + op.getPolarity_pos() + "\n"
                    + op.getSentiment_polarity_negative() + "\n"
                    + op.getSentiment_polarity_positive() + "\n"
                    + op.getTime() + "\n");
        }
    }

    public void setSentimentValues(ArrayList<Opinion> opinions) {
        Iterator it = opinions.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            Opinion obj = (Opinion) o;
            String sentiment_values = this.senti.getPolarities(obj.getClaim());
            String polarities[] = sentiment_values.split(" ");
            obj.setPolarity_pos(Integer.parseInt(polarities[0]));
            obj.setPolarity_neg(Integer.parseInt(polarities[1]));
        }
    }

    public void initializeOpinions(String filename) throws Exception {
        ArrayList<Opinion> data = parseXMLFile(filename);
        setOpinions(data);
        this.senti = new SentiStrengthApp();
        setSentimentValues(opinions);
//        this.antonymsApplication = new AntonymsDetection();
    }

    public void setSentimentValue(Opinion op) {
        AlchemyAPI alchemy = AlchemyAPI.GetInstanceFromString("b2ae9b9ddaf5fd0a4abf0ae5d0ea0222b80b949f");
        try {
            Document doc = alchemy.TextGetTextSentiment(op.getClaim());
            printDocument(doc, System.out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

    transformer.transform(new DOMSource(doc),
         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
}


    public static void main(String[] args) throws Exception {
        Main application = Main.getInstance();
        XmlParsing x = new XmlParsing();
        Negation neg = new Negation();
        Opinion op1 = new Opinion();
        
        Opinion op2 = new Opinion();
        op1.setDrs(x.generateDRS(drsfile));
        op1.setTokens(x.generateTokens(drsfile));
        
//        op2.setDrs(x.generateDRS(drsfile2));
//        op2.setTokens(x.generateTokens(drsfile2));
//        System.out.println(op2.getDrs().get(1).getLabel());
        neg.setOpinion1(op1);
        neg.setOpinion2(op2);
//        neg.compare();
        AntonymsDetection ant = new AntonymsDetection();
//        ant.getHeadCount();
//        ant.getHeadInformation();
//        ant.getSemDist("member", "identity");
//        ant.getText();
//        ant.getSentenceSimilarity();
        ant.getWordInfo("member", "N.");
//        ant.testRoget("desire", "wish");
       // application.initializeOpinions(opinionFile);
//        System.out.println(opinions.get(0).getClaim());
       // application.setSentimentValue(opinions.get(0));
        // application.print(opinions);
        //application.negationCheck();
        //application.antonymsApplication.check(opinions.get(0), opinions.get(1));
        //application.numericMismatch();
    }
}
