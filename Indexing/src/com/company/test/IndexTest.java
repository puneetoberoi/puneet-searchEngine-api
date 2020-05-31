package com.company.test;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;

public class IndexTest {

    public IndexTest(String key, String value) {
        MongoClientURI server = new MongoClientURI("mongodb+srv://puneet:02040204@nodeapi-etlso.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient as = new MongoClient(server);
        try {
            DB database = as.getDB("javamongo");
            DBCollection collection = database.getCollection("mongo");
//            MongoClientURI server = new MongoClientURI("mongodb://localhost:27017");
            //MongoClientURI server = new MongoClientURI("mongodb+srv://puneet:02040204@nanak-56iwq.mongodb.net/test?retryWrites=true&w=majority");

//            MongoClient mongoClient = (MongoClient) MongoClients.create(
//                    "mongodb+srv://puneet:<password>@nodeapi-etlso.mongodb.net/test?retryWrites=true&w=majority");
//            MongoDatabase database = mongoClient.getDatabase("test");

            //MongoClient as = new MongoClient(server);
            //MongoDatabase dbs = as.getDatabase
            //DB db = as.getDB("QueryProcessor");
            //DBCollection collection = db.getCollection("new");
            //DBCollection collection = (DBCollection) database.getCollection("new");
            //preparing the doc to insert
            BasicDBObject doc1 = new BasicDBObject("body", key).append("url", value);
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
                    System.out.println("hello1");
                }

            }
        }catch (final Exception | Error ignored) {
            System.out.println("hello2");
        }
    }
}
