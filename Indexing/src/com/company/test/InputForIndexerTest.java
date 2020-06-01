package com.company.test;

import com.company.Index;
import com.company.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InputForIndexerTest {

    public InputForIndexerTest() throws SQLException {
        Connection con = null;
        Map<String, String> map = new HashMap<>();
        String concat="";
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            System.out.println("Database Connected!");
            int count = 0;
            Statement stmt = con.createStatement();
            //Reading all the reachable and unique url.
            stmt.execute("select * from accepted");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String ur1 = rs.getString("url");
                try {
                    Document document = Jsoup.connect(ur1).timeout(40000).userAgent("Mozilla").get();
                    Elements link = document.select("a[href]");
                    //System.out.println(link.text() + " \nlinks");
                    Elements titl = document.getElementsByTag("a");
                    //System.out.println(titl.text() + " \ntitl");
                    Elements title = document.getElementsByTag("body");
                    //System.out.println(title.text() + " \ntitle");
                    Elements para = document.select("title");
                    //System.out.println(para.text() + " \npara");
//                    String description=
//                                document.select("meta[name=description]")
//                                        .attr("content");


                    String keywords =
                            document.select("meta[name=keywords]").first()
                                    .attr("content");
                    //System.out.println(keywords + " \n keywords");
                    Elements hTags = document.select("h1, h2, h3, h4, h5, h6");
                    Elements h1Tags = hTags.select("h1");
                    Elements h2Tags = hTags.select("h2");
                    //System.out.println(h1Tags.text() + " wmk");
                    //String st = (link.text()+ " " + title.text() + " " + keywords);
                    String st = (keywords + " " + h1Tags.text() + " " + h2Tags.text());
                    //System.out.println("before uniuqe:----  \n" + st);
                    String n = UtilsTest.StringUtilsTest(st);
                    //System.out.println(n + " \nunique string");
                    String[] arr = n.split(" ");
                    for(String nanak : arr){
                        System.out.println(ur1 + " = " + nanak);
                        try{
                            if(nanak.length()>=3){
                                System.out.println(nanak);
                                new IndexTest(nanak, ur1, link.text());
                            }

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
