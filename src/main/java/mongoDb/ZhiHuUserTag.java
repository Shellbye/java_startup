package mongoDb;

import com.mongodb.*;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by shellbye on 10/30/14.
 */
public class ZhiHuUserTag {
    static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Enter the main");
        try {
            MongoClient mongoClient = new MongoClient("192.168.2.221", 27017);
            DB db = mongoClient.getDB("user");

            DBCollection zhihuuserCollection = db.getCollection("zhihuuser");
            DBCollection zhihu_questionCollection = db.getCollection("zhihu_question");
            DBCollection zhihu_user_question_tagCollection = db.getCollection("zhihu_user_question_tag");
            BasicDBObject fields = new BasicDBObject("name",1);
            List<DBObject> zhihuuserList = zhihuuserCollection.find(new BasicDBObject(), fields).toArray();


            int i = 0;
            for (DBObject currentZhiHuUser : zhihuuserList) {
                // for test purpose
                if (i++ >= 100) {
                    logger.info("Quiting...");
                    break;
                }
                logger.info("Enter the user-while loop and process number " + i);
                // 1.在zhihuuser中获取用户的id，即name字段
                String name = (String) currentZhiHuUser.get("name");

                BasicDBObject queryOfUserTag = new BasicDBObject();
                queryOfUserTag.put("name", name);
                DBCursor zhihu_user_question_tagCursor = zhihu_user_question_tagCollection.find(queryOfUserTag);
                DBObject zhihu_user_tag_Object;
                if (zhihu_user_question_tagCursor.hasNext()) {
                    // todo 已有101个用户在数据库，这里就暂时不再重复处理，直接跳过
                    continue;
//                    zhihu_user_tag_Object = zhihu_user_question_tagCursor.next();
                } else {
                    zhihu_user_tag_Object = create(zhihu_user_question_tagCollection, name);
                }


                // 2.通过name在zhihu_answers中找到用户的所有问题
                DBCollection zhihu_answersCollection = db.getCollection("zhihu_answers");
                BasicDBObject query = new BasicDBObject();
                query.put("name", name);
                DBObject zhiHuAnswer = zhihu_answersCollection.findOne(query);
                if (zhiHuAnswer == null) {
                    continue;
                }
                update(zhihu_user_question_tagCollection, zhihu_user_tag_Object,
                        "zhihu_answers_id", zhiHuAnswer.get("_id"));

                DBObject zhihuQuestions = (DBObject) zhiHuAnswer.get("questions");
                // 3.遍历所有的问题的内容，通过对内容进行md5找到真正的问题
                for (String key : zhihuQuestions.keySet()) {
//                    logger.info("Enter the question-for loop and process question NO." + key);
                    String question = (String) zhihuQuestions.get(key);
                    String idOfQuestion = md5(question);


                    update(zhihu_user_question_tagCollection, zhihu_user_tag_Object,
                            "zhihu_question_md5_id", idOfQuestion);


                    BasicDBObject queryOfQuestion = new BasicDBObject();
                    queryOfQuestion.put("id", idOfQuestion);
                    DBObject zhihu_questionObject = zhihu_questionCollection.findOne(queryOfQuestion);

                    if (zhihu_questionObject != null) {
                        // 4.找到真正的问题，以及其对应的tags
                        String questionId = zhihu_questionObject.get("_id").toString();
                        update(zhihu_user_question_tagCollection, zhihu_user_tag_Object,
                                "zhihu_question_id", questionId);
                        logger.info("Got the real question " + questionId);
                        String tags = (String) zhihu_questionObject.get("tags");
                        logger.info("Enter the tags-for loop and the tags is " + tags);
                        for (String tag : tags.split(",")) {
                            String trimmedTag = tag.trim();
                            if (tagAlreadyExists(zhihu_user_tag_Object, trimmedTag)) {
                                tagCountIncrease(zhihu_user_question_tagCollection, zhihu_user_tag_Object, trimmedTag);
                            } else {
                                addTag(zhihu_user_question_tagCollection, zhihu_user_tag_Object, trimmedTag);
                            }
                        }
                        doRank(zhihu_user_question_tagCollection, zhihu_user_tag_Object);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static DBObject create(DBCollection collection, String name) {
        logger.info("create name " + name);
        BasicDBObject basicDBObject = new BasicDBObject("name", name).append("tags_info", new BasicDBObject());
        collection.insert(basicDBObject);
        return basicDBObject;
    }

    public static void update(DBCollection collection, DBObject dbObject, String key, Object value) {
        logger.info("update " + dbObject.get("_id") + " key " + key + " to " + value);
        dbObject.put(key, value);
        collection.save(dbObject);
    }

    public static boolean tagAlreadyExists(DBObject dbObject, String tag) {
        BasicDBObject tagsInfo = (BasicDBObject) dbObject.get("tags_info");
        return tagsInfo.get(tag) != null;
    }

    public static void tagCountIncrease(DBCollection collection, DBObject dbObject, String tag) throws Exception {
        logger.info("tag " + tag + " increase");
        BasicDBObject tagsInfo = (BasicDBObject) dbObject.get("tags_info");
        BasicDBObject t = (BasicDBObject) tagsInfo.get(tag);
        t.put("count", (int) t.get("count") + 1);
//        doRank(collection, dbObject);
    }

    public static void addTag(DBCollection collection, DBObject dbObject, String tag) throws Exception {
        logger.info("add tag " + tag);
        BasicDBObject tagsInfo = (BasicDBObject) dbObject.get("tags_info");
        if (tag.contains(".")) {
            tagsInfo.put(tag.replace(".", "\\u002e"), new BasicDBObject("count", 1).append("rank", -1));
        } else {
            tagsInfo.put(tag, new BasicDBObject("count", 1).append("rank", -1));
        }
        collection.save(dbObject);
//        doRank(collection, dbObject);
    }

    public static void doRank(DBCollection collection, DBObject dbObject) throws Exception {
        logger.info("Enter doRank and rank user " + dbObject.get("_id"));
        BasicDBObject basicDBObject = (BasicDBObject) dbObject.get("tags_info");

        // 1.创建比较对象
        Tag[] tags = new Tag[basicDBObject.size()];
        int i = 0;
        for (String key : basicDBObject.keySet()) {
            BasicDBObject tag = (BasicDBObject) basicDBObject.get(key);
            Tag new_tag = new Tag(tag.get("count"), tag.get("rank"), key);
            tags[i++] = new_tag;
        }

        // 2.对象排序
        Arrays.sort(tags);

        // 3.存储排序结果
        int j = 0;
        for (Tag t : tags) {
            BasicDBObject tag = (BasicDBObject) basicDBObject.get(t.getKey());
            tag.put("rank", j);
            j++;
        }

        // 4.存入数据库
        collection.save(dbObject);
    }

    public static String md5(String in) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(in.getBytes("UTF-8"));

        byte byteData[] = md.digest();

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }
}
