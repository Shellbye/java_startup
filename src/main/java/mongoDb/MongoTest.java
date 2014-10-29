package mongoDb;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

/**
 * User: shellbye.com@gmail.com
 * Date: 2014/10/29
 */
public class MongoTest {
    public static void main(String[] args){
        // http://www.tutorialspoint.com/mongodb/mongodb_aggregation.htm
        // http://www.tutorialspoint.com/mongodb/mongodb_java.htm
        try{
            // To connect to mongodb server
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            // Now connect to your databases
            DB db = mongoClient.getDB( "user" );

            DBCollection user = db.getCollection("user");
            // CRUD OF MONGO
//            create(user);
//            read(user);
//            update(user);
            delete(user);
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public static void create(DBCollection collection){
        BasicDBObject doc = new BasicDBObject("name", "MongoDB").
                append("by", "shellbye");
        collection.insert(doc);
    }

    public static void delete(DBCollection collection){
        collection.remove(collection.findOne());
    }

    public static void update(DBCollection collection){
        BasicDBObject query = new BasicDBObject();
        query.put("name", "my_name");
        DBCursor cursor = collection.find(query);
        while(cursor.hasNext()){
            DBObject updateDocument = cursor.next();
            updateDocument.put("name","JACK");
            collection.save(updateDocument);
        }
    }

    public static void read(DBCollection collection){
        DBCursor cursor = collection.find();

        int i=1;
        while (cursor.hasNext()) {
            System.out.println("Inserted Document: "+i);
            System.out.println(cursor.next());
            i++;
        }
    }
}
