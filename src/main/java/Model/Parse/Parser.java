package Model.Parse;

import Model.Term;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.List;

public class Parser {

    private static List<String> tokenList;
    private static HashMap<String, Term> terms = new HashMap<>();
    private String fileName;
    private String[] punctuation = {",","-","--"," "};

    public void parse(String docNum, String text){
        this.fileName = fileName;
        Splitter splitter = Splitter.on(CharMatcher.anyOf("()[];*?!/:\" ")).trimResults(CharMatcher.anyOf(",--")).omitEmptyStrings();
        tokenList=splitter.splitToList(text);
        classify();
    }

    private void classify() {
        int i=0;
        for(i=0; i<tokenList.size(); i++){
            String token = getTokenFromList(i);
            if(token.matches(".*\\d+.*")){
                if(token.contains("%")){
                    //Send To TokenPrecentage
                    continue;
                }
                if(token.contains("$")){
                    //Send To TokenPrice-Dollar
                    continue;
                }
                String nextToken = getTokenFromList(i+1);
                /*
                * Dollars / Thousands / Millions / Billion / Precent / Precentage /
                **/
            }
        }
    }

    public static String getTokenFromList(int index){
        String token =tokenList.get(index);
        token = token.replace(",","");
        if(!token.isEmpty()) {
            if (token.charAt(token.length() - 1) == '.')
                return token.substring(0, token.length() - 1);
        }
        return token;
    }

    public static void saveTerm(String termName, Term term){
        terms.put(termName, term);
    }

    public void printTerms() {
        for (int i=0;i<tokenList.size();i++) {
            System.out.println(getTokenFromList(i));
        }
    }
}
