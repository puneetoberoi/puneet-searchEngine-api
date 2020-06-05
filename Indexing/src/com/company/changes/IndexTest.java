package com.company.changes;

import com.mongodb.*;

public class IndexTest {
    MongoClientURI server = new MongoClientURI("mongodb+srv://puneet:02040204@nodeapi-etlso.mongodb.net/test?retryWrites=true&w=majority");
    MongoClient as = new MongoClient(server);
    public IndexTest(String key, String value) {
        try {

            DB database = as.getDB("javamongo");
            DBCollection collection = database.getCollection("mongo");
            BasicDBObject document = new BasicDBObject();
            document.put("body", key);
            document.put("url", value);
            DBObject query = new BasicDBObject("body", key);
            if(collection.find(query).count()!=0){
                DBCursor cursor = collection.find(query);
                BasicDBObject current = (BasicDBObject) cursor.next();
                Object name = current.get("url");
                String oldValue = name.toString();
                BasicDBObject toUpdate = new BasicDBObject();
                toUpdate.put("url", oldValue + " " + value);
                BasicDBObject update = new BasicDBObject();
                update.append("$set", toUpdate);
                collection.update(query, update );
            }else if(collection.find(query).count()==0) {
                collection.insert(document);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
