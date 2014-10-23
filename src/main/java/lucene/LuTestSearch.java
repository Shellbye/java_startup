package lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by shellbye on 10/23/14.
 */
public class LuTestSearch {
    public static void main(String[] arg) throws Exception{
        //使用LuTestIndex.java建立索引之后索引所在的目录（indexPath）
        String index = "/home/shellbye/result/";
        String field = "contents";

        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        QueryParser parser = new QueryParser(field, analyzer);
        while (true) {
            System.out.println("Enter query: ");     // prompt the user
            String line = in.readLine();
            if (line == null || line.length() == -1) {
                break;
            }
            line = line.trim();
            if (line.length() == 0) {
                break;
            }

            Query query = parser.parse(line);
            System.out.println("Searching for: " + query.toString(field));
            // 每页显示的数量
            Integer hitsPerPage = 5;
            doPagingSearch(in, searcher, query, hitsPerPage);
        }
        reader.close();
    }

    /**
     * This demonstrates a typical paging search scenario, where the search engine presents
     * pages of size n to the user. The user can then go to the next page if interested in
     * the next hits.
     */
    public static void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query,
                                      int hitsPerPage) throws IOException {

        // 至多搜索5页的结果
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");

        int start = 0;

        while (true) {
            int end = Math.min(hits.length, start + hitsPerPage);

            // 遍历每一页
            for (int i = start; i < end; i++) {
                Document doc = searcher.doc(hits[i].doc);
                String path = doc.get("path");
                if (path != null) {
                    System.out.println((i + 1) + ". " + path);
                    String title = doc.get("title");
                    if (title != null) {
                        System.out.println("   Title: " + doc.get("title"));
                    }
                } else {
                    System.out.println((i + 1) + ". " + "No path for this document");
                }
                System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
            }


            if (end == 0) break;

            if (numTotalHits >= end) {
                boolean quit = false;
                while (true) {
                    System.out.print("Press ");
                    if (start - hitsPerPage >= 0) {
                        System.out.print("(p)revious page, ");
                    }
                    if (start + hitsPerPage < numTotalHits) {
                        System.out.print("(n)ext page, ");
                    }
                    System.out.println("(q)uit or enter number to jump to a page.");

                    String line = in.readLine();
                    if (line.length() == 0 || line.charAt(0) == 'q') {
                        quit = true;
                        break;
                    }
                    if (line.charAt(0) == 'p') {
                        start = Math.max(0, start - hitsPerPage);
                        break;
                    } else if (line.charAt(0) == 'n') {
                        if (start + hitsPerPage < numTotalHits) {
                            start += hitsPerPage;
                        }
                        break;
                    } else {
                        int page = Integer.parseInt(line);
                        if ((page - 1) * hitsPerPage < numTotalHits) {
                            start = (page - 1) * hitsPerPage;
                            break;
                        } else {
                            System.out.println("No such page");
                        }
                    }
                }
                if (quit) break;
                end = Math.min(numTotalHits, start + hitsPerPage);
            }
        }
    }
}
