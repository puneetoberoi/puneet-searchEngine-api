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
        Set<String> uniqueURL = new HashSet<>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/submitter?useSSL=false", "root", "");
            //con.setAutoCommit(false);
            System.out.println("Database Connected!");
            stmt = con.createStatement();
            String ur1;
            stmt.execute("select * from url");
            rs = stmt.getResultSet();
            while (rs.next()) {
                ur1 = rs.getString("url");
                Document document = Jsoup.connect(ur1).timeout(40000).userAgent("Mozilla").get();
                Elements titl = document.getElementsByTag("a");
                Elements links = document.select("a[href]");
                //System.out.println(links);
                for (Element link : links) {
//                    try
//                    {
//                        URL url = new URL(link.attr("abs:href"));
//                        HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
//                        httpURLConnect.setConnectTimeout(3000);
//                        httpURLConnect.connect();
//                        if(httpURLConnect.getResponseCode()==200 || httpURLConnect.getResponseCode()==200)
//                        {
//                            System.out.println(url+" - "+httpURLConnect.getResponseMessage());
//                        }
//                        if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)
//                        {
//                            System.out.println(url+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
//                        }else{
//                            System.out.println(url+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
//                        }
//                        if(httpURLConnect.getResponseCode()==405)
//                        {
//                            System.out.println("wmk");
//                            System.out.println(url+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    try {
                        URL url1 = new URL(link.attr("abs:href"));
                        HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
                        connection.setConnectTimeout(300000);
                        connection.setRequestMethod("GET");
                        connection.connect();

                        int code = connection.getResponseCode();
                        if(code == 200 || code ==201 | code ==302) {
                            System.out.println(code + " wmk");
                            System.out.println(url1);
                            uniqueURL.add(url1.toString());
                        }
                    } catch (Exception e) {
                        if (e instanceof HttpStatusException) {
                            System.out.println(("Unable to fetch url - "));

                            throw e;
                        } else {
                            System.out.println(("Unable to fetch url - "));
                        }
                    }
//                    stmt = con.createStatement();
//                    String query = "insert into crawler1 (name) values (?)";
//                    PreparedStatement pstmt = con.prepareStatement(query);
//                    pstmt.setString(1, link.attr("abs:href"));
//                    int x = pstmt.executeUpdate();

                }
                //System.out.println(uniqueURL.size() + " size");
            }

            //con.commit();
            System.out.println("set =>"+uniqueURL);
            System.out.println(uniqueURL.size() + " size");
            Iterator it = uniqueURL.iterator();
            while(it.hasNext()){
                System.out.println(it.next() + " 1111");}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
