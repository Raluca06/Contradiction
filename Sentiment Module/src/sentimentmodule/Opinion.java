package sentimentmodule;

import java.util.ArrayList;
import java.util.Calendar;

public class Opinion {

    private String claim;
    private String holder;
    private ArrayList<String> targets;
    private String reporting_verb;
    private String opinion_bearing_word;
    private Calendar time;
    private int sentiment_polarity_positive;
    private int sentiment_polarity_negative;
    private String negation_word;
    private String pos_tagged;
    private String person;
    private String organization;
    private String phrase_structure;
    private String dependency_structure;
    private String relation;
    private String synset;
    private int polarity_pos;
    private int polarity_neg;

    public int getPolarity_neg() {
        return polarity_neg;
    }

    public void setPolarity_neg(int polarity_neg) {
        this.polarity_neg = polarity_neg;
    }

    public int getPolarity_pos() {
        return polarity_pos;
    }

    public void setPolarity_pos(int polarity_pos) {
        this.polarity_pos = polarity_pos;
    }
   

    public Opinion()
    {
    }

    public Opinion(String holder)
    {
        this.holder = holder;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getDependency_structure() {
        return dependency_structure;
    }

    public void setDependency_structure(String dependency_structure) {
        this.dependency_structure = dependency_structure;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getNegation_word() {
        return negation_word;
    }

    public void setNegation_word(String negation_word) {
        this.negation_word = negation_word;
    }

    public String getOpinion_bearing_word() {
        return opinion_bearing_word;
    }

    public void setOpinion_bearing_word(String opinion_bearing_word) {
        this.opinion_bearing_word = opinion_bearing_word;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPhrase_structure() {
        return phrase_structure;
    }

    public void setPhrase_structure(String phrase_structure) {
        this.phrase_structure = phrase_structure;
    }

    public String getPos_tagged() {
        return pos_tagged;
    }

    public void setPos_tagged(String pos_tagged) {
        this.pos_tagged = pos_tagged;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getReporting_verb() {
        return reporting_verb;
    }

    public void setReporting_verb(String reporting_verb) {
        this.reporting_verb = reporting_verb;
    }

    public int getSentiment_polarity_negative() {
        return sentiment_polarity_negative;
    }

    public void setSentiment_polarity_negative(int sentiment_polarity_negative) {
        this.sentiment_polarity_negative = sentiment_polarity_negative;
    }

    public int getSentiment_polarity_positive() {
        return sentiment_polarity_positive;
    }

    public void setSentiment_polarity_positive(int sentiment_polarity_positive) {
        this.sentiment_polarity_positive = sentiment_polarity_positive;
    }

    public String getSynset() {
        return synset;
    }

    public void setSynset(String synset) {
        this.synset = synset;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<String> targets) {
        this.targets = targets;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    

}
