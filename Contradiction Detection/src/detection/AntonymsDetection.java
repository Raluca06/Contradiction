package detection;

import java.io.File;
import edu.smu.tspell.wordnet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import ca.site.elkb.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.*;
import sentRep.Pair;
import semDist.*;

public class AntonymsDetection {

    private WordNetDatabase database;
    private ArrayList<String> affirmative_reporting_verbs;
    private ArrayList<String> contradictive_reporting_verbs;
    private static final String affirmative_rep_verbs_filename = "reporting verbs.txt";
    private static final String contradictive_rep_verbs_filename = "contradicting verbs.txt";
    private RogetELKB roget;
    private static final String opposite_categories_filename = "oppCats.txt";
    private ArrayList<OpposingCategories> oppositeCategories;
//    private SemDist sem;
    private String resultsFile = "results_roget/allResults_semanticDistance2.txt";

    public AntonymsDetection() {
        this.database = WordNetDatabase.getFileInstance();
        this.affirmative_reporting_verbs = new ArrayList<String>();
        this.contradictive_reporting_verbs = new ArrayList<String>();
        this.oppositeCategories = new ArrayList<OpposingCategories>();
        roget = new RogetELKB();
        System.setProperty("wordnet.database.dir", "C:/Program Files (x86)/WordNet/2.1/dict");
        initializeReportingVerbs();
        initializeOppositeCategories();
//        sem = new SemDist();
    }

