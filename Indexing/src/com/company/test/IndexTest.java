package com.company.test;

import com.mongodb.*;

public class IndexTest {
    MongoClientURI server = new MongoClientURI("mongodb+srv://puneet:02040204@nodeapi-etlso.mongodb.net/test?retryWrites=true&w=majority");
    MongoClient as = new MongoClient(server);
    public IndexTest(String key, String value, String link) {
        try {

            DB database = as.getDB("javamongo");
            DBCollection collection = database.getCollection("mongo");
            BasicDBObject document = new BasicDBObject();
            document.put("body", key);
            BasicDBObject document2 = new BasicDBObject();
            document2.put("url", value);
            document2.put("description", link);
            document.put("details", document2);
            DBObject query = new BasicDBObject("body", key);

            if (collection.find(query).count() == 0) {
                collection.insert(document);
            } else {
                try {
                    DBCursor cursor = collection.find(document);
                    DBObject current = cursor.next();
                    Object name = current.get("details.url");
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

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
