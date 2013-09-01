package detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.w3c.dom.*;
import java.net.URI;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;

public class DiscourseLevelContradiction {

    public DiscourseLevelContradiction() {
    }

    public URL setURL(String sentence) throws MalformedURLException, URISyntaxException {
        String uri1 = "http://svn.ask.it.usyd.edu.au/demo/demo3.cgi?sentence=";
        String uri2 = "&printer=xml&model=a&resolve=yes&submit=Parse+and+Box!";
        String formatUrl = uri1.concat(sentence.replaceAll(" ", "+")).concat(uri2);
        URI uri = new URI(formatUrl);
        System.out.println(uri);
        URL url = uri.toURL();
        return url;
    }

    public void getDRT(String sentence) throws MalformedURLException, URISyntaxException, IOException {
        URL url = setURL(sentence);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            a.append(inputLine + '\n');
        }
        in.close();
        Document doc = parse(a.toString());
    }

    /**
     * Creates a Document from the InputStream, which is from the url
     */
    public static Document parse(String is) {
        System.out.println(is);
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();

            ret = builder.parse(new InputSource(new StringReader(is)));
        } catch (Exception ex) {
            System.err.println("unable to load XML: " + ex);
        }
        return ret;
    }

    public void parseXML(NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("here!");
            }
        }

    }

    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc),
                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    public static void main(String[] args) throws MalformedURLException, URISyntaxException, IOException {
        DiscourseLevelContradiction dlc = new DiscourseLevelContradiction();
        dlc.getDRT("I like pie");

    }
}
