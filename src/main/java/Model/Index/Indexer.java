package Model.Index;

import Model.PreTerm;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

//מקשר בין המילון לפוסטינג
public class Indexer {
   // private y;
   private Dictionary dictionary;
   private Posting posting;
   private HashMap<String,String> newTermsToWrite;
   private HashMap<String,String> updateTermsToWrite;

    public Indexer() {
         dictionary = new Dictionary();
         posting = new Posting();
         newTermsToWrite = new HashMap<>();
         updateTermsToWrite = new HashMap<>();
    }

    public void index(ConcurrentHashMap<String , PreTerm> preDictionary){
        for (ConcurrentHashMap.Entry<String,PreTerm> entry  : preDictionary.entrySet()) {
            String fileName;
            String token = entry.getKey();
            if(token.length()>1)
                fileName = token.substring(0,2);
            else
                fileName = token.substring(0,1);
            String docID = entry.getValue().getDocID();
            int tf = entry.getValue().getTf();
            //check if already in dictionary
            if(dictionary.isInDictionary(token)){
               int currPtr = dictionary.updateTerm(token,tf);
               posting.updateFile(fileName,docID,tf,currPtr);
            }
            //not in dictionary
            else{
                int newPtr = posting.addToFile(fileName,docID,tf);
                dictionary.addNewTerm(token,tf,newPtr);
            }
        }
    }

    public void printDic(){
        dictionary.printDic();
    }
}
