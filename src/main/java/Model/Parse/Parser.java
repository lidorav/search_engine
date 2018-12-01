package Model.Parse;

import Model.Document;
import Model.Index.Indexer;
import Model.PreTerm;
import Model.ReadFile;
import com.google.common.base.Splitter;
import opennlp.tools.stemmer.PorterStemmer;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class Parser {
    public static int index;
    private static List<String> tokenList;
    private static ConcurrentHashMap<String, PreTerm> termsInDoc;
    private String docID;
    private StopWords stopWord;
    private PorterStemmer porterStemmer;
    private Indexer indexer;
    private Pattern pattern;


    public Parser() {
        stopWord = new StopWords();
        porterStemmer = new PorterStemmer();
        indexer = new Indexer();
        pattern = Pattern.compile("[ \\*\\|\\&\\(\\)\\[\\]\\:\\;\\!\\?\\(\\/+]|-{2}|((?=[a-zA-Z]?)\\/(?=[a-zA-Z]))|((?<=[a-zA-Z])\\/(?=[\\d]))|((?=[\\d]?)\\/(?<=[a-zA-Z]))");
    }

    public void parse(String docNum, String text) {
        termsInDoc = new ConcurrentHashMap<>();
        this.docID = docNum;
        index = 0;
        Splitter splitter = Splitter.on(pattern).omitEmptyStrings();
        tokenList = new ArrayList<>(splitter.splitToList(text));
        classify();
        indexer.index(termsInDoc);
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
        if(res.isEmpty()){
            res= Combo.parseCombo(index, token);
        }
        if(res.isEmpty()){
            res = Hyphen.parseHyphen(index, token);
        }
        if(res.isEmpty()){
            res = Quotation.parseQuotation(index, token);
        }
        return res;
    }

    public static String getTokenFromList(int index) {
        if (index >= tokenList.size())
            return "eof";
        String token = tokenList.get(index);
        token = token.replaceAll("[,\\']", "");
        if (!token.isEmpty()) {
            if (token.charAt(token.length() - 1) == '.')
                 token.substring(0, token.length() - 1);
        }
        if(token.isEmpty())
            token = getTokenFromList(index+1);
        return token;
    }

    private void addTerm(String token, String docID) {
        if(token.length()==1 && !StringUtil.isNumeric(token))
            return;
        token = porterStemmer.stem(token);
        PreTerm term = new PreTerm(token, docID);
        if (termsInDoc.containsKey(token))
            termsInDoc.get(token).increaseTf();
        else {
            Document doc = ReadFile.getDoc(docID);
            if(doc.getTitle().contains(token))
                term.setInTitle(true);
            termsInDoc.put(token, term);
        }
    }

    public static void replaceToken(int index, String newToken) {
        tokenList.set(index, newToken);
    }

    public void deployFile(){
        indexer.addChunckToFile();
    }

    public void printDic() {
        indexer.printDic();
    }
}
