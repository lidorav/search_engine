package Model;

import Model.Parse.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ReadFile {
    private File corpus;
    private Parser parser;
    private HashMap<String, Model.Document> docMap;



    //constructor
    public ReadFile(String path){
        corpus = new File(path);
        parser = new Parser();
        docMap = new HashMap<>();
        //semaphore = new Semaphore(1);
    }

    public void read() {
        int i = 0;
        for (File dir : corpus.listFiles()) {
            for (File file : dir.listFiles()) {
                try {
                    System.out.println(file.getName());
                    Document doc = Jsoup.parse(file, "UTF-8");
                    Elements documents = doc.getElementsByTag("DOC");
                    for (Element element : documents) {
                        String docTitle="";
                        String docNum = element.getElementsByTag("DOCNO").get(0).text();
                        Elements docTitleElement = element.getElementsByTag("TI");
                        if(docTitleElement.isEmpty())
                            docTitleElement = element.getElementsByTag("HEADLINE");
                        if(!docTitleElement.isEmpty()){
                            docTitle = docTitleElement.get(0).text();
                        }
                        String docCity = "";
                        Elements docCityElement = element.getElementsByTag("F");

                        if (!docCityElement.isEmpty()) {
                            if (docCityElement.attr("p").equals("104")) {
                                docCity = docCityElement.get(0).text();
                                docCity = docCity.split(" ")[0].toUpperCase();
                            }
                        }
                        docMap.put(docNum, new Model.Document(file.getName(), docTitle, i++, docCity));
                        String data = element.getElementsByTag("TEXT").get(0).text();
                        parser.parse(docNum, data);
                    }
                } catch (Exception e) {}
            }
            parser.deployFile();
        }
        //parser.shutDownExecutor();
    }

    public void print() {
        parser.printDic();
    }
}
