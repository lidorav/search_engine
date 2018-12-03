package Model.Garbage;

import Model.PreTerm;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

//Connect between Posting and Dictionary
public class Indexer implements Runnable {
   private Dictionary dictionary;
   private Posting posting;
   private BlockingQueue<HashMap<String,PreTerm>> parser_indexer;
   //private HashMap<Character,TreeMap<Integer,StringBuilder>> updateTermsToWrite;
   //private HashMap<Character,TreeMap<Integer,StringBuilder>> newTermsToWrite;
   private AtomicInteger counter = new AtomicInteger(0);
   private String path = "C:\\Users\\nkutsky\\Desktop\\Retrival\\Posting";
   private TreeMap<String,StringBuilder> tempPost;


    public Indexer(BlockingQueue bq) {
         dictionary = new Dictionary();
         //posting = new Posting();
         parser_indexer = bq;
         //initalize();
         tempPost = new TreeMap<>();
    }

    @Override
    public void run() {
        try {
            int i;
            HashMap<String, PreTerm> tempDic;
            //consuming messages until exit message is received
            while (!((tempDic = parser_indexer.take()).isEmpty())) {
                i = counter.incrementAndGet();
                System.out.println("Indexer - " + i);
                index(tempDic);
                if (i == 262) {
                    //write to file
                    try {
                        File fileTo = new File(path + "\\" + tempDic.hashCode());
                        fileTo.createNewFile();
                        BufferedWriter out = new BufferedWriter(new FileWriter(fileTo));
                        for(StringBuilder str:tempPost.values())
                            out.write(str.append("\r\n").toString());
                        out.close();
                        counter.set(0);
                        tempPost.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

        public synchronized void index(HashMap<String , PreTerm> preDictionary) {
        for (ConcurrentHashMap.Entry<String,PreTerm> entry  : preDictionary.entrySet()) {
            String ptermName = entry.getKey();
            PreTerm ptermVal = entry.getValue();
            if(Dictionary.checkExist(ptermName))
                dictionary.updateTerm(ptermName,ptermVal.getTf());
            else{
                dictionary.addNewTerm(ptermVal,counter.get());
            }
            if(tempPost.containsKey(ptermName)){
                StringBuilder sb = tempPost.get(ptermName);
                sb.append(ptermVal.getDocID()).append(":").append(ptermVal.getTf()).append(",");
            }
            else{
                StringBuilder sb = new StringBuilder();
                sb.append(ptermVal.getDocID()).append(":").append(ptermVal.getTf()).append(",");
                tempPost.put(ptermName,sb);
            }
        }
        /*
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
        }*/
    }

    private synchronized void addChunckToFile(){
        //posting.addToFile(newTermsToWrite);
        //posting.updateFile(updateTermsToWrite,newTermsToWrite);
        //cleanTermList();
    }
    /*
        private void initalize(){
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

        private void cleanTermList(){
            updateTermsToWrite.replaceAll((k,v)-> new TreeMap<>());
            newTermsToWrite.replaceAll((k,v)-> new TreeMap<>());
        }*/
/*
    private void insertToUpdateList(char fileName,int ptr, PreTerm preTerm) {
        String docID = preTerm.getDocID();
        int tf = preTerm.getTf();
        TreeMap<Integer, StringBuilder> tree;
        if(updateTermsToWrite.containsKey(fileName)) {
            tree = updateTermsToWrite.get(fileName);
        }
        else{
            tree = updateTermsToWrite.get('*');
        }
        if(tree.containsKey(ptr)){
            StringBuilder currentLine = tree.get(ptr);
            currentLine.append(docID).append(":").append(tf).append(",");
        }
        else{
            StringBuilder newLine = new StringBuilder();
            newLine.append(docID).append(":").append(tf).append(",");
            tree.put(ptr,newLine);
        }
    }

    private void insertToNewList(char fileName, PreTerm preTerm) {
        String docID = preTerm.getDocID();
        int tf = preTerm.getTf();
        TreeMap<Integer, StringBuilder>  tree;
        if (newTermsToWrite.containsKey(fileName)) {
            tree = newTermsToWrite.get(fileName);
        } else {
            tree = newTermsToWrite.get('*');
        }
        int newPtr = posting.getNextLine(fileName);
        dictionary.addNewTerm(preTerm, newPtr);
        StringBuilder newLine = new StringBuilder();
        newLine.append(docID).append(":").append(tf).append(",");
        tree.put(newPtr, newLine);
    }
*/
        public void printDic(){
        dictionary.printDic();
    }
}
