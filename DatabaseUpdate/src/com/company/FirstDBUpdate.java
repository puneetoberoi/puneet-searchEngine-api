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
                Elements titl = document.getElementsByTag("a");
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

                }
            }
        } catch (final Exception | Error ignored) {
        }
    }
}
