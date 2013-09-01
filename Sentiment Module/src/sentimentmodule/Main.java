package sentimentmodule;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    private SentiStrengthApp senti;

    public static void main(String[] args) {
    }

    public void setSentimentValues(ArrayList<Opinion> opinions) {
        Iterator it = opinions.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            Opinion obj = (Opinion) o;
//            String sentiment_values = this.senti.getPolarities(obj.getClaim());
//            String polarities[] = sentiment_values.split(" ");
//            System.out.println(polarities);
//            obj.setPolarity_pos(Integer.parseInt(polarities[0]));
//            obj.setPolarity_neg(Integer.parseInt(polarities[1]));
        }
    }
}
