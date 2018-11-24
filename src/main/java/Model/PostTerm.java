package Model;

public class PostTerm {
    private String name;
    private int df;
    private int tf;
    private int ptr;


    public PostTerm(String name,int tf, int ptr) {
        this.name = name;
        this.tf = tf;
        this.df = 1;
        this.ptr = ptr;
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
        return "Term{" +
                "name='" + name + '\'' +
                ", df=" + df +
                ", tf=" + tf +
                ", ptr=" + ptr +
                '}';
    }
}
