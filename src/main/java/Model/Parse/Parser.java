package Model.Parse;

import Model.Document;
import Model.PreTerm;
import Model.ReadFile;
import com.google.common.base.Splitter;
import opennlp.tools.stemmer.PorterStemmer;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class Parser implements Runnable {
    protected static int index;
    private static List<String> tokenList;
    private static ConcurrentHashMap<String, PreTerm> tempDictionary;
    private StopWords stopWord;
    private BlockingQueue<Document> read_parse;
    private BlockingQueue<ConcurrentHashMap<String,PreTerm>> indexer_parse;
    private static PorterStemmer porterStemmer;
    private static Pattern pattern;
    private String docID;
    private static final int bound = 100;

    public Parser(BlockingQueue bqA, BlockingQueue bqB) {
        stopWord = new StopWords();
        porterStemmer = new PorterStemmer();
        read_parse = bqA;
        indexer_parse = bqB;
        pattern = Pattern.compile("[ \\*\\#\\|\\&\\(\\)\\[\\]:\\;\\!\\?\\{\\}]|-{2}|((?=[a-zA-Z]?)/(?=[a-zA-Z]))|((?<=[a-zA-Z])/(?=[\\d]))|((?=[\\d]?)/(?<=[a-zA-Z]))");
    }

    public void run() {
        try {
            Document doc;
            //consuming messages until exit message is received
            while(!(doc = read_parse.take()).getFileName().equals("fin")){
                String docID = doc.getDocID();
                String text = doc.getText();
                parse(docID,text);
                doc.cleanText();
                }
                indexer_parse.put(new ConcurrentHashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(String docNum, String text) {
        tempDictionary = new ConcurrentHashMap<>();
        this.docID = docNum;
        index = 0;
        Splitter splitter = Splitter.on(pattern).omitEmptyStrings();
        tokenList = new ArrayList<>(splitter.splitToList(text));
        classify();
        updateDoc();
        try {
            indexer_parse.put(tempDictionary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDoc() {
        int currentMaxValue = Integer.MIN_VALUE;
        Document doc = ReadFile.getDoc(docID);
        doc.setUniqueTf(tempDictionary.size());
        for (PreTerm preTerm : tempDictionary.values()){
            if (preTerm.getTf() > currentMaxValue) {
                currentMaxValue = preTerm.getTf();
            }
        }
        doc.setMaxTf(currentMaxValue);
    }

    private void classify() {
        for (; index < tokenList.size(); index++) {
            String token = getTokenFromList(index);
            if (token.isEmpty() || stopWord.isStopWord(token))
                continue;
            if (token.matches(".*\\d+.*")) {
                String term = numParse(token);
                if (term.isEmpty())
                    term = ANumbers.parseNumber(index, token);
                addTerm(term, docID);
            } else {
                String term =letterParse(token);
                if (term.isEmpty()) {
                    term = Text.parseText(index, token);
                    if (term.isEmpty())
                        term = token;
                }
                addTerm(term, docID);
            }
        }
    }

    private String numParse (String token){
        //Price.parsePrice(index, token) + Percentage.parsePercent(index, token) + Date.dateParse(index, token)
          //      + Hyphen.parseHyphen(index, token) + Quotation.parseQuotation(index, token);
        String res = Price.parsePrice(index, token);
        if(res.isEmpty()){
            res = Percentage.parsePercent(index, token);
        }
        if(res.isEmpty()){
            res = Date.dateParse(index, token);
        }
        if(res.isEmpty()){
            res = Hyphen.parseHyphen(index, token);
        }
        if(res.isEmpty()){
            res = Quotation.parseQuotation(index, token);
        }
        return res;
    }

    private String letterParse ( String token){
        //Date.dateParse(index, token) + Combo.parseCombo(index, token) +
        // Hyphen.parseHyphen(index, token) + Quotation.parseQuotation(index, token)
        String res= Date.dateParse(index, token);
       //if(res.isEmpty()) {
           //res = Combo.parseCombo(index, token);
       //}
        if(res.isEmpty()){
            res = Hyphen.parseHyphen(index, token);
        }
        if(res.isEmpty()){
            res = Quotation.parseQuotation(index, token);
        }
        return res;
    }

    static String getTokenFromList(int index) {
        if (index >= tokenList.size())
            return "eof";
        String token = tokenList.get(index);
        token = token.replaceAll("[,'`]", "");
        if (!token.isEmpty()) {
            if (token.charAt(token.length() - 1) == '.')
                 token = token.substring(0, token.length() - 1);
        }
        if(token.isEmpty())
            token = getTokenFromList(index+1);
        return token;
    }

    private void addTerm(String token, String docID) {
        boolean isAtBegin = false;
        if(token.length()==1 && !StringUtil.isNumeric(token))
            return;
        token = porterStemmer.stem(token);
        if(index <= bound)
            isAtBegin = true;
        PreTerm term = new PreTerm(token, docID,isAtBegin);
        if (tempDictionary.containsKey(token))
            tempDictionary.get(token).increaseTf();
        else {
            tempDictionary.put(token, term);
        }
    }

    static void replaceToken(int index, String newToken) {
        tokenList.set(index, newToken);
    }

    static boolean checkExist(String token){
        return tempDictionary.containsKey(token);
    }

    static void replaceTerm(String currentTerm, String newTerm){
        PreTerm term = tempDictionary.get(currentTerm);
        term.setName(newTerm);
        tempDictionary.remove(currentTerm);
        tempDictionary.put(newTerm,term);
    }
}
