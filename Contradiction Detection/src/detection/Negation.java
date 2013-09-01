package detection;

import detection.DRS.Pred;
import java.util.ArrayList;
import java.util.Iterator;

public class Negation {

    private Opinion opinion1, opinion2;
    

    public Negation(){
    }

    public void setOpinion1(Opinion op){
        this.opinion1 = op;
    }

    public void setOpinion2(Opinion op){
        this.opinion2 = op;
    }

    public boolean isNegation(Opinion op){
        boolean ok = false;
        ArrayList<DRS> drs = op.getDrs();
        Iterator it = drs.iterator();
        while(it.hasNext()){
            DRS drss = (DRS) it.next();
            if(drss.getClass().getName().equals("Not")) ok = true;
        }
        return ok;
    }

    public boolean isNegation(DRS drs){
        boolean ok = false;
        if(drs.getClass().getName().equals("Not")) ok = true;
        return ok;
    }

    public void compare(){
        ArrayList<DRS> drss1 = opinion1.getDrs();
        ArrayList<DRS> drss2 = opinion2.getDrs();
        Iterator it1 = drss1.iterator();
        Iterator it2 = drss2.iterator();
        while(it1.hasNext())
            while(it2.hasNext()){
            DRS drs = (DRS) it1.next();
            DRS drs2 = (DRS) it2.next();
            if (isNegation(drs) & !isNegation(drs2) || !isNegation(drs) & isNegation(drs2)){
                ArrayList<DRS.Pred> pr1 = drs.getPred();
                ArrayList<DRS.Pred> pr2 = drs2.getPred();
                if(isCompatible(pr1, pr2)) System.out.print("We have a negation!");
            }
        }
    }

    public boolean isCompatible(ArrayList<Pred> pr1, ArrayList<Pred> pr2) {
        boolean ok = true;
        int commons = 0; //number of common predicates
        Iterator it1 = pr1.iterator();
        Iterator it2 = pr2.iterator();
        while (it1.hasNext()){
            DRS.Pred pred1 = (DRS.Pred) it1.next();
            while(it2.hasNext()){
                DRS.Pred pred2 = (DRS.Pred) it2.next();
                if (pred1.getSymbol().equals(pred2.getSymbol())) commons++;
            }      
        }
        return ok;
    }
}
