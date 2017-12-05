package lucene;

import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	
   String indexDir = "/Users/pratikpatil/Documents/IR/Project/Training";
   String dataDir =  "/Users/pratikpatil/Documents/IR/Project/Training/TrainingData";
   Indexer indexer;
   Searcher searcher;
   static String question;
   public static void main(String[] args) {
      LuceneTester tester; 
      try {	
    	  Scanner sc=new Scanner(System.in);
         tester = new LuceneTester();
         tester.createIndex();
         System.out.println("Enter your question");
         question = sc.nextLine();
         sc.close();
         tester.search(question);
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   private void createIndex() throws IOException {
      indexer = new Indexer(indexDir);
      int numIndexed;
      long startTime = System.currentTimeMillis();	
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
      long endTime = System.currentTimeMillis();
      indexer.close();
      System.out.println(numIndexed+" File indexed, time taken: "
         +(endTime-startTime)+" ms");		
   }

   private void search(String searchQuery) throws IOException, ParseException {
      searcher = new Searcher(indexDir);
      long startTime = System.currentTimeMillis();
      TopDocs hits = searcher.search(searchQuery);
      long endTime = System.currentTimeMillis();
   
      System.out.println(hits.totalHits +
         " documents found. Time :" + (endTime - startTime));
      System.out.println("\n Extracted information is........... \n");
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: "
            + doc.get(LuceneConstants.FILE_PATH));
     /*   Analyzer a ;
		QueryParser qp = new QueryParser(3.6, question, a ) */
            
            
      }
      searcher.close();
   }
}
