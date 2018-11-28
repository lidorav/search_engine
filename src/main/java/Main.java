import Model.Index.Posting;
import Model.ReadFile;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args){
        ReadFile reader = new ReadFile("C:\\Users\\USER\\Desktop\\retrivel\\WORK\\corpus");
        long startTime = System.nanoTime();
        reader.read();
        reader.print();
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in sec : " +
                timeElapsed / 1000000000);



        //System.out.println(term);
       /**  ANumbers temp;
        temp = new ANumbers();
        System.out.println(temp.classifyNumber("10045400403"));
        */


    }
}
