package Model;

public class PreTerm {
    private String name;
    private String docID;
    private int tf;
    private boolean inTitle;

    public PreTerm(String name, String docID) {
        this.name = name;
        this.docID = docID;
        this.tf = 1;
    }

    public String getName() {
        return name;
    }

    public String getDocID() {
        return docID;
    }

    public int getTf() {
        return tf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void increaseTf() {
        this.tf++;
    }

    public void setInTitle(boolean inTitle){
        this.inTitle = inTitle;
    }

    public boolean getInTitle(){
        return inTitle;
    }
}
