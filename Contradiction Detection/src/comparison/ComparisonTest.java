package comparison;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import detection.*;

public class ComparisonTest {
    private static ArrayList<String> sentences;
    private ArrayList<Integer> posValues;
    private ArrayList<Integer> negValue;
    private int senticount;

    private static void readFromFile(String string) throws FileNotFoundException, IOException {
        BufferedReader buffread1 = new BufferedReader(new FileReader(string));
        String line;
            while ((line = buffread1.readLine()) != null) {
                sentences.add(line);
            }
    }

    public ComparisonTest() {
        this.sentences = new ArrayList<String>();
        this.posValues = new ArrayList<Integer>();
        this.negValue = new ArrayList<Integer>();
        this.senticount = 0;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
//        if (args.length > 0) {
//            demoDP(lp, args[0]);
//        } else {
        ComparisonTest comp = new ComparisonTest();
        comp.prepare();
       // comp.sentValueAnalysis();
        LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
//        comp.readFromFile("test_numeric_contradictions(2).txt");
        comp.demoAPI(lp);
//        }
    }

//    public static void demoDP(LexicalizedParser lp, String filename) {
//        // This option shows loading and sentence-segmenting and tokenizing
//        // a file using DocumentPreprocessor.
//        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//        // You could also create a tokenizer here (as below) and pass it
//        // to DocumentPreprocessor
//        for (List<HasWord> sentence : new DocumentPreprocessor(filename)) {
//            Tree parse = lp.apply(sentence);
//            parse.pennPrint();
//            System.out.println();
//
//            GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
//            Collection tdl = gs.typedDependenciesCCprocessed();
//            System.out.println(tdl);
//            System.out.println();
//        }
//    }

    public static void demoAPI(LexicalizedParser lp) throws IOException {
        // This option shows parsing a list of correctly tokenized words
//        String[] sent = {"This", "is", "an", "easy", "sentence", "."};
        BufferedWriter buffwrite = new BufferedWriter(new FileWriter("RTE1_dev1_3ways_output.txt"));
        
        Iterator it = sentences.iterator();
        while(it.hasNext()){
            String temp = (String)it.next();
            String[] sent = temp.split(" ");
            List<CoreLabel> rawWords = Sentence.toCoreLabelList(sent);
            Tree parse = lp.apply(rawWords);
           //parse.pennPrint();
           // System.out.println();
            buffwrite.append(parse.pennString()+"#\n");
        }
        buffwrite.close();
        

//        // This option shows loading and using an explicit tokenizer
//        String sent2 = "This is another sentence.";
//        TokenizerFactory<CoreLabel> tokenizerFactory =
//                PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
//        List<CoreLabel> rawWords2 =
//                tokenizerFactory.getTokenizer(new StringReader(sent2)).tokenize();
//        parse = lp.apply(rawWords2);
//
//        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
//        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
//        System.out.println(tdl);
//        System.out.println();

//        TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
//        tp.printTree(parse);
    }
    
    public void prepare(){
       
        File xmlFile = new File("RTE1_dev1_3ways.xml");
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("pair");
           
            for (int i = 0; i < list.getLength(); i++) {
                Node sentenceNode = list.item(i);

                if (sentenceNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) sentenceNode;
                    sentences.add(element.getElementsByTagName("t").item(0).getTextContent());
                    sentences.add(element.getElementsByTagName("h").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       // System.out.println(sentences.size());
    }

    public void sentValueAnalysis() throws IOException{
        BufferedWriter buffwrite = new BufferedWriter(new FileWriter("input.txt"));
        SentiStrengthApp senti = new SentiStrengthApp();
        Iterator it = sentences.iterator();
        while(it.hasNext()){
            Object obj = it.next();
            String s = (String) obj;
            String[] pol = senti.getPolarities(s).split(" ");
            posValues.add(Integer.parseInt(pol[0]));
            negValue.add(Integer.parseInt(pol[1]));
        }
       int len = posValues.size();
       for(int i=0; i<len;  i+=2){
           int pos1 = posValues.get(i);
           int neg1 = negValue.get(i);
           int pos2 = posValues.get(i+1);
           int neg2 = negValue.get(i+1);

           if(
                   ((pos1 != pos2) && (neg1 != neg2)) 
                   ) {
               senticount++;
               System.out.println(sentences.get(i)+'\n'+sentences.get(i+1)+'\n');
           }
       }
       System.out.println(senticount);
    }
}
