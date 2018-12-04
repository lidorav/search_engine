package Model.Index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Posting {

    private String path = "C:\\Users\\USER\\Desktop\\retrivel\\WORK\\Posting";
    private static int fileID=1;
    public void initTempPosting(TreeMap<String,StringBuilder> tempPosting){
        File file = new File(path+"\\"+fileID);
        try {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Map.Entry entry : tempPosting.entrySet()) {
                bw.write(entry.getKey()+":"+entry.getValue()+"\r\n");
            }
            bw.close();
        }
        catch(IOException e){
                e.printStackTrace();
            }
            fileID++;

    }

}
