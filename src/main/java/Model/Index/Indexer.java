package Model.Index;

import Model.PreTerm;
import java.util.concurrent.ConcurrentHashMap;

//מקשר בין המילון לפוסטינג
public class Indexer {
   // private y;
   private Dictionary dictionary;
   private Posting posting;

    public Indexer() {
         dictionary = new Dictionary();
         posting = new Posting();
    }

    public void index(ConcurrentHashMap<String , PreTerm> preDictionary){
        for (ConcurrentHashMap.Entry<String,PreTerm> entry  : preDictionary.entrySet()) {
            String token = entry.getKey();
            String docID = entry.getValue().getDocID();
            int tf = entry.getValue().getTf();
            //check if already in dictionary
            if(dictionary.isInDictionary(token)){
               int currPtr = dictionary.updateTerm(token,tf);
               posting.updateFile(token.charAt(0),docID,tf,currPtr);
            }
            //not in dictionary
            else{
                int newPtr = posting.addToFile(token.charAt(0),docID,tf);
                dictionary.addNewTerm(token,tf,newPtr);
            }
        }
    }

    public void printDic(){
        dictionary.printDic();
    }
}
