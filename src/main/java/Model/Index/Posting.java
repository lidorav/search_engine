package Model.Index;

import java.io.File;
import java.io.IOException;

public class Posting {

    private String path = "C:\\Users\\USER\\Desktop\\retrivel\\WORK\\Posting";
    public Posting(){
        new File(path).mkdirs();
        for(int i=0 ; i<=9 ; i++){
            String fileName = i + ".txt";
            try {
                new File(path+"\\"+fileName).createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(char alphabet = 'a'; alphabet <='z'; alphabet++ ) {
            String fileName = alphabet + ".txt";
            try {
                    new File(path + "\\" + fileName).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}
