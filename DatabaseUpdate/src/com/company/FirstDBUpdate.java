package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

/**
 * This will pick single links from DB1 and find sub links and add to the second DB picked up by SecondDBUpdate class
 * THis will also show the extracted parts of the links we find by crawling(Links, para text and title text)
 */

public class FirstDBUpdate {
    public FirstDBUpdate() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "puneet");
            System.out.println("wmk");
            stmt = con.createStatement();
            String ur1;
            stmt.execute("select * from url");
            rs = stmt.getResultSet();
            while (rs.next()) {
                ur1 = rs.getString("url");
                Document document = Jsoup.connect(ur1).userAgent("Mozilla").get();
                //Elements title = document.getElementsByTag("title");
                Elements titl = document.getElementsByTag("a");
                //Elements para = document.getElementsByTag("p");
                Elements links = document.select("a[href]");
                for (Element link : links) {
                    try {
                        URL url1 = new URL(link.toString());
                    } catch (Exception e) {
                        if (e instanceof MalformedURLException) {
                        }
                    }
                    stmt = con.createStatement();
                    String query = "insert into crawler1 (name) values (?)";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, link.attr("abs:href"));
                    pstmt.executeUpdate();
                    //System.out.println("Unique table filled");
                    //not needed for this class
                    System.out.println("Link: " + link.attr("abs:href"));
                    //System.out.println("text : " + link.text());
                    //System.out.println("title : " + link.attr("title"));
                    System.out.println("title : " + titl.text());
                    //System.out.println("para : " + para.text());
                    System.out.println("para : " + link.tagName("title").text());
                    //System.out.println(ele.attr("content")); // tell us the browser
                    System.out.println(++count);

                }
            }
        } catch (final Exception | Error ignored) {
        }
    }
}
