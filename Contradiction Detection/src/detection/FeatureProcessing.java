package detection;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FeatureProcessing {

    private ArrayList<Opinion> opinions;
    private ArrayList<Opinion> positiveOpinions;
    private ArrayList<Opinion> negativeOpinions;
    private ArrayList<String> targets;
    private String outputFile = "opinionPairs.txt";

    public FeatureProcessing() {
        this.opinions = new ArrayList<Opinion>();
        this.positiveOpinions = new ArrayList<Opinion>();
        this.negativeOpinions = new ArrayList<Opinion>();
        this.targets = new ArrayList<String>();
    }

    public void processSentimentValues() {
        Iterator it = opinions.iterator();
        while (it.hasNext()) {
            Opinion op = (Opinion) it.next();
            if (op.getSentiment_polarity_positive() + op.getSentiment_polarity_negative() < 0) {
                negativeOpinions.add(op);
            } else {
                positiveOpinions.add(op);
            }
        }
    }

    public ArrayList<Opinion> getPositiveOpinions() {
        return this.positiveOpinions;
    }

    public ArrayList<Opinion> getNegativeOpinions() {
        return this.negativeOpinions;
    }

    /*
     * Returns a list of all the targets of the opinions
     */
    public ArrayList<String> getTargets() {
        Iterator it = opinions.iterator();
        while (it.hasNext()) {
            Opinion op = (Opinion) it.next();
            targets.add(op.getTarget());
        }
        return this.targets;
    }

    public void processOpinions() throws ParserConfigurationException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String root = "Opinions";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element rootElement = document.createElement(root);
        document.appendChild(rootElement);
        Iterator it = positiveOpinions.iterator();
        while (it.hasNext()) {
            Opinion op1 = (Opinion) it.next();
            Iterator it2 = negativeOpinions.iterator();
            while (it2.hasNext()) {
                Opinion op2 = (Opinion) it2.next();
                if (op1.getTarget() == op2.getTarget()) {
                    String element = "opinion pair";
                    String data1 = op1.getText();
                    String data2 = op2.getText();
                    Element em = document.createElement(element);
                    em.appendChild(document.createTextNode(data1));
                    em.appendChild(document.createTextNode(data2));
                    rootElement.appendChild(em);
                }
            }
        }
    }

    public void writeToFile(Document doc) throws TransformerConfigurationException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new StringWriter());

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
        transformer.transform(source, result);
        FileOutputStream fop = null;
        File file;
        try {

            file = new File("opinionPairs.txt");
            fop = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            String xmlString = result.getWriter().toString();
            System.out.println(xmlString);
            byte[] contentInBytes = xmlString.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    public void byTarget(Opinion opinion1, Opinion opinion2){
//        if ( opinion1.getTarget().equals(opinion2.getTarget()) ){
//            if(opinion1.getNegation_word() != null){
//
//            }
//        }
//    }
//    public void byHolder(Opinion opinion1, Opinion opinion2){
//        if (opinion1.getHolder().equals(opinion2.getHolder())){
//            this.byTarget(opinion1, opinion2);
//        }
//    }
}
