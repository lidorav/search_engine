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

    private String path ="C:\\Users\\USER\\Desktop\\retrivel\\WORK\\Posting";
    private Hashtable<String,Integer> postLines;

    public Posting() throws IOException {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        postLines = new Hashtable<>();
        new File(path).mkdirs();
        postCreate099();
        postCreateAZ();
        postCreateLetterNumber();
        postCreateNumberLetter();


    }

    private void postCreateAZ(){
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            for (char alphabet2 = 'a'; alphabet2 <= 'z'; alphabet2++) {
                String fileName = alphabet + ".txt";
                String fileName2 = "" + alphabet + alphabet2 +".txt";
                try {
                    new File(path + "\\" + fileName).createNewFile();
                    new File(path + "\\" + fileName2).createNewFile();
                    postLines.put(fileName, -1);
                    postLines.put(fileName2, -1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                new File(path + "\\" + "symbols.txt").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            postLines.put("symbols.txt", -1);
        }

    }

    private void postCreate099() {
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                String fileName = "" + i + j + ".txt";
                try {
                    new File(path + "\\" + fileName).createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                postLines.put(fileName, -1);
            }
            postLines.put("" + i , -1);
        }
    }

        private void postCreateNumberLetter () {
            String fileName;
            for (int i = 0; i <= 9; i++) {
                for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                    fileName = "" + i + alphabet + ".txt";
                    //    String fileName2 = "" + alphabet + i + ".txt";
                    try {
                        new File(path + "\\" + fileName).createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    postLines.put(fileName, -1);
                }

            }

        }

        private void postCreateLetterNumber () {
            String fileName;
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                for (int i = 0; i <= 9; i++) {
                    fileName = "" + alphabet + i + ".txt";
                    try {
                        new File(path + "\\" + fileName).createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    postLines.put(fileName, -1);
                }

            }

        }


    public int addToFile(String fileName, String docID, int tf) {
            String filename = getFileName(fileName);
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

        public void updateFile(String fileName, String docID, int tf, int ptr){
            String filename = getFileName(fileName);
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

        private String getFileName(String fileName){
        if(fileName.length() == 2)
        fileName = intoPlace(fileName);
        if(isSymbol(fileName)){
                fileName = "symbols.txt";
            }
            else
                fileName = fileName.toLowerCase() + ".txt";
            return fileName;
        }

        private boolean isSymbol (String fileName){
        if (!Character.isDigit(fileName.charAt(0)) && !Character.isLetter(fileName.charAt(0)))
            return true;
        return false;
        }

        private String intoPlace(String fileName){
            if(isSymbol(fileName))
                return fileName.substring(1,1);

            if( !Character.isDigit(fileName.charAt(1)) && !Character.isLetter(fileName.charAt(1))){
                return fileName.substring(0,1);
            }

            return fileName ;
        }
}

