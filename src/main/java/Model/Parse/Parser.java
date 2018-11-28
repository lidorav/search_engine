package Model.Parse;

import Model.Index.Indexer;
import Model.PreTerm;
import com.google.common.base.Splitter;
import opennlp.tools.stemmer.PorterStemmer;
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Parser {
    public static int index;
    private static List<String> tokenList;
    private static ConcurrentHashMap<String, PreTerm> termsInDoc;
    private String docID;
    private StopWords stopWord;
    private PorterStemmer porterStemmer;
    private Indexer indexer;
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public Parser() {
        stopWord = new StopWords();
        porterStemmer = new PorterStemmer();
        indexer = new Indexer();
    }

    public void shutDownExecutor(){
        executor.shutdown();
    }

    public void parse(String docNum, String text) {
        termsInDoc = new ConcurrentHashMap<>();
        this.docID = docNum;
        index = 0;
        Pattern pattern = Pattern.compile("[ \\*\\|\\&\\(\\)\\[\\]\\:\\;\\!\\?\\(\\--\\/+]|((?=[a-zA-Z]?)\\/(?=[a-zA-Z]))|((?<=[a-zA-Z])\\/(?=[\\d]))|((?=[\\d]?)\\/(?<=[a-zA-Z]))");
        Splitter splitter = Splitter.on(pattern).omitEmptyStrings();
        tokenList = new ArrayList<>(splitter.splitToList(text));
        classify();
        //executor.execute(() -> indexer.index(termsInDoc));
        indexer.index(termsInDoc);
    }

    private void classify() {
        for (; index < tokenList.size(); index++) {
            String token = getTokenFromList(index);
            if (token.isEmpty() || stopWord.isStopWord(token))
                continue;
            if (token.matches(".*\\d+.*")) {
                String term = Price.parsePrice(index, token) + Percentage.parsePercent(index, token) + Date.dateParse(index, token) + Hyphen.parseHyphen(index, token) + Quotation.parseQuotation(index, token);
                if (term.isEmpty())
                    term = ANumbers.parseNumber(index, token);
                addTerm(term, docID);
            } else {
                String term = Date.dateParse(index, token) + Hyphen.parseHyphen(index, token) + Quotation.parseQuotation(index, token);
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
        if(token.length()==1 && !StringUtil.isNumeric(token))
            return;
        token = porterStemmer.stem(token);
        PreTerm term = new PreTerm(token, docID);
        if (termsInDoc.containsKey(token))
            termsInDoc.get(token).increaseTf();
        else
            termsInDoc.put(token, term);
    }

    public static boolean checkExist(String token) {
        return termsInDoc.containsKey(token);
    }

    public static void replaceTerm(String currentTerm, String newTerm) {
        PreTerm term = termsInDoc.get(currentTerm);
        term.setName(newTerm);
        termsInDoc.remove(currentTerm);
        termsInDoc.put(newTerm, term);
    }

    public static void replaceToken(int index, String newToken) {
        tokenList.set(index, newToken);
    }

    public void deployFile(){
        //executor.execute(() -> indexer.addChunckToFile());
        indexer.addChunckToFile();
    }

    public void printDic() {
        indexer.printDic();
    }
}
