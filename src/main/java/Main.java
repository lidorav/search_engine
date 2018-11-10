import Model.ReadFile;

public class Main {
    public static void main(String[] args) {
        ReadFile reader = new ReadFile("C:\\Users\\nkutsky\\Desktop\\corpus");

        reader.read();
        reader.print();
    }
}
