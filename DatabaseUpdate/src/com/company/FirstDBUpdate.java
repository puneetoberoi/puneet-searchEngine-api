package com.company;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This will pick single links from DB1 and find sub links and add to the second DB picked up by SecondDBUpdate class
 * THis will also show the extracted parts of the links we find by crawling(Links, para text and title text)
 */

public class FirstDBUpdate {

    public FirstDBUpdate() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        //Set<String> uniqueURL = new HashSet<>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            // con.setAutoCommit(false);
            System.out.println("Database Connected!");
            stmt = con.createStatement();
            String ur1;
            stmt.execute("select * from url");
            rs = stmt.getResultSet();
            while (rs.next()) {
                try {
                    ur1 = rs.getString("url");
//                    String u = "replace into pool (url) values ('"+ur1+"')";
//                    stmt = con.createStatement();
//                    stmt.execute(u);
                    Document document = Jsoup.connect(ur1).userAgent("Mozilla").get();
                    Elements titl = document.getElementsByTag("a");
                    Elements links = document.select("a[href]");
                    for (Element link : links) {
                        System.out.println(link.attr("abs:href"));
                        String query = "replace into crawler (name) values(?)";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, link.attr("abs:href"));
                        pstmt.executeUpdate();
//                        try {
//                            URL url1 = new URL(link.attr("abs:href"));
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
                    }
                } catch (HttpStatusException e) {
                    System.out.println(e.getMessage() + " url exception");
                }
            }
            //System.out.println("set =>" + uniqueURL);
            //System.out.println(uniqueURL.size());
            //Iterator it = uniqueURL.iterator();
            while (it.hasNext()) {
                String url = (String) it.next();
//                stmt = con.createStatement();
//                boolean x = stmt.execute("select exists(select name from crawler where name = '"+url+"')");
//                System.out.println(x);
                //String query = "INSERT INTO crawler (name) values(?) on duplicate key update name = ?";
                String query = "replace into crawler (name) values(?)";

                //String query = "insert into crawler (name) values(?)";
                            //String query = "insert into crawler (name) values (?) where not exist (select url from url where url = '"+url+"')";
                PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, url);
                    //pstmt.setString(2, url);
                    pstmt.executeUpdate();



            }

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
