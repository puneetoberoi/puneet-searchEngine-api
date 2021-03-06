package com.company;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;

/**
 * This will pick single links from DB1 and find sub links and add to the second DB picked up by SecondDBUpdate class
 * THis will also show the extracted parts of the links we find by crawling(Links, para text and title text)
 */

public class FirstDBUpdate {

    public FirstDBUpdate() {
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from url");) {
            String ur1;
            while (rs.next()) {
                try {
                    ur1 = rs.getString("url");
                    Document document = Jsoup.connect(ur1).userAgent("Mozilla").get();
                    Elements links = document.select("a[href]");
                    for (Element link : links) {
                        //for each of the test link provided by the submitter we will extract the a links present on the page by parsing with jsoup
                        //System.out.println(link.attr("abs:href"));
                        String query = "replace into crawler (url) values(?)";
                        PreparedStatement pstmt = con.prepareStatement(query);
                        pstmt.setString(1, link.attr("abs:href"));
                        pstmt.executeUpdate();
                    }
                } catch (HttpStatusException | SQLException e) {
                   e.printStackTrace();
                }
            }
            new SecondDBUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
