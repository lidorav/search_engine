package Model.Index;

import java.io.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Posting {

    private String path = "C:\\Users\\nkutsky\\Desktop\\Retrival\\Posting";
    private static int fileID=1;
    private Queue<String> postingQueue;

    public Posting(){
        postingQueue = new LinkedList();
    }

    public void initTempPosting(TreeMap<String,StringBuilder> tempPosting){
        File file = new File(path+"\\"+fileID);
        try {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            postingQueue.add(String.valueOf(fileID));
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

    public void mergePosting(){
        while(postingQueue.size()>1){
            String postA = postingQueue.poll();
            String postB = postingQueue.poll();
            File fileFromA = new File(path+"\\"+postA);
            File fileFromB = new File(path+"\\"+postB);
            String newFile = postA + "+" + postB;
            postingQueue.add(newFile);
            File fileTo = new File(path+"\\"+newFile);
            try {
                BufferedReader brA = new BufferedReader(new FileReader(fileFromA));
                BufferedReader brB = new BufferedReader(new FileReader(fileFromB));
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileTo));

                //if(brA)
            }catch (IOException e){}
        }
    }



}
