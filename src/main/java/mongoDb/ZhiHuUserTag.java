package mongoDb;

import com.mongodb.*;
import java.security.MessageDigest;

/**
 * Created by shellbye on 10/30/14.
 */
public class ZhiHuUserTag {
    public static void main(String[] args){
        try{
            MongoClient mongoClient = new MongoClient( "192.168.2.221" , 27017 );
            DB db = mongoClient.getDB( "user" );

            DBCollection zhihuuserCollection = db.getCollection("zhihuuser");
            DBCollection zhihu_questionCollection = db.getCollection("zhihu_question");
            DBCursor zhihuuserCursor = zhihuuserCollection.find();

            // todo while
            for(int i = 0; i < 10; i++){
                // 1.在zhihuuser中获取用户的id，即name字段
                DBObject currentZhiHuUser = zhihuuserCursor.next();
                String name = (String)currentZhiHuUser.get("name");
                // todo record
//                System.out.println(name);


                // 2.通过name在zhihu_answers中找到用户的所有问题
                DBCollection zhihu_answersCollection = db.getCollection("zhihu_answers");
                BasicDBObject query = new BasicDBObject();
                query.put("name", name);
                DBObject zhiHuAnswer = zhihu_answersCollection.findOne(query);
//                todo record
//                System.out.println(zhiHuAnswer.get("_id"));
                DBObject zhihuQuestions = (DBObject)zhiHuAnswer.get("questions");
                for(String key : zhihuQuestions.keySet()){
                    String question = (String)zhihuQuestions.get(key);
                    String idOfQuestion = md5(question);
                    // todo record
//                    System.out.println(idOfQuestion);

                    // 用于验证md5的找法是正确的
                    BasicDBObject queryOfQuestion = new BasicDBObject();
                    queryOfQuestion.put("id", idOfQuestion);
                    DBCursor zhihu_questionCursor = zhihu_questionCollection.find(queryOfQuestion);
                    if (zhihu_questionCursor.hasNext()){
                        // 3.找到问题对应的tags
                        DBObject questionObject = zhihu_questionCursor.next();
                        String tags = (String)questionObject.get("tags");
                        System.out.println(tags);
                        for(String tag : tags.split(",")){
                            System.out.println(tag.trim());
                        }
                    }
                }
            }
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public static String md5(String in) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(in.getBytes("UTF-8"));

        byte byteData[] = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }
}
