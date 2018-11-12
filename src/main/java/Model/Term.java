package Model;

import java.util.concurrent.ConcurrentHashMap;

public class Term {
    private String name;
    private ConcurrentHashMap<String, Integer> docTf = new ConcurrentHashMap<>();
    private Double idf;

    public Term(String name, String docID) {
        this.name = name;
        addShow(docID);
    }

    public void addShow(String docID) {
        docTf.merge(docID, 1, (a, b) -> a + b);
    }

    public String getName() {
        return name;
    }

    public Double getIdf() {
        return idf;
    }

    @Override
    public String toString() {
        return "Term{" +
                "name='" + name + '\'' +
                ", docTf=" + docTf +
                '}';
    }
}
