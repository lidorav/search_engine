package Model.Parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class StopWords {

    private Set<String> stopWordSet = new HashSet<String>();
    private String path="";

    public StopWords()  {
        Scanner file = null;
        try {
            file = new Scanner(new File("stop_words.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (file.hasNext()) {
            // now dictionary is not recreated each time
            stopWordSet.add(file.next().trim());
        }

    }

    public boolean isStopWord(String s){
        if(stopWordSet.contains(s))
            return true;
        return false;
    }

    public void print (){
        System.out.println(stopWordSet);
    }



}