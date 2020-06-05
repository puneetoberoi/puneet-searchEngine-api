package com.company.Test;

import com.mongodb.*;
import org.junit.jupiter.api.Test;

class InputForIndexerTestTest {
        @Test
        void checkForExisitingDocumentAndUpdateWithNewValue(){
            String key = "facebook";
            String value = "https://www.facebook.com/mail";
            MongoClientURI server = new MongoClientURI("mongodb+srv://puneet:02040204@nodeapi-etlso.mongodb.net/test?retryWrites=true&w=majority");
            MongoClient as = new MongoClient(server);
            DB database = as.getDB("javamongo");
            DBCollection collection = database.getCollection("test");
            BasicDBObject document = new BasicDBObject();
            document.put("body", key);
            document.put("url", value);
//            BasicDBObject document2 = new BasicDBObject();
//            document2.put("url", value);
//            document2.put("description", desc);
            //document.put("details", document2);
            BasicDBObject query = new BasicDBObject();
            query.put("body", key);
            if(collection.find(query).count()==0){
                collection.insert(document);
            }else{
                DBCursor cursor = collection.find(query);
                BasicDBObject current = (BasicDBObject) cursor.next();
                Object name = current.get("url");
                String oldName = name.toString();
                //Object url = details.get("url"); // old value ready
                BasicDBObject toUpdate = new BasicDBObject();
                toUpdate.put("url", oldName + " " + value);
                BasicDBObject update = new BasicDBObject();
                update.append("$set", toUpdate);
                collection.update(query, update );
            }

        }
}