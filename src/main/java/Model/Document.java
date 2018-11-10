package Model;

public class Document {

    private int maxTf;
    private int uniqueTf;
    private String city;
    private String fileName;
    private String title;
    private int position ;

    @Override
    public String toString() {
        return "Document{" +
                "city='" + city + '\'' +
                ", fileName='" + fileName + '\'' +
                ", title='" + title + '\'' +
                ", position=" + position +
                '}';
    }

    public Document(String fileName, String title, int position, String city) {
        this.city = city;
        this.fileName = fileName;
        this.title = title;
        this.position= position;
    }
}
