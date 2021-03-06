package com.company.changes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.*;

public class InputForIndexerTest {

    public InputForIndexerTest() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            Statement stmt = con.createStatement();
            stmt.execute("select * from accepted");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String ur1 = rs.getString("url");
                System.out.println(ur1);
                try {
                    Document document = Jsoup.connect(ur1).timeout(40000).userAgent("Mozilla").get();
                    String body = document.title().intern();
                    System.out.println(document.select("keywords").text() + " way");
                    //System.out.println(body + " body\n");
                    Elements link = document.select("a[href]");
                    Elements titl = document.getElementsByTag("a");
                    Elements title = document.getElementsByTag("body");
                    String keywords =
                            document.select("meta[name=keywords]").first()
                                    .attr("content");
                    Elements hTags = document.select("h1, h2, h3, h4, h5, h6");
                    Elements h1Tags = hTags.select("h1");
                    //System.out.println(h1Tags.text() + " \n");
                    Elements h2Tags = hTags.select("h2");
                    String st = (keywords + " " + h1Tags.text() + " " + h2Tags.text());
                    String n = UtilsTest.removeDuplicates(st);
                    String[] arr = n.split(" ");
                    for(String nanak : arr){
                        try{
                            if(nanak.length()>=3){
                                new IndexTest(nanak, ur1);
                            }

                        }catch(IndexOutOfBoundsException e){
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch( SQLException e){
            e.printStackTrace();
        }
    }
}
