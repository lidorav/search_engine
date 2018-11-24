package Model.Index;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

public class Posting {

    private String path ="C:\\Users\\USER\\Desktop\\retrivel\\WORK\\Posting";

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

    public void addToFile(char c, String docID, int tf) {

            File file = new File(path + "\\" + c + ".txt");
            CharSink chs = Files.asCharSink(
                    file, Charsets.UTF_8, FileWriteMode.APPEND);
            try {
                chs.write(docID + "-" + tf + ",");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void updateFile () {
        }
}

