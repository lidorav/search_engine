package Model.Garbage;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Posting {

    private String path = " //C:\\Users\\USER\\Desktop\\retrivel\\WORK\\Posting";
    //C:\Users\nkutsky\Desktop\Retrival\Posting
    //C:\Users\USER\Desktop\retrivel\WORK\Posting
    private HashMap<String, Integer> postLines;

    Posting() {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        postLines = new HashMap<>();
        new File(path).mkdirs();
        for (int i = 0; i <= 9; i++) {
            String fileName = i + ".txt";
            try {
                new File(path + "\\" + fileName).createNewFile();
                postLines.put(fileName, -1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String fileName = alphabet + ".txt";
            try {
                new File(path + "\\" + fileName).createNewFile();
                postLines.put(fileName, -1);
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

    void addToFile(HashMap<Character, TreeMap<Integer, StringBuilder>> newTermsToWrite) {
        for (Map.Entry<Character, TreeMap<Integer, StringBuilder>> mapEntry:newTermsToWrite.entrySet()) {
            String filename = getFileName(mapEntry.getKey());
            TreeMap<Integer,StringBuilder> tree = mapEntry.getValue();
            File file = new File(path+"\\"+filename);
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
                for (StringBuilder line:tree.values()) {
                    out.write(line.toString());
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    void updateFile(HashMap<Character, TreeMap<Integer, StringBuilder>> updateTermsToWrite, HashMap<Character, TreeMap<Integer, StringBuilder>> newTermsToWrite) {
        int lineCounter = 0;
        for (Map.Entry<Character, TreeMap<Integer, StringBuilder>> mapEntry : updateTermsToWrite.entrySet()) {
            String filename = getFileName(mapEntry.getKey());
            TreeMap<Integer, StringBuilder> treeUpdate = mapEntry.getValue();
            if(treeUpdate.isEmpty())
                continue;
            File fileFrom = new File(path+"\\"+filename);
            File fileTo = new File(path+"\\"+filename+1);
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileFrom));
                BufferedWriter out = new BufferedWriter(new FileWriter(fileTo));
                String sCurrentLine;
                Set<Map.Entry<Integer, StringBuilder>> set = treeUpdate.entrySet();
                Iterator<Map.Entry<Integer, StringBuilder>> it = set.iterator();
                Map.Entry<Integer, StringBuilder> treeEntry = it.next();
                while ((sCurrentLine = br.readLine()) != null) {
                    if (lineCounter == treeEntry.getKey()) {
                        //change and write tp file
                        out.write(sCurrentLine + treeEntry.getValue().toString() + "\r\n");
                        if(it.hasNext())
                            treeEntry = it.next();
                    } else {
                        //write to file without change
                        out.write(sCurrentLine+"\r\n");
                    }
                    lineCounter++;
                }
                TreeMap<Integer, StringBuilder> treeNew = newTermsToWrite.get(mapEntry.getKey());
                for(int lineNum:treeNew.keySet()){
                    if(treeUpdate.containsKey(lineNum)){
                        out.write(treeNew.get(lineNum).append(treeUpdate.get(lineNum).append("\r\n")).toString());
                    }
                    else{
                        out.write(treeNew.get(lineNum).append("\r\n").toString());
                    }
                }

                br.close();
                out.close();
                fileFrom.delete();
                fileTo.renameTo(fileFrom);
                lineCounter=0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileName(char c) {
        String filename;
        if (isSymbol(c)) {
            filename = "symbols.txt";
        } else
            filename = Character.toLowerCase(c) + ".txt";
        return filename;
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && !(c >= 'a' && c <= 'z');
    }

    int getNextLine(char c) {
        String filename = getFileName(c);
        try {
            int ptr = postLines.get(filename) + 1;
            postLines.put(filename, ptr);
            return ptr;
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }
}

