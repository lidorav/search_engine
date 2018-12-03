package Model.Garbage;

import Model.PostTerm;
import Model.PreTerm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary {

    private static volatile ConcurrentHashMap<String, PostTerm> dictionary = new ConcurrentHashMap<>();

    boolean isInDictionary(String term) {
        return dictionary.containsKey(term);
    }

    void addNewTerm(PreTerm preTerm, int ptr) {
        dictionary.put(preTerm.getName(), new PostTerm(preTerm, ptr));

    }

    public PostTerm getTerm(String term) {
        return dictionary.get(term);
    }

    synchronized int updateTerm(String term, int tf) {
        PostTerm pterm = dictionary.get(term);
        pterm.increaseTf(tf);
        pterm.increaseDf();
        //add values by ptr
        return pterm.getPtr();
    }


    void printDic() {
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
