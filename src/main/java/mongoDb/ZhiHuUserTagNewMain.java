package mongoDb;

import com.mongodb.*;

import java.util.List;

/**
 * Created by shellbye on 11/3/14.
 */
public class ZhiHuUserTagNewMain {

    static MongoClient mongoClient;
    static DBCollection zhihuuserCollection;
    static DBCollection zhihu_user_question_tagCollection;
    static DB db;
    static List<DBObject> zhihuuserList;


    public static void main(String args[]) throws Exception{
        mongoClient = new MongoClient("192.168.2.222", 27017);
        db = mongoClient.getDB("user");
        zhihu_user_question_tagCollection = db.getCollection("zhihu_user_question_tag");
        BasicDBObject fields = new BasicDBObject("name",1);
        zhihuuserCollection = db.getCollection("zhihuuser");
        zhihuuserList = zhihuuserCollection.find(new BasicDBObject(), fields).toArray();
        int userPerThread = 100;
        int threadCount = 100;
        for (int i = 0; i < threadCount; i++) {
            ZhiHuUserTagRunner R1 = new ZhiHuUserTagRunner(Integer.toString(i), zhihuuserList,
                    i * userPerThread, (i + 1)* userPerThread);
            R1.start();
        }
    }
}
