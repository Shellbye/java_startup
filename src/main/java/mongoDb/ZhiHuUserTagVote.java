package mongoDb;

import com.mongodb.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shellbye on 11/4/14.
 */
public class ZhiHuUserTagVote {
    static final Logger logger = LogManager.getLogger();
    static MongoClient mongoClient;
    static DBCollection zhihuuserCollection;
    static DBCollection zhihu_questionCollection;
    static DB db;
    static List<DBObject> zhihuuserList;
    static DBCollection zhihu_user_question_tag_vote_Collection;

    public static void main(String[] args) {
        loggerWarn("Enter the main");
        try {
            init();
            int i = 0;
            for (DBObject currentZhiHuUser : zhihuuserList) {
//                for test purpose
//                if (i >= 10) {
//                    loggerWarn("Quiting...");
//                    break;
//                }
                i++;
                String name = (String) currentZhiHuUser.get("name");

                DBObject zhiHuAnswer = getAnswer(name);
                // skipped the user whose answer has no votes fields
                if (zhiHuAnswer == null || zhiHuAnswer.get("votes") == null) {
                    continue;
                }

                DBObject zhihu_user_tag_Object = getUserTagVote(name);
                if (zhihu_user_tag_Object == null) {
                    continue;
                }

                DBObject zhihuQuestions = getQuestions(zhiHuAnswer);
                DBObject zhihuQuestionsVotes = getVotes(zhiHuAnswer);
                if (zhihuQuestions == null || zhihuQuestionsVotes == null) {
                    continue;
                }

                update(zhihu_user_question_tag_vote_Collection, zhihu_user_tag_Object,
                        "zhihu_answers_id", zhiHuAnswer.get("_id"));

                processQuestionsAndVotes(zhihuQuestions, zhihuQuestionsVotes, zhihu_user_tag_Object);
            }
        } catch (Exception e) {
            loggerError(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void processQuestionTagVote(DBObject zhihu_questionObject, DBObject zhihu_user_tag_Object, int vote)
            throws Exception {
        String questionId = zhihu_questionObject.get("_id").toString();
        update(zhihu_user_question_tag_vote_Collection, zhihu_user_tag_Object,
                "zhihu_question_id", questionId);
        logger.info("Got the real question " + questionId);
        String tags = (String) zhihu_questionObject.get("tags");
        logger.info("Enter the tags-for loop and the tags is " + tags);
        for (String tag : tags.split(",")) {
            String trimmedTag = tag.trim();
            if (tagAlreadyExists(zhihu_user_tag_Object, trimmedTag)) {
                tagVoteIncrease(zhihu_user_question_tag_vote_Collection, zhihu_user_tag_Object, trimmedTag, vote);
            } else {
                addTag(zhihu_user_question_tag_vote_Collection, zhihu_user_tag_Object, trimmedTag, vote);
            }
        }
        doRank(zhihu_user_question_tag_vote_Collection, zhihu_user_tag_Object);
    }

    public static void doRank(DBCollection collection, DBObject dbObject) throws Exception {
        loggerInfo("Enter doRank and rank user " + dbObject.get("_id"));
        BasicDBObject basicDBObject = (BasicDBObject) dbObject.get("tags_info");

        // 1.创建比较对象
        Tag[] tags = new Tag[basicDBObject.size()];
        int i = 0;
        for (String key : basicDBObject.keySet()) {
            BasicDBObject tag = (BasicDBObject) basicDBObject.get(key);
            Tag new_tag = new Tag(tag.get("vote"), tag.get("rank"), key);
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

    public static void processQuestionsAndVotes(DBObject zhihuQuestions, DBObject zhihuQuestionsVotes,
                                                DBObject zhihu_user_tag_Object) throws Exception {
        for (String key : zhihuQuestions.keySet()) {
            loggerInfo("Enter the question-for loop and process question NO." + key);
            String question = (String) zhihuQuestions.get(key);
            String voteString = (String) zhihuQuestionsVotes.get(key);
            // vote 数据不一定有
            if (voteString == null) {
                continue;
            }
            int vote = getIntVoteFromString(voteString);
            String idOfQuestion = md5(question);

            BasicDBObject queryOfQuestion = new BasicDBObject();
            queryOfQuestion.put("id", idOfQuestion);
            DBObject zhihu_questionObject = zhihu_questionCollection.findOne(queryOfQuestion);

            if (zhihu_questionObject != null) {
                processQuestionTagVote(zhihu_questionObject, zhihu_user_tag_Object, vote);
            }
        }
    }

    public static int getIntVoteFromString(String vote){
        int v = 0;
        try{
            v = Integer.parseInt(vote);
        }catch (NumberFormatException n){
            if (vote.contains("K")){
                v = Integer.parseInt(vote.replace("K", "").trim());
                v = v * 1000;
            }
            if (vote.contains("W")){
                v = Integer.parseInt(vote.replace("W", "").trim());
                v = v * 10000;
            }
        }
        return v;
    }

    public static DBObject getVotes(DBObject zhiHuAnswer) {
        return (DBObject) zhiHuAnswer.get("votes");
    }

    public static DBObject getQuestions(DBObject zhiHuAnswer) {
        return (DBObject) zhiHuAnswer.get("questions");
    }

    public static DBObject getAnswer(String name) {
        DBCollection zhihu_answersCollection = db.getCollection("zhihu_answers");
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        DBObject zhiHuAnswer = zhihu_answersCollection.findOne(query);
        if (zhiHuAnswer == null) {
            return null;
        }
        return zhiHuAnswer;
    }

    public static DBObject getUserTagVote(String name) {
        BasicDBObject queryOfUserTag = new BasicDBObject();
        queryOfUserTag.put("name", name);
        DBCursor zhihu_user_question_tagCursor = zhihu_user_question_tag_vote_Collection.find(queryOfUserTag);
        if (zhihu_user_question_tagCursor.hasNext()) {
            return null;
        } else {
            return create(zhihu_user_question_tag_vote_Collection, name);
        }
    }

    public static void init() throws Exception {
        mongoClient = new MongoClient("192.168.2.222", 27017);
        db = mongoClient.getDB("user");

        zhihuuserCollection = db.getCollection("zhihuuser");
        zhihu_questionCollection = db.getCollection("zhihu_question");
        zhihu_user_question_tag_vote_Collection = db.getCollection("zhihu_user_question_tag_vote");
        BasicDBObject fields = new BasicDBObject("name", 1);
        zhihuuserList = zhihuuserCollection.find(new BasicDBObject(), fields).toArray();
    }

    public static DBObject create(DBCollection collection, String name) {
        loggerInfo("create name " + name);
        BasicDBObject basicDBObject = new BasicDBObject("name", name).append("tags_info", new BasicDBObject());
        collection.insert(basicDBObject);
        return basicDBObject;
    }

    public static void update(DBCollection collection, DBObject dbObject, String key, Object value) {
        loggerInfo("update " + dbObject.get("_id") + " key " + key + " to " + value);
        dbObject.put(key, value);
        collection.save(dbObject);
    }

    public static boolean tagAlreadyExists(DBObject dbObject, String tag) {
        BasicDBObject tagsInfo = (BasicDBObject) dbObject.get("tags_info");
        return tagsInfo.get(tag) != null;
    }

    public static void tagVoteIncrease(DBCollection collection, DBObject dbObject, String tag, int vote) {
        loggerInfo("tag " + tag + " increase by " + vote);
        BasicDBObject tagsInfo = (BasicDBObject) dbObject.get("tags_info");
        BasicDBObject t = (BasicDBObject) tagsInfo.get(tag);
        t.put("vote", (int) t.get("vote") + vote);
        collection.save(dbObject);
    }

    public static void addTag(DBCollection collection, DBObject dbObject, String tag, int vote) throws Exception {
        loggerInfo("add tag " + tag);
        BasicDBObject tagsInfo = (BasicDBObject) dbObject.get("tags_info");
        if (tag.contains(".")) {
            tagsInfo.put(tag.replace(".", "\\u002e"), new BasicDBObject("vote", vote).append("rank", -1));
        } else {
            tagsInfo.put(tag, new BasicDBObject("vote", vote).append("rank", -1));
        }
        collection.save(dbObject);
    }

    public static void loggerInfo(String s) {
        System.out.println(s);
//        logger.info(s);
    }

    public static void loggerWarn(String s) {
        System.out.println(s);
//        logger.warn(s);
    }

    public static void loggerError(String s) {
        System.out.println(s);
//        logger.error(s);
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
