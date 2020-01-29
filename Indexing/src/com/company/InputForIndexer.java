package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Grab url from the unique Database.
 * Grab the required content against each link to then store in MongoDB.
 */

public class InputForIndexer {
    public InputForIndexer() throws SQLException {
        Connection con = null;
        Map<String, String> map = new HashMap<>();
        String concat="";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "puneet");
            System.out.println("wmk");
            int count = 0;
            Statement stmt = con.createStatement();
            stmt.execute("select * from accepted");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String ur1 = rs.getString("url");
                //System.out.println(ur1);
                try {
                    Document document = Jsoup.connect(ur1).timeout(40000).userAgent("Mozilla").get();
                    Elements link = document.select("a[href]");
                    Elements titl = document.getElementsByTag("a");
                    Elements title = document.getElementsByTag("body");
                    Elements para = document.select("title");
//                    String description=
//                                document.select("meta[name=description]").get(0)
//                                        .attr("content");


//                    String keywords =
//                            document.select("meta[name=keywords]").first()
//                                    .attr("content");
                    Elements hTags = document.select("h1, h2, h3, h4, h5, h6");
                    Elements h1Tags = hTags.select("h1");
                    System.out.println(h1Tags.text() + " wmk");
                    String st = (para.text()+ " " + titl.text()+" "+link.tagName("title").text());
                    String n = Utils.StringUtils(st);
                    String[] arr = n.split(" ");
                    for(String nanak : arr){
                        System.out.println(ur1 + " = " + nanak);
                        try{
                            new Index(nanak, ur1);
                        }catch(Exception e){
                            if( e instanceof IndexOutOfBoundsException){
                                System.out.println("wmk2");
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch( final Exception |Error ignored){
        }
    }


    public static Map<String, String> inputToIndexer(Map<String, String> toReturn) throws IOException {
        String concat = "";
        String removeDups = "";
        String removeDups2 = "";
        Map<String, String> map2 = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for(Map.Entry m : toReturn.entrySet()){
            concat = concat + m.getKey() + " ";

        }
        concat = concat.replace(",", " ");
        concat = concat.replace(".", " ");
        concat = concat.replace("for", " ");
        concat = concat.replace("  ", " ").trim();
        concat= concat.replaceAll("[^a-zA-Z0-9\\s]", " ");

        String [] output = concat.split(" ");

        for(Map.Entry nanak: toReturn.entrySet()){
            String [] word = nanak.getKey().toString().split(" ");
            for(String w : word){
                String existing = map2.get("w");
                String extraContent = nanak.getKey().toString();
                if(!map2.containsKey(w) && w.length()<15){
                    map2.put(w, nanak.getValue().toString());
                }else {
                    map2.put(w, existing == null ? extraContent : existing + " " + extraContent);
                }
            }
        }

        for(Map.Entry nanak: map2.entrySet()){
            System.out.println("waheguru");
            System.out.println(Arrays.stream(nanak.getKey().toString().split(" ")).distinct().collect(Collectors.joining(" ")));
            System.out.println(Arrays.stream(nanak.getValue().toString().split(" ")).distinct().collect(Collectors.joining(" ")));
            System.out.println();

        }
        Map<String, String> map3 = new HashMap<>(map2);
        for(Map.Entry nanak : map2.entrySet()){
            System.out.println();
        }
        return map2;




    }


}



