/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negation;
import negation.GenNegEx;
import java.io.*;

public class CallKit {

    public void start(String sentence, int arg) throws IOException {
	String outputFilename = "CallKit1.result";
	File existed = new File(outputFilename);
	if (existed.exists())
	    existed.delete();
	FileWriter fw = new FileWriter(outputFilename, true);
	boolean value;
	if (arg == 1)
	    value = true;
	else
	    value = false;
	GenNegEx g = new GenNegEx(value);
        process(fw, g, sentence);
	fw.close();
    }

    // post: process data and prints a result to a text file
    public void process(FileWriter fw, GenNegEx g, String text) throws IOException {
//	BufferedReader file = new BufferedReader(new FileReader(textfile));
        String line = text;
//        while ((line = file.readLine()) != null) {
//	    String[] parts = line.split("\\t");
	    String sentence = cleans(line);
	    String scope = g.negScope(sentence);
	    if (scope.equals("-1"))
		fw.write(line + "\t" + "Affirmed" + "\n");
	    else if (scope.equals("-2"))
		fw.write(line + "\t" + "Negated" + "\n");
//	    else {
//		String keyWords = cleans(parts[1]);
//		if (contains(scope, sentence, keyWords))
//		    fw.write(line + "\t" + "Negated" + "\n");
//		else
//		    fw.write(line + "\t" + "Affirmed" + "\n");
//            }

	    // Prints out the scope on the screen for demonstration purposes.
	    // CHANGE as you like.
	    System.out.println(scope);

//	}
//	file.close();
    }

    // post: returns true if a keyword is in the negation scope. otherwise, returns false
    private static boolean contains(String scope, String line, String keyWords) {
	String[] token = line.split("\\s+");
	String[] s = keyWords.trim().split("\\s+");
	String[] number = scope.split("\\s+");
	int counts = 0;
	for (int i = Integer.valueOf(number[0]); i <= Integer.valueOf(number[2]); i++)
	    if (s.length == 1) {
		if (token[i].equals(s[0]))
		    return true;
	    } else
		if ((token.length - i) >= s.length) {
		    String firstWord = token[i];
		    if (firstWord.equals(s[0])) {
			counts++;
			for (int j = 1; j < s.length; j++) {
			    if (token[i + j].equals(s[j]))
				counts++;
			    else {
				counts = 0;
				break;
			    }
			    if (counts == s.length)
				return true;
			}
		    }
		}
	return false;
    }

    // post: removes punctuations
    private static String cleans(String line) {
	line = line.toLowerCase();
	if (line.contains("\""))
	    line = line.replaceAll("\"", "");
	if (line.contains(","))
	    line = line.replaceAll(",", "");
	if (line.contains("."))
	    line = line.replaceAll("\\.", "");
	if (line.contains(";"))
	    line = line.replaceAll(";", "");
	if (line.contains(":"))
	    line = line.replaceAll(":", "");
	return line;
    }

    public static void main(String[] args) throws IOException{
        CallKit call = new CallKit();
        call.start("He said he doesn't like me", 1);
    }
}