    private void initializeReportingVerbs() {
        File file_affirmative = new File(affirmative_rep_verbs_filename);
        File file_contradictive = new File(contradictive_rep_verbs_filename);
        try {
            BufferedReader buffread1 = new BufferedReader(new FileReader(file_affirmative));
            BufferedReader buffread2 = new BufferedReader(new FileReader(file_contradictive));
            String line;
            while ((line = buffread1.readLine()) != null) {
                affirmative_reporting_verbs.add(line);
            }
            while ((line = buffread2.readLine()) != null) {
                contradictive_reporting_verbs.add(line);
            }
            buffread1.close();
            buffread2.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void initializeOppositeCategories() {
        File oppCats = new File(opposite_categories_filename);
        try {
            BufferedReader buff = new BufferedReader(new FileReader(oppCats));
            String line;
            while ((line = buff.readLine()) != null) {
                String[] categories = line.split(" ");
                OpposingCategories cats = new OpposingCategories();
                cats.setModified(categories[0]);
                cats.setOriginal(categories[1]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public Contradiction checkReportingVerbRelations(Opinion op1, Opinion op2) {
        Contradiction result = new Contradiction();
        String reporting_verb1 = op1.getReporting_verb();
        String reporting_verb2 = op2.getReporting_verb();
        String claim1 = op1.getClaim();
        String claim2 = op2.getClaim();
        if (op1.getTarget().equals(op2.getTarget())) {
            if (!reporting_verb1.equals(reporting_verb2)) {
                VerbSynset verbSynset;
                WordSense[] antonyms;
                Synset[] synsets1 = database.getSynsets(reporting_verb1, SynsetType.VERB);
                for (int i = 0; i < synsets1.length; i++) {
                    verbSynset = (VerbSynset) (synsets1[i]);
                    antonyms = verbSynset.getAntonyms(verbSynset.toString());
                    if (antonyms.length != 0) {
                        for (int j = 0; j < antonyms.length; j++) {
                            if (antonyms[j].toString().equals(reporting_verb2)) {
                                setResult(op1, op2, true, Contradiction.ContradictionContext.Antonymy, "Reporting verbs are antonyms.");
                            }
                        }
                    } else {
                        if (affirmative_reporting_verbs.contains(reporting_verb1) && contradictive_reporting_verbs.contains(reporting_verb2)) {
                            setResult(op1, op2, true, Contradiction.ContradictionContext.Antonymy, "Reporting verbs indicate contradiction in claims");
                        }
                    }
                }

            } else {
            }
        }
        return result;
    }

    public Contradiction setResult(Opinion op1, Opinion op2, boolean validate, Contradiction.ContradictionContext context, String details) {
        Contradiction result = new Contradiction();
        result.setOpinion1(op1);
        result.setOpinion2(op2);
        result.setContradiction(validate);
        result.setContext(context);
        result.setDetails(details);
        return result;
    }

//    public void relationAnalysis(Opinion op, LexicalizedParser lp){
//        Tree parse = lp.apply(op.getDependency_structure().toString());
//        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
//        Collection tdl = gs.typedDependenciesCollapsed();
//        System.out.println(tdl);
//    }
    public void testRoget(String word1, String word2) {
        int headCount = getHeadCount();
        ArrayList<RogetClass> classList = roget.category.getClassList();
        Iterator it = classList.iterator();
        while (it.hasNext()) {
            RogetClass rog = (RogetClass) it.next();
            System.out.println(rog.getClassName());
            ArrayList<Section> sectionList = rog.getSectionList();
            Iterator it2 = sectionList.iterator();
            while (it2.hasNext()) {
                Section section = (Section) it2.next();
//                System.out.println(section.getSectionName());
                ArrayList<HeadInfo> headInfo = section.getHeadInfoList();
                Iterator it3 = headInfo.iterator();
                while (it3.hasNext()) {
                    HeadInfo hi = (HeadInfo) it3.next();
                    System.out.println(hi);
                }

            }


        }

    }

    public int getHeadCount() {
        int headCount = roget.category.getHeadCount();
        return headCount;
    }

    public void getHeadInformation() {
        ArrayList<HeadInfo> heads = roget.category.getHeadList();
        System.out.println("List of heads");
        for (HeadInfo hi : heads) {
            System.out.println(hi);
        }
    }

    public int getSemDist(String word1, String word2) {
        //create SemDist object
        semDist.SemDist sd = new semDist.SemDist();

        //get distance between words of any POS
//        int distance = sd.getSimilarity("member", "identity");
        int distance = sd.getSimilarity(word1, word2);
//        System.out.println(word1 + " " + word2 + " " + distance);

        //get distance between noun senses
//        distance = sd.getSimilarity("cat", "car", "N.");
//        System.out.println(distance);

        //get distance between a noun and a verb sense
//        distance = sd.getSimilarity("cat", "N.", "dog", "N.");
//        System.out.println(distance);
        return distance;
    }

    public int getSemanticDistance(String word1, String word2) {
//        semDist.SemDist sd = new semDist.SemDist();
        int distance = 0;
//        distance = sem.getSimilarity(word1, word2);
//        System.out.println(distance);
        return distance;
    }

    public void getText() {
        //get head number 12
        Head h12 = roget.text.getHead(12);
        System.out.println(h12);

        //get the first noun paragraph from the head
        Paragraph p1N = h12.getPara(0, "N.");
        System.out.println(p1N);

        //get the words and phrases from the paragraph and print them.
        ArrayList<String> paragraphWords = p1N.getAllWordList();
        System.out.println(paragraphWords);

        //get the first semicolon group in the paragraph
        SG sg1N = p1N.getSG(0);
        System.out.println(sg1N);

        //get all words in the semicolon group and print them.
        ArrayList<String> sgWords = sg1N.getAllWordList();
        System.out.println(sgWords);
    }

    public void getWordInfo(String word, String pos) {
        File file = new File(resultsFile);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter buff = new BufferedWriter(fw);
            ArrayList<HeadInfo> heads = roget.category.getHeadList();
            Iterator it = heads.iterator();
            while (it.hasNext()) {
                HeadInfo head = (HeadInfo) it.next();
                int headNumber = head.getHeadNum();
                Head h12 = roget.text.getHead(headNumber);
                String headName = h12.getHeadName();
//            System.out.println(word + headName + " " + getSemanticDistance(word, headName));
                int distance = getSemDist(word, headName.toLowerCase());
                buff.append(word + " " + headName.toLowerCase() + " " + distance + "\n");
                int paraCount = h12.getParaCount(pos);
                for (int j = 0; j < paraCount; j++) {
                    Paragraph p1N = h12.getPara(j, pos);
                    int semicolonGroups = p1N.getSGCount();
                    for (int k = 0; k < semicolonGroups; k++) {
                        SG sg1N = p1N.getSG(k);
                        ArrayList<String> sgWords = sg1N.getAllWordList();
                        Iterator iter = sgWords.iterator();
                        while (iter.hasNext()) {
                            String sgword = (String) iter.next();
                            if (word.equals(sgword)) {
//                           printToFile(word, headName, getSemanticDistance(word, headName));
//                                buff.append(word + " " + headName.toLowerCase() + " " + getSemDist(word, headName)+ "\n");
//                            System.out.println("found it!" + h12.getHeadName() + getSemanticDistance(word, h12.getHeadName()));
                            }
                        }

                    }
                }
            }
            buff.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getSentenceSimilarity() {
        // creates a sentence building object for Roget's and a Simple word distance one
        sentRep.SentenceFactory rogetRep = new sentRep.RogetSentenceFactory();
        sentRep.SentenceFactory simpleRep = new sentRep.SimpleSentenceFactory();

        //the sentences POS tagged
        String sentence1 = "Dogs/NNS and/CC cats/NNS make/VBP excellent/JJ pets/NNS";
        String sentence2 = "Canine/NN or/CC feline/JJ animals/NNS make/VBP good/JJ companions/NNS";

        //split the sentence into words and then pass it to the two representation builders.
        String[] s1Words = sentence1.split(" ");
        sentRep.Sentence senRogets1 = rogetRep.buildRepresentationVector(s1Words, 1, 5.0);
        sentRep.Sentence senSimple1 = simpleRep.buildRepresentationVector(s1Words, 0); //second parameter does nothing for simple representation

        String[] s2Words = sentence2.split(" ");
        sentRep.Sentence senRogets2 = rogetRep.buildRepresentationVector(s2Words, 1, 5.0);
        sentRep.Sentence senSimple2 = simpleRep.buildRepresentationVector(s2Words, 0);

        //print out the cosine distance of the re-weighted Roget's diestance and the simple one
        System.out.println("Roget's Distance: " + senRogets1.similarityModified(senRogets2));
        System.out.println("Simple Distance: " + senSimple1.similarityModified(senSimple2));
    }

    public void printToFile(String word1, String word2, int distance) {
    }

    public void printToFile(String word1, String word2, String path) {
    }
}
