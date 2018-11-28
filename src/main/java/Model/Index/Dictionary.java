package Model.Index;

import Model.PostTerm;
import Model.PreTerm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary {

    private static ConcurrentHashMap<String, PostTerm> dictionary = new ConcurrentHashMap<>();

    public boolean isInDictionary(String term) {
        return dictionary.containsKey(term);
    }

    public void addNewTerm(String term, int tf, int ptr) {
        dictionary.put(term, new PostTerm(term, tf, ptr));

    }

    public PostTerm getTerm(String term) {
        return dictionary.get(term);
    }

    public int updateTerm(String term, int tf) {
        PostTerm pterm = dictionary.get(term);
        pterm.increaseTf(tf);
        pterm.increaseDf();
        int ptr = pterm.getPtr();
        //add values by ptr
        dictionary.replace(term, pterm);
        return ptr;
    }


    public void printDic() {
        PrintWriter outputfile = null;
        try {
            outputfile = new PrintWriter("C:\\Users\\nkutsky\\Desktop\\Retrival\\Dic.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (PostTerm p : dictionary.values()
        ) {
            outputfile.println(p);
        }
        outputfile.close();
    }

    public static boolean checkExist(String token) {
        return dictionary.containsKey(token);
    }

    public static void replaceTerm(String currentTerm, String newTerm) {
        PostTerm term = dictionary.get(currentTerm);
        term.setName(newTerm);
        dictionary.remove(currentTerm);
        dictionary.put(newTerm, term);
    }
}
