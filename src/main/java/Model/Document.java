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

    public String getFileName() {
        return fileName;
    }

    public void setMaxTf(int maxTf) {
        this.maxTf = maxTf;
    }

    public void setUniqueTf(int uniqueTf) {
        this.uniqueTf = uniqueTf;
    }

    public String getTitle() {
        return title;
    }
}
