package mongoDb;

import com.mongodb.*;

import java.util.ArrayList;

/**
 * User: shellbye.com@gmail.com
 * Date: 2015/4/8
 */
public class FindPerformance {
    public static void main(String[] args) throws Exception {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("db_name");
        DBCollection collection = db.getCollection("col_name");// over 3 million document
        ArrayList<String> testName = new ArrayList<>();
        // add ten test name in testName

        ArrayList<String> testUrl = new ArrayList<>(); // fields with index
        // add ten test url in testUrl

        Long total_0 = 0L;
        Long total_1 = 0L;
        Long total_2 = 0L;
        Long total_3 = 0L;

        int j = 0;
        for (int i = 0; i < 10; i++){
            Long before0 = System.currentTimeMillis();
            DBCursor cursor0 = collection.find(new BasicDBObject().append("name", testName.get(i))).limit(1);
            if (cursor0.hasNext())
                j++;
            Long result0 = System.currentTimeMillis() - before0;
            total_0 += result0;


            Long before1 = System.currentTimeMillis();
            DBObject dbObject1 = collection.findOne(new BasicDBObject().append("name", testName.get(i)));
            if (dbObject1 != null)
                j++;
            Long result1 = System.currentTimeMillis() - before1;
            total_1 += result1;



            Long before2 = System.currentTimeMillis();
            DBCursor cursor2 = collection.find(new BasicDBObject().append("url_name", testUrl.get(i))).limit(1);
            if (cursor2.hasNext())
                j++;
            Long result2 = System.currentTimeMillis() - before2;
            total_2 += result2;


            Long before3 = System.currentTimeMillis();
            DBObject dbObject3 = collection.findOne(new BasicDBObject().append("url_name", testUrl.get(i)));
            if (dbObject3 != null)
                j++;
            Long result3 = System.currentTimeMillis() - before3;
            total_3 += result3;
        }
        System.out.println(total_0);
        System.out.println(total_1);
        System.out.println(total_2);
        System.out.println(total_3);
    }
}
