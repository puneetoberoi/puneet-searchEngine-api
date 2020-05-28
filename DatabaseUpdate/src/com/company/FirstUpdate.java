package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FirstUpdate {
    public FirstUpdate() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Set<String> uniqueURL = new HashSet<>();
        int count = 0;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            con.setAutoCommit(false);
            System.out.println("Database Connected!");
            stmt = con.createStatement();
            String ur1;
            stmt.execute("select * from url");
            rs = stmt.getResultSet();
            while (rs.next()) {
                ur1 = rs.getString("url");
                System.out.println(ur1 + " \n--------------------------");
                Document document = Jsoup.connect(ur1).userAgent("Mozilla").get();
                Elements titl = document.getElementsByTag("a");
                Elements links = document.select("a[href]");
                for (Element link : links) {

                    uniqueURL.add(link.attr("abs:href"));
//                    stmt = con.createStatement();
//                    String query = "insert into crawler (name) values (?)";
//                    PreparedStatement pstmt = con.prepareStatement(query);
//                    pstmt.setString(1, link.attr("abs:href"));
//                    int x = pstmt.executeUpdate();

                }

            }
            Iterator it = uniqueURL.iterator();
            while(it.hasNext()){
                System.out.println(it.next() + " 1111");
                stmt = con.createStatement();
                String query = "insert into crawler1 (name) values (?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, (String) it.next());
                int x = pstmt.executeUpdate();
            }
            con.commit();

        } catch (Error | Exception ignored) {
        }
    }
}
