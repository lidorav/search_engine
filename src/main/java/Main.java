import Model.ReadFile;

public class Main {
    public static void main(String[] args) {
        ReadFile reader = new ReadFile("C:\\Users\\USER\\Desktop\\retrivel\\WORK\\corpus");

        reader.read();
        reader.print();
    }
}
