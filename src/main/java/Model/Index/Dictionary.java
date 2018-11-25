package Model.Index;

import Model.PostTerm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Dictionary {

    private HashMap<String, PostTerm> dictionary = new HashMap<>();

    public boolean isInDictionary(String term){
        return dictionary.containsKey(term);
    }

    public void addNewTerm(String term, int tf, int ptr){
        dictionary.put(term,new PostTerm(term,tf,ptr));

    }

    public PostTerm getTerm(String term){
        return dictionary.get(term);
    }

    public int updateTerm(String term, int tf){
        PostTerm pterm = dictionary.get(term);
        pterm.increaseTf(tf);
        pterm.increaseDf();
        int ptr = pterm.getPtr();
        //add values by ptr
        dictionary.replace(term,pterm);
        return ptr;
    }


    public void printDic(){
        PrintWriter outputfile = null;
        try {
            outputfile = new PrintWriter("dic"+"-1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (PostTerm p:dictionary.values()
                ) {
            System.out.println(p);
        }
        outputfile.close();
    }




}
