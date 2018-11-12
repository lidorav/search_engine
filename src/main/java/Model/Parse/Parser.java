package Model.Parse;

import Model.Term;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.List;

public class Parser {
    public static int index;
    private static List<String> tokenList;
    private static HashMap<String, Term> terms = new HashMap<>();
    private String fileName;

    public void parse(String docNum, String text){
        this.fileName = docNum;
        index = 0;
        Splitter splitter = Splitter.on(CharMatcher.anyOf("()[];*?!:\" ")).trimResults(CharMatcher.anyOf(",--")).omitEmptyStrings();
        tokenList=splitter.splitToList(text);
        classify();
    }

    private void classify() {
        for (; index < tokenList.size(); index++) {
            String token = getTokenFromList(index);
            if (token.matches(".*\\d+.*")) {
                String term = Price.parsePrice(index, token) + Percentage.parsePrecent(index, token) + Date.dateParse(index, token);
                if (term.isEmpty())
                    term = ANumbers.parseNumber(index, token);
                addTerm(term, fileName, false);
            } else {
                String term = Date.dateParse(index,token);
                addTerm(term, fileName, true);
            }
        }
    }

    public static String getTokenFromList(int index){
        if(index >= tokenList.size())
            return tokenList.get(tokenList.size()-1);
        String token =tokenList.get(index);
        token = token.replace(",","");
        if(!token.isEmpty()) {
            if (token.charAt(token.length() - 1) == '.')
                return token.substring(0, token.length() - 1);
        }
        return token;
    }

    private void addTerm(String token, String docID, boolean isWord){
        Term term = new Term(token,docID);
        if(terms.containsKey(token)){
            terms.get(token).addShow(docID);
        }
        else {
            terms.put(token,term);
        }
    }

    public void printTerms() {
        for (int i=0;i<tokenList.size();i++) {
            System.out.println(getTokenFromList(i));
        }
        System.out.println();
        for(String str: terms.keySet()){
            System.out.println(str);
        }
    }
}
