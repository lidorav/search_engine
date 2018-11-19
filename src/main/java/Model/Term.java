package Model;

import java.util.concurrent.ConcurrentHashMap;

public class Term {
    private String name;
    private ConcurrentHashMap<String, Integer> docTf = new ConcurrentHashMap<>();
    private int df;
    private int tf;

    public Term(String name, String docID) {
        this.name = name;
        addShow(docID);
    }

    public void addShow(String docID) {
        docTf.merge(docID, 1, (a, b) -> a + b);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * with a given token - return its df
     * @param token
     * @return df
     */
    public int countDf (String token) {
        this.df = 0;
        if (docTf.containsKey(token)) {
            df = docTf.get(token);
        }
        return df;
    }
    @Override
    public String toString() {
        return "Term{" +
                "name='" + name + '\'' +
                ", docTf=" + docTf + '\'' +
                ", docdf=" + df +
                '}';
    }

    public void print(){
        for (int i:docTf.values())
        System.out.println(i);
              {

        }
    }

}
