import Model.ReadFile;
import Model.Term;

public class Main {
    public static void main(String[] args) {
        ReadFile reader = new ReadFile("C:\\Users\\USER\\Desktop\\retrivel\\WORK\\corpus");

        reader.read();
        reader.print();


        //System.out.println(term);
       /**  ANumbers temp;
        temp = new ANumbers();
        System.out.println(temp.classifyNumber("10045400403"));
        */


    }
}
