package detection;

import java.util.ArrayList;

public class DRS {

    public enum thematicRoles {Actor, Agent, Asset, Attribute, Beneficiary, Cause, Location, Destination, Source, Experiencer,
    Extent, Instrument, Material, Product, Patient, Predicate, Recipient, Stimulus, Theme, Time, Topic};
    
    private ArrayList<DiscourseReferent> domain;
    private ArrayList<Pred> pred;
    private ArrayList<Rel> rel;
    private String label;
    private String type;

    public DRS(){
        this.domain = new ArrayList<DiscourseReferent>();
        this.pred = new ArrayList<Pred>();
        this.rel = new ArrayList<Rel>();
    }

    public ArrayList<DiscourseReferent> getDomain() {
        return this.domain;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Pred> getPred() {
        return this.pred;
    }

    public ArrayList<Rel> getRel() {
        return rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected class DiscourseReferent {//Discourse referent
        String label;
        String name;
        String[] index;
        String[] index_pos;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    protected class Pred {

        String arg;
        String symbol;
        String type;
        String sense;
        String[] pos;
        String[] index;

        public String[] getPos() {
            return pos;
        }

        public void setPos(String[] pos) {
            this.pos = pos;
        }

        public String getArg() {
            return arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        public String[] getIndex() {
            return index;
        }

        public void setIndex(String[] index) {
            this.index = index;
        }

        public String getSense() {
            return sense;
        }

        public void setSense(String sense) {
            this.sense = sense;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    protected class Rel {

        String arg1;
        String arg2;
        String symbol;
        String sense;

        public String getArg1() {
            return arg1;
        }

        public void setArg1(String arg1) {
            this.arg1 = arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public void setArg2(String arg2) {
            this.arg2 = arg2;
        }

        public String getSense() {
            return sense;
        }

        public void setSense(String sense) {
            this.sense = sense;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
