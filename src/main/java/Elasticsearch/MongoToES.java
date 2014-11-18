package Elasticsearch;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * Created by shellbye on 11/18/14.
 */
public class MongoToES {
    static Client client;
    static MongoClient mongoClient;
    static DBCollection mongoCollection;
    public static void main(String[] args) throws Exception{
        setup("192.168.2.222","127.0.0.1","user","linkedin_user");
        search(new String[]{"bank"}, new String[]{"account"});
    }

    public static void search(String[] indexes, String[] types){

        SearchResponse searchResponse = client
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(QueryBuilders.termQuery("account_number", "20"))
                .execute().actionGet();
        System.out.println(searchResponse.toString());
    }

    public static void setup(String mongoServerIp, String ESServerIp, String mongoDbName, String mongoCollectionName) throws Exception{
        client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(ESServerIp, 9300));
        mongoClient = new MongoClient(mongoServerIp, 27017);
        DB db = mongoClient.getDB(mongoDbName);
        mongoCollection = db.getCollection(mongoCollectionName);
    }
}
