package Elasticsearch;

import com.mongodb.*;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shellbye on 11/18/14.
 */
public class MongoToES {
    static Client client;
    static MongoClient mongoClient;
    static DBCollection mongoCollection;
    public static void main(String[] args) throws Exception{
        String dbName = "user";
        String collectionName = "linkedin_user";
        setup("192.168.2.222",dbName,collectionName, "127.0.0.1");
//        BasicDBObject fields = new BasicDBObject("skills", 1).append("address", 1).append("education", 1);
        List<DBObject> objectList = mongoCollection.find(new BasicDBObject()).limit(10).toArray();
        for(int i = 0; i < 10; i++){
            DBObject object = objectList.get(i);
            Map<String, Object> objectHashMap = new HashMap<String, Object>();
            processJsonObjectLinkedInUser(objectHashMap, object);
            IndexResponse indexResponse = client.prepareIndex(dbName, collectionName, Integer.toString(i))
                .setSource(objectHashMap)
                .execute()
                .actionGet();
            loggerInfo(indexResponse.isCreated());
        }
//        search(new String[]{"user"}, new String[]{"linkedin_user"}, "", "");
    }

    public static Map<String, Object> processJsonObjectLinkedInUser(Map<String, Object> objectHashMap, DBObject object){
        for (String key : object.keySet()) {
            if ("skills".equals(key)){
                ArrayList<String> skills = (ArrayList<String>)object.get(key);
                for (String skill : skills){
                    //eg:"skill: 技能指数1技能：Software Engineering"
                    Pattern pattern = Pattern.compile("skill: 技能指数(.*)技能：(.*)");
                    Matcher matcher = pattern.matcher(skill);
                    loggerInfo(skill);
                    while (matcher.find()) {
                        int currentSkillIndex = Integer.parseInt(matcher.group(1));
                        String currentSkill = matcher.group(2);
                    }
                }
            }
        }
        return objectHashMap;
    }

    public static Map<String, Object> processJsonObjectZhiHuUserQuestionTag(Map<String, Object> objectHashMap, DBObject object){
        for(String key : object.keySet()){
            if ("_id".equals(key)){
                continue;
            }
            if("tags_info".equals(key)){
                objectHashMap.put(key + "String", object.get(key).toString());
            }
            objectHashMap.put(key, object.get(key));
        }
        return objectHashMap;
    }

    public static void search(String[] indexes, String[] types, String searchKey, String searchValue){
        SearchResponse searchResponse = client
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(QueryBuilders.termQuery(searchKey, searchValue))
                .execute().actionGet();
        loggerInfo(searchResponse.toString());
    }

    public static void setup(String mongoServerIp, String mongoDbName, String mongoCollectionName, String ESServerIp) throws Exception{
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "shellbyelasticsearch").build();
        client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(ESServerIp, 9300));
        mongoClient = new MongoClient(mongoServerIp, 27017);
        DB db = mongoClient.getDB(mongoDbName);
        mongoCollection = db.getCollection(mongoCollectionName);
    }

    public static void loggerInfo(Object s) {
        System.out.println(s);
//        logger.info(s);
    }

    public static void loggerWarn(Object s) {
        System.out.println(s);
//        logger.warn(s);
    }

    public static void loggerError(Object s) {
        System.out.println(s);
//        logger.error(s);
    }
}
