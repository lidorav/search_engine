package Model.Index;

import Model.PreTerm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

//Connect between Posting and Dictionary
public class Indexer {
   private Dictionary dictionary;
   private Posting posting;
   private HashMap<Character,TreeMap<Integer,String>> updateTermsToWrite;
   private HashMap<Character,TreeMap<Integer,String>> newTermsToWrite;
   private HashSet<String> test;

    public Indexer() {
         dictionary = new Dictionary();
         posting = new Posting();
         test = new HashSet<>();
         initalize();
    }

    public void index(ConcurrentHashMap<String , PreTerm> preDictionary){
        for (ConcurrentHashMap.Entry<String,PreTerm> entry  : preDictionary.entrySet()) {
            char fileName;
            String pterm = entry.getKey();
            fileName = Character.toLowerCase(pterm.charAt(0));
            String docID = entry.getValue().getDocID();
            if(!test.contains(docID))
                test.add(docID);
            int tf = entry.getValue().getTf();
            //check if already in dictionary
            if(dictionary.isInDictionary(pterm)){
               int currPtr = dictionary.updateTerm(pterm,tf);
               insertToUpdateList(fileName,currPtr,docID,tf);
            }
            //not in dictionary
            else{
                insertToNewList(fileName,docID,pterm,tf);
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

    private void insertToUpdateList(char fileName,int ptr, String docID, int tf) {
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

    private void insertToNewList(char fileName, String docID,String pterm, int tf) {
        TreeMap<Integer, String> tree;
        if (newTermsToWrite.containsKey(fileName)) {
            tree = newTermsToWrite.get(fileName);
        } else {
            tree = newTermsToWrite.get('*');
        }
        int newPtr = posting.getNextLine(fileName);
        dictionary.addNewTerm(pterm, tf, newPtr);
        String newLine = docID + ":" + tf + ",\r\n";
        tree.put(newPtr, newLine);
        newTermsToWrite.replace(fileName, tree);
    }

        public void printDic(){
        dictionary.printDic();
        System.out.println(test.size());
    }
}
