package Model.Parse;

import Model.PreTerm;
import Model.PostTerm;
import com.google.common.base.Splitter;
import opennlp.tools.stemmer.PorterStemmer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
    public static int index;
    private static List<String> tokenList;
    private static HashMap<String,PreTerm> termsInDoc;
    private String docID;
    private StopWords stopWord = new StopWords();
    private  PorterStemmer porterStemmer = new PorterStemmer();


    public void parse(String docNum, String text) {
        this.docID = docNum;
        index = 0;
        Pattern pattern = Pattern.compile("[ \\*\\&\\(\\)\\[\\]\\:\\;\\!\\?\\(\\--\\/+]|((?=[a-zA-Z]?)\\/(?=[a-zA-Z]))|((?<=[a-zA-Z])\\/(?=[\\d]))|((?=[\\d]?)\\/(?<=[a-zA-Z]))");
        Splitter splitter = Splitter.on(pattern).omitEmptyStrings();
        tokenList = new ArrayList<>(splitter.splitToList(text));
        classify();
    }

    private void classify() {
        for (; index < tokenList.size(); index++) {
            String token = getTokenFromList(index);
            if(token.isEmpty() || stopWord.isStopWord(token))
                continue;
            if (token.matches(".*\\d+.*")) {
                String term = Price.parsePrice(index, token) + Percentage.parsePercent(index, token) + Date.dateParse(index, token) + Hyphen.parseHyphen(index,token) + Quotation.parseQuotation(index,token);
                if (term.isEmpty())
                    term = ANumbers.parseNumber(index, token);
                addTerm(term, docID);
            } else {
                String term = Date.dateParse(index, token) + Hyphen.parseHyphen(index, token) + Quotation.parseQuotation(index,token);
                if (term.isEmpty()) {
                    term = Text.parseText(index, token);
                    if (term.isEmpty())
                        term = token;
                }
                addTerm(term, docID);
            }
        }
    }

    public static String getTokenFromList(int index) {
        if (index >= tokenList.size())
            return tokenList.get(tokenList.size() - 1);
        String token = tokenList.get(index);
        token = token.replaceAll("[,//']", "");
        if (!token.isEmpty()) {
            if (token.charAt(token.length() - 1) == '.')
                return token.substring(0, token.length() - 1);
        }
        return token;
    }

    private void addTerm(String token, String docID) {
        token = porterStemmer.stem(token);
        PreTerm term = new PreTerm(token,docID);
        if (termsInDoc.containsKey(token))
            termsInDoc.get(token).increaseTf();
        else
            termsInDoc.put(token, term);
    }

    public static boolean checkExist(String token){
        return termsInDoc.containsKey(token);
    }

    public static void replaceTerm(String currentTerm, String newTerm){
        PreTerm term = termsInDoc.get(currentTerm);
        term.setName(newTerm);
        termsInDoc.remove(currentTerm);
        termsInDoc.put(newTerm,term);
    }

    public static void replaceToken (int index, String newToken){
        tokenList.set(index,newToken);
    }

    /*public void printTerms(){
        //create a file first
        PrintWriter outputfile = null;
        try {
            outputfile = new PrintWriter(docID+"-1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i=0;i<tokenList.size();i++) {
            outputfile.println(getTokenFromList(i));
        }
        outputfile.close();

        try {
            outputfile = new PrintWriter(docID+"-2");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        for(String str: termsInDoc.keySet()){
            outputfile.println(str);
        }
        outputfile.close();


        //~~~~~~~~~~~~~//
        try {
            outputfile = new PrintWriter(docID+"-3");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        //replace your System.out.print("your output");

        for(PreTerm term: terms.values()){
            ConcurrentHashMap<String, Integer> map = term.getDocTf();
                outputfile.println(term);
            }
            outputfile.println();
            outputfile.println();
            outputfile.close();
    }*/
}
