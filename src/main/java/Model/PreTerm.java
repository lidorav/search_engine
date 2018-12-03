package Model;

public class PreTerm {
    private String name;
    private String docID;
    private int tf;
    private boolean inTitle;
    private boolean atBeginOfDoc;

    public PreTerm(String name, String docID, boolean atBegin) {
        this.name = name;
        this.docID = docID;
        this.tf = 1;
        isInTitle();
        this.atBeginOfDoc = atBegin;
    }

    private void isInTitle(){
        //checks if the term is in the title opf the doc
        Document doc = ReadFile.getDoc(docID);
        String title = doc.getTitle().toLowerCase();
        inTitle = title.contains(name.toLowerCase());
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

    public boolean getAtBeginOfDoc() {
        return atBeginOfDoc;
    }
}
