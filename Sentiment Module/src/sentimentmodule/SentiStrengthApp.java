package sentimentmodule;

import uk.ac.wlv.sentistrength.*;

public class SentiStrengthApp {

    private static String  sentidata = "sentidata";
    private static String data = "D:/SentStrength_Data_Sept2011/";
    private static String scale = "scale";
    public SentiStrengthApp() {
    }
    public static void main(String[] args) {
        SentiStrength sentiStrength = new SentiStrength();
        String ssthInitialisation[] = {sentidata, data, scale};
        sentiStrength.initialise(ssthInitialisation); //Initialise
        String polarities[] = sentiStrength.computeSentimentScores("I hate everybody").split(" ");
        System.out.println(polarities[2]);
    }
}
