package detection; 

public class Contradiction {

    public enum ContradictionContext{
        Negation, Antonymy, NumericMismatch, Unknown, Other
    }

    private Opinion opinion1;
    private Opinion opinion2;
    private boolean isContradiction;
    private ContradictionContext context;
    private String details;

    public Contradiction(){
        this.opinion1 = new Opinion();
        this.opinion2 = new Opinion();
        this.isContradiction = false;
        this.context = ContradictionContext.Unknown;
        this.details = "";
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ContradictionContext getContext() {
        return context;
    }

    public void setContext(ContradictionContext context) {
        this.context = context;
    }

    public boolean isContradiction() {
        return this.isContradiction;
    }

    public void setContradiction(boolean value) {
        this.isContradiction = value;
    }

    public Opinion getOpinion1() {
        return opinion1;
    }

    public void setOpinion1(Opinion opinion1) {
        this.opinion1 = opinion1;
    }

    public Opinion getOpinion2() {
        return opinion2;
    }

    public void setOpinion2(Opinion opinion2) {
        this.opinion2 = opinion2;
    }

}
