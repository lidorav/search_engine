package Model.Parse;

import Model.Term;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
    public static int index;
    private static List<String> tokenList;
    private static HashMap<String, Term> terms = new HashMap<>();
    private String fileName;

    public void parse(String docNum, String text) {
        this.fileName = docNum;
        index = 0;
        Pattern pattern = Pattern.compile("[ \\(\\)\\[\\]\\:\\;\\!\\?\\\"\\(]|((?=[a-zA-Z]?)\\/(?=[a-zA-Z]))|((?<=[a-zA-Z])\\/(?=[\\d]))|((?=[\\d]?)\\/(?<=[a-zA-Z]))");
        Splitter splitter = Splitter.on(pattern).trimResults(CharMatcher.anyOf(",--")).omitEmptyStrings();
        tokenList = splitter.splitToList(text);
        classify();
    }

    private void classify() {
        for (; index < tokenList.size(); index++) {
            String token = getTokenFromList(index);
            if (token.matches(".*\\d+.*")) {
                String term = Price.parsePrice(index, token) + Percentage.parsePrecent(index, token) + Date.dateParse(index, token) + Hyphen.parseHyphen(index,token);
                if (term.isEmpty())
                    term = ANumbers.parseNumber(index, token);
                addTerm(term, fileName, false);
            } else {
                String term = Date.dateParse(index, token) + Hyphen.parseHyphen(index, token);
                if (term.isEmpty())
                    term = token;
                addTerm(term, fileName, true);
            }
        }
    }

    public static String getTokenFromList(int index) {
        if (index >= tokenList.size())
            return tokenList.get(tokenList.size() - 1);
        String token = tokenList.get(index);
        token = token.replace(",", "");
        if (!token.isEmpty()) {
            if (token.charAt(token.length() - 1) == '.')
                return token.substring(0, token.length() - 1);
        }
        return token;
    }

    private void addTerm(String token, String docID, boolean isWord) {
        if (Character.isUpperCase(token.charAt(0))) {
            token = token.toUpperCase();
        }
        Term term = new Term(token, docID);
        if (terms.containsKey(token)) {
            terms.get(token).addShow(docID);
        } else {
            if (isWord) {
                String upperCased = token.toUpperCase();
                if (terms.containsKey(upperCased)) {
                    Term t = terms.get(upperCased);
                    t.setName(token);
                    t.addShow(docID);
                    terms.remove(upperCased);
                    terms.put(token, t);
                    return;
                }
                terms.put(token, term);
                return;
            }
            terms.put(token, term);
        }
    }
    public void printTerms(){

        //create a file first
        PrintWriter outputfile = null;
        try {
            outputfile = new PrintWriter(fileName+"-1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //replace your System.out.print("your output");

        for (int i=0;i<tokenList.size();i++) {
            outputfile.println(getTokenFromList(i));
        }
        outputfile.close();

        try {
            outputfile = new PrintWriter(fileName+"-2");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    //replace your System.out.print("your output");

        for(String str: terms.keySet()){
            outputfile.println(str);
        }
        outputfile.close();
    }
}
