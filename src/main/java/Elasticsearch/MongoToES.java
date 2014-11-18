package Elasticsearch;

import com.mongodb.*;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shellbye on 11/18/14.
 */
public class MongoToES {
    static Client client;
    static MongoClient mongoClient;
    static DBCollection mongoCollection;
    public static void main(String[] args) throws Exception{
        setup("192.168.2.222","127.0.0.1","user","linkedin_user");
        BasicDBObject fields = new BasicDBObject("skills", 1).append("address", 1).append("education", 1);
        List<DBObject> objectList = mongoCollection.find(new BasicDBObject(), fields).toArray();
        for(int i = 0; i < 10; i++){
            DBObject object = objectList.get(i);
            Map<String, Object> objectHashMap = new HashMap<String, Object>();
            for(String key : object.keySet()){
                if ("_id".equals(key)){
                    continue;
                }
                objectHashMap.put(key, object.get(key));
            }
            IndexResponse indexResponse = client.prepareIndex("user", "linkedin_user", Integer.toString(i))
                .setSource(objectHashMap)
                .execute()
                .actionGet();
            loggerInfo(indexResponse.isCreated());
        }
        search(new String[]{"bank"}, new String[]{"account"}, "account_number", "20");
    }

    public static void search(String[] indexes, String[] types, String searchKey, String searchValue){
        SearchResponse searchResponse = client
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(QueryBuilders.termQuery(searchKey, searchValue))
                .execute().actionGet();
        loggerInfo(searchResponse.toString());
    }

    public static void setup(String mongoServerIp, String ESServerIp, String mongoDbName, String mongoCollectionName) throws Exception{
        client = new TransportClient()
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
