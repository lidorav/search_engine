import Model.Document;
import Model.Index.Indexer;
import Model.Parse.Parser;
import Model.PreTerm;
import Model.ReadFile;

import java.util.HashMap;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args){
        BlockingQueue<Document> queueA = new LinkedBlockingQueue<>();
        BlockingQueue<ConcurrentHashMap<String, PreTerm>> queueB = new LinkedBlockingQueue<>();
        ReadFile reader = new ReadFile("D:\\corpus", queueA);
        Parser parser = new Parser(queueA,queueB);
        Indexer indexer = new Indexer(queueB);
        long startTime = System.nanoTime();
        Thread t1 = new Thread(reader);
        Thread t2 = new Thread(parser);
        Thread t3 = new Thread(indexer);
        t1.start();
        t2.start();
        t3.start();
        try{
            t1.join();
            t2.join();
            t3.join();
        }catch (Exception e){}

        //starting consumer to consume messages from queue
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        indexer.printDic();

        System.out.println("Execution time in sec : " +
                timeElapsed / 1000000000);



        //System.out.println(term);
       /**  ANumbers temp;
        temp = new ANumbers();
        System.out.println(temp.classifyNumber("10045400403"));
        */


    }
}
