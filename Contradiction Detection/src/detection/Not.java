package detection;

public class Not extends DRS{

    private DRS drs;
    private String[] pos;
    private String[] index;

    public String[] getIndex() {
        return index;
    }

    public void setIndex(String[] index) {
        this.index = index;
    }

    public String[] getPos() {
        return pos;
    }

    public void setPos(String[] pos) {
        this.pos = pos;
    }

    public DRS getDrs() {
        return drs;
    }

    public void setDrs(DRS drs) {
        this.drs = drs;
    }
}
