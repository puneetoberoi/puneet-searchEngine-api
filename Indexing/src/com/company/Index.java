package com.company;

import com.mongodb.*;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Index {
    public Index(String key, String value, String a) throws IOException {
        try {
            MongoClientURI server = new MongoClientURI("mongodb://localhost:27017");
            MongoClient as = new MongoClient(server);
            //MongoDatabase dbs = as.getDatabase
            DB db = as.getDB("QueryProcessor");
            DBCollection collection = db.getCollection("new");
            //preparing the doc to insert
            BasicDBObject doc1 = new BasicDBObject("body", key).append("url", value).append("description", a);
            //object to check if the key already exist, where we will just append.
            DBObject query = new BasicDBObject("body", key);
            //if no key yet insert else append

            if (collection.find(query).count() == 0) {
                collection.insert(doc1);
            } else {
                try {
                    DBCursor cursor = collection.find(doc1);
                    DBObject current = cursor.next();
                    Object name = current.get("url");
//                String updateName = (name != null ? name.toString() : "") + " " + key;
                    String updateName = (name.toString() + " " + value);
                    System.out.println(updateName);
                    //System.out.println("12341423523525");
                    BasicDBObject updateQuery = new BasicDBObject();
                    updateQuery.append("$set", new BasicDBObject("url", updateName));
                    final BasicDBObject searchQuery = new BasicDBObject();
                    searchQuery.put("_id", current.get("_id") + " ");

                    collection.update(searchQuery, updateQuery);
                    System.out.println(updateName);

                } catch (final Exception | Error ignored) {
                    System.out.println("hello");
                }

            }
        }catch (final Exception | Error ignored) {
            System.out.println("hello");
        }
    }

}
