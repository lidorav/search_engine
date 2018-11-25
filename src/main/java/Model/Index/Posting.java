package Model.Index;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class Posting {

    private String path ="C:\\Users\\nkutsky\\Desktop\\Retrival\\Posting";
    private Hashtable<String,Integer> postLines;

    public Posting(){
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        postLines = new Hashtable<>();
        new File(path).mkdirs();
        for(int i=0 ; i<=9 ; i++){
            String fileName = i + ".txt";
            try {
                new File(path+"\\"+fileName).createNewFile();
                postLines.put(fileName,-1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(char alphabet = 'a'; alphabet <='z'; alphabet++ ) {
            String fileName = alphabet + ".txt";
            try {
                new File(path + "\\" + fileName).createNewFile();
                postLines.put(fileName,-1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            new File(path+"\\"+"symbols.txt").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        postLines.put("symbols.txt",-1);
    }

    public int addToFile(char c, String docID, int tf) {
            String filename = getFileName(c);
            int ptr = -1;
            File file = new File(path + "\\" + filename);
            CharSink chs = Files.asCharSink(
                    file, Charsets.UTF_8, FileWriteMode.APPEND);
            try {
                chs.write(docID + ":" + tf + ",\r\n");
                ptr = postLines.get(filename) + 1;
                postLines.put(filename, ptr);
            } catch (IOException e) {
                e.printStackTrace();
            }


        return ptr;
    }

        public void updateFile(char c, String docID, int tf, int ptr){
            String filename = getFileName(c);
            int lineCounter = 0;
            File file = new File(path + "\\" + filename);
            try {
                List<String> lines = Files.readLines(file, Charsets.UTF_8);
                for(String line: lines){
                    if(lineCounter != ptr) {
                        lineCounter++;
                        continue;
                    }
                    else{
                         lines.set(lineCounter,line+docID + ":" + tf + ",");
                         break;
                    }
                }
                FileUtils.writeLines(file,lines);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private String getFileName(char c){
            String filename;
            if(isSymbol(c)){
                filename = "symbols.txt";
            }
            else
                filename = Character.toLowerCase(c) + ".txt";
            return filename;
        }

        private boolean isSymbol (char c){
        if (!Character.isDigit(c) && !Character.isLetter(c))
            return true;
        return false;
        }
}

