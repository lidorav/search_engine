package Model.Index;

import Model.PreTerm;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

//Connect between Posting and Dictionary
public class Indexer {
   private Dictionary dictionary;
   private Posting posting;
   private HashMap<Character,TreeMap<Integer,String>> updateTermsToWrite;
   private HashMap<Character,TreeMap<Integer,String>> newTermsToWrite;

    public Indexer() {
         dictionary = new Dictionary();
         posting = new Posting();
         initalize();
    }

    public void index(ConcurrentHashMap<String , PreTerm> preDictionary){
        for (ConcurrentHashMap.Entry<String,PreTerm> entry  : preDictionary.entrySet()) {
            char fileName;
            String ptermName = entry.getKey();
            fileName = Character.toLowerCase(ptermName.charAt(0));
            PreTerm ptermVal  = entry.getValue();
            //check if already in dictionary
            if(dictionary.isInDictionary(ptermName)){
               int currPtr = dictionary.updateTerm(ptermName,ptermVal.getTf());
               insertToUpdateList(fileName,currPtr,ptermVal);
            }
            //not in dictionary
            else{
                insertToNewList(fileName,ptermVal);
            }
        }
    }

    public void addChunckToFile(){
        posting.addToFile(newTermsToWrite);
        posting.updateFile(updateTermsToWrite);
        initalize();
    }

    public void initalize(){
        updateTermsToWrite = new HashMap<>();
        newTermsToWrite = new HashMap<>();
        for(char ch='a';ch<='z';ch++) {
            updateTermsToWrite.put(ch, new TreeMap<>());
            newTermsToWrite.put(ch, new TreeMap<>());
        }
        for(char ch='0';ch<='9';ch++) {
            updateTermsToWrite.put(ch, new TreeMap<>());
            newTermsToWrite.put(ch, new TreeMap<>());
        }
        updateTermsToWrite.put('*',new TreeMap<>());
        newTermsToWrite.put('*',new TreeMap<>());
    }

    private void insertToUpdateList(char fileName,int ptr, PreTerm preTerm) {
        String docID = preTerm.getDocID();
        int tf = preTerm.getTf();
        TreeMap<Integer, String> tree;
        if(updateTermsToWrite.containsKey(fileName)) {
            tree = updateTermsToWrite.get(fileName);
        }
        else{
            tree = updateTermsToWrite.get('*');
        }
        if(tree.containsKey(ptr)){
            String currentLine = tree.get(ptr);
            currentLine = currentLine + docID + ":" + tf + ",";
            tree.replace(ptr,currentLine);
            updateTermsToWrite.replace(fileName,tree);
        }
        else{
            String newLine = docID + ":" + tf + ",";
            tree.put(ptr,newLine);
            updateTermsToWrite.replace(fileName,tree);
        }
    }

    private void insertToNewList(char fileName, PreTerm preTerm) {
        String docID = preTerm.getDocID();
        int tf = preTerm.getTf();
        TreeMap<Integer, String> tree;
        if (newTermsToWrite.containsKey(fileName)) {
            tree = newTermsToWrite.get(fileName);
        } else {
            tree = newTermsToWrite.get('*');
        }
        int newPtr = posting.getNextLine(fileName);
        dictionary.addNewTerm(preTerm, newPtr);
        String newLine = docID + ":" + tf + ",\r\n";
        tree.put(newPtr, newLine);
        newTermsToWrite.replace(fileName, tree);
    }

        public void printDic(){
        dictionary.printDic();
    }
}
