package Model.Index;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Posting {

    private String path = "C:\\Users\\nkutsky\\Desktop\\Retrival\\Posting";
    private Hashtable<String, Integer> postLines;

    public Posting() {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        postLines = new Hashtable<>();
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

    public void addToFile(HashMap<Character, TreeMap<Integer, String>> newTermsToWrite) {
        for (Map.Entry<Character, TreeMap<Integer, String>> mapEntry:newTermsToWrite.entrySet()) {
            String filename = getFileName(mapEntry.getKey());
            TreeMap<Integer,String> tree = mapEntry.getValue();
            File file = new File(path+"\\"+filename);
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
                for (String line:tree.values()) {
                    out.write(line);
                }
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void updateFile(HashMap<Character, TreeMap<Integer, String>> updateTermsToWrite) {
        int lineCounter = 0;
        for (Map.Entry<Character, TreeMap<Integer, String>> mapEntry : updateTermsToWrite.entrySet()) {
            String filename = getFileName(mapEntry.getKey());
            TreeMap<Integer, String> tree = mapEntry.getValue();
            if(tree.isEmpty())
                continue;
            File fileFrom = new File(path+"\\"+filename);
            File fileTo = new File(path+"\\"+filename+1);
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileFrom));
                BufferedWriter out = new BufferedWriter(new FileWriter(fileTo));
                String sCurrentLine;
                Set set = tree.entrySet();
                Iterator it = set.iterator();
                Map.Entry<Integer, String> treeEntry;
                treeEntry = (Map.Entry) it.next();
                while ((sCurrentLine = br.readLine()) != null) {
                    if (lineCounter == treeEntry.getKey()) {
                        //change and write tp file
                        String newLine = sCurrentLine + treeEntry.getValue() + "\r\n";
                        out.write(newLine);
                        if(it.hasNext())
                            treeEntry = (Map.Entry) it.next();
                    } else {
                        //write to file without change
                        out.write(sCurrentLine+"\r\n");
                    }
                    lineCounter++;
                }
                br.close();
                out.close();
                fileFrom.delete();
                fileTo.renameTo(fileFrom);
                lineCounter=0;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
        if (!Character.isDigit(c) && !Character.isLetter(c))
            return true;
        return false;
    }

    public int getNextLine(char c) {
        String filename = getFileName(c);
        int ptr = postLines.get(filename) + 1;
        postLines.put(filename, ptr);
        return ptr;
    }
}

