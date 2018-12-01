package Model;

public class PostTerm {
    private String name;
    private int df;
    private int tf;
    private int ptr;
    private boolean inTitle;
    private boolean atBeginOfDoc;

    public PostTerm(PreTerm preTerm, int ptr){
        this.name = preTerm.getName();
        this.tf = preTerm.getTf();
        this.df = 1;
        this.ptr = ptr;
        this.inTitle = preTerm.getInTitle();
        this.atBeginOfDoc = preTerm.getAtBeginOfDoc();


    }

    public void increaseTf(int tf){
        this.tf +=tf;
    }

    public void increaseDf(){
        this.df++;
    }

    public void setPtr(int ptr){
        this.ptr = ptr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDf() {
        return df;
    }

    public int getTf() {
        return tf;
    }

    public int getPtr() {
        return ptr;
    }

    @Override
    public String toString() {
        return "PostTerm{" +
                "name='" + name + '\'' +
                ", df=" + df +
                ", tf=" + tf +
                ", ptr=" + ptr +
                ", inTitle=" + inTitle +
                ", atBeginOfDoc=" + atBeginOfDoc +
                '}';
    }
}
