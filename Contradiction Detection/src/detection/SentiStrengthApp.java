package detection;

import uk.ac.wlv.sentistrength.*;

/**
 *
 * @author Ralu
 * @time 18:23:07
 */
public class SentiStrengthApp {

    private String sentidata = "sentidata";
    private String data = "D:/SentStrength_Data_Sept2011/";
    public SentiStrengthApp() {
    }

    public String getPolarities(String sentence) {
        String polarities[];
        SentiStrength sentiStrength = new SentiStrength();
        String ssthInitialisation[] = {sentidata, data};
        sentiStrength.initialise(ssthInitialisation); //Initialise
        polarities = sentiStrength.computeSentimentScores(sentence).split(" ");
        return polarities[2];
    }
}
