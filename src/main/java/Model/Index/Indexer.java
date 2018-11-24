package Model.Index;


import Model.Index.Dictionary;
import Model.Index.Posting;
import Model.PreTerm;

import java.util.HashMap;
import java.util.Map;

//מקשר בין המילון לפוסטינג
public class Indexer {
   // private y;
   private Dictionary dictionary;
   private Posting posting;

    public Indexer() {
         dictionary = new Dictionary();
         posting = new Posting();
    }

    public void index(HashMap<String , PreTerm> preDictionary){
        for (Map.Entry<String,PreTerm> entry  : preDictionary.entrySet()
             ) {
            String token = entry.getKey();
            String docID = entry.getValue().getDocID();
            int tf = entry.getValue().getTf();
            //check if already in dictionary
            if(dictionary.isInDictionary(token)){
               int currPtr = dictionary.updateTerm(entry.getKey(),entry.getValue().getTf());
               posting.updateFile(token.charAt(0),docID,tf,currPtr);
            }
            //not in dictionary
            else{
                int newPtr = posting.addToFile(token.charAt(0),docID,tf);
                dictionary.addNewTerm(token,tf,newPtr);
            }
        }
    }
}
