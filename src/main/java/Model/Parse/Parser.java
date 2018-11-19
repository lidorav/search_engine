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
    private String docID;

    public void parse(String docNum, String text) {
        this.docID = docNum;
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
                addTerm(term, docID);
            } else {
                String term = Date.dateParse(index, token) + Hyphen.parseHyphen(index, token) + Text.parseText(index, token);
                if (term.isEmpty())
                    term = token;
                addTerm(term, docID);
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

    private void addTerm(String token, String docID) {
        Term term = new Term(token, docID);
        if (terms.containsKey(token))
            terms.get(token).addShow(docID);
        else
            terms.put(token, term);
    }

    public static boolean checkExist(String token){
        return terms.containsKey(token);
    }

    public static void replaceTerm(String currentToken, String newToken){
        Term term = terms.get(currentToken);
        term.setName(newToken);
        terms.remove(currentToken);
        terms.put(newToken,term);
    }

    public static void replaceTokens(int index, String newToken){
        tokenList.set(index,newToken);
    }

    public void printTerms(){
        //create a file first
        PrintWriter outputfile = null;
        try {
            outputfile = new PrintWriter(docID+"-1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //replace your System.out.print("your output");

        for (int i=0;i<tokenList.size();i++) {
            String token =getTokenFromList(i);
            outputfile.println(token);
            outputfile.println(terms.get(token));
        }
        outputfile.close();

        try {
            outputfile = new PrintWriter(docID+"-2");
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
